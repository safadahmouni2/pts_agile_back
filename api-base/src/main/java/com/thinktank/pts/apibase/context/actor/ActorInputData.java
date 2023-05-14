package com.thinktank.pts.apibase.context.actor;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ActorInputData {

	private String actorFromRequest;

	/** for diagnostics/logging */
	private String requestURI;

	/** for diagnostics/logging */
	private String requestMethod;

}
