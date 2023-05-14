package com.thinktank.pts.agileservice.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.thinktank.pts.apibase.context.BaseResourceServerConfig;

/**
 * 
 * @author zouhairs
 * @since 2 Mar 2023
 *
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends BaseResourceServerConfig {

	@Override
	public void configure(HttpSecurity http) throws Exception {

		super.configure(http);

		http.authorizeRequests()
				.requestMatchers(
						EndpointRequest.to(InfoEndpoint.class, HealthEndpoint.class, PrometheusScrapeEndpoint.class))
				.permitAll().antMatchers("/graphql/schema.json", "/actuator", "/graphql/**").permitAll().anyRequest()
				.authenticated();

	}
}
