package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Member;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Member entity.
 */
@SuppressWarnings("unused")
public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findMembersByAquateamOrSkipperOrBoatdriver(boolean aquateam, boolean skipper, boolean boatdriver);

}
