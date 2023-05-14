package com.thinktank.pts.apibase.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * config used to create an instance the RestTemplate service
 * 
 * @author zouhairs
 * @since 20 Mar 2023
 *
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
