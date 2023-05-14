package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.TopicInput;
import com.thinktank.pts.agileservice.model.Topic;

/**
 * 
 * @author chemkhih
 * @since Feb 23, 2023
 *
 */

class TopicInputMapperTest {

	private TopicInputMapper topicInputMapper = new TopicInputMapper();

	/**
	 * {@link TopicInputMapper#create(TopicInput)}
	 */
	@Test
	void createTopicTest() {

		EasyRandom generator = new EasyRandom();
		TopicInput topicInput = generator.nextObject(TopicInput.class);
		Topic createdTopic = topicInputMapper.create(topicInput);
		assertThat(createdTopic.getName()).isEqualTo(topicInput.getName());
	}

	/**
	 * {@link TopicInputMapper#patchFields(TopicInput, Topic)}
	 */
	@Test
	void patchFieldsTest() {
		Map<String, Object> arg = new HashMap<>();

		EasyRandom generator = new EasyRandom();
		Topic topic = generator.nextObject(Topic.class);
		TopicInput topicInput = generator.nextObject(TopicInput.class);
		arg.put("name", "name");
		topicInput.setArguments(arg);
		topicInputMapper.patchFields(topicInput, topic);
		assertThat(topic.getName()).isEqualTo(topicInput.getName());
	}
}
