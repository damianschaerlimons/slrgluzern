package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.FurtherEducation;
import ch.slrg.mvp.repository.FurtherEducationRepository;

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
 * Test class for the FurtherEducationResource REST controller.
 *
 * @see FurtherEducationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class FurtherEducationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_NIVEAU = "AAAAA";
    private static final String UPDATED_NIVEAU = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private FurtherEducationRepository furtherEducationRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFurtherEducationMockMvc;

    private FurtherEducation furtherEducation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FurtherEducationResource furtherEducationResource = new FurtherEducationResource();
        ReflectionTestUtils.setField(furtherEducationResource, "furtherEducationRepository", furtherEducationRepository);
        this.restFurtherEducationMockMvc = MockMvcBuilders.standaloneSetup(furtherEducationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        furtherEducation = new FurtherEducation();
        furtherEducation.setName(DEFAULT_NAME);
        furtherEducation.setDescription(DEFAULT_DESCRIPTION);
        furtherEducation.setNiveau(DEFAULT_NIVEAU);
        furtherEducation.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFurtherEducation() throws Exception {
        int databaseSizeBeforeCreate = furtherEducationRepository.findAll().size();

        // Create the FurtherEducation

        restFurtherEducationMockMvc.perform(post("/api/further-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(furtherEducation)))
                .andExpect(status().isCreated());

        // Validate the FurtherEducation in the database
        List<FurtherEducation> furtherEducations = furtherEducationRepository.findAll();
        assertThat(furtherEducations).hasSize(databaseSizeBeforeCreate + 1);
        FurtherEducation testFurtherEducation = furtherEducations.get(furtherEducations.size() - 1);
        assertThat(testFurtherEducation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFurtherEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFurtherEducation.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
        assertThat(testFurtherEducation.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void getAllFurtherEducations() throws Exception {
        // Initialize the database
        furtherEducationRepository.saveAndFlush(furtherEducation);

        // Get all the furtherEducations
        restFurtherEducationMockMvc.perform(get("/api/further-educations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(furtherEducation.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getFurtherEducation() throws Exception {
        // Initialize the database
        furtherEducationRepository.saveAndFlush(furtherEducation);

        // Get the furtherEducation
        restFurtherEducationMockMvc.perform(get("/api/further-educations/{id}", furtherEducation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(furtherEducation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFurtherEducation() throws Exception {
        // Get the furtherEducation
        restFurtherEducationMockMvc.perform(get("/api/further-educations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFurtherEducation() throws Exception {
        // Initialize the database
        furtherEducationRepository.saveAndFlush(furtherEducation);
        int databaseSizeBeforeUpdate = furtherEducationRepository.findAll().size();

        // Update the furtherEducation
        FurtherEducation updatedFurtherEducation = new FurtherEducation();
        updatedFurtherEducation.setId(furtherEducation.getId());
        updatedFurtherEducation.setName(UPDATED_NAME);
        updatedFurtherEducation.setDescription(UPDATED_DESCRIPTION);
        updatedFurtherEducation.setNiveau(UPDATED_NIVEAU);
        updatedFurtherEducation.setDate(UPDATED_DATE);

        restFurtherEducationMockMvc.perform(put("/api/further-educations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFurtherEducation)))
                .andExpect(status().isOk());

        // Validate the FurtherEducation in the database
        List<FurtherEducation> furtherEducations = furtherEducationRepository.findAll();
        assertThat(furtherEducations).hasSize(databaseSizeBeforeUpdate);
        FurtherEducation testFurtherEducation = furtherEducations.get(furtherEducations.size() - 1);
        assertThat(testFurtherEducation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFurtherEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFurtherEducation.getNiveau()).isEqualTo(UPDATED_NIVEAU);
        assertThat(testFurtherEducation.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteFurtherEducation() throws Exception {
        // Initialize the database
        furtherEducationRepository.saveAndFlush(furtherEducation);
        int databaseSizeBeforeDelete = furtherEducationRepository.findAll().size();

        // Get the furtherEducation
        restFurtherEducationMockMvc.perform(delete("/api/further-educations/{id}", furtherEducation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<FurtherEducation> furtherEducations = furtherEducationRepository.findAll();
        assertThat(furtherEducations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
