package com.thinktank.pts.agileservice.model;

import java.io.Serializable;

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

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DS_PARTICIPANT")
public class DsParticipant extends AbstractBaseMainTraceableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_CODE")
	private String userCode;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@ManyToOne
	@JoinColumn(name = "DAILY_SCRUM_ID", referencedColumnName = "id", nullable = false)
	private DailyScrum dailyScrum;
}
