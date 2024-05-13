package com.infosociety.social.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * WebConfiguration.
 *
 * @author Baruch Speiser, Cambium.
 */
@Configuration
@EnableSwagger2
public class WebConfiguration extends WebMvcConfigurationSupport {
  private static final String DEFAULT_WEBJAR_PATH = "classpath:/META-INF/resources/webjars";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //Add Swagger-based web resources that our UI serves up:
    registry.addResourceHandler("/swagger-ui/**")
      .addResourceLocations(DEFAULT_WEBJAR_PATH + "/springfox-swagger-ui/");
    registry.addResourceHandler("/webjars/**")
      .addResourceLocations(DEFAULT_WEBJAR_PATH + "/");
    //If the UI is served up by this web server, assume it is on the classpath somewhere in a /static folder:
    registry.addResourceHandler("/**")
      .addResourceLocations("classpath:/static/")  
      .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
  }

  @Bean
  public InternalResourceViewResolver defaultViewResolver() {
    //Allow Swagger resources to default to index.html inside its folder 
    return new InternalResourceViewResolver();
  }
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
    registry.addViewController("/swagger-ui" ).setViewName("redirect:/swagger-ui/");  
    registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
  }
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("GET", "POST", "PUT", "DELETE");
  }
  
  @Bean
  public Docket api() { 
    return new Docket(DocumentationType.SWAGGER_2)  
      .select()                                  
      .apis(RequestHandlerSelectors.any())              
      .paths(PathSelectors.ant("/api/social/**"))                          
      .build();                                           
  }

}
