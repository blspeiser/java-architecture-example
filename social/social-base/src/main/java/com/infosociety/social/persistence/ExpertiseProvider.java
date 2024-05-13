package com.infosociety.social.persistence;

import com.infosociety.social.types.Expertise;

import java.util.List;

/**
 * ExpertiseProvider.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface ExpertiseProvider {

  /** @return all expertise */
  public List<Expertise> getAllExpertise();
}
