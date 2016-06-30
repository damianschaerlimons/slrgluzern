package ch.slrg.mvp.web.rest.mapper;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.web.rest.dto.EducationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Education and its DTO EducationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EducationMapper {

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.name", target = "memberName")
    @Mapping(source = "member.lastname", target = "memberLastname")

    @Mapping(source = "educationtype.id", target = "educationtypeId")
    @Mapping(source = "educationtype.name", target = "educationtypeName")
    EducationDTO educationToEducationDTO(Education education);

    List<EducationDTO> educationsToEducationDTOs(List<Education> educations);

    @Mapping(source = "memberId", target = "member")
    @Mapping(source = "educationtypeId", target = "educationtype")
    Education educationDTOToEducation(EducationDTO educationDTO);

    List<Education> educationDTOsToEducations(List<EducationDTO> educationDTOs);

    default Member memberFromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }

    default Educationtype educationtypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Educationtype educationtype = new Educationtype();
        educationtype.setId(id);
        return educationtype;
    }
}
