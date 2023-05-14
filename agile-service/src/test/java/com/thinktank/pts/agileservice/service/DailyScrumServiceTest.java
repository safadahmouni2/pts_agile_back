package com.thinktank.pts.agileservice.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.repository.DailyScrumRepository;
import com.thinktank.pts.agileservice.repository.SprintRepository;

/**
 * 
 * @author karabakaa
 * @since Apr 10, 2023
 *
 */
class DailyScrumServiceTest extends AbstractPersistenceTestBase {

	private static final Long STARTED_SPRINT_STATE_ID = 1017898L;

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

	@Autowired
	private SprintRepository sprintRepository;

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

	@Autowired
	private DailyScrumService dailyScrumService;

	@Test
	void createDailyScrumTest() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum dailyScrum = easyRandom.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint1);

		// ACTION
		DailyScrum created = dailyScrumService.create(dailyScrum);
		// ASSERT
		assertThat(created.getId(), is(notNullValue()));

	}

	@Test
	void updateDailyScrumTest() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum newDs = easyRandom.nextObject(DailyScrum.class);
		newDs.setId(null);
		newDs.setSprint(sprint1);
		newDs = dailyScrumService.create(newDs);
		// ACTION

		Map<String, Object> arg = new HashMap<>();
		DailyScrumInput dsInput = easyRandom.nextObject(DailyScrumInput.class);
		arg.put("finishedAt", "finishedAt");
		arg.put("sprintProgress", "sprintProgress");
		dsInput.setArguments(arg);

		DailyScrum updated = dailyScrumService.update(newDs.getId(), dsInput);
		// ASSERT
		assertThat(updated.getId(), is(notNullValue()));
		assertEquals(updated.getFinishedAt(), dsInput.getFinishedAt());
		assertEquals(updated.getSprintProgress(), dsInput.getSprintProgress());
	}

	/**
	 * {@link DailyScrumService#getStartedDailyScrumBySprintId(Long)}
	 */
	@Test
	void testGetStartedDailyScrumBySprintId_success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		List<DailyScrum> dailyScrums = easyRandom.objects(DailyScrum.class, 3).collect(Collectors.toList());
		for (DailyScrum ds : dailyScrums) {
			ds.setStateId(STARTED_SPRINT_STATE_ID);
			ds.setSprint(sprint1);
		}

		dailyScrumRepository.saveAll(dailyScrums);

		// Action
		DailyScrum result = dailyScrumService.getStartedDailyScrumBySprintId(sprint1.getId());
		// ASSERT
		assertThat(result, is(notNullValue()));
	}

	@Test
	void getDailyScrumById() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum ds = easyRandom.nextObject(DailyScrum.class);
		ds.setId(null);
		ds.setSprint(sprint1);
		ds = dailyScrumService.save(ds);
		// ACTION
		Optional<DailyScrum> opt = dailyScrumService.getById(ds.getId());

		// ASSERT
		assertTrue(opt.isPresent());
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters();
		result.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		result.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(DailyScrum.class)));
		result.excludeField(FieldPredicates.named("dsParticipants").and(FieldPredicates.inClass(DailyScrum.class)));
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return result;
	}

}
