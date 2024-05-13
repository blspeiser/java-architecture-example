package com.infosociety.social.persistence.repositories;

import com.infosociety.social.persistence.entities.AcademicDegreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * AcademicDegreeRepository.
 *
 * @author Baruch Speiser, Cambium.
 */
@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegreeEntity, Integer> {
    
  /**
   * Find an academic degree by its expert ID.
   * @param expertId
   * @return academic degree entity, if found
   */
  @Query("SELECT new com.infosociety.social.persistence.entities.AcademicDegreeEntity(" +
                "academicDegree.id, " +
                "academicDegree.name) " +
            "FROM AcademicDegreeEntity academicDegree " +
                "LEFT JOIN ExpertEntity expert ON academicDegree.id = expert.academicDegreeId " +
            "WHERE expert.id = :expertId")
  public Optional<AcademicDegreeEntity> findByExpertId(Integer expertId);
}
