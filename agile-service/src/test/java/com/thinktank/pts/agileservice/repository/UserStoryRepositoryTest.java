package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import java.util.List;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.StateCountDto;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.model.UserStory;

/**
 * 
 * @author lamiam
 * @since 11 Apr 2023
 *
 */
class UserStoryRepositoryTest extends AbstractPersistenceTestBase {

	private static final Long PRODUCT_ID = 1017898L;

	@Autowired
	private UserStoryRepository userStoryRepository;

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private FeatureRepository featureRepository;

	@Autowired
	private TopicRepository topicRepository;

	@BeforeEach
	void before() {
		userStoryRepository.deleteAll();
	}

	@AfterEach
	void after() {
		userStoryRepository.deleteAll();
	}

	@Test
	void createUserStory_success() {

		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);

		UserStory userStory = easyRandom.nextObject(UserStory.class);
		userStory.setId(null);

		// ACTION
		userStory = userStoryRepository.save(userStory);
		// ASSERT
		assertThat(userStory.getId(), is(notNullValue()));

	}

	@Test
	void findAllUserStoriesBySprint_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(Sprint.class)));
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(UserStory.class)));

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		UserStory userStory = easyRandom.nextObject(UserStory.class);
		userStory.setId(null);
		userStory.setSprint(sprint1);
		userStoryRepository.save(userStory);

		// ACTION
		List<UserStory> userStories = userStoryRepository.findBySprintId(sprint1.getId());
		// ASSERT
		assertThat(userStories, is(not(empty())));

	}

	@Test
	void findAllUserStoriesByFeature_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);

		Feature feature = easyRandom.nextObject(Feature.class);
		feature.setId(null);
		feature = featureRepository.save(feature);

		UserStory userStory = easyRandom.nextObject(UserStory.class);
		userStory.setId(null);
		userStory.setFeature(feature);
		userStoryRepository.save(userStory);

		// ACTION
		List<UserStory> userStories = userStoryRepository.findByFeatureId(feature.getId());
		// ASSERT
		assertThat(userStories, is(not(empty())));

	}

	@Test
	void findAllUserStoriesByTopic_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(Topic.class)));
		parameters.excludeField(FieldPredicates.named("ticketId").and(FieldPredicates.inClass(UserStory.class)));

		EasyRandom easyRandom = new EasyRandom(parameters);

		Topic topic = easyRandom.nextObject(Topic.class);
		topic.setId(null);
		topic = topicRepository.save(topic);

		UserStory userStory = easyRandom.nextObject(UserStory.class);
		userStory.setId(null);
		userStory.setTopic(topic);
		userStoryRepository.save(userStory);

		// ACTION
		List<UserStory> userStories = userStoryRepository.findByTopicId(topic.getId());
		// ASSERT
		assertThat(userStories, is(not(empty())));

	}

	@Test
	void findAllUserStoriesByProduct_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		UserStory userStory = easyRandom.nextObject(UserStory.class);
		userStory.setId(null);
		userStory.setProductId(PRODUCT_ID);
		userStoryRepository.save(userStory);

		// ACTION
		List<UserStory> userStories = userStoryRepository.findByProductId(PRODUCT_ID);
		// ASSERT
		assertThat(userStories, is(not(empty())));

	}

	@Test
	void getCountUsPerStateBySprint_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint = easyRandom.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		UserStory userStory1 = easyRandom.nextObject(UserStory.class);
		userStory1.setId(null);
		userStory1.setSprint(sprint);
		userStory1.setStateId(1L);
		userStoryRepository.save(userStory1);

		UserStory userStory2 = easyRandom.nextObject(UserStory.class);
		userStory2.setId(null);
		userStory2.setSprint(sprint);
		userStory2.setStateId(1L);
		userStoryRepository.save(userStory2);

		UserStory userStory3 = easyRandom.nextObject(UserStory.class);
		userStory3.setId(null);
		userStory3.setSprint(sprint);
		userStory3.setStateId(2L);
		userStoryRepository.save(userStory3);

		// ACTION
		List<StateCountDto> stateCounts = userStoryRepository.getCountUsPerStateBySprint(sprint.getId());
		// ASSERT
		assertThat(stateCounts, is(not(empty())));

	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.randomize(named("userCode").and(ofType(String.class)).and(inClass(UserStory.class)),
				new StringRandomizer(10, 10));
		result.excludeField(FieldPredicates.named("topic").and(FieldPredicates.inClass(UserStory.class)));
		result.excludeField(FieldPredicates.named("feature").and(FieldPredicates.inClass(UserStory.class)));
		result.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(UserStory.class)));

		result.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));

		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Feature.class)));

		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Topic.class)));
		return result;
	}

}
