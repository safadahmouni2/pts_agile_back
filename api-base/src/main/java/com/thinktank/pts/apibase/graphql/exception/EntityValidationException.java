package com.thinktank.pts.apibase.graphql.exception;

import java.util.Collections;
import java.util.List;

import com.thinktank.pts.apibase.business.service.Notification;

import graphql.ErrorType;
import graphql.language.SourceLocation;

public class EntityValidationException extends ExceptionToBeForwardedToClient {

	private static final long serialVersionUID = 1L;

	private final transient Notification notification;

	public EntityValidationException(Notification notification) {
		this.notification = notification;
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
		return notification.getErrors().get(0);
	}
}