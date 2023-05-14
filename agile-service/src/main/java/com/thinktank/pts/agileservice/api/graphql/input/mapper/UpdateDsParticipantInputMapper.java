package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.UpdateDsParticipantInput;
import com.thinktank.pts.agileservice.model.DsParticipant;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

public class UpdateDsParticipantInputMapper
		extends AbstractBaseGraphQlInputMapper<UpdateDsParticipantInput, DsParticipant> {

	@Override
	public DsParticipant create(UpdateDsParticipantInput input) {
		DsParticipant result = null;

		if (input != null) {

			result = new DsParticipant();

			result.setStateId(input.getStateId());

		}
		return result;
	}

	@Override
	protected DsParticipant patchFields(UpdateDsParticipantInput input, DsParticipant entity) {
		patchStateId(input, entity);
		return entity;
	}

	private void patchStateId(UpdateDsParticipantInput input, DsParticipant entity) {
		if (input.canPatch(UpdateDsParticipantInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

}
