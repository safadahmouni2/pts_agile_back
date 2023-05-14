package com.thinktank.pts.apibase.graphql;

import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thinktank.pts.apibase.graphql.scalar.CustomExtendedScalars;

import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.kickstart.spring.web.boot.GraphQLWebAutoConfiguration;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.ObjectMapperConfigurerContext;
import graphql.kickstart.tools.SchemaParserOptions;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;

/**
 * Our GraphQL specifics.
 * <p>
 * We provide a custom graphql query and mutation execution strategy, because we chose to return entities as result
 * objects. If the transaction then wouldn't span the graphql layer, we would get a LazyInitializationException when
 * lazy loaded values/collections are resolved by graphql (see
 * https://stackoverflow.com/questions/48037601/lazyinitializationexception-with-graphql-spring).
 *
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnBean({ GraphQLResolver.class })
@ConditionalOnProperty(value = "graphql.servlet.enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore({ GraphQLWebAutoConfiguration.class })
public class CustomGraphQLConfig {

	@Bean
	public GraphQLScalarType bigDecimalScalar() {
		return ExtendedScalars.GraphQLBigDecimal;
	}

	@Bean
	public GraphQLScalarType dateScalar() {
		return ExtendedScalars.Date;
	}

	@Bean
	public GraphQLScalarType dateTimeScalar() {
		return ExtendedScalars.DateTime;
	}

	@Bean
	public GraphQLScalarType timeScalar() {
		return ExtendedScalars.Time;
	}

	@Bean
	public GraphQLScalarType localTimeScalar() {
		return ExtendedScalars.LocalTime;
	}

	@Bean
	public GraphQLScalarType objectScalar() {
		return ExtendedScalars.Object;
	}

	@Bean
	public GraphQLScalarType integerType() {
		return ExtendedScalars.GraphQLBigInteger;
	}

	@Bean
	public GraphQLScalarType longType() {
		return ExtendedScalars.GraphQLLong;
	}

	@Bean
	public GraphQLScalarType json() {
		return ExtendedScalars.Json;
	}

	@Bean
	public GraphQLScalarType localDateTimeScalar() {
		return CustomExtendedScalars.localDateTime;
	}

	@Bean
	public SchemaParserOptions schemaParserOptions() {
		return SchemaParserOptions.newOptions().objectMapperConfigurer((ObjectMapper mapper,
				ObjectMapperConfigurerContext context) -> mapper.registerModule(new JavaTimeModule())).build();
	}

	@Bean(GraphQLWebAutoConfiguration.QUERY_EXECUTION_STRATEGY)
	@ConditionalOnProperty(value = "pts.graphql.customquerystrategy.enabled", havingValue = "true", matchIfMissing = true)
	public AsyncExecutionStrategy queryStrategy() {
		return new AsyncExecutionStrategy() {

			@Override
			@Transactional
			public CompletableFuture<ExecutionResult> execute(ExecutionContext executionContext,
					ExecutionStrategyParameters parameters) {

				return super.execute(executionContext, parameters);
			}

		};
	}

	@Bean(GraphQLWebAutoConfiguration.MUTATION_EXECUTION_STRATEGY)
	@ConditionalOnProperty(value = "pts.graphql.custommutationstrategy.enabled", havingValue = "true", matchIfMissing = true)
	public AsyncSerialExecutionStrategy mutationStrategy() {
		return new AsyncSerialExecutionStrategy() {

			@Override
			@Transactional
			public CompletableFuture<ExecutionResult> execute(ExecutionContext executionContext,
					ExecutionStrategyParameters parameters) {

				return super.execute(executionContext, parameters);
			}

		};
	}
}
