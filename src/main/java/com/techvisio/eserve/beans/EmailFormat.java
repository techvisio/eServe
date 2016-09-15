package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TB_EMAIL_FORMAT")
public class EmailFormat extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EMAIL_FORMAT_ID")
	private Long emailFormatId;
	@Column(name="COMMUNICATION_TYPE")
	private String communicationType;
	@Column(name="FORMAT")
	private String format;
	@Column(name="TITLE")
	private String title;
	@Column(name="SUBJECT")
	private String subject;
	@Column(name="EVENT_TYPE")
	private String eventType;
	
	@Transient
	private String parsedTemplate;
	
	public Long getEmailFormatId() {
		return emailFormatId;
	}
	public void setEmailFormatId(Long emailFormatId) {
		this.emailFormatId = emailFormatId;
	}
	public String getCommunicationType() {
		return communicationType;
	}
	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getParsedTemplate() {
		return parsedTemplate;
	}
	public void setParsedTemplate(String parsedTemplate) {
		this.parsedTemplate = parsedTemplate;
	}
	
}
