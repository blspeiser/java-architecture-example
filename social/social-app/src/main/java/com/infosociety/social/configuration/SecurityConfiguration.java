package com.infosociety.social.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.infosociety.social.SocialApplication;
import com.infosociety.social.security.TokenAuthorizationFilter;

/**
 * SecurityConfiguration.
 *
 * @author Baruch Speiser, Cambium.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
  @Autowired
  private Environment environment;
  @Autowired
  private PublicEndpoints endpoints;
  @Value("${security.application.key}")
  private String apiKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(getAvailableEndpoints())
      .permitAll()
      .anyRequest().authenticated()
      .and().headers().frameOptions().sameOrigin()
      .and().csrf().disable()
      .exceptionHandling()
      .authenticationEntryPoint((request, response, authException) -> log.info(authException.getLocalizedMessage()))
      .and().addFilterBefore(new TokenAuthorizationFilter(environment, apiKey), UsernamePasswordAuthenticationFilter.class)
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  private String[] getAvailableEndpoints() {
    List<String> list = environment.acceptsProfiles(SocialApplication.PROFILE_DEV)
        ? endpoints.getDevelopmentEnvironmentPatterns()
        : endpoints.getProductionEnvironmentPatterns();
    String[] endpoints = new String[list.size()];
    list.toArray(endpoints);
    return endpoints;
  }
}
