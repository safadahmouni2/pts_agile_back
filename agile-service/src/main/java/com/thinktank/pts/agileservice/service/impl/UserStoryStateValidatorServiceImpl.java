package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.agileservice.repository.UserStoryRepository;
import com.thinktank.pts.agileservice.service.UserStoryStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

/**
 * 
 * @author karabakaa
 * @since May 5, 2023
 *
 */
@Service
public class UserStoryStateValidatorServiceImpl implements UserStoryStateValidatorService {

	@Autowired
	private UserStoryRepository userStoryRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(UserStory usToValidate) {

		Notification result = new Notification();
		if (usToValidate.getId() != null) {
			UserStory existingUs = userStoryRepository.getById(usToValidate.getId());

			if (!isAllowedStatusTransition(existingUs, usToValidate)) {

				result.addError(getValidationMessage(existingUs.getStateId(), usToValidate.getStateId()));

			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(UserStory existingUs, UserStory usToValidate) {
		boolean result = true;
		if (!Objects.equal(existingUs.getStateId(), usToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi.getNextAllowedStatesForUserStory(existingUs.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), usToValidate.getStateId()));
		}

		return result;

	}

}