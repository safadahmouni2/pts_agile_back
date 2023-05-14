package com.thinktank.pts.apibase.graphql.scalar;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

/**
 * 
 * @author zouhairs
 * @since 5 Apr 2023
 *
 */
@PublicApi
public class CustomExtendedScalars {

	public static final GraphQLScalarType localDateTime = LocalDateTimeScalar.INSTANCE;

}