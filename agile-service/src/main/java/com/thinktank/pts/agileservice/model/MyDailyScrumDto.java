package com.thinktank.pts.agileservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * JSON object add to used in method getMyDailyScrum as response
 *
 * @author karabakaa
 * @since Apr 24, 2023
 *
 */
@Data
@RequiredArgsConstructor
public class MyDailyScrumDto {

	final private Long sprintId;
	final private Long dsId;

	final private Long sprintTicketId;

	final private String sprintName;

	final private LocalDate startDate;

	final private LocalDate endDate;

	final private LocalTime dsStartTime;

	final private Long dsDuration;

	final private LocalDateTime dsStartedAt;

	final private LocalDateTime dsFinishedAt;

	final private Long sprintStateId;

	final private Long productId;

	final private LocalDateTime dsCreated;

	final private Long dsSprintId;

	final private String scrumMasterBySprint;

	final private BigDecimal sprintProgress;

	// fields used only in result
	private String dsStatusInfo;
	private LocalTime dsEndTime;
}
