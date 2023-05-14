package com.thinktank.pts.agileservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.UserStoryInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.UserStoryInputMapper;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.model.StateCountDto;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.agileservice.repository.UserStoryRepository;
import com.thinktank.pts.agileservice.service.FeatureService;
import com.thinktank.pts.agileservice.service.SprintService;
import com.thinktank.pts.agileservice.service.TopicService;
import com.thinktank.pts.agileservice.service.UserStoryService;
import com.thinktank.pts.agileservice.service.UserStoryStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

/**
 * 
 * @author lamiam
 * @since 6 Apr 2023
 *
 */
@Service
public class UserStoryServiceImpl implements UserStoryService {

	@Autowired
	private UserStoryRepository userStoryRepository;

	@Autowired
	private TopicService topicService;

	@Autowired
	private SprintService sprintService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private UserStoryStateValidatorService userStoryStateValidatorService;

	private UserStoryInputMapper userStoryInputMapper = new UserStoryInputMapper();

	/**
	 * {@inheritDoc}
	 */

	@Override
	public UserStory create(UserStoryInput input) {
		UserStory userStory = userStoryInputMapper.create(input);
		setUSTopicSprintAndFeature(userStory, input);
		return userStoryRepository.save(userStory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<UserStory> getById(Long id) {
		return userStoryRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesBySprintId(Long sprintId) {
		return userStoryRepository.findBySprintId(sprintId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByTopicId(Long topicId) {
		return userStoryRepository.findByTopicId(topicId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByFeatureId(Long featureId) {
		return userStoryRepository.findByFeatureId(featureId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByProductId(Long productId) {
		return userStoryRepository.findByProductId(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesBySprintIdAndStateId(Long sprintId, List<Long> states) {
		return userStoryRepository.findBySprintIdAndStateIdIn(sprintId, states);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<StateCountDto> getCountUsPerStateBySprint(Long sprintId) {
		return userStoryRepository.getCountUsPerStateBySprint(sprintId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<UserStory> getUserStoryDetails(Long id) {
		return userStoryRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getUserStoryMaxOrder(Long topicId) {
		return userStoryRepository.getUserStoryMaxOrder(topicId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getUserstoryPointsDsbySprint(Long sprintId) {
		return userStoryRepository.getUserstoryPointsDsbySprint(sprintId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByProductIdWithoutSprint(Long productId) {
		return userStoryRepository.findByProductIdAndSprintIsNull(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByProductIdWithoutFeature(Long productId) {
		return userStoryRepository.findByProductIdAndFeatureIsNull(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserStory> getListUserStoriesByProductIdWithoutTopic(Long productId) {
		return userStoryRepository.findByProductIdAndTopicIsNull(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserStory update(Long id, UserStoryInput input) {
		UserStory us = getById(id).orElseThrow(() -> new UnknownIDException(id));

		UserStory userStory = userStoryInputMapper.patch(input, us);

		updateUSTopicSprintAndFeature(userStory, input);

		return save(userStory);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserStory save(UserStory us) {
		Notification notification = userStoryStateValidatorService.validateStatus(us);
		if (notification.isErrorFree()) {
			return userStoryRepository.save(us);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	private void updateUSTopicSprintAndFeature(UserStory userStory, UserStoryInput input) {

		if (input.canPatch(UserStoryInput.Fields.sprintId)) {
			if (input.getSprintId() != null) {
				mapSprint(userStory, input);

			} else {
				userStory.setSprint(null);
			}
		}
		if (input.canPatch(UserStoryInput.Fields.featureId)) {
			if (input.getFeatureId() != null) {
				mapFeature(userStory, input);

			} else {
				userStory.setFeature(null);
			}
		}
		if (input.canPatch(UserStoryInput.Fields.topicId)) {
			if (input.getTopicId() != null) {
				mapTopic(userStory, input);

			} else {
				userStory.setTopic(null);
			}
		}
	}

	private void setUSTopicSprintAndFeature(UserStory userStory, UserStoryInput input) {

		if (input.getSprintId() != null) {
			mapSprint(userStory, input);
		}

		if (input.getFeatureId() != null) {
			mapFeature(userStory, input);
		}

		if (input.getTopicId() != null) {
			mapTopic(userStory, input);
		}
	}

	private void mapTopic(UserStory userStory, UserStoryInput input) {
		Topic topic = topicService.findById(input.getTopicId())
				.orElseThrow(() -> new UnknownIDException(input.getTopicId()));

		userStory.setTopic(topic);
	}

	private void mapFeature(UserStory userStory, UserStoryInput input) {
		Feature feature = featureService.findById(input.getFeatureId())
				.orElseThrow(() -> new UnknownIDException(input.getFeatureId()));

		userStory.setFeature(feature);
	}

	private void mapSprint(UserStory userStory, UserStoryInput input) {
		Sprint sprint = sprintService.findById(input.getSprintId())
				.orElseThrow(() -> new UnknownIDException(input.getSprintId()));

		userStory.setSprint(sprint);
	}
}
