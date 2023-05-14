package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.SprintInput;
import com.thinktank.pts.agileservice.model.Sprint;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

/**
 * 
 * @author laifia
 * @since 1 Mar 2023
 *
 */
public class SprintInputMapper extends AbstractBaseGraphQlInputMapper<SprintInput, Sprint> {

	@Override
	public Sprint create(SprintInput input) {

		Sprint result = null;
		if (input != null) {

			result = new Sprint();
			result.setProductId(input.getProductId());
			result.setStateId(input.getStateId());
			result.setName(input.getName());
			result.setStartDate(input.getStartDate());
			result.setEndDate(input.getEndDate());
			result.setDsStartTime(input.getDsStartTime());
			result.setDsDuration(input.getDsDuration());
			result.setShortName(input.getShortName());
			result.setProject(input.getProject());
			result.setParent(input.getParent());
			result.setDsMeetingUrl(input.getDsMeetingUrl());

		}
		return result;

	}

	@Override
	protected Sprint patchFields(SprintInput input, Sprint entity) {

		patchProductId(input, entity);
		patchStateId(input, entity);
		patchName(input, entity);
		patchStartDate(input, entity);
		patchEndDate(input, entity);
		patchDsStartTime(input, entity);
		patchDsDuration(input, entity);
		patchShortName(input, entity);
		patchDsProject(input, entity);
		patchDsParent(input, entity);
		patchDsMeetingUrl(input, entity);

		return entity;
	}

	private void patchDsMeetingUrl(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.dsMeetingUrl)) {
			entity.setDsMeetingUrl(input.getDsMeetingUrl());
		}
	}

	private void patchDsParent(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.parent)) {
			entity.setParent(input.getParent());
		}
	}

	private void patchDsProject(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.project)) {
			entity.setProject(input.getProject());
		}
	}

	private void patchShortName(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.shortName)) {
			entity.setShortName(input.getShortName());
		}
	}

	private void patchDsDuration(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.dsDuration)) {
			entity.setDsDuration(input.getDsDuration());
		}
	}

	private void patchDsStartTime(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.dsStartTime)) {
			entity.setDsStartTime(input.getDsStartTime());
		}
	}

	private void patchEndDate(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.endDate)) {
			entity.setEndDate(input.getEndDate());
		}
	}

	private void patchName(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.name)) {
			entity.setName(input.getName());
		}
	}

	private void patchStartDate(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.startDate)) {
			entity.setStartDate(input.getStartDate());
		}
	}

	private void patchStateId(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	private void patchProductId(SprintInput input, Sprint entity) {
		if (input.canPatch(SprintInput.Fields.productId)) {
			entity.setProductId(input.getProductId());
		}
	}

}
