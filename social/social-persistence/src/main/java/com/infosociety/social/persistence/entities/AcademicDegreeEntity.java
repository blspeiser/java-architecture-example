package com.infosociety.social.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AcademicDegreeEntity.
 * 
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "ACADEMIC_DEGREE")
public class AcademicDegreeEntity {
  @Id
  public Integer id;
  public String name;

  public AcademicDegreeEntity() {
    // default
  }

  public AcademicDegreeEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

}
