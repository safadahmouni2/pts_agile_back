package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.UserStoryInput;
import com.thinktank.pts.agileservice.model.UserStory;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

/**
 * 
 * @author lamiam
 * @since 6 Apr 2023
 *
 */
public class UserStoryInputMapper extends AbstractBaseGraphQlInputMapper<UserStoryInput, UserStory> {

	@Override
	public UserStory create(UserStoryInput input) {
		UserStory result = null;
		if (input != null) {

			result = new UserStory();
			result.setAcceptanceCriteria(input.getAcceptanceCriteria());
			result.setBenefit(input.getBenefit());
			result.setChatMessage(input.getChatMessage());
			result.setGoal(input.getGoal());
			result.setLongDescription(input.getLongDescription());
			result.setOrderByDs(input.getOrderByDs());
			result.setOrderByTopic(input.getOrderByTopic());
			result.setParentId(input.getParentId());
			result.setProductId(input.getProductId());
			result.setProgress(input.getProgress());
			result.setProject(input.getProject());
			result.setResponsibleId(input.getResponsibleId());
			result.setRole(input.getRole());
			result.setShortDescription(input.getShortDescription());
			result.setStateId(input.getStateId());
			result.setStoryPoints(input.getStoryPoints());
			result.setTestId(input.getTestId());
			result.setTestRunID(input.getTestRunID());
			result.setTestStepID(input.getTestStepID());
			result.setUrgencyId(input.getUrgencyId());
			result.setUsId(input.getUsId());
			result.setUserCode(input.getUserCode());

		}
		return result;
	}

	@Override
	protected UserStory patchFields(UserStoryInput input, UserStory entity) {
		patchAcceptanceCriteria(input, entity);
		patchBenefit(input, entity);
		patchChatMessage(input, entity);
		patchGoal(input, entity);
		patchLongDescription(input, entity);
		patchOrderByDs(input, entity);
		patchOrderByTopic(input, entity);
		patchParentId(input, entity);
		patchProductId(input, entity);
		patchProgress(input, entity);
		patchProject(input, entity);
		patchResponsibleId(input, entity);
		patchUserCode(input, entity);
		patchRole(input, entity);
		patchShortDescription(input, entity);
		patchStateId(input, entity);
		patchStoryPoints(input, entity);
		patchTestId(input, entity);
		patchTestRunId(input, entity);
		patchTestStepId(input, entity);
		patchUrgency(input, entity);
		return null;
	}

	private void patchAcceptanceCriteria(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.acceptanceCriteria)) {
			entity.setAcceptanceCriteria(input.getAcceptanceCriteria());
		}
	}

	private void patchBenefit(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.benefit)) {
			entity.setBenefit(input.getBenefit());
		}
	}

	private void patchChatMessage(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.chatMessage)) {
			entity.setChatMessage(input.getChatMessage());
		}
	}

	private void patchGoal(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.goal)) {
			entity.setGoal(input.getAcceptanceCriteria());
		}
	}

	private void patchLongDescription(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.longDescription)) {
			entity.setLongDescription(input.getLongDescription());
		}
	}

	private void patchOrderByDs(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.orderByDs)) {
			entity.setOrderByDs(input.getOrderByDs());
		}
	}

	private void patchOrderByTopic(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.orderByTopic)) {
			entity.setOrderByTopic(input.getOrderByTopic());
		}
	}

	private void patchParentId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.parentId)) {
			entity.setParentId(input.getParentId());
		}
	}

	private void patchProductId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.productId)) {
			entity.setProductId(input.getProductId());
		}
	}

	private void patchProgress(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.progress)) {
			entity.setProgress(input.getProgress());
		}
	}

	private void patchProject(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.project)) {
			entity.setProject(input.getProject());
		}
	}

	private void patchResponsibleId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.responsibleId)) {
			entity.setResponsibleId(input.getResponsibleId());
		}
	}

	private void patchUserCode(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.userCode)) {
			entity.setUserCode(input.getUserCode());
		}
	}

	private void patchRole(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.role)) {
			entity.setRole(input.getRole());
		}
	}

	private void patchShortDescription(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.shortDescription)) {
			entity.setShortDescription(input.getShortDescription());
		}
	}

	private void patchStateId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	private void patchStoryPoints(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.storyPoints)) {
			entity.setStoryPoints(input.getStoryPoints());
		}
	}

	private void patchTestId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.testId)) {
			entity.setTestId(input.getTestId());
		}
	}

	private void patchTestRunId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.testRunID)) {
			entity.setTestRunID(input.getTestRunID());
		}
	}

	private void patchTestStepId(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.testStepID)) {
			entity.setTestStepID(input.getTestStepID());
		}
	}

	private void patchUrgency(UserStoryInput input, UserStory entity) {
		if (input.canPatch(UserStoryInput.Fields.urgencyId)) {
			entity.setUrgencyId(input.getUrgencyId());
		}
	}

}
