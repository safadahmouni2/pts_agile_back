package com.thinktank.pts.apibase.graphql;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractGraphQlArgumentAwareBaseInput {

	/** GraphQl arguments: attribute value(s) by attibute name */
	private Map<String, Object> arguments;

	/**
	 * Set all graphql arguments before a attribute will be patched. So we can decide (see {@link #canPatch(String)}),
	 * if we need to patch values of this input. Important for null-value-updatablility.
	 * 
	 * @param arguments
	 */
	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public Map<String, Object> getArguments() {
		return Collections.unmodifiableMap(this.arguments);
	}

	/**
	 * The input can patch if the requested attribute is part of the arguments of the current graphql payload.
	 * 
	 * @param argumentName
	 *            attribute about to be patched
	 * @return
	 */
	public boolean canPatch(String argumentName) {
		boolean result = arguments != null && arguments.containsKey(argumentName);

		log.debug("canPatch for '{}'={}", argumentName, result);

		return result;
	}

}
