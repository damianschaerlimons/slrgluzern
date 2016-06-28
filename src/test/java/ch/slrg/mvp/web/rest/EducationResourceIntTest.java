package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.Education;
import ch.slrg.mvp.repository.EducationRepository;
import ch.slrg.mvp.service.EducationService;
import ch.slrg.mvp.web.rest.dto.EducationDTO;
import ch.slrg.mvp.web.rest.mapper.EducationMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EducationResource REST controller.
 *
 * @see EducationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class EducationResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    private static final LocalDate DEFAULT_VALID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private EducationRepository educationRepository;

    @Inject
    private EducationMapper educationMapper;

    @Inject
    private EducationService educationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEducationMockMvc;

    private Education education;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationResource educationResource = new EducationResource();
        ReflectionTestUtils.setField(educationResource, "educationService", educationService);
        ReflectionTestUtils.setField(educationResource, "educationMapper", educationMapper);
        this.restEducationMockMvc = MockMvcBuilders.standaloneSetup(educationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        education = new Education();
        education.setNote(DEFAULT_NOTE);
        education.setValid(DEFAULT_VALID);
    }

    @Test
    @Transactional
    public void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // Create the Education
        EducationDTO educationDTO = educationMapper.educationToEducationDTO(education);

        restEducationMockMvc.perform(post("/api/educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
                .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educations = educationRepository.findAll();
        assertThat(educations).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educations.get(educations.size() - 1);
        assertThat(testEducation.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEducation.getValid()).isEqualTo(DEFAULT_VALID);
    }

    @Test
    @Transactional
    public void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educations
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
                .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.toString())));
    }

    @Test
    @Transactional
    public void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = new Education();
        updatedEducation.setId(education.getId());
        updatedEducation.setNote(UPDATED_NOTE);
        updatedEducation.setValid(UPDATED_VALID);
        EducationDTO educationDTO = educationMapper.educationToEducationDTO(updatedEducation);

        restEducationMockMvc.perform(put("/api/educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationDTO)))
                .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educations = educationRepository.findAll();
        assertThat(educations).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educations.get(educations.size() - 1);
        assertThat(testEducation.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEducation.getValid()).isEqualTo(UPDATED_VALID);
    }

    @Test
    @Transactional
    public void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);
        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Get the education
        restEducationMockMvc.perform(delete("/api/educations/{id}", education.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Education> educations = educationRepository.findAll();
        assertThat(educations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
