package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.Appearances;
import ch.slrg.mvp.repository.AppearancesRepository;
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
 * REST controller for managing Appearances.
 */
@RestController
@RequestMapping("/api")
public class AppearancesResource {

    private final Logger log = LoggerFactory.getLogger(AppearancesResource.class);
        
    @Inject
    private AppearancesRepository appearancesRepository;
    
    /**
     * POST  /appearances : Create a new appearances.
     *
     * @param appearances the appearances to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appearances, or with status 400 (Bad Request) if the appearances has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appearances> createAppearances(@RequestBody Appearances appearances) throws URISyntaxException {
        log.debug("REST request to save Appearances : {}", appearances);
        if (appearances.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("appearances", "idexists", "A new appearances cannot already have an ID")).body(null);
        }
        Appearances result = appearancesRepository.save(appearances);
        return ResponseEntity.created(new URI("/api/appearances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("appearances", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appearances : Updates an existing appearances.
     *
     * @param appearances the appearances to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appearances,
     * or with status 400 (Bad Request) if the appearances is not valid,
     * or with status 500 (Internal Server Error) if the appearances couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appearances> updateAppearances(@RequestBody Appearances appearances) throws URISyntaxException {
        log.debug("REST request to update Appearances : {}", appearances);
        if (appearances.getId() == null) {
            return createAppearances(appearances);
        }
        Appearances result = appearancesRepository.save(appearances);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("appearances", appearances.getId().toString()))
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
    public List<Appearances> getAllAppearances() {
        log.debug("REST request to get all Appearances");
        List<Appearances> appearances = appearancesRepository.findAll();
        return appearances;
    }

    /**
     * GET  /appearances/:id : get the "id" appearances.
     *
     * @param id the id of the appearances to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appearances, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/appearances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Appearances> getAppearances(@PathVariable Long id) {
        log.debug("REST request to get Appearances : {}", id);
        Appearances appearances = appearancesRepository.findOne(id);
        return Optional.ofNullable(appearances)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appearances/:id : delete the "id" appearances.
     *
     * @param id the id of the appearances to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/appearances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAppearances(@PathVariable Long id) {
        log.debug("REST request to delete Appearances : {}", id);
        appearancesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("appearances", id.toString())).build();
    }

}
