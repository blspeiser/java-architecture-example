package com.infosociety.social.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infosociety.social.api.ExpertsService;
import com.infosociety.social.rest.types.GetBasicProfileResponse;
import com.infosociety.social.rest.types.GetFullProfileResponse;
import com.infosociety.social.types.Expert;

/**
 * ProfileController.
 *
 * @author Baruch Speiser, Cambium.
 */
@RestController
@RequestMapping("/api/social/profile")
public class ExpertProfileController {
  private static final Logger log = LoggerFactory.getLogger(ListsController.class);
  @Autowired
  private ExpertsService service;
  
  /** service */
  public void setService(ExpertsService service) {
    this.service = service;
  }

  @GetMapping("/basic/{id}")
  public ResponseEntity<GetBasicProfileResponse> getBasicProfile(@PathVariable long id) {
    try {
      GetBasicProfileResponse response = new GetBasicProfileResponse();
      response.profile = this.service.getExpert(id, Expert.BASIC_LISTING);
      return (null == response.profile) 
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok(response);
    } catch(Exception e) {
      log.error("Unexpected error while fetching basic profile", e);
      return ResponseEntity.status(500).build();
    }
  }
  
  @GetMapping("/full/{id}")
  public ResponseEntity<GetFullProfileResponse> getFullProfile(@PathVariable long id) {
    try {
      GetFullProfileResponse response = new GetFullProfileResponse();
      response.profile = this.service.getExpert(id, Expert.FULL_PROFILE);
      return (null == response.profile) 
          ? ResponseEntity.notFound().build()
          : ResponseEntity.ok(response);
    } catch(Exception e) {
      log.error("Unexpected error while fetching full profile", e);
      return ResponseEntity.status(500).build();
    }
  }
  
}
