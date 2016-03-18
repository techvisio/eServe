package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_WORK_ITEM")
public class WorkItem extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="WORKITEM_ID")
	private Long workItemId;
	@Column(name="ENTITY_TYPE")
	private String entityType;
	@Column(name="ENTITY_ID")
	private Long entityId;
	@Column(name="PRIVILEGE_ID")
	private Long privilegeId;
	@Column(name="ASSIGNEE_ID")
	private Long assigneeId;
	@Column(name="WORKTYPE")
	private String workType;
	@Column(name="ENTITY_URL")
	private String entityUrl;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="COMMENT")
	private String comment;
	@Column(name="PRIORITY")
	private String priority;
	@Column(name="DUE_DATE")
	private  Date dueDate;
	@Column(name="STATUS")
	private String status;
	
	public Long getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public Long getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public Long getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(Long assigneeId) {
		assigneeId = assigneeId;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getEntityUrl() {
		return entityUrl;
	}
	public void setEntityUrl(String entityUrl) {
		this.entityUrl = entityUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
}