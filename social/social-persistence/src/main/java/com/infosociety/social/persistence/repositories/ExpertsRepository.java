package com.infosociety.social.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infosociety.social.persistence.entities.ExpertEntity;

/**
 * ExpertsRepository.
 *
 * @author Baruch Speiser, Cambium.
 */
@Repository
public interface ExpertsRepository extends JpaRepository<ExpertEntity, Integer> {
    
  /**
   * Find all relevant experts by topic and country. 
   * @param topicId
   * @param countryId
   * @return list of relevant experts
   */
  @Query("SELECT new com.infosociety.social.persistence.entities.ExpertEntity(" +
              "expert.id, " +
              "expert.firstName, " +
              "expert.lastName, " +
              "expert.academicDegreeId, " +
              "expert.position, " +
              "expert.company, " +
              "expert.countryId, " +
              "expert.zip, " +
              "expert.image, " +
              "expert.email, " +
              "expert.phone) " +
          "FROM ExpertEntity expert " +
              "LEFT JOIN ExpertsExpertiseEntity expertise ON expert.id = expertise.expertId " +
              "LEFT JOIN TopicEntity topic ON expertise.topicId = topic.id " +
          "WHERE topic.id = :topicId "
          + "AND expert.countryId = :countryId")
  public List<ExpertEntity> findAllByTopicIdAndCountryId(Integer topicId, Integer countryId);

  /**
   * Find all relevant experts by topic. 
   * @param topicId
   * @return list of relevant experts
   */
  @Query("SELECT new com.infosociety.social.persistence.entities.ExpertEntity(" +
              "expert.id, " +
              "expert.firstName, " +
              "expert.lastName, " +
              "expert.academicDegreeId, " +
              "expert.position, " +
              "expert.company, " +
              "expert.countryId, " +
              "expert.zip, " +
              "expert.image, " +
              "expert.email, " +
              "expert.phone) " +
          "FROM ExpertEntity expert " +
              "LEFT JOIN ExpertsExpertiseEntity expertise ON expert.id = expertise.expertId " +
              "LEFT JOIN TopicEntity topic ON expertise.topicId = topic.id " +
          "WHERE topic.id = :topicId")
  public List<ExpertEntity> findAllByTopicId(Integer topicId);

  /**
   * Find all relevant experts by country. 
   * @param countryId
   * @return list of relevant experts
   */
  public List<ExpertEntity> findAllByCountryId(Integer countryId);
}
