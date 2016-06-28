package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.AppearancesType;
import ch.slrg.mvp.repository.AppearancesTypeRepository;

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
 * Test class for the AppearancesTypeResource REST controller.
 *
 * @see AppearancesTypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class AppearancesTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private AppearancesTypeRepository appearancesTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAppearancesTypeMockMvc;

    private AppearancesType appearancesType;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppearancesTypeResource appearancesTypeResource = new AppearancesTypeResource();
        ReflectionTestUtils.setField(appearancesTypeResource, "appearancesTypeRepository", appearancesTypeRepository);
        this.restAppearancesTypeMockMvc = MockMvcBuilders.standaloneSetup(appearancesTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        appearancesType = new AppearancesType();
        appearancesType.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAppearancesType() throws Exception {
        int databaseSizeBeforeCreate = appearancesTypeRepository.findAll().size();

        // Create the AppearancesType

        restAppearancesTypeMockMvc.perform(post("/api/appearances-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appearancesType)))
                .andExpect(status().isCreated());

        // Validate the AppearancesType in the database
        List<AppearancesType> appearancesTypes = appearancesTypeRepository.findAll();
        assertThat(appearancesTypes).hasSize(databaseSizeBeforeCreate + 1);
        AppearancesType testAppearancesType = appearancesTypes.get(appearancesTypes.size() - 1);
        assertThat(testAppearancesType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllAppearancesTypes() throws Exception {
        // Initialize the database
        appearancesTypeRepository.saveAndFlush(appearancesType);

        // Get all the appearancesTypes
        restAppearancesTypeMockMvc.perform(get("/api/appearances-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(appearancesType.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAppearancesType() throws Exception {
        // Initialize the database
        appearancesTypeRepository.saveAndFlush(appearancesType);

        // Get the appearancesType
        restAppearancesTypeMockMvc.perform(get("/api/appearances-types/{id}", appearancesType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(appearancesType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppearancesType() throws Exception {
        // Get the appearancesType
        restAppearancesTypeMockMvc.perform(get("/api/appearances-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppearancesType() throws Exception {
        // Initialize the database
        appearancesTypeRepository.saveAndFlush(appearancesType);
        int databaseSizeBeforeUpdate = appearancesTypeRepository.findAll().size();

        // Update the appearancesType
        AppearancesType updatedAppearancesType = new AppearancesType();
        updatedAppearancesType.setId(appearancesType.getId());
        updatedAppearancesType.setName(UPDATED_NAME);

        restAppearancesTypeMockMvc.perform(put("/api/appearances-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAppearancesType)))
                .andExpect(status().isOk());

        // Validate the AppearancesType in the database
        List<AppearancesType> appearancesTypes = appearancesTypeRepository.findAll();
        assertThat(appearancesTypes).hasSize(databaseSizeBeforeUpdate);
        AppearancesType testAppearancesType = appearancesTypes.get(appearancesTypes.size() - 1);
        assertThat(testAppearancesType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteAppearancesType() throws Exception {
        // Initialize the database
        appearancesTypeRepository.saveAndFlush(appearancesType);
        int databaseSizeBeforeDelete = appearancesTypeRepository.findAll().size();

        // Get the appearancesType
        restAppearancesTypeMockMvc.perform(delete("/api/appearances-types/{id}", appearancesType.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AppearancesType> appearancesTypes = appearancesTypeRepository.findAll();
        assertThat(appearancesTypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
