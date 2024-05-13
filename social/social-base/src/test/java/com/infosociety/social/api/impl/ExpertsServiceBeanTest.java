package com.infosociety.social.api.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.infosociety.social.api.impl.ExpertsServiceBean;
import com.infosociety.social.persistence.ExpertsProvider;
import com.infosociety.social.types.AcademicDegree;
import com.infosociety.social.types.Category;
import com.infosociety.social.types.Country;
import com.infosociety.social.types.Expert;
import com.infosociety.social.types.Expertise;

public class ExpertsServiceBeanTest {
  ExpertsServiceBean service = new ExpertsServiceBean();

  @Before
  public void init() {
    service.setProvider(new ExpertsProvider() {
      @Override
      public Expert getBasicListing(long id) {
        return (id == 37)
            ? new Expert(37, "Jon", "Hatfield", "Agronomist", "ACME", "89751", new Country(2, "UK", "United Kingdom"), 
                "images/profiles/37.jpg", "jhatfield@acme.com", "2567813543", 
                Collections.emptyList(), Collections.emptyList(), Expert.BASIC_LISTING)
            : null;
      }
  
      @Override
      public Expert getFullProfile(long id) {
        return (id == 37)
            ? new Expert(37, "Jon", "Hatfield", "Agronomist", "ACME", "89751", new Country(2, "UK", "United Kingdom"), 
                "images/profiles/37.jpg", "jhatfield@acme.com", "2567813543", 
                Arrays.asList(new Expertise(22, "Fertilizer Acidity", new Category(9, "Fertilizers"))), 
                Arrays.asList(new AcademicDegree(84, "Ecology")),
                Expert.FULL_PROFILE)
            : null;
      }
    });
  }

  @Test
  public void testGetExpert() {
    assertNull(service.getExpert(22, null));

    Expert x = service.getExpert(37, Expert.FULL_PROFILE);
    assertNotNull(x);
    assertNotNull(x.getFirstName());
    assertNotNull(x.getCountry());
    assertNotNull(x.getAcademicDegrees());
    assertNotNull(x.getExpertise());
    assertFalse(x.getAcademicDegrees().isEmpty());
    assertFalse(x.getExpertise().isEmpty());
    assertTrue(x.isFullProfile());
    
    x = service.getExpert(37, Expert.BASIC_LISTING);
    assertNotNull(x);
    assertNotNull(x.getFirstName());
    assertNotNull(x.getCountry());
    assertNotNull(x.getAcademicDegrees());
    assertNotNull(x.getExpertise());
    assertTrue(x.getAcademicDegrees().isEmpty());
    assertTrue(x.getExpertise().isEmpty());
    assertFalse(x.isFullProfile());
    
    x = service.getExpert(37, null);
    assertNotNull(x);
    assertFalse(x.isFullProfile());
  }

}
