package com.infosociety.social.types;


/**
 * Country.
 *
 * @author Baruch Speiser, Cambium.
 */
public class Country {
  private int id;
  private String code;
  private String name;
  private double latitude;
  private double longitude;

  public Country(int id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public Country(int id, String name, double latitude, double longitude) {
    this.id = id;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public int getID() {
    return this.id;
  }
  public String getCode() {
    return this.code;
  }
  public String getName() {
    return this.name;
  }
  public double getLatitude() {
    return this.latitude;
  }
  public double getLongitude() {
    return this.longitude;
  }
}
