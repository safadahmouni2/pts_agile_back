package com.thinktank.pts.agileservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.thinktank.pts.apibase.model.base.AbstractBaseMainTraceableEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author bettaiebs
 * @since 29 Feb 2023
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SPRINT_MEMBER")
public class SprintMember extends AbstractBaseMainTraceableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "USER_CODE", length = 10)
	private String userCode;

	@Column(name = "ROLE", length = 250)
	private String role;

	@Column(name = "SPRINT_ID")
	private Long sprintId;

}
