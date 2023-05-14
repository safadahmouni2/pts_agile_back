package com.thinktank.pts.agileservice.service;

import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.apibase.business.service.Notification;

/**
 * 
 * @author karabakaa
 * @since May 9, 2023
 *
 */
public interface SprintMemberValidatorService {

	/**
	 * 
	 * @param sprintId
	 * @param sprintMember
	 * @return
	 */
	Notification validateSmNumbers(Long sprintId, SprintMember sprintMember);

}
