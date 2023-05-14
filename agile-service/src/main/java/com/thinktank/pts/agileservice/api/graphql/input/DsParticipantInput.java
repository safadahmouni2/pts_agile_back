package com.thinktank.pts.agileservice.api.graphql.input;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 
 * @author dahmounis
 * @since 04 Apr 2023
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class DsParticipantInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long stateId;

	private Long userId;

	private String userCode;

	private Long dailyScrumId;

	private Long ticketId;

}
