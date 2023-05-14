package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Topic;

class TopicRepositoryTest extends AbstractPersistenceTestBase {

	@Autowired
	private TopicRepository topicRepository;

	@BeforeEach
	void before() {
		topicRepository.deleteAll();

	}

	@AfterEach
	void after() {
		topicRepository.deleteAll();

	}

	@Test
	void createTopic_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		// ACTION
		topic = topicRepository.save(topic);
		// ASSERT
		assertThat(topic.getId(), is(notNullValue()));

	}

	@Test
	void findAllTopics_success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setId(null);
		topicRepository.save(topic);
		// ACTION
		List<Topic> topics = topicRepository.findAll();
		// ASSERT
		assertThat(topics, is(not(empty())));

	}

	/**
	 * Tests the {@link TopicRepository#getTopicMaxOrderByProduct(Long)}.
	 */
	@Test
	void getTopicMaxOrderByProduct_success() {
		// ARRANGE
		Long productId = 1L;
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);
		List<Topic> topics = easyRandom.objects(Topic.class, 3).collect(Collectors.toList());
		topics.forEach(topic -> {
			topic.setProductId(productId);
		});
		topicRepository.saveAll(topics);
		// ACTION
		Long maxOrder = topicRepository.getTopicMaxOrderByProduct(productId);
		// ASSERT
		assertThat(maxOrder, is(not(nullValue())));
	}

	// test for update Topic
	@Test
	void updateTopic_success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom generator = new EasyRandom(parameters);
		Topic topic = generator.nextObject(Topic.class);
		topic.setDescription("try reposetery test");
		// ACTION
		Topic topicUpdated = topicRepository.save(topic);
		// ASSERT
		assertThat(topicUpdated, is(notNullValue()));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Topic.class)));
		return result;
	}
}
