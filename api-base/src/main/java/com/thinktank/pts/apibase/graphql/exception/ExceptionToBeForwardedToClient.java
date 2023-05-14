package com.thinktank.pts.apibase.graphql.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.GraphQLError;

/**
 * Base class for Exceptions to be forwarded to the GraphQL client, i.e. exceptions that should have a detailed message
 * in the error section of the response instead of a generic "Internal Server Error(s)..." message.
 * <p>
 * Provided to please a client with a helpful error message for understanding the problem.
 * <p>
 * Note this exception triggers a rollback if thrown inside a transaction...then we get 400 without anything reported to
 * the client! It's only useful when used inside a Query- or Mutationresolver.
 * 
 * @author zouhairs
 * @since 22 Feb 2023
 *
 */
public abstract class ExceptionToBeForwardedToClient extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1L;

	@Override
	@JsonIgnore
	public StackTraceElement[] getStackTrace() {
		return super.getStackTrace();
	}

}