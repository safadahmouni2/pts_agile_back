package com.thinktank.pts.apibase.context.actor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActorContextBuilderImpl implements ActorContextBuilder {

	@Override
	public ActorContext buildActorContext(ActorInputData input) {
		ActorContext result = ActorContextHolder.createEmptyContext();
		log.debug("buildActorContext [input={}]", input);

		if (input.getActorFromRequest() != null) {
			result = buildWithRequestData(input);
		}
		return result;

	}

	private ActorContext buildWithRequestData(ActorInputData input) {
		ActorContext result = ActorContext.builder().name(input.getActorFromRequest()).build();
		log.debug("actor from request [{}|{} {}]", result.getName(), input.getRequestMethod(), input.getRequestURI());
		return result;
	}

}
