package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import com.thinktank.pts.agileservice.api.graphql.input.UserStoryInput;
import com.thinktank.pts.agileservice.model.UserStory;

/**
 * 
 * @author lamiam
 * @since 12 Apr 2023
 *
 */
class UserStoryInputMapperTest {

	private UserStoryInputMapper userStoryInputMapper = new UserStoryInputMapper();

	/**
	 * {@link UserStoryInputMapper#create(UserStoryInput)}
	 */
	@Test
	void createUserStoryTest() {

		EasyRandom generator = new EasyRandom();
		UserStoryInput userStoryInput = generator.nextObject(UserStoryInput.class);
		UserStory createdUserStory = userStoryInputMapper.create(userStoryInput);
		assertThat(createdUserStory.getParentId()).isEqualTo(userStoryInput.getParentId());
		assertThat(createdUserStory.getStateId()).isEqualTo(userStoryInput.getStateId());
	}

	/**
	 * {@link UserStoryInputMapper#patchFields(UserStoryInput, UserStory)}
	 */
	@Test
	void patchFieldsTest() {
		Map<String, Object> arg = new HashMap<>();

		EasyRandom generator = new EasyRandom();
		UserStory userStory = generator.nextObject(UserStory.class);
		UserStoryInput userStoryInput = generator.nextObject(UserStoryInput.class);
		arg.put("parentId", "parentId");
		arg.put("stateId", "stateId");
		userStoryInput.setArguments(arg);
		userStoryInputMapper.patchFields(userStoryInput, userStory);
		assertThat(userStory.getParentId()).isEqualTo(userStoryInput.getParentId());
		assertThat(userStory.getStateId()).isEqualTo(userStoryInput.getStateId());
	}
}
