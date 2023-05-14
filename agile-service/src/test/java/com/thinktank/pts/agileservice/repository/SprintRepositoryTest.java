package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.MyDailyScrumDto;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.UserStory;

class SprintRepositoryTest extends AbstractPersistenceTestBase {

	private static final Long IN_PROGRESS_SPRINT_STATE_ID = 1017798L;
	private static final Long STARTED_DS_STATE_ID = 1017898L;

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private UserStoryRepository userStoryRepository;

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

	@BeforeEach
	void before() {
		dailyScrumRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	@AfterEach
	void after() {
		dailyScrumRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	@Test
	void getSprintByProductId_Success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(DailyScrum.class)));
		parameters.excludeField(FieldPredicates.named("dsParticipants").and(FieldPredicates.inClass(DailyScrum.class)));


		EasyRandom generator = new EasyRandom(parameters);

		Long productId1 = 1L;
		Long productId2 = 2L;

		Sprint sprint1 = generator.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1.setProductId(productId1);
		Sprint sprint2 = generator.nextObject(Sprint.class);
		sprint2.setProductId(productId2);

		sprintRepository.saveAll(Arrays.asList(sprint1, sprint2));

		// Action
		List<Sprint> result = sprintRepository.findByProductIdOrderByStartDateDesc(productId1);

		// Assert
		assertEquals(1, result.size());

	}

	@Test
	void createSprint_Success() {
		// Arrange

		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);

		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);

		// Action
		Sprint sprintCreated = sprintRepository.save(sprint);
		// Assert
		assertThat(sprintCreated.getId(), is(notNullValue()));

	}


	@Test
	void getMyDailyScrums_Success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(DailyScrum.class)));
		parameters.excludeField(FieldPredicates.named("dsParticipants").and(FieldPredicates.inClass(DailyScrum.class)));
		parameters.excludeField(FieldPredicates.named("created").and(FieldPredicates.inClass(DailyScrum.class)));

		EasyRandom generator = new EasyRandom(parameters);

		Long productId1 = 1L;
		Long productId2 = 2L;

		Sprint sprint1 = generator.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1.setStateId(IN_PROGRESS_SPRINT_STATE_ID);
		sprint1.setProductId(productId1);

		Sprint sprint2 = generator.nextObject(Sprint.class);
		sprint2.setId(null);
		sprint2.setStateId(IN_PROGRESS_SPRINT_STATE_ID);
		sprint2.setProductId(productId2);

		sprint1 = sprintRepository.save(sprint1);
		sprint2 = sprintRepository.save(sprint2);

		List<DailyScrum> dailyScrums = generator.objects(DailyScrum.class, 3).collect(Collectors.toList());
		for (DailyScrum ds : dailyScrums) {
			ds.setStateId(STARTED_DS_STATE_ID);
			ds.setSprint(sprint1);
		}


		dailyScrums = dailyScrumRepository.saveAll(dailyScrums);

		LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDate = startOfDay.toLocalDate().atTime(LocalTime.MAX);

		// Action
		List<MyDailyScrumDto> result = sprintRepository.getMyDailyScrums(startOfDay, endOfDate,
				Arrays.asList(productId1, productId2), IN_PROGRESS_SPRINT_STATE_ID);

		// Assert
		assertEquals(4, result.size());

	}

	@Test
	void getSprintProgress_Success() {
		
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();
		parameters.randomize(named("userCode").and(ofType(String.class)).and(inClass(UserStory.class)),
				new StringRandomizer(10, 10));
		parameters.excludeField(FieldPredicates.named("topic").and(FieldPredicates.inClass(UserStory.class)));
		parameters.excludeField(FieldPredicates.named("feature").and(FieldPredicates.inClass(UserStory.class)));
		parameters.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(UserStory.class)));

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint = easyRandom.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		UserStory userStory1 = easyRandom.nextObject(UserStory.class);
		userStory1.setId(null);
		userStory1.setSprint(sprint);
		userStory1.setProgress(new BigDecimal(10));
		userStory1.setStoryPoints(null);
		userStoryRepository.save(userStory1);

		UserStory userStory2 = easyRandom.nextObject(UserStory.class);
		userStory2.setId(null);
		userStory2.setSprint(sprint);
		userStory2.setProgress(new BigDecimal(50));
		userStory2.setStoryPoints(new BigDecimal(5));
		userStoryRepository.save(userStory2);

		// ACTION
		double progress = sprintRepository.getSprintProgress(sprint.getId());
		// ASSERT
		assertThat(progress, is(notNullValue()));

	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return result;
	}
}
