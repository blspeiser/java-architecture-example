package com.infosociety.social.persistence;

import com.infosociety.social.types.Country;
import com.infosociety.social.types.Expert;
import com.infosociety.social.types.Expertise;
import com.infosociety.social.types.SearchCriteria;

import java.util.List;

/**
 * SearchProvider.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface SearchProvider {

  /**
   * Search experts based on the criteria
   * @param criteria
   * @return list of experts
   */
  public List<Expert> searchExperts(SearchCriteria criteria);
  
  /**
   * Search expertise based on the criteria
   * @param criteria
   * @return list of expertise
   */
  public List<Expertise> searchExpertise(SearchCriteria criteria);
  
  /**
   * Search countries based on the criteria
   * @param criteria
   * @return list of countries
   */
  public List<Country> searchCountries(SearchCriteria criteria);

}
