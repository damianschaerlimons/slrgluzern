package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Educationtype;
import ch.slrg.mvp.repository.EducationtypeRepository;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Educationtype.
 */
@RestController
@RequestMapping("/api")
public class EducationtypeResource {

    private final Logger log = LoggerFactory.getLogger(EducationtypeResource.class);
        
    @Inject
    private EducationtypeRepository educationtypeRepository;
    
    /**
     * POST  /educationtypes : Create a new educationtype.
     *
     * @param educationtype the educationtype to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationtype, or with status 400 (Bad Request) if the educationtype has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educationtypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Educationtype> createEducationtype(@RequestBody Educationtype educationtype) throws URISyntaxException {
        log.debug("REST request to save Educationtype : {}", educationtype);
        if (educationtype.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("educationtype", "idexists", "A new educationtype cannot already have an ID")).body(null);
        }
        Educationtype result = educationtypeRepository.save(educationtype);
        return ResponseEntity.created(new URI("/api/educationtypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("educationtype", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educationtypes : Updates an existing educationtype.
     *
     * @param educationtype the educationtype to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationtype,
     * or with status 400 (Bad Request) if the educationtype is not valid,
     * or with status 500 (Internal Server Error) if the educationtype couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educationtypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Educationtype> updateEducationtype(@RequestBody Educationtype educationtype) throws URISyntaxException {
        log.debug("REST request to update Educationtype : {}", educationtype);
        if (educationtype.getId() == null) {
            return createEducationtype(educationtype);
        }
        Educationtype result = educationtypeRepository.save(educationtype);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("educationtype", educationtype.getId().toString()))
            .body(result);
    }

    /**
     * GET  /educationtypes : get all the educationtypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of educationtypes in body
     */
    @RequestMapping(value = "/educationtypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Educationtype> getAllEducationtypes() {
        log.debug("REST request to get all Educationtypes");
        List<Educationtype> educationtypes = educationtypeRepository.findAll();
        return educationtypes;
    }

    /**
     * GET  /educationtypes/:id : get the "id" educationtype.
     *
     * @param id the id of the educationtype to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationtype, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/educationtypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Educationtype> getEducationtype(@PathVariable Long id) {
        log.debug("REST request to get Educationtype : {}", id);
        Educationtype educationtype = educationtypeRepository.findOne(id);
        return Optional.ofNullable(educationtype)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /educationtypes/:id : delete the "id" educationtype.
     *
     * @param id the id of the educationtype to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/educationtypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEducationtype(@PathVariable Long id) {
        log.debug("REST request to delete Educationtype : {}", id);
        educationtypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("educationtype", id.toString())).build();
    }

}
