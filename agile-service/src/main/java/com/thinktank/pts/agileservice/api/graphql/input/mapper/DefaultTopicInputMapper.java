package com.thinktank.pts.agileservice.api.graphql.input.mapper;

import com.thinktank.pts.agileservice.api.graphql.input.DefaultTopicInput;
import com.thinktank.pts.agileservice.model.DefaultTopic;
import com.thinktank.pts.apibase.graphql.input.mapper.AbstractBaseGraphQlInputMapper;

public class DefaultTopicInputMapper extends AbstractBaseGraphQlInputMapper<DefaultTopicInput, DefaultTopic> {

	@Override
	public DefaultTopic create(DefaultTopicInput input) {
		DefaultTopic result = null;

		if (input != null) {

			result = new DefaultTopic();

			result.setProductId(input.getProductId());
			result.setTopicId(input.getTopicId());

		}
		return result;
	}

	@Override
	protected DefaultTopic patchFields(DefaultTopicInput input, DefaultTopic entity) {
		patchProductId(input, entity);
		patchTopicId(input, entity);
		return entity;
	}

	private void patchTopicId(DefaultTopicInput input, DefaultTopic entity) {
		if (input.canPatch(DefaultTopicInput.Fields.topicId)) {
			entity.setTopicId(input.getTopicId());
		}
	}

	private void patchProductId(DefaultTopicInput input, DefaultTopic entity) {
		if (input.canPatch(DefaultTopicInput.Fields.productId)) {
			entity.setProductId(input.getProductId());
		}
	}

}
