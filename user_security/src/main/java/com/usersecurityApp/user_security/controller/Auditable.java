package com.usersecurityApp.user_security.controller;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class Auditable<U> {

	@CreatedBy
	protected U createdBy;

	@CreatedDate
	@Temporal(TIMESTAMP)
	protected Date createdDate;

	@LastModifiedBy
	protected U lastModifiedBy;

	@LastModifiedDate
	@Temporal(TIMESTAMP)
	protected Date lastModifiedDate;

/*
	public U getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public U getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
*/

}
