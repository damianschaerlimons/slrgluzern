package ch.slrg.mvp.service;

import ch.slrg.mvp.domain.Appearances;
import ch.slrg.mvp.repository.AppearancesRepository;
import ch.slrg.mvp.web.rest.dto.AppearancesDTO;
import ch.slrg.mvp.web.rest.mapper.AppearancesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Appearances.
 */
@Service
@Transactional
public class AppearancesService {

    private final Logger log = LoggerFactory.getLogger(AppearancesService.class);

    @Inject
    private AppearancesRepository appearancesRepository;

    @Inject
    private AppearancesMapper appearancesMapper;

    /**
     * Save a appearances.
     *
     * @param appearancesDTO the entity to save
     * @return the persisted entity
     */
    public AppearancesDTO save(AppearancesDTO appearancesDTO) {
        log.debug("Request to save Appearances : {}", appearancesDTO);
        Appearances appearances = appearancesMapper.appearancesDTOToAppearances(appearancesDTO);
        appearances = appearancesRepository.save(appearances);
        AppearancesDTO result = appearancesMapper.appearancesToAppearancesDTO(appearances);
        return result;
    }

    /**
     *  Get all the appearances.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AppearancesDTO> findAll() {
        log.debug("Request to get all Appearances");
        List<AppearancesDTO> result = appearancesRepository.findAll().stream()
            .map(appearancesMapper::appearancesToAppearancesDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one appearances by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AppearancesDTO findOne(Long id) {
        log.debug("Request to get Appearances : {}", id);
        Appearances appearances = appearancesRepository.findOne(id);
        AppearancesDTO appearancesDTO = appearancesMapper.appearancesToAppearancesDTO(appearances);
        return appearancesDTO;
    }

    /**
     *  Delete the  appearances by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appearances : {}", id);
        appearancesRepository.delete(id);
    }


    public List<AppearancesDTO> findAppearancesByMemberId(Long id) {

        log.debug("Request all Appearances from Member: {}", id);
        List<AppearancesDTO> result =  appearancesRepository.findAppearancesByMemberId(id).stream()
            .map(appearancesMapper::appearancesToAppearancesDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
