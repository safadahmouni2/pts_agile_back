package com.thinktank.pts.agileservice.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.api.graphql.output.TopicMaxOrderOutput;
import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.repository.TopicRepository;

/**
 * 
 * @author hajjib
 * @since Feb 23, 2023
 *
 */

class TopicServiceTest extends AbstractPersistenceTestBase {

	@Autowired
	private TopicRepository topicRepository;

	@BeforeEach
	void before() {
		topicRepository.deleteAll();

	}

	@Autowired
	private TopicService topicService;

	@Test
	void createTopicTest() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		Topic created = topicService.save(topic);
		assertThat(created.getId(), is(notNullValue()));

	}

	/**
	 * {@link TopicService#getTopicMaxOrderByProduct(Long)}
	 */
	@Test
	void testGetTopicMaxOrderByProduct() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Topic> topics = easyRandom.objects(Topic.class, 3).collect(Collectors.toList());
		topics.forEach(topic -> {
			topic.setId(null);
			topic.setProductId(productId);
			topic = topicService.save(topic);
		});
		// Action
		TopicMaxOrderOutput result = topicService.getTopicMaxOrderByProduct(productId);
		// ASSERT
		assertThat(result, is(notNullValue()));
	}

	/**
	 * Tests the behavior of {@link TopicService#getTopicsByProductId(Long)} when a product ID is provided. Expects the
	 * method to return the list of Topics by productId.
	 */

	@Test
	void getTopicsByProductIdTest() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Topic> topics = easyRandom.objects(Topic.class, 3).collect(Collectors.toList());
		topics.forEach(topic -> {
			topic.setId(null);
			topic.setProductId(productId);
		});
		topicRepository.saveAll(topics);
		// ACTION
		List<Topic> result = topicService.getTopicsByProductId(productId);
		// ASSERT
		assertEquals(3, result.size());
	}

	@Test
	void getTopicById() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topic = topicService.save(topic);
		// ACTION
		topic = topicService.getById(topic.getId());
		// ASSERT
		assertThat(topic.getId(), is(notNullValue()));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Topic.class)));
		return result;
	}

}
