package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.apibase.business.service.Notification;

public interface TopicStateValidatorService {

	Notification validateStatus(Topic topicToValidate);

}
