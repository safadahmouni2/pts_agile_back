package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.apibase.business.service.Notification;

public interface FeatureStateValidatorService {

	Notification validateStatus(Feature featureToValidate);

}
