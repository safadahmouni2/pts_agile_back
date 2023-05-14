package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.apibase.business.service.Notification;

/**
 * 
 * @author karabakaa
 * @since May 5, 2023
 *
 */
public interface UserStoryStateValidatorService {

	Notification validateStatus(UserStory usToValidate);

}
