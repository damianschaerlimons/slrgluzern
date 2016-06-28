package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Membertype;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Membertype entity.
 */
@SuppressWarnings("unused")
public interface MembertypeRepository extends JpaRepository<Membertype,Long> {

}
