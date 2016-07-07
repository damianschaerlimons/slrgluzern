package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Education;
import ch.slrg.mvp.repository.EducationRepository;
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
 * REST controller for managing Education.
 */
@RestController
@RequestMapping("/api")
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);
        
    @Inject
    private EducationRepository educationRepository;
    
    /**
     * POST  /educations : Create a new education.
     *
     * @param education the education to create
     * @return the ResponseEntity with status 201 (Created) and with body the new education, or with status 400 (Bad Request) if the education has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education> createEducation(@RequestBody Education education) throws URISyntaxException {
        log.debug("REST request to save Education : {}", education);
        if (education.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("education", "idexists", "A new education cannot already have an ID")).body(null);
        }
        Education result = educationRepository.save(education);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("education", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educations : Updates an existing education.
     *
     * @param education the education to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated education,
     * or with status 400 (Bad Request) if the education is not valid,
     * or with status 500 (Internal Server Error) if the education couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education> updateEducation(@RequestBody Education education) throws URISyntaxException {
        log.debug("REST request to update Education : {}", education);
        if (education.getId() == null) {
            return createEducation(education);
        }
        Education result = educationRepository.save(education);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("education", education.getId().toString()))
            .body(result);
    }

    /**
     * GET  /educations : get all the educations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of educations in body
     */
    @RequestMapping(value = "/educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Education> getAllEducations() {
        log.debug("REST request to get all Educations");
        List<Education> educations = educationRepository.findAll();
        return educations;
    }

    /**
     * GET  /educations/:id : get the "id" education.
     *
     * @param id the id of the education to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the education, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/educations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Education> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        Education education = educationRepository.findOne(id);
        return Optional.ofNullable(education)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /educations/:id : delete the "id" education.
     *
     * @param id the id of the education to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/educations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("education", id.toString())).build();
    }

}
