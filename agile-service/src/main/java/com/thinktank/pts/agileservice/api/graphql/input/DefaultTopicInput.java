package com.thinktank.pts.agileservice.api.graphql.input;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 
 * @author dahmounis
 * @since 23 Mar 2023
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class DefaultTopicInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long productId;

	private Long topicId;

}
