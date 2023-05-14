package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.agileservice.repository.TopicRepository;
import com.thinktank.pts.agileservice.service.TopicStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

@Service
public class TopicStateValidatorServiceImpl implements TopicStateValidatorService {

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(Topic topicToValidate) {

		Notification result = new Notification();
		if (topicToValidate.getId() != null) {
			Topic existingTopic = topicRepository.getById(topicToValidate.getId());

			if (!isAllowedStatusTransition(existingTopic, topicToValidate)) {

				result.addError(getValidationMessage(existingTopic.getStateId(), topicToValidate.getStateId()));

			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(Topic existingTopic, Topic topicToValidate) {
		boolean result = true;
		if (!Objects.equal(existingTopic.getStateId(), topicToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi.getNextAllowedStatesForTopic(existingTopic.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), topicToValidate.getStateId()));
		}

		return result;

	}

}
