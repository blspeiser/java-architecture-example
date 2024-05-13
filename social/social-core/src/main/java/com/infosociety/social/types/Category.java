package com.infosociety.social.types;

import java.util.Collections;
import java.util.List;

/**
 * Category.
 *
 * @author Baruch Speiser, Cambium.
 */
public class Category {
  private int id;
  private String name;
  private List<Expertise> expertise;

  public Category(final int id, final String name) {
    this.id = id;
    this.name = name;
  }
  public Category(final int id, final String name, final List<Expertise> expertise) {
    this.id = id;
    this.name = name;
    this.expertise = expertise;
  }

  public int getID() {
    return this.id;
  }
  public String getName() {
    return this.name;
  }
  public List<Expertise> getExpertise() {
    return Collections.unmodifiableList(this.expertise);
  }

}
