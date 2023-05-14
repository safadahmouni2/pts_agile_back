package com.thinktank.pts.apibase.graphql.exception;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.language.SourceLocation;

/**
 * Exceptions thrown in case of an unknown ID
 *
 */
public class UnknownIDException extends ExceptionToBeForwardedToClient {

	private static final long serialVersionUID = 1L;

	private final Long id;

	public UnknownIDException(Long id) {
		this.id = id;
	}

	@Override
	public List<SourceLocation> getLocations() {
		return Collections.emptyList();
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.ValidationError;
	}

	@Override
	public String getMessage() {
		return getMessage(id);
	}

	@Override
	public Map<String, Object> getExtensions() {
		return Collections.singletonMap("id", id);
	}

	private static String getMessage(Long id) {
		return "Unknown ID (" + id + ")";
	}
}
