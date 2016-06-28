package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Educationtype;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Educationtype entity.
 */
@SuppressWarnings("unused")
public interface EducationtypeRepository extends JpaRepository<Educationtype,Long> {

}
