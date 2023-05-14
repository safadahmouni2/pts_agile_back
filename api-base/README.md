# API-BASE

Provides spring configurations and common classes for apis (i.e. service endpoints with GraphQL or REST) of spring boot microservices.

## Features

- [ApiBaseAutoConfiguration](src/main/java/com/thinktank/pts/apibase/ApiBaseAutoConfiguration.java) is the entry point for Spring context configuration.
	(This is configured in [spring.factories](src/main/resources/META-INF/spring.factories)). Activates [CustomGraphQLConfig](src/main/java/com/thinktank/pts/apibase/graphql/CustomGraphQLConfig.java) that defines *SchemaParserOptions*, custom *Scalars*, *ExecutionStrategies* etc.
	
- [AbstractParentAwareMapperForRest](src/main/java/com/thinktank/pts/apibase/rest/mapper/AbstractParentAwareMapperForRest.java)	
- Core classes and functionalities to handle RestApi exceptions