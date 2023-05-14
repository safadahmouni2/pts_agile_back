package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.SprintMemberInput;
import com.thinktank.pts.agileservice.model.SprintMember;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

/**
 * 
 * @author bettaiebs
 * @since 6 March 2023
 */

public class SprintMemberInputMapper extends AbstractBaseGraphQlInputMapper<SprintMemberInput, SprintMember> {

	@Override
	public SprintMember create(SprintMemberInput input) {

		SprintMember result = null;
		if (input != null) {

			result = new SprintMember();
			result.setStateId(input.getStateId());
			result.setUserCode(input.getUserCode());
			result.setRole(input.getRole());
			result.setSprintId(input.getSprintId());
		}

		return result;
	}

	@Override
	protected SprintMember patchFields(SprintMemberInput input, SprintMember entity) {

		patchStateId(input, entity);
		patchUserCode(input, entity);
		patchRole(input, entity);
		patchSprintId(input, entity);

		return entity;
	}

	/**
	 * @param input
	 * @param entity
	 */
	private void patchStateId(SprintMemberInput input, SprintMember entity) {

		if (input.canPatch(SprintMemberInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	/**
	 * @param input
	 * @param entity
	 */
	private void patchUserCode(SprintMemberInput input, SprintMember entity) {

		if (input.canPatch(SprintMemberInput.Fields.userCode)) {
			entity.setUserCode(input.getUserCode());
		}
	}

	/**
	 * @param input
	 * @param entity
	 */
	private void patchRole(SprintMemberInput input, SprintMember entity) {

		if (input.canPatch(SprintMemberInput.Fields.role)) {
			entity.setRole(input.getRole());
		}
	}

	/**
	 * @param input
	 * @param entity
	 */
	private void patchSprintId(SprintMemberInput input, SprintMember entity) {

		if (input.canPatch(SprintMemberInput.Fields.sprintId)) {
			entity.setSprintId(input.getSprintId());
		}
	}

}
