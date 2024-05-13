package com.infosociety.social.persistence.impl;

import com.infosociety.social.persistence.CountriesProvider;
import com.infosociety.social.persistence.entities.CountryEntity;
import com.infosociety.social.persistence.repositories.CountriesRepository;
import com.infosociety.social.types.Country;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * CountriesProviderBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Component
public class CountriesProviderBean implements CountriesProvider {
  private static final Logger log = LoggerFactory.getLogger(CountriesProviderBean.class);
  @Autowired
  private CountriesRepository repository;
  
  /** @param repository */
  public void setRepository(CountriesRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Country> getAllCountries() {
    List<CountryEntity> list = this.repository.findAll();
    log.trace("Found {} countries", list.size());
    //dehydrate:
    List<Country> countries = new ArrayList<>(list.size());
    for(CountryEntity c : list) {
      countries.add(new Country(c.id, c.name, c.latitude, c.longitude));
    }
    return countries;
  }
}
