package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.TopicInput;
import com.thinktank.pts.agileservice.model.Topic;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

/**
 * 
 * @author chemkhih
 * @since Feb 21, 2023
 *
 */
public class TopicInputMapper extends AbstractBaseGraphQlInputMapper<TopicInput, Topic> {

	@Override
	public Topic create(TopicInput input) {
		Topic result = null;

		if (input != null) {

			result = new Topic();

			result.setDescription(input.getDescription());
			result.setDisplayOrder(input.getDisplayOrder());
			result.setName(input.getName());
			result.setProductId(input.getProductId());
			result.setStateId(input.getStateId());
			result.setProject(input.getProject());
		}
		return result;
	}

	@Override
	protected Topic patchFields(TopicInput input, Topic entity) {
		patchDescription(input, entity);
		patchDisplayOrder(input, entity);
		patchName(input, entity);
		patchProductId(input, entity);
		patchProject(input, entity);
		patchStateId(input, entity);
		return entity;
	}

	private void patchDisplayOrder(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.displayOrder)) {
			entity.setDisplayOrder(input.getDisplayOrder());
		}
	}

	private void patchStateId(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	private void patchProductId(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.productId)) {
			entity.setProductId(input.getProductId());
		}
	}

	private void patchProject(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.project)) {
			entity.setProject(input.getProject());
		}
	}

	private void patchName(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.name)) {
			entity.setName(input.getName());
		}
	}

	private void patchDescription(TopicInput input, Topic entity) {
		if (input.canPatch(TopicInput.Fields.description)) {
			entity.setDescription(input.getDescription());
		}
	}
}
