package com.infosociety.social.types;

/**
 * AcademicDegree.
 *
 * @author Baruch Speiser, Cambium.
 */
public class AcademicDegree {
  private int id;
  private String name;

  public AcademicDegree(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  public int getID() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
}
