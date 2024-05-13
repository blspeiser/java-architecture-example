package com.infosociety.social.api;

import com.infosociety.social.types.Expert;

/**
 * ExpertsService.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface ExpertsService {

  /**
   * Get an expert.
   * @param id
   * @param scale - Expert.BASIC_LISTING, Expert.FULL_PROFILE, etc.
   * @return Expert, or null if not found
   */
  public Expert getExpert(long id, Expert.Scale scale);

}
