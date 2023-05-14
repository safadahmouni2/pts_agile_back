package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.agileservice.repository.DsParticipantRepository;
import com.thinktank.pts.agileservice.service.DsParticipantValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

@Service
public class DsParticipantValidatorServiceImpl implements DsParticipantValidatorService {
	@Autowired
	private DsParticipantRepository dsParticipantRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(DsParticipant dsParticipantToValidate) {
		Notification result = new Notification();
		if (dsParticipantToValidate.getId() != null) {
			DsParticipant existingDsParticipant = dsParticipantRepository.getById(dsParticipantToValidate.getId());

			if (!isAllowedStatusTransition(existingDsParticipant, dsParticipantToValidate)) {

				result.addError(
						getValidationMessage(existingDsParticipant.getStateId(), dsParticipantToValidate.getStateId()));
			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(DsParticipant existingDsParticipant,
			DsParticipant dsParticipantToValidate) {
		boolean result = true;
		if (!Objects.equal(existingDsParticipant.getStateId(), dsParticipantToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi
					.getNextAllowedStatesForDsParticipant(existingDsParticipant.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), dsParticipantToValidate.getStateId()));
		}

		return result;
	}

}
