package com.infosociety.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Profiles;


/**
 * SocialApplication.
 * 
 * Web application bootstrap class. 
 *
 * @author Baruch Speiser, Cambium.
 */
@SpringBootApplication
public class SocialApplication {
  public static final Profiles PROFILE_DEV = Profiles.of("dev");
  public static final Profiles PROFILE_PROD = Profiles.of("prod");

	/**
	 * Program entry point. 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}

}
