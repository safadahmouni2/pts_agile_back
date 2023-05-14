package com.thinktank.pts.agileservice.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

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
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.agileservice.model.Sprint;

class DsParticipantRepositoryTest extends AbstractPersistenceTestBase {

	@Autowired
	private DsParticipantRepository dsParticipantRepository;

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

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

	// test for create dsParticipantRepository
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
		DsParticipant createdDsParticipant = dsParticipantRepository.save(dsParticipant);

		// Assert
		assertThat(createdDsParticipant.getId(), is(notNullValue()));
	}

	// test for update DsParticipant
	@Test
	void testUpdateDsParticipant_success() {
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
		dsParticipant.setUserCode("QZE");
		dsParticipant.setDailyScrum(dailyScrum);

		// Action
		DsParticipant createdDsParticipant = dsParticipantRepository.save(dsParticipant);
		// Assert
		assertThat(createdDsParticipant.getId(), is(notNullValue()));
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters parameters = new EasyRandomParameters().randomize(
				named("userCode").and(ofType(String.class)).and(inClass(DsParticipant.class)),
				new StringRandomizer(10, 10));
		parameters.excludeField(FieldPredicates.named("sprint").and(FieldPredicates.inClass(DailyScrum.class)));
		parameters.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		parameters.excludeField(FieldPredicates.named("dsParticipants").and(FieldPredicates.inClass(DailyScrum.class)));
		parameters.excludeField(FieldPredicates.named("dailyScrum").and(FieldPredicates.inClass(DsParticipant.class)));
		parameters.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return parameters;
	}
}
