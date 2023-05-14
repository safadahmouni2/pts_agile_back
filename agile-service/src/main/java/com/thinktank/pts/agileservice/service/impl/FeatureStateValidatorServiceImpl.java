package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.rest.client.pts.StateApi;
import com.thinktank.pts.agileservice.api.rest.client.pts.model.State;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.agileservice.repository.FeatureRepository;
import com.thinktank.pts.agileservice.service.FeatureStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

import graphql.com.google.common.base.Objects;

@Service
public class FeatureStateValidatorServiceImpl implements FeatureStateValidatorService {

	@Autowired
	private FeatureRepository featureRepository;

	@Autowired
	@Qualifier("customPtsCoreStateApi")
	private StateApi stateApi;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateStatus(Feature featureToValidate) {

		Notification result = new Notification();
		if (featureToValidate.getId() != null) {
			Feature existingFeature = featureRepository.getById(featureToValidate.getId());

			if (!isAllowedStatusTransition(existingFeature, featureToValidate)) {

				result.addError(getValidationMessage(existingFeature.getStateId(), featureToValidate.getStateId()));

			}
		}
		return result;
	}

	private String getValidationMessage(Long stateId, Long stateToValidate) {
		return "[" + stateId + ":" + stateToValidate + "] " + " Transition not allowed";
	}

	private boolean isAllowedStatusTransition(Feature existingFeature, Feature featureToValidate) {
		boolean result = true;
		if (!Objects.equal(existingFeature.getStateId(), featureToValidate.getStateId())) {
			List<State> allowedStates = this.stateApi.getNextAllowedStatesForFeature(existingFeature.getStateId());
			result = allowedStates.stream()
					.anyMatch(state -> Objects.equal(state.getStatusId(), featureToValidate.getStateId()));
		}

		return result;

	}
}
