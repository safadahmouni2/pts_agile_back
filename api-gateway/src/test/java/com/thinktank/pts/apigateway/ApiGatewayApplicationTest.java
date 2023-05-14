package com.thinktank.pts.apigateway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = { "eureka.client.enabled=false" })
class ApiGatewayApplicationTest {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		Assertions.assertThat(this.context).isNotNull();
	}

}
