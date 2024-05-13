package com.infosociety.social.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosociety.social.api.ExpertsService;
import com.infosociety.social.persistence.ExpertsProvider;
import com.infosociety.social.types.Expert;

/**
 * ExpertsServiceBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Service
public class ExpertsServiceBean implements ExpertsService {
  private static final Logger log = LoggerFactory.getLogger(ExpertsServiceBean.class);
  @Autowired
  private ExpertsProvider provider;

  /** @param provider */
  public void setProvider(ExpertsProvider provider) {
    this.provider = provider;
  }
  
  @Override
  public Expert getExpert(long id, Expert.Scale scale) {
    log.debug("Fetching expert {} with depth: {}", id, scale);
    Expert expert;
    if(scale == Expert.FULL_PROFILE) {
      expert = this.provider.getFullProfile(id);
    } else {
      //scale is BASIC_LISTING or not provided
      expert = this.provider.getBasicListing(id);
    }
    return expert;
  }
}
