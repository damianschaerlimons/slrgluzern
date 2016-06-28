package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.Membertype;
import ch.slrg.mvp.repository.MembertypeRepository;

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
 * Test class for the MembertypeResource REST controller.
 *
 * @see MembertypeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class MembertypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private MembertypeRepository membertypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMembertypeMockMvc;

    private Membertype membertype;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MembertypeResource membertypeResource = new MembertypeResource();
        ReflectionTestUtils.setField(membertypeResource, "membertypeRepository", membertypeRepository);
        this.restMembertypeMockMvc = MockMvcBuilders.standaloneSetup(membertypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        membertype = new Membertype();
        membertype.setName(DEFAULT_NAME);
        membertype.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMembertype() throws Exception {
        int databaseSizeBeforeCreate = membertypeRepository.findAll().size();

        // Create the Membertype

        restMembertypeMockMvc.perform(post("/api/membertypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(membertype)))
                .andExpect(status().isCreated());

        // Validate the Membertype in the database
        List<Membertype> membertypes = membertypeRepository.findAll();
        assertThat(membertypes).hasSize(databaseSizeBeforeCreate + 1);
        Membertype testMembertype = membertypes.get(membertypes.size() - 1);
        assertThat(testMembertype.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMembertype.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMembertypes() throws Exception {
        // Initialize the database
        membertypeRepository.saveAndFlush(membertype);

        // Get all the membertypes
        restMembertypeMockMvc.perform(get("/api/membertypes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(membertype.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMembertype() throws Exception {
        // Initialize the database
        membertypeRepository.saveAndFlush(membertype);

        // Get the membertype
        restMembertypeMockMvc.perform(get("/api/membertypes/{id}", membertype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(membertype.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembertype() throws Exception {
        // Get the membertype
        restMembertypeMockMvc.perform(get("/api/membertypes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembertype() throws Exception {
        // Initialize the database
        membertypeRepository.saveAndFlush(membertype);
        int databaseSizeBeforeUpdate = membertypeRepository.findAll().size();

        // Update the membertype
        Membertype updatedMembertype = new Membertype();
        updatedMembertype.setId(membertype.getId());
        updatedMembertype.setName(UPDATED_NAME);
        updatedMembertype.setDescription(UPDATED_DESCRIPTION);

        restMembertypeMockMvc.perform(put("/api/membertypes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMembertype)))
                .andExpect(status().isOk());

        // Validate the Membertype in the database
        List<Membertype> membertypes = membertypeRepository.findAll();
        assertThat(membertypes).hasSize(databaseSizeBeforeUpdate);
        Membertype testMembertype = membertypes.get(membertypes.size() - 1);
        assertThat(testMembertype.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMembertype.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteMembertype() throws Exception {
        // Initialize the database
        membertypeRepository.saveAndFlush(membertype);
        int databaseSizeBeforeDelete = membertypeRepository.findAll().size();

        // Get the membertype
        restMembertypeMockMvc.perform(delete("/api/membertypes/{id}", membertype.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Membertype> membertypes = membertypeRepository.findAll();
        assertThat(membertypes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
