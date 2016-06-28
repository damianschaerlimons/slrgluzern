package ch.slrg.mvp.service;

import ch.slrg.mvp.domain.Education;
import ch.slrg.mvp.repository.EducationRepository;
import ch.slrg.mvp.web.rest.dto.EducationDTO;
import ch.slrg.mvp.web.rest.mapper.EducationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Education.
 */
@Service
@Transactional
public class EducationService {

    private final Logger log = LoggerFactory.getLogger(EducationService.class);

    @Inject
    private EducationRepository educationRepository;

    @Inject
    private EducationMapper educationMapper;

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    public EducationDTO save(EducationDTO educationDTO) {
        log.debug("Request to save Education : {}", educationDTO);
        Education education = educationMapper.educationDTOToEducation(educationDTO);
        education = educationRepository.save(education);
        EducationDTO result = educationMapper.educationToEducationDTO(education);
        return result;
    }

    /**
     *  Get all the educations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EducationDTO> findAll() {
        log.debug("Request to get all Educations");
        List<EducationDTO> result = educationRepository.findAll().stream()
            .map(educationMapper::educationToEducationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one education by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EducationDTO findOne(Long id) {
        log.debug("Request to get Education : {}", id);
        Education education = educationRepository.findOne(id);
        EducationDTO educationDTO = educationMapper.educationToEducationDTO(education);
        return educationDTO;
    }

    /**
     *  Delete the  education by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Education : {}", id);
        educationRepository.delete(id);
    }

    public List<EducationDTO> findEducationsByMember(Long id) {
        log.debug("Request to get all Educations by a certail Member ID");
        List<EducationDTO> result = educationRepository.findEducationByMemberId(id).stream()
            .map(educationMapper::educationToEducationDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        log.debug(result.toString());
        return result;
    }
}
