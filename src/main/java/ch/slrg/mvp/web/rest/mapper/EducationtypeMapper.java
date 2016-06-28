package ch.slrg.mvp.web.rest.mapper;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.web.rest.dto.EducationtypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Educationtype and its DTO EducationtypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EducationtypeMapper {

    EducationtypeDTO educationtypeToEducationtypeDTO(Educationtype educationtype);

    List<EducationtypeDTO> educationtypesToEducationtypeDTOs(List<Educationtype> educationtypes);

    Educationtype educationtypeDTOToEducationtype(EducationtypeDTO educationtypeDTO);

    List<Educationtype> educationtypeDTOsToEducationtypes(List<EducationtypeDTO> educationtypeDTOs);
}
