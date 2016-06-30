package ch.slrg.mvp.web.rest.mapper;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.web.rest.dto.AppearancesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Appearances and its DTO AppearancesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AppearancesMapper {

    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.name", target = "memberName")
    @Mapping(source = "member.lastname", target = "memberLastname")
    @Mapping(source = "appearancesType.id", target = "appearancesTypeId")
    @Mapping(source = "appearancesType.name", target = "appearancesTypeName")
    AppearancesDTO appearancesToAppearancesDTO(Appearances appearances);

    List<AppearancesDTO> appearancesToAppearancesDTOs(List<Appearances> appearances);

    @Mapping(source = "memberId", target = "member")
    @Mapping(source = "appearancesTypeId", target = "appearancesType")
    Appearances appearancesDTOToAppearances(AppearancesDTO appearancesDTO);

    List<Appearances> appearancesDTOsToAppearances(List<AppearancesDTO> appearancesDTOs);

    default Member memberFromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }

    default AppearancesType appearancesTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        AppearancesType appearancesType = new AppearancesType();
        appearancesType.setId(id);
        return appearancesType;
    }
}
