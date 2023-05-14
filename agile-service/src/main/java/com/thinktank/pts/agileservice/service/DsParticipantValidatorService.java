package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.apibase.business.service.Notification;

public interface DsParticipantValidatorService {

	Notification validateStatus(DsParticipant dsParticipantToValidate);

}
