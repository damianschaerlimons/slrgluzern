package ch.slrg.mvp.web.rest.mapper;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.web.rest.dto.FurtherEducationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FurtherEducation and its DTO FurtherEducationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FurtherEducationMapper {

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.name", target = "memberName")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.name", target = "teacherName")
    FurtherEducationDTO furtherEducationToFurtherEducationDTO(FurtherEducation furtherEducation);

    List<FurtherEducationDTO> furtherEducationsToFurtherEducationDTOs(List<FurtherEducation> furtherEducations);

    @Mapping(source = "memberId", target = "member")
    @Mapping(source = "teacherId", target = "teacher")
    FurtherEducation furtherEducationDTOToFurtherEducation(FurtherEducationDTO furtherEducationDTO);

    List<FurtherEducation> furtherEducationDTOsToFurtherEducations(List<FurtherEducationDTO> furtherEducationDTOs);

    default Member memberFromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
