package com.infosociety.social.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CategoryEntity.
 * 
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "CATEGORY")
public class CategoryEntity {
  @Id
  public Integer id;
  public String name;

  public CategoryEntity() {
    // default
  }

  public CategoryEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

}
