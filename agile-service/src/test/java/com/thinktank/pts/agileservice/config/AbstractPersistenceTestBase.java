package com.thinktank.pts.agileservice.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties", properties = {
		"spring.cloud.discovery.enabled=false", "spring.zipkin.enabled=false" })
public abstract class AbstractPersistenceTestBase extends AbstractTestBase {

}