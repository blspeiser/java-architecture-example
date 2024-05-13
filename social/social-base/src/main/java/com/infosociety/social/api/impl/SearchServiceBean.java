package com.infosociety.social.api.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosociety.social.api.SearchService;
import com.infosociety.social.persistence.SearchProvider;
import com.infosociety.social.types.Country;
import com.infosociety.social.types.Expert;
import com.infosociety.social.types.Expertise;
import com.infosociety.social.types.SearchCriteria;

/**
 * SearchServiceBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Service
public class SearchServiceBean implements SearchService {
  private static final Logger log = LoggerFactory.getLogger(SearchServiceBean.class);
  @Autowired
  private SearchProvider provider;

  /** @param provider */
  public void setProvider(SearchProvider provider) {
    this.provider = provider;
  }
  
  @Override
  public List<Expert> searchExperts(SearchCriteria criteria) {
    log.debug("Searching experts: {}", criteria);
    return this.provider.searchExperts(criteria);
  }

  @Override
  public List<Expertise> searchExpertise(SearchCriteria criteria) {
    log.debug("Searching expertise: {}", criteria);
    return this.provider.searchExpertise(criteria);
  }

  @Override
  public List<Country> searchCountries(SearchCriteria criteria) {
    log.debug("Searching countries: {}", criteria);
    return this.provider.searchCountries(criteria);
  }
}
