package com.infosociety.social.types;


import java.util.Collections;
import java.util.List;

/**
 * Expert.
 *
 * @author Baruch Speiser, Cambium.
 */
public class Expert {
  public static enum Scale {
    BASIC_LISTING,
    FULL_PROFILE
  }
  public static final Expert.Scale BASIC_LISTING = Expert.Scale.BASIC_LISTING;
  public static final Expert.Scale FULL_PROFILE  = Expert.Scale.FULL_PROFILE;

  private long id;
  private String firstName;
  private String lastName;
  private String role;
  private String company;
  private String zip;
  private Country country;
  private String image;
  private String email;
  private String phone;
  private List<Expertise> expertise;
  private List<AcademicDegree> academicDegrees;
  private Expert.Scale scale; //indicates how much data is present

  public Expert(
      long id,
      String firstName,
      String lastName,
      String role,
      String company,
      String zip,
      Country country,
      String image,
      String email,
      String phone,
      List<Expertise> expertise,
      List<AcademicDegree> academicDegrees,
      Scale scale)
  {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.company = company;
    this.zip = zip;
    this.country = country;
    this.image = image;
    this.email = email;
    this.phone = phone;
    this.expertise = expertise;
    this.academicDegrees = academicDegrees;
    this.scale = scale;
  }

  public boolean isFullProfile() {
    return this.scale == Expert.Scale.FULL_PROFILE;
  }

  public long getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getRole() {
    return this.role;
  }

  public String getCompany() {
    return this.company;
  }

  public String getZip() {
    return this.zip;
  }

  public Country getCountry() {
    return this.country;
  }

  public String getImage() {
    return this.image;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPhone() {
    return this.phone;
  }

  public List<Expertise> getExpertise() {
    return Collections.unmodifiableList(this.expertise);
  }

  public List<AcademicDegree> getAcademicDegrees() {
    return Collections.unmodifiableList(this.academicDegrees);
  }
}
