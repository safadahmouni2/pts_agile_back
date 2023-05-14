package com.thinktank.pts.agileservice.model;

import java.io.Serializable;
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

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TOPIC")
public class Topic extends AbstractBaseMainTraceableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "TICKET_ID", unique = true)
	private Long ticketId;

	@Column(name = "STATE_ID")
	private Long stateId;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	@Column(name = "PROJECT")
	private String project;

	@Column(name = "NAME", nullable = false, length = 250)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DISPLAY_ORDER")
	private Long displayOrder;

	@OneToMany
	@JoinColumn(name = "TOPIC_ID")
	private List<UserStory> userStories;
}
