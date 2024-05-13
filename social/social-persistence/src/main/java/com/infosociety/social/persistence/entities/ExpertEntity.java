package com.infosociety.social.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ExpertEntity.
 *
 * @author Baruch Speiser, Cambium.
 */
@Entity
@Table(name = "EXPERTS")
public class ExpertEntity {
  @Id
  public Integer id;
  public String firstName;
  public String lastName;
  public Integer academicDegreeId;
  public String position;
  public String company;
  public Integer countryId;
  public String zip;
  public String image;
  public String email;
  public String phone;
  
  public ExpertEntity() {
    //default
  }

  public ExpertEntity(
      Integer id, 
      String firstName, 
      String lastName, 
      Integer academicDegreeId, 
      String position,
      String company, 
      Integer countryId, 
      String zip, 
      String image, 
      String email,
      String phone) 
  {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.academicDegreeId = academicDegreeId;
    this.position = position;
    this.company = company;
    this.countryId = countryId;
    this.zip = zip;
    this.image = image;
    this.email = email;
    this.phone = phone;
  }
    
}
