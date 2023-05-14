package com.thinktank.pts.agileservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.agileservice.repository.SprintMemberRepository;
import com.thinktank.pts.agileservice.service.SprintMemberValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;

/**
 * 
 * @author karabakaa
 * @since May 9, 2023
 *
 */
@Service
public class SprintMemberValidatorServiceImpl implements SprintMemberValidatorService {

	private static final String SCRUM_MASTER_ROLE = "Scrum master";
	private static final Long ON_SM_STATE_ID = 1030058L;

	@Autowired
	private SprintMemberRepository sprintMemberRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Notification validateSmNumbers(Long sprintId, SprintMember sprintMember) {

		Notification result = new Notification();

		Long smNumbers = sprintMemberRepository.getCountOfScrumMasterBySprint(sprintId);
		if (smNumbers > 0 && SCRUM_MASTER_ROLE.equals(sprintMember.getRole())
				&& ON_SM_STATE_ID.equals(sprintMember.getStateId())) {

			result.addError(getValidationMessage());

		}
		return result;
	}

	private String getValidationMessage() {
		return "Sprint must have only one scrum master!";
	}
}