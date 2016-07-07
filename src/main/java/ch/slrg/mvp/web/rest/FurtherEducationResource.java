package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.FurtherEducation;
import ch.slrg.mvp.repository.FurtherEducationRepository;
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
 * REST controller for managing FurtherEducation.
 */
@RestController
@RequestMapping("/api")
public class FurtherEducationResource {

    private final Logger log = LoggerFactory.getLogger(FurtherEducationResource.class);
        
    @Inject
    private FurtherEducationRepository furtherEducationRepository;
    
    /**
     * POST  /further-educations : Create a new furtherEducation.
     *
     * @param furtherEducation the furtherEducation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new furtherEducation, or with status 400 (Bad Request) if the furtherEducation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducation> createFurtherEducation(@RequestBody FurtherEducation furtherEducation) throws URISyntaxException {
        log.debug("REST request to save FurtherEducation : {}", furtherEducation);
        if (furtherEducation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("furtherEducation", "idexists", "A new furtherEducation cannot already have an ID")).body(null);
        }
        FurtherEducation result = furtherEducationRepository.save(furtherEducation);
        return ResponseEntity.created(new URI("/api/further-educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("furtherEducation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /further-educations : Updates an existing furtherEducation.
     *
     * @param furtherEducation the furtherEducation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated furtherEducation,
     * or with status 400 (Bad Request) if the furtherEducation is not valid,
     * or with status 500 (Internal Server Error) if the furtherEducation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducation> updateFurtherEducation(@RequestBody FurtherEducation furtherEducation) throws URISyntaxException {
        log.debug("REST request to update FurtherEducation : {}", furtherEducation);
        if (furtherEducation.getId() == null) {
            return createFurtherEducation(furtherEducation);
        }
        FurtherEducation result = furtherEducationRepository.save(furtherEducation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("furtherEducation", furtherEducation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /further-educations : get all the furtherEducations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of furtherEducations in body
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FurtherEducation> getAllFurtherEducations() {
        log.debug("REST request to get all FurtherEducations");
        List<FurtherEducation> furtherEducations = furtherEducationRepository.findAll();
        return furtherEducations;
    }

    /**
     * GET  /further-educations/:id : get the "id" furtherEducation.
     *
     * @param id the id of the furtherEducation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the furtherEducation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/further-educations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducation> getFurtherEducation(@PathVariable Long id) {
        log.debug("REST request to get FurtherEducation : {}", id);
        FurtherEducation furtherEducation = furtherEducationRepository.findOne(id);
        return Optional.ofNullable(furtherEducation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /further-educations/:id : delete the "id" furtherEducation.
     *
     * @param id the id of the furtherEducation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/further-educations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFurtherEducation(@PathVariable Long id) {
        log.debug("REST request to delete FurtherEducation : {}", id);
        furtherEducationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("furtherEducation", id.toString())).build();
    }

}
