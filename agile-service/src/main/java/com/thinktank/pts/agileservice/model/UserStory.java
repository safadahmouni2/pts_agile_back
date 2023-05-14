package com.thinktank.pts.agileservice.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.thinktank.pts.apibase.model.base.AbstractBaseMainTraceableEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author lamiam
 * @since 5 Apr 2023
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_STORY")
public class UserStory extends AbstractBaseMainTraceableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "US_ID")
	private Long usId;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@Column(name = "TEST_RUN_ID")
	private Long testRunID;

	@Column(name = "TEST_STEP_ID")
	private Long testStepID;

	@ManyToOne
	@JoinColumn(name = "FEATURE_ID", referencedColumnName = "id")
	private Feature feature;

	@Column(name = "TEST_ID")
	private Long testId;

	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", referencedColumnName = "id")
	private Sprint sprint;

	@ManyToOne
	@JoinColumn(name = "TOPIC_ID", referencedColumnName = "id")
	private Topic topic;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	// TODO to be checked
	@Column(name = "PROJECT")
	private String project;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "URGENCY_ID")
	private Long urgencyId;

	@Column(name = "SHORT_DESCRIPTION")
	private String shortDescription;

	@Column(name = "LONG_DESCRIPTION")
	private String longDescription;

	@Column(name = "ACCEPTANCE_CRITERIA")
	private String acceptanceCriteria;

	@Column(name = "ROLE", length = 250)
	private String role;

	@Column(name = "GOAL")
	private String goal;

	@Column(name = "BENEFIT")
	private String benefit;

	@Column(name = "CHAT_MESSAGE")
	private String chatMessage;

	@Column(name = "RESPONSIBLE_ID")
	private Long responsibleId;

	@Column(name = "PROGRESS")
	private BigDecimal progress;

	@Column(name = "STORY_POINTS")
	private BigDecimal storyPoints;

	@Column(name = "ORDER_BY_DS")
	private BigDecimal orderByDs;

	@Column(name = "ORDER_BY_TOPIC")
	private BigDecimal orderByTopic;

	@Column(name = "USER_CODE")
	private String userCode;
}
