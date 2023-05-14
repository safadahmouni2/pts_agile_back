package com.thinktank.pts.agileservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;
import static org.junit.Assert.assertEquals;

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
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.agileservice.repository.SprintMemberRepository;
import com.thinktank.pts.agileservice.repository.SprintRepository;

/**
 * 
 * @author bettaiebs
 * @since 7 March 2023
 */
class SprintMemberServiceTest extends AbstractPersistenceTestBase {

	@Autowired
	private SprintMemberService sprintMemberService;

	@Autowired
	private SprintMemberRepository sprintMemberRepository;

	@Autowired
	private SprintRepository sprintRepository;

	@BeforeEach
	void before() {
		sprintMemberRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	@AfterEach
	void after() {
		sprintMemberRepository.deleteAll();
		sprintRepository.deleteAll();
	}

	@Test
	void findBySprintId_Success() {
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		List<Sprint> sprints = generator.objects(Sprint.class, 2).collect(Collectors.toList());

		sprints = sprintRepository.saveAll(sprints);

		SprintMember sprintMember1 = generator.nextObject(SprintMember.class);
		sprintMember1.setSprintId(sprints.get(0).getId());

		SprintMember sprintMember2 = generator.nextObject(SprintMember.class);
		sprintMember2.setSprintId(sprints.get(0).getId());

		SprintMember sprintMember3 = generator.nextObject(SprintMember.class);
		sprintMember3.setSprintId(sprints.get(1).getId());

		sprintMemberRepository.saveAll(Arrays.asList(sprintMember1, sprintMember2, sprintMember3));

		// Action
		List<SprintMember> result = sprintMemberService.findBySprintId(sprints.get(0).getId());

		// Assert
		assertEquals(2, result.size());

	}

	@Test
	void saveSprintMember_Success() {
		// Arrange
		EasyRandomParameters parameters = buildEasyRandomParameters();

		EasyRandom generator = new EasyRandom(parameters);
		Sprint sprint = generator.nextObject(Sprint.class);
		sprint.setId(null);
		sprint = sprintRepository.save(sprint);

		SprintMember sprintMember = generator.nextObject(SprintMember.class);
		sprintMember.setSprintId(sprint.getId());

		// Action
		SprintMember createdSprintMember = sprintMemberService.save(sprintMember);

		// Assert
		assertThat(sprintMember).usingRecursiveComparison()
				.ignoringFields("id", "creator", "created", "modifier", "modified").isEqualTo(createdSprintMember);
	}

	private EasyRandomParameters buildEasyRandomParameters() {
		EasyRandomParameters result = new EasyRandomParameters().randomize(
				named("userCode").and(ofType(String.class)).and(inClass(SprintMember.class)),
				new StringRandomizer(10, 10));
		result.excludeField(FieldPredicates.named("dailyScrums").and(FieldPredicates.inClass(Sprint.class)));
		result.excludeField(FieldPredicates.named("userStories").and(FieldPredicates.inClass(Sprint.class)));
		return result;
	}

}
