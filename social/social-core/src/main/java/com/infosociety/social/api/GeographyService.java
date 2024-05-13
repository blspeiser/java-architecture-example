package com.infosociety.social.api;

import com.infosociety.social.types.Country;

import java.util.List;

/**
 * GeographyService.
 *
 * @author Baruch Speiser, Cambium.
 */
public interface GeographyService {

  /** @return list all available countries */
  public List<Country> getAllCountries();

  /** @return country by id, or null if not found */
  public Country lookup(int id);

  /** @return country by code, or null if not found */
  public Country lookup(String countryCode);

}
