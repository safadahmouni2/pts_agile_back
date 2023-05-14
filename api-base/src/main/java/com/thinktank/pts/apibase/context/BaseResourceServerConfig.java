package com.thinktank.pts.apibase.context;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import com.thinktank.pts.apibase.context.actor.ActorContextBuilderImpl;
import com.thinktank.pts.apibase.context.actor.ActorProviderRequestFilter;

public class BaseResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(new ActorProviderRequestFilter(new ActorContextBuilderImpl()),
				AnonymousAuthenticationFilter.class);
	}
}
