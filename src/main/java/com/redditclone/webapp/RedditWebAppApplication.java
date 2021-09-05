package com.redditclone.webapp;

import com.redditclone.webapp.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAsync
@EnableWebSecurity
@Import(SwaggerConfiguration.class)
public class RedditWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditWebAppApplication.class, args);
	}

}
