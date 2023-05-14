package com.thinktank.pts.apibase.context.actor;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * The <b>ActorContext</b> provides information about the primordial initiator of a request/use case/process and is
 * automatically populated for all requests, even inter-microservice-calls (see <b>ActorProviderRequestFilter</b>).
 * 
 * <li>Could be a user who is logged in into a frontend and executes some action. Could be a quartz job performing some
 * work.
 * <li>The actor information is required for different functionalities, e.g. to feed the fill creator / editor)
 * 
 * @author zouhairs
 * @since 2 Mar 2023
 *
 */
@Data
@Builder
public class ActorContext {

	private String name;

	public boolean isEmpty() {
		return StringUtils.hasLength(name);
	}
}