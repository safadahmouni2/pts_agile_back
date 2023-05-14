package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import javax.transaction.Transactional;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.DefaultTopic;
import com.thinktank.pts.agileservice.model.Topic;

class DefaultTopicRepositoryTest extends AbstractPersistenceTestBase {

	@Autowired
	private DefaultTopicRepository defaultTopicRepository;

	@Autowired
	private TopicRepository topicRepository;

	@BeforeEach
	void before() {
		defaultTopicRepository.deleteAll();
		topicRepository.deleteAll();
	}

	@AfterEach
	void after() {
		defaultTopicRepository.deleteAll();
	}

	@Test
	void saveDefaultTopic_Success() {
		// Arrange
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topic = topicRepository.save(topic);

		DefaultTopic defaultTopic = generator.nextObject(DefaultTopic.class);
		defaultTopic.setTopicId(topic.getId());
		defaultTopic.setProductId(123L);

		// Action
		DefaultTopic createdDefaultTopic = defaultTopicRepository.save(defaultTopic);

		// Assert
		assertThat(createdDefaultTopic.getId(), is(notNullValue()));

	}

	/**
	 * Tests the behavior of {@link DefaultTopicRepository#findByProductId(Long)} when a product ID is provided. Expects
	 * the method to return defaultTopic by productId.
	 */

	@Test
	void getDefaultTopicByProductId_Success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topic = topicRepository.save(topic);

		DefaultTopic defaultTopic = generator.nextObject(DefaultTopic.class);
		defaultTopic.setTopicId(topic.getId());
		defaultTopic.setProductId(12L);

		defaultTopic.setId(null);
		defaultTopic = defaultTopicRepository.save(defaultTopic);
		// ACTION
		defaultTopic = defaultTopicRepository.findByProductId(defaultTopic.getProductId());
		// ASSERT
		assertThat(defaultTopic.getProductId(), is(notNullValue()));
	}

	@Transactional
	@Test
	void deleteDefaultTopicByProductId_Success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(Topic.class)));
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(DefaultTopic.class)));

		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topic = topicRepository.save(topic);

		DefaultTopic defaultTopic = generator.nextObject(DefaultTopic.class);
		defaultTopic.setTopicId(topic.getId());
		defaultTopic.setProductId(123L);

		defaultTopic.setId(null);
		defaultTopic = defaultTopicRepository.save(defaultTopic);
		// ACTION
		long deletedCount = defaultTopicRepository.deleteByProductId(123L);
		// ASSERT
		assertThat(deletedCount, is(1L));
	}

	@Test
	void existDefaultTopicByProductId_Success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topic = topicRepository.save(topic);

		DefaultTopic defaultTopic = generator.nextObject(DefaultTopic.class);
		defaultTopic.setTopicId(topic.getId());

		defaultTopic.setId(null);
		defaultTopic = defaultTopicRepository.save(defaultTopic);
		// ACTION
		boolean cc = defaultTopicRepository.existsByTopicId(defaultTopic.getTopicId());
		// ASSERT
		assertThat(cc, is(true));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters parameters = new EasyRandomParameters();
		parameters.randomize(named("productId").and(ofType(String.class)).and(inClass(DefaultTopic.class)),
				new StringRandomizer(10, 10));
		parameters.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Topic.class)));
		return parameters;
	}
}
