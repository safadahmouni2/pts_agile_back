package com.thinktank.pts.agileservice.api.graphql.input;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class SearchSprintMemberInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long sprintId;

	private Long stateId;
}
