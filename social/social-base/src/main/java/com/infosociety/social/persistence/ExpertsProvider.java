package com.infosociety.social.persistence;

import com.infosociety.social.types.Expert;

/**
 * ExpertsProvider.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface ExpertsProvider {
  
  
  /**
   * Get the basic demographic data of an expert by ID. 
   * @param id
   * @return basic content of an expert, or null if not found
   */
  public Expert getBasicListing(long id);
  
  /**
   * Get the full data of an expert by ID, including degrees and fields of expertise, etc. 
   * @param id
   * @return full profile of an expert, or null if not found
   */
  public Expert getFullProfile(long id);
}
