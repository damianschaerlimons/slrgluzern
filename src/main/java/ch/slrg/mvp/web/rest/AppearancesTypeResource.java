package ch.slrg.mvp.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.slrg.mvp.domain.AppearancesType;
import ch.slrg.mvp.repository.AppearancesTypeRepository;
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
 * REST controller for managing AppearancesType.
 */
@RestController
@RequestMapping("/api")
public class AppearancesTypeResource {

    private final Logger log = LoggerFactory.getLogger(AppearancesTypeResource.class);
        
    @Inject
    private AppearancesTypeRepository appearancesTypeRepository;
    
    /**
     * POST  /appearances-types : Create a new appearancesType.
     *
     * @param appearancesType the appearancesType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appearancesType, or with status 400 (Bad Request) if the appearancesType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesType> createAppearancesType(@RequestBody AppearancesType appearancesType) throws URISyntaxException {
        log.debug("REST request to save AppearancesType : {}", appearancesType);
        if (appearancesType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("appearancesType", "idexists", "A new appearancesType cannot already have an ID")).body(null);
        }
        AppearancesType result = appearancesTypeRepository.save(appearancesType);
        return ResponseEntity.created(new URI("/api/appearances-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("appearancesType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appearances-types : Updates an existing appearancesType.
     *
     * @param appearancesType the appearancesType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appearancesType,
     * or with status 400 (Bad Request) if the appearancesType is not valid,
     * or with status 500 (Internal Server Error) if the appearancesType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/appearances-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesType> updateAppearancesType(@RequestBody AppearancesType appearancesType) throws URISyntaxException {
        log.debug("REST request to update AppearancesType : {}", appearancesType);
        if (appearancesType.getId() == null) {
            return createAppearancesType(appearancesType);
        }
        AppearancesType result = appearancesTypeRepository.save(appearancesType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("appearancesType", appearancesType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appearances-types : get all the appearancesTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appearancesTypes in body
     */
    @RequestMapping(value = "/appearances-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AppearancesType> getAllAppearancesTypes() {
        log.debug("REST request to get all AppearancesTypes");
        List<AppearancesType> appearancesTypes = appearancesTypeRepository.findAll();
        return appearancesTypes;
    }

    /**
     * GET  /appearances-types/:id : get the "id" appearancesType.
     *
     * @param id the id of the appearancesType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appearancesType, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/appearances-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppearancesType> getAppearancesType(@PathVariable Long id) {
        log.debug("REST request to get AppearancesType : {}", id);
        AppearancesType appearancesType = appearancesTypeRepository.findOne(id);
        return Optional.ofNullable(appearancesType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /appearances-types/:id : delete the "id" appearancesType.
     *
     * @param id the id of the appearancesType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/appearances-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAppearancesType(@PathVariable Long id) {
        log.debug("REST request to delete AppearancesType : {}", id);
        appearancesTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("appearancesType", id.toString())).build();
    }

}
