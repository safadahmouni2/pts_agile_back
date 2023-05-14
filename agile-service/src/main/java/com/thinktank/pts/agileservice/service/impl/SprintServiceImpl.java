package com.thinktank.pts.agileservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.SprintInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.SprintInputMapper;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.agileservice.repository.SprintRepository;
import com.thinktank.pts.agileservice.service.SprintService;
import com.thinktank.pts.agileservice.service.SprintStateValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

/**
 * 
 * @author laifia
 * @since 28 Feb 2023
 *
 */
@Service
public class SprintServiceImpl implements SprintService {

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private SprintStateValidatorService sprintStateValidatorService;

	private SprintInputMapper sprintInputMapper = new SprintInputMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sprint save(Sprint sprint) {
		Notification notification = sprintStateValidatorService.validateStatus(sprint);
		if (notification.isErrorFree()) {
			return sprintRepository.save(sprint);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Sprint create(SprintInput sprintInput) {
		Sprint sprint = sprintInputMapper.create(sprintInput);
		return save(sprint);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Sprint> getSprintsByProductId(Long productId) {
		return sprintRepository.findByProductIdOrderByStartDateDesc(productId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sprint getById(Long id) {
		return sprintRepository.getById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Sprint> findById(Long id) {
		return sprintRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Sprint update(Long id, SprintInput sprintInput) {
		Sprint sprint = sprintRepository.findById(id).orElseThrow(() -> new UnknownIDException(id));
		sprint = sprintInputMapper.patch(sprintInput, sprint);
		return save(sprint);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Sprint> findByTicketId(Long ticketId) {
		return sprintRepository.findByTicketId(ticketId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getSprintProgress(Long sprintId) {
		return sprintRepository.getSprintProgress(sprintId);
	}

}
