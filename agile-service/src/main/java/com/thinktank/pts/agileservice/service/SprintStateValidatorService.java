package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.apibase.business.service.Notification;

public interface SprintStateValidatorService {

	Notification validateStatus(Sprint sprintToValidate);

}
