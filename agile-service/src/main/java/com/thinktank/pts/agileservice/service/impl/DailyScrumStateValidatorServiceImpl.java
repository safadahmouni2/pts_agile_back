package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.agileservice.repository.DailyScrumRepository;
import com.thinktank.pts.agileservice.service.DailyScrumStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

/**
 * 
 * @author karabakaa
 * @since Apr 5, 2023
 *
 */
@Service
public class DailyScrumStateValidatorServiceImpl implements DailyScrumStateValidatorService {

	@Autowired
	private DailyScrumRepository dailyScrumRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(DailyScrum dailyScrumToValidate) {

		Notification result = new Notification();
		if (dailyScrumToValidate.getId() != null) {
			DailyScrum existingDs = dailyScrumRepository.getById(dailyScrumToValidate.getId());

			if (!isAllowedStatusTransition(existingDs, dailyScrumToValidate)) {

				result.addError(getValidationMessage(existingDs.getStateId(), dailyScrumToValidate.getStateId()));

			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(DailyScrum existingTopic, DailyScrum dsToValidate) {
		boolean result = true;
		if (!Objects.equal(existingTopic.getStateId(), dsToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi.getNextAllowedStatesForDailyScrum(existingTopic.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), dsToValidate.getStateId()));
		}

		return result;

	}

}