package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.DsParticipantInput;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

public class DsParticipantInputMapper extends AbstractBaseGraphQlInputMapper<DsParticipantInput, DsParticipant> {

	@Override
	public DsParticipant create(DsParticipantInput input) {
		DsParticipant result = null;

		if (input != null) {

			result = new DsParticipant();

			result.setStateId(input.getStateId());
			result.setUserId(input.getUserId());
			result.setUserCode(input.getUserCode());
			result.setTicketId(input.getTicketId());

		}
		return result;
	}

	@Override
	protected DsParticipant patchFields(DsParticipantInput input, DsParticipant entity) {
		patchStateId(input, entity);
		patchUserCode(input, entity);
		patchUserId(input, entity);
		return entity;
	}

	private void patchStateId(DsParticipantInput input, DsParticipant entity) {
		if (input.canPatch(DsParticipantInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	private void patchUserId(DsParticipantInput input, DsParticipant entity) {
		if (input.canPatch(DsParticipantInput.Fields.userId)) {
			entity.setUserId(input.getUserId());
		}
	}

	private void patchUserCode(DsParticipantInput input, DsParticipant entity) {

		if (input.canPatch(DsParticipantInput.Fields.userCode)) {
			entity.setUserCode(input.getUserCode());
		}
	}

}
