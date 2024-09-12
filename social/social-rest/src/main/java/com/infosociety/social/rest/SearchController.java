package com.infosociety.social.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosociety.social.api.SearchService;
import com.infosociety.social.rest.types.SearchCountriesResponse;
import com.infosociety.social.rest.types.SearchExpertiseResponse;
import com.infosociety.social.rest.types.SearchExpertsResponse;
import com.infosociety.social.types.SearchCriteria;

/**
 * SearchController.
 *
 * @author Baruch Speiser, Cambium.
 */
@RestController
@RequestMapping("/api/social/search")
public class SearchController {
  private static final Logger log = LoggerFactory.getLogger(ListsController.class);
  @Autowired
  private SearchService service;

  /** service */
  public void setService(SearchService service) {
    this.service = service;
  }
  
  @GetMapping("/experts")
  public ResponseEntity<SearchExpertsResponse> searchExperts(
    @RequestParam(required=false,defaultValue="-1") int categoryID,
    @RequestParam(required=false,defaultValue="-1") String countryCode)
  {
    try {
      SearchCriteria criteria = new SearchCriteria();
      criteria.categoryID = categoryID;
      criteria.countryCode = countryCode;
      SearchExpertsResponse response = new SearchExpertsResponse();
      response.experts = this.service.searchExperts(criteria);
      return ResponseEntity.ok(response);
    } catch(IllegalArgumentException e) {
      log.debug("Invalid request while searching experts", e);
      return ResponseEntity.status(400).build();
    } catch(Exception e) {
      log.error("Unexpected error while searching experts", e);
      return ResponseEntity.status(500).build();
    }
  }

  @GetMapping("/expertise")
  public ResponseEntity<SearchExpertiseResponse> searchExpertise(
      @RequestParam(required=false,defaultValue="-1") String expertise)
  {
    try {
      SearchCriteria criteria = new SearchCriteria();
      criteria.expertiseName = expertise;
      SearchExpertiseResponse response = new SearchExpertiseResponse();
      response.expertise = this.service.searchExpertise(criteria);
      return ResponseEntity.ok(response);
    } catch(IllegalArgumentException e) {
      log.debug("Invalid request while searching expertise", e);
      return ResponseEntity.status(400).build();
    } catch(Exception e) {
      log.error("Unexpected error while searching expertise", e);
      return ResponseEntity.status(500).build();
    }
  }

  @GetMapping("/countries")
  public ResponseEntity<SearchCountriesResponse> searchCountries(
      @RequestParam(required=false,defaultValue="-1") String country)
  {
    try {
      SearchCriteria criteria = new SearchCriteria();
      criteria.countryName = country;
      SearchCountriesResponse response = new SearchCountriesResponse();
      response.countries = this.service.searchCountries(criteria);
      return ResponseEntity.ok(response);
    } catch(IllegalArgumentException e) {
	  log.debug("Invalid request while searching countries", e);
	  return ResponseEntity.status(400).build();
	} catch(Exception e) {
      log.error("Unexpected error while searching countries", e);
      return ResponseEntity.status(500).build();
    }
  }

}
