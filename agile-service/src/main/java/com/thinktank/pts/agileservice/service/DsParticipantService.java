package com.thinktank.pts.agileservice.service;

import java.util.Optional;

import com.thinktank.pts.agileservice.api.graphql.input.UpdateDsParticipantInput;
import com.thinktank.pts.agileservice.model.DsParticipant;

public interface DsParticipantService {
	/**
	 * 
	 * @param dsParticipant
	 * @return DsParticipant created {@link com.thinktank.pts.agileservice.model.DsParticipant}
	 */
	DsParticipant save(DsParticipant dsParticipant);

	/**
	 * 
	 * @param id
	 * @param dailyScrum
	 *            input
	 * @return DailyScrum updated {@link com.thinktank.pts.agileservice.model.DailyScrum}
	 */
	DsParticipant update(Long id, UpdateDsParticipantInput input);

	/**
	 * Method used to retrieve the ds participant by id
	 * 
	 * @param id
	 * @return Optional DsParticipant {@link com.thinktank.pts.agileservice.model.DsParticipant}
	 */
	Optional<DsParticipant> getById(Long id);

}
