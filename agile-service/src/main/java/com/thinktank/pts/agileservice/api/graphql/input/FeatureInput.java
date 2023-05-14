package com.thinktank.pts.agileservice.api.graphql.input;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class FeatureInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long stateId;

	private Long productId;

	private String project;

	private String name;

	private String description;

	private Long displayOrder;

}
