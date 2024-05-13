package com.infosociety.social.types;

import java.util.Set;

/**
 * SearchCriteria.
 *
 * @author Baruch Speiser, Cambium.
 */
public class SearchCriteria {
  public String expertName = null;

  //Use when searching within a single category:
  public int categoryID = -1;
  public String categoryName = null;

  //Use when searching for a specific expertise:
  public int expertiseID = -1;
  public String expertiseName = null;

  public int countryID = -1;
  public String countryCode = null;
  public String countryName = null;

  //Multisearch options - will take lower precedence than the singular filter options
  public Set<Integer> countryIDs = null;
  public Set<Integer> expertiseIDs = null;
  
 
  @Override
  public String toString() {
    return String.format(
        "SearchCriteria [expertName=%s, categoryID=%s, categoryName=%s, "
        + "expertiseID=%s, expertiseName=%s, countryID=%s, countryCode=%s, "
        + "countryName=%s, countryIDs=%s, expertiseIDs=%s]",
        expertName, categoryID, categoryName, expertiseID, expertiseName, 
        countryID, countryCode, countryName, countryIDs, expertiseIDs);
  }
  
  
}
