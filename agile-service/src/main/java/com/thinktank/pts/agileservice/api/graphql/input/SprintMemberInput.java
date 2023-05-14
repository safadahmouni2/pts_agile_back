package com.thinktank.pts.agileservice.api.graphql.input;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 
 * @author bettaiebs
 * @since 6 March 2023
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class SprintMemberInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long stateId;

	private String userCode;

	private String role;

	private Long sprintId;

}
