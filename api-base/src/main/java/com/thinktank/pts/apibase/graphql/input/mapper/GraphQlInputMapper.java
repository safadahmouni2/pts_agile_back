package com.thinktank.pts.apibase.graphql.input.mapper;

import java.util.Map;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

public class GraphQlInputMapper {

	/**
	 * Allows to populate suitable arguments(by argumentName) to given input. Used in case of parent/child inputs.
	 * 
	 * This is about null-updatablility, I think.
	 * 
	 * @param input
	 * @param arguments
	 * @param argumentName
	 */
	@SuppressWarnings("unchecked")
	protected void putChildArguments(AbstractGraphQlArgumentAwareBaseInput input, Map<String, Object> arguments,
			String argumentName) {
		if (input != null && arguments != null) {
			input.setArguments((Map<String, Object>) arguments.get(argumentName));
		}
	}
}
