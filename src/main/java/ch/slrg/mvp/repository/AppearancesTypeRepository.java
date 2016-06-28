package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.AppearancesType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AppearancesType entity.
 */
@SuppressWarnings("unused")
public interface AppearancesTypeRepository extends JpaRepository<AppearancesType,Long> {

}
