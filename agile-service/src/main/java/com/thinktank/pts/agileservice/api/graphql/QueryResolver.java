package com.thinktank.pts.agileservice.api.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinktank.pts.agileservice.api.graphql.input.SearchSprintMemberInput;
import com.thinktank.pts.agileservice.api.graphql.output.FeatureMaxOrderOutput;
import com.thinktank.pts.agileservice.api.graphql.output.TopicMaxOrderOutput;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.DefaultTopic;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.model.MyDailyScrumDto;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.agileservice.model.StateCountDto;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.agileservice.service.DailyScrumService;
import com.thinktank.pts.agileservice.service.DefaultTopicService;
import com.thinktank.pts.agileservice.service.FeatureService;
import com.thinktank.pts.agileservice.service.SprintMemberService;
import com.thinktank.pts.agileservice.service.SprintService;
import com.thinktank.pts.agileservice.service.TopicService;
import com.thinktank.pts.agileservice.service.UserStoryService;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;
import com.thinktank.pts.apibase.graphql.output.CollectionResult;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class QueryResolver implements GraphQLQueryResolver {

	@Autowired
	private TopicService topicService;

	@Autowired
	private SprintService sprintService;

	@Autowired
	private SprintMemberService sprintMemberService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private DefaultTopicService defaultTopicService;

	@Autowired
	private DailyScrumService dailyScrumService;

	@Autowired
	private UserStoryService userStoryService;

	public Topic getTopic(Long id) {
		return this.topicService.getById(id);
	}

	public TopicMaxOrderOutput getTopicMaxOrderByProduct(Long productId) {
		return this.topicService.getTopicMaxOrderByProduct(productId);
	}

	public CollectionResult<Topic> getTopicsByProductId(Long productId) {

		CollectionResult<Topic> result = new CollectionResult<>();

		result.setItems(this.topicService.getTopicsByProductId(productId));

		return result;

	}

	/**
	 * in order to update a specific sprint getSprintById is needed
	 * 
	 * @param productId
	 * @return
	 */
	public CollectionResult<Sprint> getSprintsByProductId(Long productId) {

		CollectionResult<Sprint> result = new CollectionResult<>();
		result.setItems(this.sprintService.getSprintsByProductId(productId));
		return result;

	}

	/**
	 * Method used to retrieve the list of sprint members by sprintId and stateId
	 * 
	 * @param sprintId,
	 *            stateId
	 * @return CollectionResult<SprintMember> created {@link com.thinktank.pts.agileservice.model.SprintMember}
	 */
	public CollectionResult<SprintMember> filterSprintMembers(SearchSprintMemberInput input) {

		CollectionResult<SprintMember> result = new CollectionResult<>();
		result.setItems(this.sprintMemberService.filterSprintMembers(input.getSprintId(), input.getStateId()));

		return result;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Sprint getSprintDetailsById(Long id) {
		return this.sprintService.findById(id).orElseThrow(() -> new UnknownIDException(id));
	}

	/**
	 * Returns a Feature with the given identifier.
	 *
	 * @param id
	 *            must not be {@literal null}.
	 * @return the entity Feature with the given identifier.
	 */
	public Feature getFeature(Long id) {
		return featureService.getById(id);
	}

	/**
	 * Method used to retrieve the max displayOrder of features by productId
	 * 
	 * @param productId
	 * @return
	 */
	public FeatureMaxOrderOutput getFeatureMaxOrderByProduct(Long productId) {
		return this.featureService.getFeatureMaxOrderByProduct(productId);
	}

	/***
	 * Method used to retrieve features by productId
	 * 
	 * @param productId
	 * @return @return CollectionResult<Feature> created {@link com.thinktank.pts.agileservice.model.Feature}
	 */
	public CollectionResult<Feature> getFeaturesByProductId(Long productId) {

		CollectionResult<Feature> result = new CollectionResult<>();

		result.setItems(this.featureService.getFeaturesByProductId(productId));

		return result;

	}

	/**
	 * Method used to retrieve the existence of the DefaultTopic by topicId
	 * 
	 * @param topicId
	 * @return boolean
	 */
	public Boolean isDefaultTopic(Long topicId) {
		return this.defaultTopicService.isDefaultTopic(topicId);
	}

	/**
	 * Method used to retrieve the DefaultTopic by productId
	 * 
	 * @param productId
	 */
	public DefaultTopic getDefaultTopicByProductId(Long productId) {
		return this.defaultTopicService.getByProductId(productId);
	}

	/**
	 * Method used to retrieve the started daily scrum by sprint id
	 * 
	 * @param productId
	 */
	public DailyScrum getStartedDailyScrumBySprintId(Long sprintId) {
		return dailyScrumService.getStartedDailyScrumBySprintId(sprintId);
	}

	/**
	 * Method used to retrieve the list of user story by sprint
	 * 
	 * @param sprintId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesBySprintId(Long sprintId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesBySprintId(sprintId));
		return result;

	}

	/**
	 * Method used to retrieve the list of user story by feature
	 * 
	 * @param featureId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByFeatureId(Long featureId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByFeatureId(featureId));
		return result;

	}

	/**
	 * Method used to retrieve the list of user story by topic
	 * 
	 * @param topicId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByTopicId(Long topicId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByTopicId(topicId));
		return result;

	}

	/**
	 * Method used to retrieve the list of user story by productID
	 * 
	 * @param productId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByProductId(Long productId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByProductId(productId));
		return result;

	}

	/**
	 * Method used to retrieve sprint details by ticket id
	 * 
	 * @param ticketId
	 * @return
	 */
	public Sprint getSprintDetailsByTicketId(Long ticketId) {
		return this.sprintService.findByTicketId(ticketId).orElseThrow(() -> new UnknownIDException(ticketId));
	}

	/**
	 * Method used to retrieve the list of user story by sprintID and stateID
	 * 
	 * @param sprintId
	 * @param states
	 * @return
	 */
	public CollectionResult<UserStory> getListUserStoriesBySprintIdAndStateId(Long sprintId, List<Long> states) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesBySprintIdAndStateId(sprintId, states));
		return result;

	}

	/**
	 * Method used to retrieve user details by us id
	 * 
	 * @param usId
	 * @return
	 */
	public UserStory getUserStoryDetails(Long id) {
		return userStoryService.getUserStoryDetails(id).orElseThrow(() -> new UnknownIDException(id));
	}

	/**
	 * Method used to get the order of the user story
	 * 
	 * @param topicId
	 * @return
	 */
	public Long getUserStoryMaxOrder(Long topicId) {
		return userStoryService.getUserStoryMaxOrder(topicId);
	}

	/**
	 * Method used to get the total of user story points of sprint
	 * 
	 * @param sprintId
	 * @return
	 */
	public Long getUserstoryPointsDsbySprint(Long sprintId) {
		return userStoryService.getUserstoryPointsDsbySprint(sprintId);
	}

	/**
	 * Method used to retrieve the list of user story by productID without sprint
	 * 
	 * @param productId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByProductIdWithoutSprint(Long productId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByProductIdWithoutSprint(productId));
		return result;

	}

	/**
	 * Method used to retrieve the list of user story by productID without feature
	 * 
	 * @param productId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByProductIdWithoutFeature(Long productId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByProductIdWithoutFeature(productId));
		return result;

	}

	/**
	 * Method used to retrieve the list of user story by productID without topic
	 * 
	 * @param productId
	 * @return
	 */
	public CollectionResult<UserStory> getUserStoriesByProductIdWithoutTopic(Long productId) {

		CollectionResult<UserStory> result = new CollectionResult<>();
		result.setItems(userStoryService.getListUserStoriesByProductIdWithoutTopic(productId));
		return result;

	}

	/**
	 * 
	 * Method used to retrieve the list of current user's daily scrums done or need to be done in the current day for
	 * his in-progress sprints for his products order by ds finished and started time
	 * 
	 * @param productIds
	 * @return
	 */
	public CollectionResult<MyDailyScrumDto> getMyDailyScrums(List<Long> productIds) {

		CollectionResult<MyDailyScrumDto> result = new CollectionResult<>();
		result.setItems(dailyScrumService.getMyDailyScrums(productIds));
		return result;

	}

	/**
	 * Method used to retrieve the count of us grouped by states by sprint ID
	 * 
	 * @param sprintId
	 * @return
	 */
	public CollectionResult<StateCountDto> getCountUsPerStateBySprint(Long sprintId) {
		CollectionResult<StateCountDto> result = new CollectionResult<>();
		result.setItems(userStoryService.getCountUsPerStateBySprint(sprintId));
		return result;
	}

	/**
	 * Method used to retrieve the sum of percentage of sprint by sprint ID
	 * 
	 * @param sprintId
	 * @return
	 */
	public double getSprintProgress(Long sprintId) {
		return sprintService.getSprintProgress(sprintId);
	}

}
