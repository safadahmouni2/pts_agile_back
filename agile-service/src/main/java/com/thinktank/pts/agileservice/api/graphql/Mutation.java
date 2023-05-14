package com.thinktank.pts.agileservice.api.graphql;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.api.graphql.input.DefaultTopicInput;
import com.thinktank.pts.agileservice.api.graphql.input.DsParticipantInput;
import com.thinktank.pts.agileservice.api.graphql.input.FeatureInput;
import com.thinktank.pts.agileservice.api.graphql.input.SprintInput;
import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.api.graphql.input.TopicInput;
import com.thinktank.pts.agileservice.api.graphql.input.UpdateDsParticipantInput;
import com.thinktank.pts.agileservice.api.graphql.input.UserStoryInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.DailyScrumInputMapper;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.DefaultTopicInputMapper;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.DsParticipantInputMapper;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.SprintMemberInputMapper;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.model.DefaultTopic;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.agileservice.service.DailyScrumService;
import com.thinktank.pts.agileservice.service.DefaultTopicService;
import com.thinktank.pts.agileservice.service.DsParticipantService;
import com.thinktank.pts.agileservice.service.FeatureService;
import com.thinktank.pts.agileservice.service.SprintMemberService;
import com.thinktank.pts.agileservice.service.SprintService;
import com.thinktank.pts.agileservice.service.TopicService;
import com.thinktank.pts.agileservice.service.UserStoryService;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class Mutation implements GraphQLMutationResolver {

	private static final String ARGUMENTS_INPUT = "input";

	@Autowired
	private TopicService topicService;

	@Autowired
	private SprintService sprintService;

	@Autowired
	private SprintMemberService sprintMemberService;

	@Autowired
	private DefaultTopicService defaultTopicService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private DailyScrumService dailyScrumService;

	@Autowired
	private DsParticipantService dsParticipantService;

	@Autowired
	private UserStoryService userStoryService;

	private SprintMemberInputMapper sprintMemberInputMapper = new SprintMemberInputMapper();

	private DefaultTopicInputMapper defaultTopicInputMapper = new DefaultTopicInputMapper();

	private DailyScrumInputMapper dailyScrumInputMapper = new DailyScrumInputMapper();

	private DsParticipantInputMapper dsParticipantInputMapper = new DsParticipantInputMapper();

	/**
	 * api to create new Topic
	 * 
	 * @param input
	 * @return topic created
	 */
	public Topic createTopic(TopicInput input) {
		return topicService.create(input);
	}

	@SuppressWarnings("unchecked")
	public Topic updateTopic(Long id, TopicInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return topicService.update(id, input);
	}

	/**
	 * api to create new DefaultTopic
	 * 
	 * @param input
	 * @return DefaultTopic created
	 */
	public DefaultTopic createDefaultTopic(DefaultTopicInput input) {
		DefaultTopic defaultTopicPayload = defaultTopicInputMapper.create(input);
		return defaultTopicService.save(defaultTopicPayload);
	}

	/**
	 * @param productId
	 *            api to delete from DefaultTopic
	 * 
	 */

	public void deleteDefaultTopic(Long productId) {
		defaultTopicService.deleteByProductId(productId);
	}

	/**
	 * Api to create new Sprint
	 * 
	 * @param input
	 *            - the {@link Sprint} object to be saved
	 * @return - the saved {@link Sprint} object
	 */
	public Sprint createSprint(SprintInput input) {
		return sprintService.create(input);
	}

	/**
	 * Api to update Sprint
	 * 
	 * @param id
	 *            - the identifier of the {@link Sprint} instance to be updated
	 * @param input
	 *            - the updated {@link SprintInput} object
	 * @param env
	 *            - the environment in which the update is taking place
	 * @return - the updated {@link Sprint} object
	 */
	@SuppressWarnings("unchecked")
	public Sprint updateSprint(Long id, SprintInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return sprintService.update(id, input);
	}

	/**
	 * Api to create SprintMember
	 * 
	 * @param input
	 *            - the identifier of the {@link SprintMemberInput} instance to be created
	 * @return - the created {@link SprintMember} object
	 */
	public SprintMember createSprintMember(SprintMemberInput input) {
		SprintMember sprintMemberPayload = sprintMemberInputMapper.create(input);
		return sprintMemberService.create(sprintMemberPayload);
	}

	/**
	 * Api to update SprintMember
	 * 
	 * @param sprintMemberId
	 *            - the identifier of the {@link SprintMember} instance to be updated
	 * @param env
	 *            - the environment in which the update is taking place
	 * @return - the updated {@link SprintMember} object
	 */
	@SuppressWarnings("unchecked")
	public SprintMember updateSprintMember(Long id, SprintMemberInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return sprintMemberService.update(id, input);
	}

	/**
	 * api to create new Feature
	 * 
	 * @param input
	 * @return feature created
	 */
	public Feature createFeature(FeatureInput input) {
		return featureService.create(input);
	}

	/**
	 * 
	 * This method handle update fields on specific Feature
	 * 
	 * @param FeatureInput
	 *            fields that will be updated
	 * 
	 * @param FeatureId
	 *            - the identifier of the {@link SprintMember} instance to be updated
	 * @param env
	 *            - the environment in which the update is taking place
	 * @return - the updated {@link SprintMember} object
	 */
	@SuppressWarnings("unchecked")
	public Feature updateFeature(Long id, FeatureInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return featureService.update(id, input);
	}

	/**
	 * api to create new DsParticipant
	 * 
	 * @param input
	 * @return DsParticipant created
	 */
	public DsParticipant createDsParticipant(DsParticipantInput input) {

		Optional<DailyScrum> opt = dailyScrumService.getById(input.getDailyScrumId());

		if (!opt.isPresent()) {
			throw new UnknownIDException(input.getDailyScrumId());
		}

		DsParticipant dsParticipantPayload = dsParticipantInputMapper.create(input);
		dsParticipantPayload.setDailyScrum(opt.get());

		return dsParticipantService.save(dsParticipantPayload);
	}

	/**
	 * api to create new DailyScrum
	 * 
	 * @param input
	 * @return dailycrum created
	 */
	public DailyScrum createDailyScrum(DailyScrumInput input) {

		Sprint sprint = sprintService.getById(input.getSprintId());

		if (sprint == null) {
			throw new UnknownIDException(input.getSprintId());
		}

		DailyScrum dailyScrum = dailyScrumInputMapper.create(input);
		dailyScrum.setSprint(sprint);

		return dailyScrumService.create(dailyScrum);
	}

	/**
	 * Api to update DailyScrum
	 * 
	 * @param dailyScrumId
	 *            - the identifier of the {@link DailyScrum} instance to be updated
	 * @param env
	 *            - the environment in which the update is taking place
	 * @return - the updated {@link DailyScrum} object
	 */
	@SuppressWarnings("unchecked")
	public DailyScrum updateDailyScrum(Long id, DailyScrumInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return dailyScrumService.update(id, input);
	}

	/**
	 * Api to update DsParticipant
	 * 
	 * @param id
	 *            the identifier of the {@link DsParticipant} instance to be updated
	 * @param env:
	 *            the environment in which the update is taking place
	 * @return - the updated {@link DsParticipant} object
	 */
	@SuppressWarnings("unchecked")
	public DsParticipant updateDsParticipant(Long id, UpdateDsParticipantInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return dsParticipantService.update(id, input);
	}

	/**
	 * api to create new UserStory
	 * 
	 * @param input
	 * @return userStory created
	 */
	public UserStory createUserStory(UserStoryInput input) {
		return userStoryService.create(input);
	}

	/**
	 * Api to update UserStory
	 * 
	 * @param userStoryId
	 *            - the identifier of the {@link UserStory} instance to be updated
	 * @param env
	 *            - the environment in which the update is taking place
	 * @return - the updated {@link UserStory} object
	 */
	@SuppressWarnings("unchecked")
	public UserStory updateUserStory(Long id, UserStoryInput input, DataFetchingEnvironment env) {
		input.setArguments((Map<String, Object>) env.getArguments().get(ARGUMENTS_INPUT));
		return userStoryService.update(id, input);
	}
}
