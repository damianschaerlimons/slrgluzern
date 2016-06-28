package ch.slrg.mvp.web.rest.mapper;

import ch.slrg.mvp.domain.*;
import ch.slrg.mvp.web.rest.dto.MemberDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Member and its DTO MemberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MemberMapper {

    @Mapping(source = "membertype.id", target = "membertypeId")
    @Mapping(source = "membertype.name", target = "membertypeName")
    MemberDTO memberToMemberDTO(Member member);

    List<MemberDTO> membersToMemberDTOs(List<Member> members);

    @Mapping(source = "membertypeId", target = "membertype")
    Member memberDTOToMember(MemberDTO memberDTO);

    List<Member> memberDTOsToMembers(List<MemberDTO> memberDTOs);

    default Membertype membertypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Membertype membertype = new Membertype();
        membertype.setId(id);
        return membertype;
    }
}
