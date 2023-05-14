package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.repository.SprintRepository;
import com.thinktank.pts.agileservice.service.SprintStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

@Service
public class SprintStateValidatorServiceImpl implements SprintStateValidatorService {

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(Sprint sprintToValidate) {

		Notification result = new Notification();
		if (sprintToValidate.getId() != null) {
			Sprint existingSprint = sprintRepository.getById(sprintToValidate.getId());

			if (!isAllowedStatusTransition(existingSprint, sprintToValidate)) {

				result.addError(getValidationMessage(existingSprint.getStateId(), sprintToValidate.getStateId()));

			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(Sprint existingSprint, Sprint sprintToValidate) {
		boolean result = true;
		if (!Objects.equal(existingSprint.getStateId(), sprintToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi.getNextAllowedStatesForSprint(existingSprint.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), sprintToValidate.getStateId()));
		}

		return result;

	}

}
