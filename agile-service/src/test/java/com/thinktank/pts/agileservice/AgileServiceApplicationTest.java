package com.thinktank.pts.agileservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;

class AgileServiceApplicationTest extends AbstractPersistenceTestBase {

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		Assertions.assertThat(this.context).isNotNull();
	}

}
