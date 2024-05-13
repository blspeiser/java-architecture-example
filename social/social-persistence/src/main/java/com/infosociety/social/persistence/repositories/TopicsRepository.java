package com.infosociety.social.persistence.repositories;

import com.infosociety.social.persistence.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TopicsRepository.
 *
 * @author Baruch Speiser, Cambium.
 */
@Repository
public interface TopicsRepository extends JpaRepository<TopicEntity, Integer> {
  
  /**
   * Find topics by category. 
   * @param categoryId
   * @return list of relevant topics
   */
  public List<TopicEntity> findAllByCategoryId(Integer categoryId);
  
  /**
   * Find topics by category and partial name search for expertise. 
   * @param categoryId
   * @param expertise Substring to search for - for example "nomy" would find taxonomy, economy, etc.
   * @return list of relevant topics
   */
  public List<TopicEntity> findAllByCategoryIdAndNameContainsIgnoreCase(Integer categoryId, String expertise);

  /**
   * Find topics associated with a specific expert. 
   * @param expertId
   * @return list of relevant topics
   */
  @Query("SELECT new com.infosociety.social.persistence.entities.TopicEntity(" +
              "topic.id, " +
              "topic.name, " +
              "topic.categoryId) " +
          "FROM TopicEntity topic " +
              "LEFT JOIN ExpertsExpertiseEntity expertise ON topic.id = expertise.topicId " +
          "WHERE expertise.expertId = :expertId")
  public List<TopicEntity> findAllByExpertId(Integer expertId);
}
