package com.thinktank.pts.agileservice.api.graphql.input;

import java.time.LocalDate;
import java.time.LocalTime;

import com.thinktank.pts.apibase.graphql.AbstractGraphQlArgumentAwareBaseInput;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 
 * @author laifia
 * @since 1 Mar 2023
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants(level = AccessLevel.PUBLIC)
public class SprintInput extends AbstractGraphQlArgumentAwareBaseInput {

	private Long productId;

	private Long stateId;

	private String name;

	private LocalDate startDate;

	private LocalDate endDate;

	private LocalTime dsStartTime;

	private Long dsDuration;

	private String shortName;

	private String project;

	private String parent;

	private String dsMeetingUrl;

}
