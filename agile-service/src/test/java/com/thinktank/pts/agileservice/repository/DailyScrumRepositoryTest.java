package com.thinktank.pts.agileservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import java.time.LocalDateTime;
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
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.Sprint;

/**
 * 
 * @author karabakaa
 * @since Apr 10, 2023
 *
 */
class DailyScrumRepositoryTest extends AbstractPersistenceTestBase {

	private static final Long STARTED_DS_STATE_ID = 1017898L;

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

	@Test
	void createDailyScrum_success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum dailyScrum = easyRandom.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint1);

		// ACTION
		dailyScrum = dailyScrumRepository.save(dailyScrum);
		// ASSERT
		assertThat(dailyScrum.getId(), is(notNullValue()));

	}

	@Test
	void findAllDailyScrums_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum dailyScrum = easyRandom.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint1);
		dailyScrumRepository.save(dailyScrum);

		// ACTION
		List<DailyScrum> dailyScrums = dailyScrumRepository.findAll();
		// ASSERT
		assertThat(dailyScrums, is(not(empty())));

	}

	/**
	 * Tests the {@link DailyScrumRepository#findFirstBySprintIdAndStateIdOrderByIdDesc(Long sprintId, Long stateId)}.
	 */
	@Test
	void getStartedDailyScrumBySprintIddOrderByIdDesc_success() {
		// ARRANGE

		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		List<DailyScrum> dailyScrums = easyRandom.objects(DailyScrum.class, 3).collect(Collectors.toList());
		for (DailyScrum ds : dailyScrums) {
			ds.setStateId(STARTED_DS_STATE_ID);
			ds.setSprint(sprint1);
		}

		dailyScrumRepository.saveAll(dailyScrums);

		// ACTION
		DailyScrum result = dailyScrumRepository.findFirstBySprintIdAndStateIdOrderByIdDesc(sprint1.getId(),
				STARTED_DS_STATE_ID);
		// ASSERT
		assertThat(result, is(not(nullValue())));
		assertThat(result.getStateId()).isEqualTo(STARTED_DS_STATE_ID);
	}

	// test for update DailyScrum
	@Test
	void updateDailyScrum_success() {
		// ARRANGE

		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom easyRandom = new EasyRandom(parameters);

		Sprint sprint1 = easyRandom.nextObject(Sprint.class);
		sprint1.setId(null);
		sprint1 = sprintRepository.save(sprint1);

		DailyScrum dailyScrum = easyRandom.nextObject(DailyScrum.class);
		dailyScrum.setFinishedAt(LocalDateTime.now());
		dailyScrum.setSprint(sprint1);

		// ACTION
		DailyScrum dailyScrumUpdated = dailyScrumRepository.save(dailyScrum);
		// ASSERT
		assertThat(dailyScrumUpdated, is(notNullValue()));
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
