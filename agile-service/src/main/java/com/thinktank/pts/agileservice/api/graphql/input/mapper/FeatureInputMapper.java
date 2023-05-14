package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.FeatureInput;
import com.thinktank.pts.agileservice.model.Feature;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

public class FeatureInputMapper extends AbstractBaseGraphQlInputMapper<FeatureInput, Feature> {

	@Override
	public Feature create(FeatureInput input) {
		Feature result = null;
		if (input != null) {
			result = new Feature();
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
	protected Feature patchFields(FeatureInput input, Feature entity) {
		patchStateId(input, entity);
		patchProductId(input, entity);
		patchProject(input, entity);
		patchName(input, entity);
		patchDescription(input, entity);
		patchDisplayOrder(input, entity);
		return entity;
	}

	private void patchDisplayOrder(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.displayOrder)) {
			entity.setDisplayOrder(input.getDisplayOrder());
		}
	}

	private void patchStateId(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.stateId)) {
			entity.setStateId(input.getStateId());
		}
	}

	private void patchProductId(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.productId)) {
			entity.setProductId(input.getProductId());
		}
	}

	private void patchProject(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.project)) {
			entity.setProject(input.getProject());
		}
	}

	private void patchName(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.name)) {
			entity.setName(input.getName());
		}
	}

	private void patchDescription(FeatureInput input, Feature entity) {
		if (input.canPatch(FeatureInput.Fields.description)) {
			entity.setDescription(input.getDescription());
		}
	}
}
