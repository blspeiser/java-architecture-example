package com.infosociety.social.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosociety.social.api.ExpertiseService;
import com.infosociety.social.api.GeographyService;
import com.infosociety.social.rest.types.ListCategoriesResponse;
import com.infosociety.social.rest.types.ListCountriesResponse;
import com.infosociety.social.rest.types.ListExpertiseResponse;

/**
 * ListsController.
 *
 * @author Baruch Speiser, Cambium.
 */
@RestController
@RequestMapping("/api/social/lists")
public class ListsController {
  private static final Logger log = LoggerFactory.getLogger(ListsController.class);
  @Autowired
  private GeographyService geographyService;
  @Autowired
  private ExpertiseService expertiseService;
  
  /** service */
  public void setGeographyService(GeographyService service) {
    this.geographyService = service;
  }

  /** service */
  public void setExpertiseService(ExpertiseService service) {
    this.expertiseService = service;
  }

  @GetMapping("/countries")
  public ResponseEntity<ListCountriesResponse> listCountries() {
    try {
      ListCountriesResponse response = new ListCountriesResponse();
      response.countries = this.geographyService.getAllCountries();
      return ResponseEntity.ok(response);
    } catch(Exception e) {
      log.error("Unexpected error while listing countries", e);
      return ResponseEntity.status(500).build();
    }
  }
  
  @GetMapping("/categories")
  public ResponseEntity<ListCategoriesResponse> listCategories() {
    try {
      ListCategoriesResponse response = new ListCategoriesResponse();
      response.categories = this.expertiseService.getAllCategories();
      return ResponseEntity.ok(response);
    } catch(Exception e) {
      log.error("Unexpected error while listing categories", e);
      return ResponseEntity.status(500).build();
    }
  }
  
  @GetMapping("/expertise")
  public ResponseEntity<ListExpertiseResponse> listExpertise() {
    try {
      ListExpertiseResponse response = new ListExpertiseResponse();
      response.expertise = this.expertiseService.getAllExpertise();
      return ResponseEntity.ok(response);
    } catch(Exception e) {
      log.error("Unexpected error while listing expertise", e);
      return ResponseEntity.status(500).build();
    }
  }

}
