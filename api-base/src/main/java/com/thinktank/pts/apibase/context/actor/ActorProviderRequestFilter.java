package com.thinktank.pts.apibase.context.actor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Determines the actor information for every request and sets it into the {@link ActorContext}. TODO NB: Actor
 * information has to be secured against spoofing!
 * 
 * @author zouhairs
 * @since 1 Mar 2023
 *
 */
public class ActorProviderRequestFilter extends OncePerRequestFilter {

	private ActorContextBuilder actorContextBuilder;

	public ActorProviderRequestFilter(ActorContextBuilder actorContextBuilder) {
		this.actorContextBuilder = actorContextBuilder;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// @formatter:off
		ActorInputData actorInputData = ActorInputData.builder()
				.actorFromRequest(request.getHeader(ActorContextParameterNames.ACTOR))
				.requestURI(request.getRequestURI())
				.requestMethod(request.getMethod())
				.build();
		// @formatter:on
		ActorContext actor = actorContextBuilder.buildActorContext(actorInputData);

		ActorContextHolder.setActor(actor);

		filterChain.doFilter(request, response);

		ActorContextHolder.removeActor();
	}

}
