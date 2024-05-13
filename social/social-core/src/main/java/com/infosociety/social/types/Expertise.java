package com.infosociety.social.types;

/**
 * Expertise.
 *
 * @author Baruch Speiser, Cambium.
 */
public class Expertise {
  private int id;
  private String name;
  private Category category;

  public Expertise(final int id, final String name, final Category category) {
    this.id = id;
    this.name = name;
    this.category = category;
  }

  public int getID() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public Category getCategory() {
    return category;
  }
}
