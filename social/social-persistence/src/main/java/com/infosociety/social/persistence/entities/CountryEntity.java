package com.infosociety.social.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CountryEntity.
 * 
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "COUNTRIES")
public class CountryEntity {
  @Id
  public Integer id;
  public String name;
  public Double latitude;
  public Double longitude;

  public CountryEntity() {
    // default
  }

  public CountryEntity(Integer id, String name, Double latitude, Double longitude) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

}
