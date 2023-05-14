package com.thinktank.pts.apibase.model.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.thinktank.pts.apibase.context.actor.ActorContextHolder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractBaseMainTraceableEntity implements Serializable, BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "CREATOR", updatable = false)
	private String creator;

	@CreatedDate
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "CREATED", updatable = false, nullable = false)
	private LocalDateTime created;

	@LastModifiedBy
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "MODIFIER")
	private String modifier;

	@LastModifiedDate
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "MODIFIED", nullable = false)
	private LocalDateTime modified;

	@Version
	@Setter(AccessLevel.PRIVATE)
	@Column(name = "VERSION", nullable = false)
	private Integer version;

	@PrePersist
	public void persistCreator() {
		setCreator(ActorContextHolder.getActor().getName());
		setModifier(ActorContextHolder.getActor().getName());
	}

	@PreUpdate
	public void updateModifier() {
		setModifier(ActorContextHolder.getActor().getName());
	}
}
