package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.Educationtype;
import ch.slrg.mvp.repository.EducationtypeRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EducationtypeResource REST controller.
 *
 * @see EducationtypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class EducationtypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private EducationtypeRepository educationtypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEducationtypeMockMvc;

    private Educationtype educationtype;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EducationtypeResource educationtypeResource = new EducationtypeResource();
        ReflectionTestUtils.setField(educationtypeResource, "educationtypeRepository", educationtypeRepository);
        this.restEducationtypeMockMvc = MockMvcBuilders.standaloneSetup(educationtypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        educationtype = new Educationtype();
        educationtype.setName(DEFAULT_NAME);
        educationtype.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEducationtype() throws Exception {
        int databaseSizeBeforeCreate = educationtypeRepository.findAll().size();

        // Create the Educationtype

        restEducationtypeMockMvc.perform(post("/api/educationtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(educationtype)))
                .andExpect(status().isCreated());

        // Validate the Educationtype in the database
        List<Educationtype> educationtypes = educationtypeRepository.findAll();
        assertThat(educationtypes).hasSize(databaseSizeBeforeCreate + 1);
        Educationtype testEducationtype = educationtypes.get(educationtypes.size() - 1);
        assertThat(testEducationtype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEducationtype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEducationtypes() throws Exception {
        // Initialize the database
        educationtypeRepository.saveAndFlush(educationtype);

        // Get all the educationtypes
        restEducationtypeMockMvc.perform(get("/api/educationtypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(educationtype.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEducationtype() throws Exception {
        // Initialize the database
        educationtypeRepository.saveAndFlush(educationtype);

        // Get the educationtype
        restEducationtypeMockMvc.perform(get("/api/educationtypes/{id}", educationtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(educationtype.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducationtype() throws Exception {
        // Get the educationtype
        restEducationtypeMockMvc.perform(get("/api/educationtypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationtype() throws Exception {
        // Initialize the database
        educationtypeRepository.saveAndFlush(educationtype);
        int databaseSizeBeforeUpdate = educationtypeRepository.findAll().size();

        // Update the educationtype
        Educationtype updatedEducationtype = new Educationtype();
        updatedEducationtype.setId(educationtype.getId());
        updatedEducationtype.setName(UPDATED_NAME);
        updatedEducationtype.setDescription(UPDATED_DESCRIPTION);

        restEducationtypeMockMvc.perform(put("/api/educationtypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEducationtype)))
                .andExpect(status().isOk());

        // Validate the Educationtype in the database
        List<Educationtype> educationtypes = educationtypeRepository.findAll();
        assertThat(educationtypes).hasSize(databaseSizeBeforeUpdate);
        Educationtype testEducationtype = educationtypes.get(educationtypes.size() - 1);
        assertThat(testEducationtype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEducationtype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteEducationtype() throws Exception {
        // Initialize the database
        educationtypeRepository.saveAndFlush(educationtype);
        int databaseSizeBeforeDelete = educationtypeRepository.findAll().size();

        // Get the educationtype
        restEducationtypeMockMvc.perform(delete("/api/educationtypes/{id}", educationtype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Educationtype> educationtypes = educationtypeRepository.findAll();
        assertThat(educationtypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
