package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Education;
import ch.slrg.mvp.service.EducationService;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import ch.slrg.mvp.web.rest.dto.EducationDTO;
import ch.slrg.mvp.web.rest.mapper.EducationMapper;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Education.
 */
@RestController
@RequestMapping("/api")
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);
        
    @Inject
    private EducationService educationService;
    
    @Inject
    private EducationMapper educationMapper;
    
    /**
     * POST  /educations : Create a new education.
     *
     * @param educationDTO the educationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationDTO, or with status 400 (Bad Request) if the education has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationDTO> createEducation(@RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to save Education : {}", educationDTO);
        if (educationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("education", "idexists", "A new education cannot already have an ID")).body(null);
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("education", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educations : Updates an existing education.
     *
     * @param educationDTO the educationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationDTO,
     * or with status 400 (Bad Request) if the educationDTO is not valid,
     * or with status 500 (Internal Server Error) if the educationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/educations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationDTO> updateEducation(@RequestBody EducationDTO educationDTO) throws URISyntaxException {
        log.debug("REST request to update Education : {}", educationDTO);
        if (educationDTO.getId() == null) {
            return createEducation(educationDTO);
        }
        EducationDTO result = educationService.save(educationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("education", educationDTO.getId().toString()))
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
    public List<EducationDTO> getAllEducations() {
        log.debug("REST request to get all Educations");
        return educationService.findAll();
    }

    /**
     * GET  /educations/:id : get the "id" education.
     *
     * @param id the id of the educationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/educations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationDTO> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        EducationDTO educationDTO = educationService.findOne(id);
        return Optional.ofNullable(educationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /educations/:id : delete the "id" education.
     *
     * @param id the id of the educationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/educations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("education", id.toString())).build();
    }

}
