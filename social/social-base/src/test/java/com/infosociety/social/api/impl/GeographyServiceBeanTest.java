package com.infosociety.social.api.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.infosociety.social.api.impl.GeographyServiceBean;
import com.infosociety.social.persistence.CountriesProvider;
import com.infosociety.social.types.Country;

public class GeographyServiceBeanTest {
  GeographyServiceBean service = new GeographyServiceBean();
  
  @Before
  public void init() {
    service.setProvider(new CountriesProvider() {
      @Override
      public List<Country> getAllCountries() {
        return Arrays.asList(new Country(2, "UK", "United Kingdom"));
      }
    });
    service.onInit();
  }
  
  @Test
  public void testGetAllCountries() {
    List<Country> list = service.getAllCountries();
    assertNotNull(list);
    assertFalse(list.isEmpty());
    Country c = list.get(0);
    assertNotNull(c);
    assertEquals(2, c.getID());
    assertEquals("UK", c.getCode());
    assertEquals("United Kingdom", c.getName());
  }

  @Test
  public void testLookupInt() {
    assertNull(service.lookup(6));
    Country c = service.lookup(2);
    assertEquals("United Kingdom", c.getName());
  }

  @Test
  public void testLookupString() {
    assertNull(service.lookup("US"));
    Country c = service.lookup("UK");
    assertEquals("United Kingdom", c.getName());
  }

}
