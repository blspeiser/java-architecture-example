package com.infosociety.social.persistence.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * ExpertsExpertiseEntity.
 *
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "EXPERTS_EXPERTISE")
public class ExpertsExpertiseEntity {
  @Id
  @SequenceGenerator(name = "experts_expertise_seq", sequenceName = "experts_expertise_seq" ,allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "experts_expertise_seq")
  Integer id;
  Integer expertId;
  Integer topicId;
  
  public ExpertsExpertiseEntity() {
    //default
  }

  public ExpertsExpertiseEntity(Integer id, Integer expertId, Integer topicId) {
    this.id = id;
    this.expertId = expertId;
    this.topicId = topicId;
  }
    
}
