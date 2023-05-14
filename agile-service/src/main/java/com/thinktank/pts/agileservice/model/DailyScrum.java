package com.thinktank.pts.agileservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.thinktank.pts.apibase.model.base.AbstractBaseMainTraceableEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author karabakaa
 * @since Apr 4, 2023
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DAILY_SCRUM")
public class DailyScrum extends AbstractBaseMainTraceableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@Column(name = "STARTED_AT")
	private LocalDateTime startedAt;

	@Column(name = "FINISHED_AT")
	private LocalDateTime finishedAt;

	@Column(name = "SPRINT_PROGRESS")
	private BigDecimal sprintProgress;

	@ManyToOne
	@JoinColumn(name = "SPRINT_ID", referencedColumnName = "id", nullable = false)
	private Sprint sprint;

	@OneToMany
	@JoinColumn(name = "DAILY_SCRUM_ID")
	private List<DsParticipant> dsParticipants;

}
