package com.thinktank.pts.agileservice.api.graphql.input;

import java.math.BigDecimal;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class UserStoryInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long usId;

	private Long stateId;

	private Long testRunID;

	private Long testStepID;

	private Long featureId;

	private Long testId;

	private Long sprintId;

	private Long topicId;

	private Long productId;

	private String project;

	private Long parentId;

	private Long urgencyId;

	private String shortDescription;

	private String longDescription;

	private String acceptanceCriteria;

	private String role;

	private String goal;

	private String benefit;

	private String chatMessage;

	private Long responsibleId;
	// TODO
	private String userCode;

	private BigDecimal progress;

	private BigDecimal storyPoints;

	private BigDecimal orderByDs;

	private BigDecimal orderByTopic;

}
