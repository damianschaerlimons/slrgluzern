package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Appearances;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Appearances entity.
 */
@SuppressWarnings("unused")
public interface AppearancesRepository extends JpaRepository<Appearances,Long> {
    List<Appearances> findAppearancesByMemberId(Long id);

}
