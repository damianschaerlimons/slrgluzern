package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Appearances;
import ch.slrg.mvp.service.AppearancesService;
import ch.slrg.mvp.web.rest.util.HeaderUtil;
import ch.slrg.mvp.web.rest.dto.AppearancesDTO;
import ch.slrg.mvp.web.rest.mapper.AppearancesMapper;
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
 * REST controller for managing Appearances.
 */
@RestController
@RequestMapping("/api")
public class AppearancesResource {

    private final Logger log = LoggerFactory.getLogger(AppearancesResource.class);
        
    @Inject
    private AppearancesService appearancesService;
    
    @Inject
    private AppearancesMapper appearancesMapper;
    
    /**
     * POST  /appearances : Create a new appearances.
     *
     * @param appearancesDTO the appearancesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appearancesDTO, or with status 400 (Bad Request) if the appearances has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesDTO> createAppearances(@RequestBody AppearancesDTO appearancesDTO) throws URISyntaxException {
        log.debug("REST request to save Appearances : {}", appearancesDTO);
        if (appearancesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("appearances", "idexists", "A new appearances cannot already have an ID")).body(null);
        }
        AppearancesDTO result = appearancesService.save(appearancesDTO);
        return ResponseEntity.created(new URI("/api/appearances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("appearances", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appearances : Updates an existing appearances.
     *
     * @param appearancesDTO the appearancesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appearancesDTO,
     * or with status 400 (Bad Request) if the appearancesDTO is not valid,
     * or with status 500 (Internal Server Error) if the appearancesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesDTO> updateAppearances(@RequestBody AppearancesDTO appearancesDTO) throws URISyntaxException {
        log.debug("REST request to update Appearances : {}", appearancesDTO);
        if (appearancesDTO.getId() == null) {
            return createAppearances(appearancesDTO);
        }
        AppearancesDTO result = appearancesService.save(appearancesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("appearances", appearancesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appearances : get all the appearances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appearances in body
     */
    @RequestMapping(value = "/appearances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppearancesDTO> getAllAppearances() {
        log.debug("REST request to get all Appearances");
        return appearancesService.findAll();
    }

    /**
     * GET  /appearances/:id : get the "id" appearances.
     *
     * @param id the id of the appearancesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appearancesDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/appearances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesDTO> getAppearances(@PathVariable Long id) {
        log.debug("REST request to get Appearances : {}", id);
        AppearancesDTO appearancesDTO = appearancesService.findOne(id);
        return Optional.ofNullable(appearancesDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appearances/:id : delete the "id" appearances.
     *
     * @param id the id of the appearancesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/appearances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAppearances(@PathVariable Long id) {
        log.debug("REST request to delete Appearances : {}", id);
        appearancesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("appearances", id.toString())).build();
    }

}
