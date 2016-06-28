package ch.slrg.mvp.service;

import ch.slrg.mvp.domain.Educationtype;
import ch.slrg.mvp.repository.EducationtypeRepository;
import ch.slrg.mvp.web.rest.dto.EducationtypeDTO;
import ch.slrg.mvp.web.rest.mapper.EducationtypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Educationtype.
 */
@Service
@Transactional
public class EducationtypeService {

    private final Logger log = LoggerFactory.getLogger(EducationtypeService.class);
    
    @Inject
    private EducationtypeRepository educationtypeRepository;
    
    @Inject
    private EducationtypeMapper educationtypeMapper;
    
    /**
     * Save a educationtype.
     * 
     * @param educationtypeDTO the entity to save
     * @return the persisted entity
     */
    public EducationtypeDTO save(EducationtypeDTO educationtypeDTO) {
        log.debug("Request to save Educationtype : {}", educationtypeDTO);
        Educationtype educationtype = educationtypeMapper.educationtypeDTOToEducationtype(educationtypeDTO);
        educationtype = educationtypeRepository.save(educationtype);
        EducationtypeDTO result = educationtypeMapper.educationtypeToEducationtypeDTO(educationtype);
        return result;
    }

    /**
     *  Get all the educationtypes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EducationtypeDTO> findAll() {
        log.debug("Request to get all Educationtypes");
        List<EducationtypeDTO> result = educationtypeRepository.findAll().stream()
            .map(educationtypeMapper::educationtypeToEducationtypeDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one educationtype by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EducationtypeDTO findOne(Long id) {
        log.debug("Request to get Educationtype : {}", id);
        Educationtype educationtype = educationtypeRepository.findOne(id);
        EducationtypeDTO educationtypeDTO = educationtypeMapper.educationtypeToEducationtypeDTO(educationtype);
        return educationtypeDTO;
    }

    /**
     *  Delete the  educationtype by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Educationtype : {}", id);
        educationtypeRepository.delete(id);
    }
}
