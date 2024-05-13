package com.infosociety.social.api;

import com.infosociety.social.types.Country;
import com.infosociety.social.types.Expert;
import com.infosociety.social.types.Expertise;
import com.infosociety.social.types.SearchCriteria;

import java.util.List;

/**
 * SearchService.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface SearchService {

  /**
   * Currently supported search fields:
   * - category (optional) [exact match]
   * - country (optional) [exact match]
   *
   * @return list of experts according to the search criteria
   */
  public List<Expert> searchExperts(SearchCriteria criteria);

  /**
   * Currently supported search fields:
   * - expertise (optional) [case insensitive match by name]
   *
   * @return list of experts according to the search criteria
   */
  public List<Expertise> searchExpertise(SearchCriteria criteria);

  /**
   * Currently supported search fields:
   * - country (optional) [case insensitive match by name]
   *
   * @return list of experts according to the search criteria
   */
  public List<Country> searchCountries(SearchCriteria criteria);

}
