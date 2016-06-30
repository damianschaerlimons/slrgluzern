package ch.slrg.mvp.repository;

import ch.slrg.mvp.domain.Assessment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Assessment entity.
 */
@SuppressWarnings("unused")
public interface AssessmentRepository extends JpaRepository<Assessment,Long> {

    List<Assessment> findAssessmentByMemberId(Long id);
}
