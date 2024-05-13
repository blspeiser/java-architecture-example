package com.infosociety.social.configuration;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * PublicEndpoints.
 * 
 * This class indicates which endpoints should be considered public and available
 * for external consumption.
 * 
 * @author Baruch Speiser, Cambium.
 */
@Service
public class PublicEndpoints {

  /**
   * Provide the list of URL patterns allowed when the system is operating in a
   * development enviornment.
   * @return list of URL patterns
   */
  public List<String> getDevelopmentEnvironmentPatterns() {
    return Arrays.asList(
        "/api/social/**", 
        "/h2/**", 
        "/**", 
        "/swagger-ui/**", 
        "/v2/api-docs/**");
  }

  /**
   * Provide the list of URL patterns allowed when the system is operating in a
   * production enviornment.
   * @return list of URL patterns
   */
  public List<String> getProductionEnvironmentPatterns() {
    return Arrays.asList("/api/social/**");
  }
}
