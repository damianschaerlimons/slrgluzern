package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.FurtherEducation;
import ch.slrg.mvp.service.FurtherEducationService;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import ch.slrg.mvp.web.rest.util.PaginationUtil;
import ch.slrg.mvp.web.rest.dto.FurtherEducationDTO;
import ch.slrg.mvp.web.rest.mapper.FurtherEducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing FurtherEducation.
 */
@RestController
@RequestMapping("/api")
public class FurtherEducationResource {

    private final Logger log = LoggerFactory.getLogger(FurtherEducationResource.class);
        
    @Inject
    private FurtherEducationService furtherEducationService;
    
    @Inject
    private FurtherEducationMapper furtherEducationMapper;
    
    /**
     * POST  /further-educations : Create a new furtherEducation.
     *
     * @param furtherEducationDTO the furtherEducationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new furtherEducationDTO, or with status 400 (Bad Request) if the furtherEducation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducationDTO> createFurtherEducation(@RequestBody FurtherEducationDTO furtherEducationDTO) throws URISyntaxException {
        log.debug("REST request to save FurtherEducation : {}", furtherEducationDTO);
        if (furtherEducationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("furtherEducation", "idexists", "A new furtherEducation cannot already have an ID")).body(null);
        }
        FurtherEducationDTO result = furtherEducationService.save(furtherEducationDTO);
        return ResponseEntity.created(new URI("/api/further-educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("furtherEducation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /further-educations : Updates an existing furtherEducation.
     *
     * @param furtherEducationDTO the furtherEducationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated furtherEducationDTO,
     * or with status 400 (Bad Request) if the furtherEducationDTO is not valid,
     * or with status 500 (Internal Server Error) if the furtherEducationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducationDTO> updateFurtherEducation(@RequestBody FurtherEducationDTO furtherEducationDTO) throws URISyntaxException {
        log.debug("REST request to update FurtherEducation : {}", furtherEducationDTO);
        if (furtherEducationDTO.getId() == null) {
            return createFurtherEducation(furtherEducationDTO);
        }
        FurtherEducationDTO result = furtherEducationService.save(furtherEducationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("furtherEducation", furtherEducationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /further-educations : get all the furtherEducations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of furtherEducations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/further-educations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FurtherEducationDTO>> getAllFurtherEducations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of FurtherEducations");
        Page<FurtherEducation> page = furtherEducationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/further-educations");
        return new ResponseEntity<>(furtherEducationMapper.furtherEducationsToFurtherEducationDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /further-educations/:id : get the "id" furtherEducation.
     *
     * @param id the id of the furtherEducationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the furtherEducationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/further-educations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FurtherEducationDTO> getFurtherEducation(@PathVariable Long id) {
        log.debug("REST request to get FurtherEducation : {}", id);
        FurtherEducationDTO furtherEducationDTO = furtherEducationService.findOne(id);
        return Optional.ofNullable(furtherEducationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /further-educations/:id : delete the "id" furtherEducation.
     *
     * @param id the id of the furtherEducationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/further-educations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFurtherEducation(@PathVariable Long id) {
        log.debug("REST request to delete FurtherEducation : {}", id);
        furtherEducationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("furtherEducation", id.toString())).build();
    }

}
