package com.thinktank.pts.agileservice.service;

import java.util.List;
import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.UserStoryInput;
import com.thinktank.pts.agileservice.model.StateCountDto;
import com.thinktank.pts.agileservice.model.UserStory;

/**
 * 
 * @author lamiam
 * @since 6 Apr 2023
 *
 */
public interface UserStoryService {

	/**
	 * 
	 * @param userStoryInput
	 * @return userStory created {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	UserStory create(UserStoryInput userStoryInput);

	/**
	 * 
	 * @param id
	 * @param userStory
	 *            input
	 * @return UserStory updated {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	UserStory update(Long id, UserStoryInput input);

	/**
	 * Method used to retrieve the user Story by id
	 * 
	 * @param id
	 * @return Optional UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	Optional<UserStory> getById(Long id);

	/**
	 * Method used to retrieve the list of user Story by sprintId
	 * 
	 * @param sprintId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesBySprintId(Long sprintId);

	/**
	 * Method used to retrieve the list of user Story by topicId
	 * 
	 * @param topicId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByTopicId(Long topicId);

	/**
	 * Method used to retrieve the list of user Story by featureId
	 * 
	 * @param featureId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByFeatureId(Long featureId);

	/**
	 * Method used to retrieve the list of user Story by productId
	 * 
	 * @param productId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByProductId(Long productId);

	/**
	 * Method used to retrieve the list of user Story by sprintId and states
	 * 
	 * @param sprintId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesBySprintIdAndStateId(Long sprintId, List<Long> states);

	/**
	 * Method used to retrieve the count of us grouped by states by sprint ID
	 * 
	 * @param sprintId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<StateCountDto> getCountUsPerStateBySprint(Long sprintId);

	/**
	 * 
	 * @param id
	 * @return userStory details {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	Optional<UserStory> getUserStoryDetails(Long id);

	/**
	 * 
	 * @param topicId
	 * @return max order for user story in the same topic
	 */
	Long getUserStoryMaxOrder(Long topicId);

	/**
	 * 
	 * @param sprintId
	 * @return total of user story points of sprint
	 */
	Long getUserstoryPointsDsbySprint(Long sprintId);

	/**
	 * Method used to retrieve the list of user Story by productId without Sprint
	 * 
	 * @param productId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByProductIdWithoutSprint(Long productId);

	/**
	 * Method used to retrieve the list of user Story by productId without Feature
	 * 
	 * @param productId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByProductIdWithoutFeature(Long productId);

	/**
	 * Method used to retrieve the list of user Story by productId without Topic
	 * 
	 * @param productId
	 * @return List UserStory {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	List<UserStory> getListUserStoriesByProductIdWithoutTopic(Long productId);

	/**
	 * 
	 * @param us
	 * @return UserStory created {@link com.thinktank.pts.agileservice.model.UserStory}
	 */
	UserStory save(UserStory us);
}
