package com.thinktank.pts.agileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.SprintMemberInputMapper;
import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.agileservice.repository.SprintMemberRepository;
import com.thinktank.pts.agileservice.service.SprintMemberService;
import com.thinktank.pts.agileservice.service.SprintMemberValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

/**
 * 
 * @author bettaiebs
 * @since 29 Feb 2023
 *
 */

@Service
public class SprintMemberServiceImpl implements SprintMemberService {

	@Autowired
	private SprintMemberRepository sprintMemberRepository;

	@Autowired
	private SprintMemberValidatorService sprintMemberValidatorService;

	private SprintMemberInputMapper sprintMemberInputMapper = new SprintMemberInputMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SprintMember create(SprintMember sprintMember) {
		return save(sprintMember);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public SprintMember update(Long id, SprintMemberInput input) {
		SprintMember sm = sprintMemberRepository.findById(id).orElseThrow(() -> new UnknownIDException(id));

		SprintMember sprintMember = sprintMemberInputMapper.patch(input, sm);

		return save(sprintMember);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SprintMember save(SprintMember sprintMember) {
		Notification notification = sprintMemberValidatorService.validateSmNumbers(sprintMember.getSprintId(),
				sprintMember);
		if (notification.isErrorFree()) {
			return sprintMemberRepository.save(sprintMember);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SprintMember> findBySprintId(Long sprintId) {
		return this.sprintMemberRepository.findBySprintId(sprintId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SprintMember getById(Long id) {
		return this.sprintMemberRepository.getById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SprintMember> filterSprintMembers(Long sprintId, Long stateId) {
		List<SprintMember> result;
		if (stateId != null && stateId != 0) {
			result = this.sprintMemberRepository.findBySprintIdAndStateId(sprintId, stateId);
		} else {
			result = this.sprintMemberRepository.findBySprintId(sprintId);
		}

		return result;
	}

}
