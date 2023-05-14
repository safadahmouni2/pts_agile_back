package com.thinktank.pts.agileservice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thinktank.pts.agileservice.api.graphql.input.UpdateDsParticipantInput;
import com.thinktank.pts.agileservice.api.graphql.input.mapper.UpdateDsParticipantInputMapper;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.agileservice.repository.DsParticipantRepository;
import com.thinktank.pts.agileservice.service.DsParticipantService;
import com.thinktank.pts.agileservice.service.DsParticipantValidatorService;
import com.thinktank.pts.apibase.business.service.Notification;
import com.thinktank.pts.apibase.graphql.exception.EntityValidationException;
import com.thinktank.pts.apibase.graphql.exception.UnknownIDException;

@Service
public class DsParticipantServiceImpl implements DsParticipantService {

	@Autowired
	private DsParticipantRepository dsParticipantRepository;

	@Autowired
	DsParticipantValidatorService dsParticipantValidatorService;

	private UpdateDsParticipantInputMapper updateDsParticipantInputMapper = new UpdateDsParticipantInputMapper();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DsParticipant save(DsParticipant dsParticipant) {
		Notification notification = dsParticipantValidatorService.validateStatus(dsParticipant);
		if (notification.isErrorFree()) {
			return dsParticipantRepository.save(dsParticipant);
		} else {
			throw new EntityValidationException(notification);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public DsParticipant update(Long id, UpdateDsParticipantInput input) {
		Optional<DsParticipant> opt = getById(id);

		if (!opt.isPresent()) {
			throw new UnknownIDException(id);

		}

		DsParticipant dsParticipant = updateDsParticipantInputMapper.patch(input, opt.get());

		return save(dsParticipant);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<DsParticipant> getById(Long id) {
		return dsParticipantRepository.findById(id);
	}

}
