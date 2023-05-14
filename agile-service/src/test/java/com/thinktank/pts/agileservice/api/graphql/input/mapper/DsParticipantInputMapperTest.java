package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.DsParticipantInput;
import com.thinktank.pts.agileservice.model.DsParticipant;

class DsParticipantInputMapperTest {

	private DsParticipantInputMapper inputMapper = new DsParticipantInputMapper();

	/**
	 * {@link DsParticipantInputMapper#create(DsParticipantInput)}
	 */
	@Test
	void createDefaultTopicTest() {

		// Arrange
		EasyRandom generator = new EasyRandom();
		DsParticipantInput input = generator.nextObject(DsParticipantInput.class);

		// Action: map input to DsParticipant
		DsParticipant createdDsParticipantInput = inputMapper.create(input);

		// Assert
		assertEquals(createdDsParticipantInput.getStateId(), input.getStateId());
	}

	/**
	 * {@link DsParticipantInputMapper#patchFields(DsParticipantInput, DsParticipant)}
	 */
	@Test
	void patchFieldsTest() {
		Map<String, Object> arg = new HashMap<>();

		EasyRandom generator = new EasyRandom();
		DsParticipant dsParticipant = generator.nextObject(DsParticipant.class);
		DsParticipantInput dsParticipantInput = generator.nextObject(DsParticipantInput.class);
		arg.put("userCode", "userCode");
		dsParticipantInput.setArguments(arg);
		inputMapper.patchFields(dsParticipantInput, dsParticipant);
		assertThat(dsParticipant.getUserCode()).isEqualTo(dsParticipantInput.getUserCode());
	}
}
