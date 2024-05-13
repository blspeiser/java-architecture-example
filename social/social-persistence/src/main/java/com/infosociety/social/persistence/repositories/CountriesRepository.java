package com.infosociety.social.persistence.repositories;

import com.infosociety.social.persistence.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CountriesRepository.
 *
 * @author Baruch Speiser, Cambium.
 */
@Repository
public interface CountriesRepository extends JpaRepository<CountryEntity, Integer> {
  
  /**
   * Find countries by partial name match.
   * @param country Substring to search for - for example "stan" would find Pakistan, Turkmenistan, Afghanistan, etc. 
   * @return list of countries that match the search string. 
   */
  public List<CountryEntity> findAllByNameContainsIgnoreCase(String country);
}
