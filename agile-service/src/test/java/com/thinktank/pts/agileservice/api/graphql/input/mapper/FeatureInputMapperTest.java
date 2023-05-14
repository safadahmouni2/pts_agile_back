package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.FeatureInput;
import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.model.SprintMember;

class FeatureInputMapperTest {
	private FeatureInputMapper inputMapper = new FeatureInputMapper();

	/**
	 * {@link SprintMemberInputMapper#patchFields(SprintMemberInput, SprintMember)}
	 */
	@Test
	void patchFieldsTest() {

		// Arrange
		Map<String, Object> arg = new HashMap<>();
		EasyRandom generator = new EasyRandom();
		Feature feature = generator.nextObject(Feature.class);
		FeatureInput featureInput = generator.nextObject(FeatureInput.class);
		arg.put("name", "name");
		featureInput.setArguments(arg);

		// Action: patch sprintMember with input
		feature = inputMapper.patchFields(featureInput, feature);
		// Assert
		assertEquals(feature.getName(), featureInput.getName());
	}

}
