package com.thinktank.pts.apibase.graphql.scalar;

import static graphql.scalars.util.Kit.typeName;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.function.Function;

import graphql.Internal;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

/**
 * 
 * @author zouhairs
 * @since 5 Apr 2023
 *
 */
@Internal
public final class LocalDateTimeScalar {

	public static final GraphQLScalarType INSTANCE;

	private LocalDateTimeScalar() {
	}

	private static final DateTimeFormatter customOutputFormatter = getCustomDateTimeFormatter();

	static {
		Coercing<LocalDateTime, String> coercing = new Coercing<LocalDateTime, String>() {

			@Override
			public String serialize(final Object input) throws CoercingSerializeException {
				LocalDateTime localDateTime;
				if (input instanceof LocalDateTime) {
					localDateTime = (LocalDateTime) input;
				} else if (input instanceof String) {
					localDateTime = parseLocalDateTime(input.toString(), CoercingSerializeException::new);
				} else {
					throw new CoercingSerializeException(
							"Expected something we can convert to 'java.time.LocalDateTime' but was '" + typeName(input)
									+ "'.");
				}
				try {
					return customOutputFormatter.format(localDateTime);
				} catch (DateTimeException e) {
					throw new CoercingSerializeException(
							"Unable to turn TemporalAccessor into LocalDateTime because of : '" + e.getMessage()
									+ "'.");
				}
			}

			@Override
			public LocalDateTime parseValue(final Object input) throws CoercingParseValueException {
				LocalDateTime localDateTime;
				if (input instanceof LocalDateTime) {
					localDateTime = (LocalDateTime) input;
				} else if (input instanceof String) {
					localDateTime = parseLocalDateTime(input.toString(), CoercingParseValueException::new);
				} else {
					throw new CoercingParseValueException("Expected a 'String' but was '" + typeName(input) + "'.");
				}

				return localDateTime;
			}

			@Override
			public LocalDateTime parseLiteral(final Object input) throws CoercingParseLiteralException {
				if (!(input instanceof StringValue)) {
					throw new CoercingParseLiteralException(
							"Expected AST type 'StringValue' but was '" + typeName(input) + "'.");
				}
				return parseLocalDateTime(((StringValue) input).getValue(), CoercingParseLiteralException::new);
			}

			private LocalDateTime parseLocalDateTime(String s, Function<String, RuntimeException> exceptionMaker) {
				try {
					TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(s);
					return LocalDateTime.from(temporalAccessor);
				} catch (DateTimeParseException e) {
					throw exceptionMaker.apply(
							"Invalid local date time value : '" + s + "'. because of : '" + e.getMessage() + "'");
				}
			}

		};

		INSTANCE = GraphQLScalarType.newScalar().name("LocalDateTime")
				.description("A slightly refined version of RFC-3339 compliant DateTime Scalar").coercing(coercing)
				.build();
	}

	private static DateTimeFormatter getCustomDateTimeFormatter() {
		return new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE).appendLiteral('T')
				.appendValue(HOUR_OF_DAY, 2).appendLiteral(':').appendValue(MINUTE_OF_HOUR, 2).appendLiteral(':')
				.appendValue(SECOND_OF_MINUTE, 2).appendFraction(NANO_OF_SECOND, 3, 3, true).toFormatter();
	}

}