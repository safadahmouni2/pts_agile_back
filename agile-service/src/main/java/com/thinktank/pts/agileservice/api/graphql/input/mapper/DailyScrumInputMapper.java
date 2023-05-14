package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.DailyScrumInput;
import com.thinktank.pts.agileservice.model.DailyScrum;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

/**
 * 
 * @author karabakaa
 * @since Apr 5, 2023
 *
 */
public class DailyScrumInputMapper extends AbstractBaseGraphQlInputMapper<DailyScrumInput, DailyScrum> {

	@Override
	public DailyScrum create(DailyScrumInput input) {

		DailyScrum result = null;
		if (input != null) {

			result = new DailyScrum();

			result.setStateId(input.getStateId());
			result.setStartedAt(input.getStartedAt());
			result.setFinishedAt(input.getFinishedAt());
			result.setSprintProgress(input.getSprintProgress());

		}
		return result;

	}

	@Override
	protected DailyScrum patchFields(DailyScrumInput input, DailyScrum entity) {

		patchStateId(input, entity);
		patchFinishedAt(input, entity);
		patchSprintProgress(input, entity);

		return entity;
	}

	private void patchSprintProgress(DailyScrumInput input, DailyScrum entity) {
		if (input.canPatch(DailyScrumInput.Fields.sprintProgress)) {
			entity.setSprintProgress(input.getSprintProgress());
		}
	}

	private void patchFinishedAt(DailyScrumInput input, DailyScrum entity) {
		if (input.canPatch(DailyScrumInput.Fields.finishedAt)) {
			entity.setFinishedAt(input.getFinishedAt());
		}
	}

	private void patchStateId(DailyScrumInput input, DailyScrum entity) {
		if (input.canPatch(DailyScrumInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

}
