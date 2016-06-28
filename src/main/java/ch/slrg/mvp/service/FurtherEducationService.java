package ch.slrg.mvp.service;

import ch.slrg.mvp.domain.FurtherEducation;
import ch.slrg.mvp.repository.FurtherEducationRepository;
import ch.slrg.mvp.web.rest.dto.FurtherEducationDTO;
import ch.slrg.mvp.web.rest.mapper.FurtherEducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FurtherEducation.
 */
@Service
@Transactional
public class FurtherEducationService {

    private final Logger log = LoggerFactory.getLogger(FurtherEducationService.class);

    @Inject
    private FurtherEducationRepository furtherEducationRepository;

    @Inject
    private FurtherEducationMapper furtherEducationMapper;

    /**
     * Save a furtherEducation.
     *
     * @param furtherEducationDTO the entity to save
     * @return the persisted entity
     */
    public FurtherEducationDTO save(FurtherEducationDTO furtherEducationDTO) {
        log.debug("Request to save FurtherEducation : {}", furtherEducationDTO);
        FurtherEducation furtherEducation = furtherEducationMapper.furtherEducationDTOToFurtherEducation(furtherEducationDTO);
        furtherEducation = furtherEducationRepository.save(furtherEducation);
        FurtherEducationDTO result = furtherEducationMapper.furtherEducationToFurtherEducationDTO(furtherEducation);
        return result;
    }

    /**
     *  Get all the furtherEducations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FurtherEducation> findAll(Pageable pageable) {
        log.debug("Request to get all FurtherEducations");
        Page<FurtherEducation> result = furtherEducationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one furtherEducation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FurtherEducationDTO findOne(Long id) {
        log.debug("Request to get FurtherEducation : {}", id);
        FurtherEducation furtherEducation = furtherEducationRepository.findOne(id);
        FurtherEducationDTO furtherEducationDTO = furtherEducationMapper.furtherEducationToFurtherEducationDTO(furtherEducation);
        return furtherEducationDTO;
    }

    /**
     *  Delete the  furtherEducation by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FurtherEducation : {}", id);
        furtherEducationRepository.delete(id);
    }

    public List<FurtherEducationDTO> findFurtherEducationByMemberId(Long id) {
        List<FurtherEducationDTO> result =  furtherEducationRepository.findFurtherEducationByMemberId(id).stream()
            .map(furtherEducationMapper::furtherEducationToFurtherEducationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        log.debug(result.toString());
        return result;
    }
}
