package com.infosociety.social.api.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosociety.social.api.GeographyService;
import com.infosociety.social.persistence.CountriesProvider;
import com.infosociety.social.types.Country;


/**
 * GeographyServiceBean.
 *
 * @author Baruch Speiser, Cambium.
 */
@Service
public class GeographyServiceBean implements GeographyService {
  private static final Logger log = LoggerFactory.getLogger(ExpertiseServiceBean.class);
  @Autowired
  private CountriesProvider provider;
  private Map<Integer, Country> idMap;
  private Map<String, Country> codeMap;
  private List<Country> countries;

  /** @param provider */
  public void setProvider(CountriesProvider provider) {
    this.provider = provider;
  }
  
  @PostConstruct
  void onInit() {
    this.countries = this.provider.getAllCountries();
    if(this.countries.isEmpty()) {
      log.warn("Provider did return any countries - has the database been properly populated?");
      return;
    }
    this.idMap = new TreeMap<>();
    this.codeMap = new TreeMap<>();
    for(Country country : countries) {
      this.idMap.put(country.getID(), country);
      this.codeMap.put(country.getCode(), country);
    }
    //Do not assume the countries will be sorted alphabetically
    this.countries.sort(new Comparator<Country>() {
      @Override
      public int compare(Country a, Country b) {
        return a.getName().compareTo(b.getName());
      }
    });
    this.countries = Collections.unmodifiableList(this.countries);
  }

  @Override
  public List<Country> getAllCountries() {
    return this.countries;
  }

  @Override
  public Country lookup(int id) {
    return this.idMap.get(id);
  }

  @Override
  public Country lookup(String countryCode) {
    return this.codeMap.get(countryCode);
  }
}
