package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.apibase.business.service.Notification;

/**
 * 
 * @author karabakaa
 * @since Apr 7, 2023
 *
 */
public interface DailyScrumStateValidatorService {

	Notification validateStatus(DailyScrum dailyScrumToValidate);

}
