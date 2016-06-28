package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Education;

import ch.slrg.mvp.web.rest.dto.EducationDTO;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Education entity.
 */
@SuppressWarnings("unused")
public interface EducationRepository extends JpaRepository<Education,Long> {

    List<Education> findEducationByMemberId(long id);

}
