package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.repository.AppearancesRepository;
import ch.slrg.mvp.repository.AssessmentRepository;
import ch.slrg.mvp.repository.EducationRepository;
import ch.slrg.mvp.repository.FurtherEducationRepository;
import ch.slrg.mvp.service.MemberService;
import ch.slrg.mvp.web.rest.dto.MemberDTO;
import ch.slrg.mvp.web.rest.mapper.MemberMapper;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Member.
 */
@RestController
@RequestMapping("/api")
public class MemberResource {

    private final Logger log = LoggerFactory.getLogger(MemberResource.class);

    @Inject
    private MemberService memberService;

    @Inject
    private MemberMapper memberMapper;

    @Inject
    private EducationRepository educationRepository;

    @Inject
    private AppearancesRepository appearancesRepository;

    @Inject
    private FurtherEducationRepository furtherEducationRepository;

    @Inject
    private AssessmentRepository assessmentRepository;



    /**
     * POST  /members : Create a new member.
     *
     * @param memberDTO the memberDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memberDTO, or with status 400 (Bad Request) if the member has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/members",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO) throws URISyntaxException {
        log.debug("REST request to save Member : {}", memberDTO);
        if (memberDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("member", "idexists", "A new member cannot already have an ID")).body(null);
        }
        MemberDTO result = memberService.save(memberDTO);
        return ResponseEntity.created(new URI("/api/members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("member", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /members : Updates an existing member.
     *
     * @param memberDTO the memberDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memberDTO,
     * or with status 400 (Bad Request) if the memberDTO is not valid,
     * or with status 500 (Internal Server Error) if the memberDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/members",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberDTO memberDTO) throws URISyntaxException {
        log.debug("REST request to update Member : {}", memberDTO);
        if (memberDTO.getId() == null) {
            return createMember(memberDTO);
        }
        MemberDTO result = memberService.save(memberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("member", memberDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /members : get all the members.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of members in body
     */
    @RequestMapping(value = "/members",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MemberDTO> getAllMembers() {
        log.debug("REST request to get all Members");
        return memberService.findAll();
    }

    /**
     * GET  /members/:id : get the "id" member.
     *
     * @param id the id of the memberDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memberDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/members/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long id) {
        log.debug("REST request to get Member : {}", id);
        MemberDTO memberDTO = memberService.findOne(id);
        return Optional.ofNullable(memberDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /members/:id : delete the "id" member.
     *
     * @param id the id of the memberDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/members/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        log.debug("REST request to delete Member : {}", id);
        memberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("member", id.toString())).build();
    }



    /**
     * GET  /members/:id/education : delete the "id" member.
     *
     * @param id the id of the Member to load the Educations from
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/members/{id}/educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Education>> getListEducation(@PathVariable Long id) {
        log.debug("REST request to find Member Education : {}", id);
        List<Education> dtos = educationRepository.findEducationByMemberId(id);
        return Optional.ofNullable(dtos)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /members/:id/appearances :  get all Appearances by one member.
     *
     * @param id the id of the Member to load the Educations from
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/members/{id}/appearances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Appearances>> getListAppearances(@PathVariable Long id) {
        log.debug("REST request to find Member Appearances : {}", id);
        List<Appearances> dtos = appearancesRepository.findAppearancesByMemberId(id);
        return Optional.ofNullable(dtos)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /members/:id/furtheredu :  get all further Educations by one member.
     *
     * @param id the id of the Member to load the Educations from
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/members/{id}/furtheredu",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FurtherEducation>> getListFurtherEdu(@PathVariable Long id) {
        log.debug("REST request to find Member Appearances : {}", id);
        List<FurtherEducation> dtos = furtherEducationRepository.findFurtherEducationByMemberId(id);
        return Optional.ofNullable(dtos)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /members/:id/assessments :  get all further Educations by one member.
     *
     * @param id the id of the Member to load the Educations from
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/members/{id}/assessments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Assessment>> getAssessments(@PathVariable Long id) {
        log.debug("REST request to find Member Assessments : {}", id);
        List<Assessment> dtos = assessmentRepository.findAssessmentByMemberId(id);
        return Optional.ofNullable(dtos)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    /**
////     * GET  /members/search :  get all further Educations by one member.
////     *
////     * @return the ResponseEntity with status 200 (OK)
////     */
//    @RequestMapping(value = "/members/search",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<List<MemberDTO>> searchMembers(@RequestParam(value="aquateam") boolean aquateam, @RequestParam(value="skipper") boolean skipper, @RequestParam(value="boatdriver") boolean boatdriver) {
//        log.debug("Search for aquateam: {}, skipper: {}, boatdriver: {}", aquateam, skipper, boatdriver );
//        List<Member> members = memberService.search(aquateam, skipper, boatdriver);
//        return new ResponseEntity<>(memberMapper.membersToMemberDTOs(members), HttpStatus.OK);
//    }


//    /**
//     * GET  /members/search :  get all further Educations by one member.
//     *
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @RequestMapping(value = "/members/export",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public void exportMembers(HttpServletResponse response, @RequestParam(value="aquateam")boolean aquateam, @RequestParam(value="skipper") boolean skipper, @RequestParam(value="boatdriver") boolean boatdriver) {
//        log.debug("Export for aquateam: {}, skipper: {}, boatdriver: {}", aquateam, skipper, boatdriver );
//        memberService.export(response, aquateam, skipper, boatdriver);
//    }


}
