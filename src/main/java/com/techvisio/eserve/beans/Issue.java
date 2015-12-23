package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ISSUE_MASTER")
public class Issue extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ISSUE_ID")
	private Long issueId;
	@Column(name="VALUE")
	private String value;
	public Long getIssueId() {
		return issueId;
	}
	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
