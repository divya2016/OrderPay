package com.assignment.data.models;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

public class AuditEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Version
	private Long version;
	@CreatedDate
	private DateTime createdDate;
	@LastModifiedDate
	private DateTime lastModifiedDate;
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public AuditEntity(String id, Long version, DateTime createdDate, DateTime lastModifiedDate, String createdBy,
			String lastModifiedBy) {
		super();
		this.id = id;
		this.version = version;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
	}

	public AuditEntity() {
		// TODO Auto-generated constructor stub
	}

}
