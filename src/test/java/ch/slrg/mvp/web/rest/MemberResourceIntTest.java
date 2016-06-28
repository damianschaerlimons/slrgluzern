package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.SlrgApp;
import ch.slrg.mvp.domain.Member;
import ch.slrg.mvp.repository.MemberRepository;
import ch.slrg.mvp.service.MemberService;
import ch.slrg.mvp.web.rest.dto.MemberDTO;
import ch.slrg.mvp.web.rest.mapper.MemberMapper;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SlrgApp.class)
@WebAppConfiguration
@IntegrationTest
public class MemberResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LASTNAME = "AAAAA";
    private static final String UPDATED_LASTNAME = "BBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_BREVETNR = new BigDecimal(1);
    private static final BigDecimal UPDATED_BREVETNR = new BigDecimal(2);
    private static final String DEFAULT_ADRESS = "AAAAA";
    private static final String UPDATED_ADRESS = "BBBBB";

    private static final Integer DEFAULT_PLZ = 1;
    private static final Integer UPDATED_PLZ = 2;
    private static final String DEFAULT_PLACE = "AAAAA";
    private static final String UPDATED_PLACE = "BBBBB";

    private static final Boolean DEFAULT_AQUATEAM = false;
    private static final Boolean UPDATED_AQUATEAM = true;

    private static final Boolean DEFAULT_SKIPPER = false;
    private static final Boolean UPDATED_SKIPPER = true;

    private static final Boolean DEFAULT_BOATDRIVER = false;
    private static final Boolean UPDATED_BOATDRIVER = true;

    private static final Boolean DEFAULT_RESCUE = false;
    private static final Boolean UPDATED_RESCUE = true;
    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private MemberMapper memberMapper;

    @Inject
    private MemberService memberService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMemberMockMvc;

    private Member member;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemberResource memberResource = new MemberResource();
        ReflectionTestUtils.setField(memberResource, "memberService", memberService);
        ReflectionTestUtils.setField(memberResource, "memberMapper", memberMapper);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        member = new Member();
        member.setName(DEFAULT_NAME);
        member.setLastname(DEFAULT_LASTNAME);
        member.setBirthday(DEFAULT_BIRTHDAY);
        member.setBrevetnr(DEFAULT_BREVETNR);
        member.setAdress(DEFAULT_ADRESS);
        member.setPlz(DEFAULT_PLZ);
        member.setPlace(DEFAULT_PLACE);
        member.setAquateam(DEFAULT_AQUATEAM);
        member.setSkipper(DEFAULT_SKIPPER);
        member.setBoatdriver(DEFAULT_BOATDRIVER);
        member.setRescue(DEFAULT_RESCUE);
        member.setPhone(DEFAULT_PHONE);
        member.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(member);

        restMemberMockMvc.perform(post("/api/members")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
                .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = members.get(members.size() - 1);
        assertThat(testMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMember.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testMember.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testMember.getBrevetnr()).isEqualTo(DEFAULT_BREVETNR);
        assertThat(testMember.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testMember.getPlz()).isEqualTo(DEFAULT_PLZ);
        assertThat(testMember.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testMember.isAquateam()).isEqualTo(DEFAULT_AQUATEAM);
        assertThat(testMember.isSkipper()).isEqualTo(DEFAULT_SKIPPER);
        assertThat(testMember.isBoatdriver()).isEqualTo(DEFAULT_BOATDRIVER);
        assertThat(testMember.isRescue()).isEqualTo(DEFAULT_RESCUE);
        assertThat(testMember.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the members
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
                .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
                .andExpect(jsonPath("$.[*].brevetnr").value(hasItem(DEFAULT_BREVETNR.intValue())))
                .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS.toString())))
                .andExpect(jsonPath("$.[*].plz").value(hasItem(DEFAULT_PLZ)))
                .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
                .andExpect(jsonPath("$.[*].aquateam").value(hasItem(DEFAULT_AQUATEAM.booleanValue())))
                .andExpect(jsonPath("$.[*].skipper").value(hasItem(DEFAULT_SKIPPER.booleanValue())))
                .andExpect(jsonPath("$.[*].boatdriver").value(hasItem(DEFAULT_BOATDRIVER.booleanValue())))
                .andExpect(jsonPath("$.[*].rescue").value(hasItem(DEFAULT_RESCUE.booleanValue())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.brevetnr").value(DEFAULT_BREVETNR.intValue()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS.toString()))
            .andExpect(jsonPath("$.plz").value(DEFAULT_PLZ))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.aquateam").value(DEFAULT_AQUATEAM.booleanValue()))
            .andExpect(jsonPath("$.skipper").value(DEFAULT_SKIPPER.booleanValue()))
            .andExpect(jsonPath("$.boatdriver").value(DEFAULT_BOATDRIVER.booleanValue()))
            .andExpect(jsonPath("$.rescue").value(DEFAULT_RESCUE.booleanValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = new Member();
        updatedMember.setId(member.getId());
        updatedMember.setName(UPDATED_NAME);
        updatedMember.setLastname(UPDATED_LASTNAME);
        updatedMember.setBirthday(UPDATED_BIRTHDAY);
        updatedMember.setBrevetnr(UPDATED_BREVETNR);
        updatedMember.setAdress(UPDATED_ADRESS);
        updatedMember.setPlz(UPDATED_PLZ);
        updatedMember.setPlace(UPDATED_PLACE);
        updatedMember.setAquateam(UPDATED_AQUATEAM);
        updatedMember.setSkipper(UPDATED_SKIPPER);
        updatedMember.setBoatdriver(UPDATED_BOATDRIVER);
        updatedMember.setRescue(UPDATED_RESCUE);
        updatedMember.setPhone(UPDATED_PHONE);
        updatedMember.setEmail(UPDATED_EMAIL);
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
                .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeUpdate);
        Member testMember = members.get(members.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testMember.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testMember.getBrevetnr()).isEqualTo(UPDATED_BREVETNR);
        assertThat(testMember.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testMember.getPlz()).isEqualTo(UPDATED_PLZ);
        assertThat(testMember.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testMember.isAquateam()).isEqualTo(UPDATED_AQUATEAM);
        assertThat(testMember.isSkipper()).isEqualTo(UPDATED_SKIPPER);
        assertThat(testMember.isBoatdriver()).isEqualTo(UPDATED_BOATDRIVER);
        assertThat(testMember.isRescue()).isEqualTo(UPDATED_RESCUE);
        assertThat(testMember.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeDelete - 1);
    }
}
