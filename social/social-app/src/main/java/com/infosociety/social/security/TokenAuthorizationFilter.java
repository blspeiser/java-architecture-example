package com.infosociety.social.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.infosociety.social.SocialApplication;

/**
 * TokenAuthorizationFilter.
 *
 *  Compares the received Bearer token against a preconfigured API key. 
 *
 * @author Baruch Speiser, Cambium.
 */
public class TokenAuthorizationFilter implements Filter {
  private static final Logger log = LoggerFactory.getLogger(TokenAuthorizationFilter.class);
  private static final String AUTHORIZATION_TYPE_PREFIX = "Bearer ";
    
  private Environment environment;
  private String apiKey;
  
  public TokenAuthorizationFilter(Environment environment, String apiKey) {
    this.environment = environment;
    this.apiKey = apiKey;
    if(this.apiKey == null || this.apiKey.isEmpty()) {
      log.error("No API key configured! Check the configuration of the"
          + " security.application.key property - in the meanwhile, refusing all requests!");
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
  throws IOException, ServletException 
  {
    if(request instanceof HttpServletRequest) {
      //We only care about HTTP requests, there isn't anything else here anyway
      HttpServletRequest req = (HttpServletRequest)request;
      if(!isAuthenticated(req)) {
        log.debug("Unauthorized request");
        //Not allowed in, reject the request:
        HttpServletResponse res = (HttpServletResponse)response;
        res.sendError(HttpStatus.UNAUTHORIZED.value());
        return;
      }
      //otherwise, fall through and continue handling the request
    } 
    log.trace("Authorized request");
    chain.doFilter(request, response);
  }

  /** Check to see if the request includes our custom authorization header. */
  private boolean isAuthenticated(HttpServletRequest request) {
    if(this.apiKey == null || this.apiKey.isEmpty()) {
      //see log in constructor
      return false;
    }
    try {
      String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
      if(null != authorization && authorization.startsWith(AUTHORIZATION_TYPE_PREFIX)) {
        String token = authorization.substring(AUTHORIZATION_TYPE_PREFIX.length()).trim();
        return token.equals(apiKey); 
      }
      //otherwise:
      if(environment.acceptsProfiles(SocialApplication.PROFILE_DEV)) {
        log.warn("Development mode currently enabled - allowing all requests, even without authentication!");
        return true;
      }
      return false;
    } catch(Exception e) {
      log.warn("Failure while parsing authorization header, could have been sent in an incorrect format", e);
      return false;
    }
  }

}
