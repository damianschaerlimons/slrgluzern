package ch.slrg.mvp.web.rest;

import ch.slrg.mvp.security.AuthoritiesConstants;
import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Membertype;
import ch.slrg.mvp.repository.MembertypeRepository;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Membertype.
 */
@RestController
@RequestMapping("/api")
public class MembertypeResource {

    private final Logger log = LoggerFactory.getLogger(MembertypeResource.class);

    @Inject
    private MembertypeRepository membertypeRepository;

    /**
     * POST  /membertypes : Create a new membertype.
     *
     * @param membertype the membertype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membertype, or with status 400 (Bad Request) if the membertype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/membertypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Membertype> createMembertype(@RequestBody Membertype membertype) throws URISyntaxException {
        log.debug("REST request to save Membertype : {}", membertype);
        if (membertype.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("membertype", "idexists", "A new membertype cannot already have an ID")).body(null);
        }
        Membertype result = membertypeRepository.save(membertype);
        return ResponseEntity.created(new URI("/api/membertypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("membertype", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membertypes : Updates an existing membertype.
     *
     * @param membertype the membertype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membertype,
     * or with status 400 (Bad Request) if the membertype is not valid,
     * or with status 500 (Internal Server Error) if the membertype couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/membertypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Membertype> updateMembertype(@RequestBody Membertype membertype) throws URISyntaxException {
        log.debug("REST request to update Membertype : {}", membertype);
        if (membertype.getId() == null) {
            return createMembertype(membertype);
        }
        Membertype result = membertypeRepository.save(membertype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("membertype", membertype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membertypes : get all the membertypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of membertypes in body
     */
    @RequestMapping(value = "/membertypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<Membertype> getAllMembertypes() {
        log.debug("REST request to get all Membertypes");
        List<Membertype> membertypes = membertypeRepository.findAll();
        return membertypes;
    }

    /**
     * GET  /membertypes/:id : get the "id" membertype.
     *
     * @param id the id of the membertype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membertype, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/membertypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Membertype> getMembertype(@PathVariable Long id) {
        log.debug("REST request to get Membertype : {}", id);
        Membertype membertype = membertypeRepository.findOne(id);
        return Optional.ofNullable(membertype)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /membertypes/:id : delete the "id" membertype.
     *
     * @param id the id of the membertype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/membertypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteMembertype(@PathVariable Long id) {
        log.debug("REST request to delete Membertype : {}", id);
        membertypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("membertype", id.toString())).build();
    }

}
