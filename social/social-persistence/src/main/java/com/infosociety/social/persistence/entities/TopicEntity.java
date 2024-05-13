package com.infosociety.social.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * TopicEntity.
 *
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "TOPIC")
public class TopicEntity {
  @Id
  public Integer id;
  public String name;
  public Integer categoryId;
  
  public TopicEntity() {
    //default
  }

  public TopicEntity(Integer id, String name, Integer categoryId) {
    this.id = id;
    this.name = name;
    this.categoryId = categoryId;
  }
    
}
