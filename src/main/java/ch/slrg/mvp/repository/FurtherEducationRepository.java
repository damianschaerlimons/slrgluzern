package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.FurtherEducation;

import ch.slrg.mvp.web.rest.dto.FurtherEducationDTO;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FurtherEducation entity.
 */
@SuppressWarnings("unused")
public interface FurtherEducationRepository extends JpaRepository<FurtherEducation,Long> {

    List<FurtherEducation> findFurtherEducationByMemberId(Long id);
}
