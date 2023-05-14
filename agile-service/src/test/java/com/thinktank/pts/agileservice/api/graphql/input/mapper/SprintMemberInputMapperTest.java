package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.model.SprintMember;

/**
 * 
 * @author bettaiebs 7 March 2023
 */
class SprintMemberInputMapperTest {

	private SprintMemberInputMapper inputMapper = new SprintMemberInputMapper();

	/**
	 * {@link SprintMemberInputMapper#create(SprintMemberInput)}
	 */
	@Test
	void createSprintMemberTest() {

		// Arrange
		EasyRandom generator = new EasyRandom();
		SprintMemberInput input = generator.nextObject(SprintMemberInput.class);

		// Action: map input to sprintMember
		SprintMember createdSprintMember = inputMapper.create(input);

		// Assert
		assertEquals(createdSprintMember.getUserCode(), input.getUserCode());
	}

	/**
	 * {@link SprintMemberInputMapper#patchFields(SprintMemberInput, SprintMember)}
	 */
	@Test
	void patchFieldsTest() {

		// Arrange
		Map<String, Object> arg = new HashMap<>();
		EasyRandom generator = new EasyRandom();
		SprintMember sprintMember = generator.nextObject(SprintMember.class);
		SprintMemberInput input = generator.nextObject(SprintMemberInput.class);
		arg.put("userCode", "userCode");
		input.setArguments(arg);

		// Action: patch sprintMember with input
		inputMapper.patchFields(input, sprintMember);

		// Assert
		assertEquals(sprintMember.getUserCode(), input.getUserCode());
	}

}
