package com.thinktank.pts.agileservice.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinktank.pts.agileservice.api.graphql.input.UpdateDsParticipantInput;
import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.repository.DailyScrumRepository;
import com.thinktank.pts.agileservice.repository.DsParticipantRepository;
import com.thinktank.pts.agileservice.repository.SprintRepository;

class DsParticipantServiceTest extends AbstractPersistenceTestBase {

	@Autowired
	private DsParticipantRepository dsParticipantRepository;

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

	@Autowired
	private DsParticipantService dsParticipantService;

	@Autowired
	private SprintRepository sprintRepository;

	@BeforeEach
	void before() {
		dsParticipantRepository.deleteAll();
		dailyScrumRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	@AfterEach
	void after() {
		dsParticipantRepository.deleteAll();
		dailyScrumRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	// test for save new DsParticipant service
	@Test
	void saveDsParticipant_Success() {
		// Arrange
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		DailyScrum dailyScrum = generator.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint);
		dailyScrum = dailyScrumRepository.save(dailyScrum);

		DsParticipant dsParticipant = generator.nextObject(DsParticipant.class);
		dsParticipant.setId(null);
		dsParticipant.setDailyScrum(dailyScrum);

		// Action
		DsParticipant createdDsParticipant = dsParticipantService.save(dsParticipant);

		// Assert
		assertThat(createdDsParticipant.getId(), is(notNullValue()));
	}

	// test for update DsParticipant service to be fixed with new updates
	@Test
	void updateDsParticipanTest() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		DailyScrum dailyScrum = generator.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint);
		dailyScrum = dailyScrumRepository.save(dailyScrum);

		DsParticipant dsParticipant = generator.nextObject(DsParticipant.class);
		dsParticipant.setId(null);
		dsParticipant.setDailyScrum(dailyScrum);
		dsParticipant.setStateId(1017910L);

		dsParticipant = dsParticipantService.save(dsParticipant);

		// ACTION
		Map<String, Object> arg = new HashMap<>();
		UpdateDsParticipantInput dsParticipantInput = new UpdateDsParticipantInput();
		arg.put("stateId", "stateId");
		dsParticipantInput.setStateId(1017909L);

		dsParticipantInput.setArguments(arg);

		DsParticipant updated = dsParticipantService.update(dsParticipant.getId(), dsParticipantInput);

		// ASSERT
		assertThat(updated.getId(), is(notNullValue()));
		assertEquals(updated.getStateId(), dsParticipantInput.getStateId());
	}

	// test for get DsParticipant by id service
	@Test
	void testGetDsParticipantById_success() {
		// ARRANGE
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		DailyScrum dailyScrum = generator.nextObject(DailyScrum.class);
		dailyScrum.setId(null);
		dailyScrum.setSprint(sprint);
		dailyScrum = dailyScrumRepository.save(dailyScrum);

		DsParticipant dsParticipant = generator.nextObject(DsParticipant.class);
		dsParticipant.setId(null);
		dsParticipant.setDailyScrum(dailyScrum);

		dsParticipant = dsParticipantService.save(dsParticipant);
		// ACTION
		Optional<DsParticipant> opt = dsParticipantService.getById(dsParticipant.getId());

		// ASSERT
		assertTrue(opt.isPresent());
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters().randomize(
				named("userCode").and(ofType(String.class)).and(inClass(DsParticipant.class)),
				new StringRandomizer(10, 10));
		result.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(DailyScrum.class)));
		result.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		result.excludeField(FieldPredicates.named("dsParticipants").and(FieldPredicates.inClass(DailyScrum.class)));
		result.excludeField(FieldPredicates.named("dailyScrum").and(FieldPredicates.inClass(DsParticipant.class)));
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return result;
	}
}
