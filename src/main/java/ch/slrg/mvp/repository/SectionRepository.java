package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Section;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Section entity.
 */
@SuppressWarnings("unused")
public interface SectionRepository extends JpaRepository<Section,Long> {

}
