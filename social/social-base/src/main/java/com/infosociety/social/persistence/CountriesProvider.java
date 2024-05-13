package com.infosociety.social.persistence;

import com.infosociety.social.types.Country;

import java.util.List;

/**
 * CountriesProvider.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface CountriesProvider {
  
  /** @return all countries */
  public List<Country> getAllCountries();
}
