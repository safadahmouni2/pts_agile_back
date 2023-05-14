package com.thinktank.pts.agileservice.api.graphql.input;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class DailyScrumInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long stateId;

	private Long sprintId;

	private LocalDateTime startedAt;

	private LocalDateTime finishedAt;

	private BigDecimal sprintProgress;

}
