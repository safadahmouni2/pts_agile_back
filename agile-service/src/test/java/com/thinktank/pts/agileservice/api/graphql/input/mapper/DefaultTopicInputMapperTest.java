package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.DefaultTopicInput;
import com.thinktank.pts.agileservice.model.DefaultTopic;

class DefaultTopicInputMapperTest {

	private DefaultTopicInputMapper inputMapper = new DefaultTopicInputMapper();

	/**
	 * {@link DefaultTopicInputMapper#create(DefaultTopicInput)}
	 */
	@Test
	void createDefaultTopicTest() {

		// Arrange
		EasyRandom generator = new EasyRandom();
		DefaultTopicInput input = generator.nextObject(DefaultTopicInput.class);

		// Action: map input to DefaultTopic
		DefaultTopic createdDefaultTopic = inputMapper.create(input);

		// Assert
		assertEquals(createdDefaultTopic.getProductId(), input.getProductId());
	}

}
