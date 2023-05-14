package com.thinktank.pts.apibase.graphql.exception;

import java.util.Collections;
import java.util.List;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.language.SourceLocation;

public class InvalidStateException extends ExceptionToBeForwardedToClient {

	private static final long serialVersionUID = 1L;

	private final String customMessage;

	public InvalidStateException(String customMessage) {
		super();
		this.customMessage = customMessage;
	}

	@Override
	public String getMessage() {
		return getMessage(this.customMessage);
	}

	@Override
	public List<SourceLocation> getLocations() {
		return Collections.emptyList();
	}

	@Override
	public ErrorClassification getErrorType() {
		return ErrorType.ValidationError;
	}

	private static String getMessage(String message) {
		return message + " : ";
	}

}
