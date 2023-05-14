package com.thinktank.pts.agileservice.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.config.AbstractPersistenceTestBase;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.repository.SprintRepository;

/**
 * 
 * @author laifia
 * @since 28 Feb 2023
 *
 */
class SprintServiceTest extends AbstractPersistenceTestBase {

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private SprintService sprintService;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@BeforeEach
	void before() {
		sprintRepository.deleteAll();

	}

	@AfterEach
	void after() {
		sprintRepository.deleteAll();
	}

	@Test
	void getSprintByProductId_Success() {
		// Arrange

		EasyRandomParameters parameters = buildEasyRandomParameters();

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
		List<Sprint> result = sprintService.getSprintsByProductId(productId1);

		// Assert
		assertEquals(1L, result.size());

	}

	@Test
	void createSprint_Success() {
		// Arrange

		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);

		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);

		// Action

		Sprint sprintCreated = sprintService.save(sprint);

		// Assert
		assertThat(sprintCreated.getId(), is(notNullValue()));

	}


	@Ignore("used to test connexion to pts-core")
	void getAllowedStatesByStateIdTest_Generated_Api() {
		// ARRANGE
		Long statusId = 1017796L;
		// ACTION
		List<State> result = stateApi.getNextAllowedStatesForSprint(statusId);
		// ASSERT
		// add assert on list size
		assertEquals(2, result.size());
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters parameters = new EasyRandomParameters();
		parameters.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		parameters.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return parameters;
	}
}
