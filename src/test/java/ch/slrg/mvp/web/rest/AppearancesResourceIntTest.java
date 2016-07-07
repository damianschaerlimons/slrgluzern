package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.Appearances;
import ch.slrg.mvp.repository.AppearancesRepository;

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
 * Test class for the AppearancesResource REST controller.
 *
 * @see AppearancesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class AppearancesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final LocalDate DEFAULT_VALID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_HOURS = 1;
    private static final Integer UPDATED_HOURS = 2;

    @Inject
    private AppearancesRepository appearancesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAppearancesMockMvc;

    private Appearances appearances;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppearancesResource appearancesResource = new AppearancesResource();
        ReflectionTestUtils.setField(appearancesResource, "appearancesRepository", appearancesRepository);
        this.restAppearancesMockMvc = MockMvcBuilders.standaloneSetup(appearancesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        appearances = new Appearances();
        appearances.setName(DEFAULT_NAME);
        appearances.setValid(DEFAULT_VALID);
        appearances.setHours(DEFAULT_HOURS);
    }

    @Test
    @Transactional
    public void createAppearances() throws Exception {
        int databaseSizeBeforeCreate = appearancesRepository.findAll().size();

        // Create the Appearances

        restAppearancesMockMvc.perform(post("/api/appearances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(appearances)))
                .andExpect(status().isCreated());

        // Validate the Appearances in the database
        List<Appearances> appearances = appearancesRepository.findAll();
        assertThat(appearances).hasSize(databaseSizeBeforeCreate + 1);
        Appearances testAppearances = appearances.get(appearances.size() - 1);
        assertThat(testAppearances.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppearances.getValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testAppearances.getHours()).isEqualTo(DEFAULT_HOURS);
    }

    @Test
    @Transactional
    public void getAllAppearances() throws Exception {
        // Initialize the database
        appearancesRepository.saveAndFlush(appearances);

        // Get all the appearances
        restAppearancesMockMvc.perform(get("/api/appearances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(appearances.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.toString())))
                .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS)));
    }

    @Test
    @Transactional
    public void getAppearances() throws Exception {
        // Initialize the database
        appearancesRepository.saveAndFlush(appearances);

        // Get the appearances
        restAppearancesMockMvc.perform(get("/api/appearances/{id}", appearances.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(appearances.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.toString()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS));
    }

    @Test
    @Transactional
    public void getNonExistingAppearances() throws Exception {
        // Get the appearances
        restAppearancesMockMvc.perform(get("/api/appearances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppearances() throws Exception {
        // Initialize the database
        appearancesRepository.saveAndFlush(appearances);
        int databaseSizeBeforeUpdate = appearancesRepository.findAll().size();

        // Update the appearances
        Appearances updatedAppearances = new Appearances();
        updatedAppearances.setId(appearances.getId());
        updatedAppearances.setName(UPDATED_NAME);
        updatedAppearances.setValid(UPDATED_VALID);
        updatedAppearances.setHours(UPDATED_HOURS);

        restAppearancesMockMvc.perform(put("/api/appearances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAppearances)))
                .andExpect(status().isOk());

        // Validate the Appearances in the database
        List<Appearances> appearances = appearancesRepository.findAll();
        assertThat(appearances).hasSize(databaseSizeBeforeUpdate);
        Appearances testAppearances = appearances.get(appearances.size() - 1);
        assertThat(testAppearances.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppearances.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testAppearances.getHours()).isEqualTo(UPDATED_HOURS);
    }

    @Test
    @Transactional
    public void deleteAppearances() throws Exception {
        // Initialize the database
        appearancesRepository.saveAndFlush(appearances);
        int databaseSizeBeforeDelete = appearancesRepository.findAll().size();

        // Get the appearances
        restAppearancesMockMvc.perform(delete("/api/appearances/{id}", appearances.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Appearances> appearances = appearancesRepository.findAll();
        assertThat(appearances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
