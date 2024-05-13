package com.infosociety.social.api;

import com.infosociety.social.types.Category;
import com.infosociety.social.types.Expertise;

import java.util.List;

/**
 * ExpertiseService.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface ExpertiseService {

  /** @return shallow list of all categories, without the various expertise inside each one. */
  public List<Category> listAllCategories();

  /** @return deep list of all categories, including the list of expertise in each one */
  public List<Category> getAllCategories();

  /** @return all expertise, including its category */
  public List<Expertise> getAllExpertise();

  /** @return category by id, or null if not found */
  public Category lookupCategory(int id);

  /** @return expertise by id, or null if not found */
  public Expertise lookupExpertise(int id);
}
