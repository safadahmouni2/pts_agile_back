package com.thinktank.pts.agileservice.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.thinktank.pts.apibase.model.base.AbstractBaseMainTraceableEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author laifia
 * @since 28 Feb 2023
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SPRINT")
public class Sprint extends AbstractBaseMainTraceableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "NAME", nullable = false, length = 250)
	private String name;

	@Column(name = "START_DATE")
	private LocalDate startDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

	@Column(name = "DS_START_TIME")
	private LocalTime dsStartTime;

	@Column(name = "DS_DURATION")
	private Long dsDuration;

	@Column(name = "SHORT_NAME", length = 100)
	private String shortName;

	@Column(name = "PROJECT", length = 100)
	private String project;

	@Column(name = "PARENT", length = 100)
	private String parent;

	@Column(name = "DS_MEETING_URL", length = 500)
	private String dsMeetingUrl;

	@OneToMany
	@JoinColumn(name = "SPRINT_ID")
	private List<DailyScrum> dailyScrums;

	@OneToMany
	@JoinColumn(name = "SPRINT_ID")
	private List<UserStory> userStories;
}
