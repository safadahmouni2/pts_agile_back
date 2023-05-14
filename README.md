# Monorepository parent for PTS services

Every subdirectory is a separate maven-project and uses the parent pom in the root of the monorepository.

The parent pom has all the projects as modules simply for the purposes to give eclipse hints in which order to build the separate projects (libraries first).

```
<modules>
		<module>api-base</module>
		...
		<module>agile-service</module>
		...
</modules>

```

## Repository Contents

### Libraries and modules
* [api-base](./api-base)
### Services
* [agile-service](./agile-service)


## Plugin- and dependency-management. 
Common used libraries should be configured in the parent pom.
All version numbers are configured in the properties section

```
<properties>
		<!-- Maven properties -->
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.version>11</java.version>
		...
		...
		<!-- plugin versions -->
		<maven-jar-plugin.version>3.2.0</maven-jar-plugin.version>

		<!-- 3rd party libraries -->
		<spring-cloud.version>2021.0.1</spring-cloud.version>
		...
		...
</properties>
```

Very specific dependencies that are used by one project only should stay in the projects pom only and not in the parent pom.

## Development on local machine

See [devlocal](./_devlocal) for things needed in local development.