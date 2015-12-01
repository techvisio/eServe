package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_QUESTION_MASTER")
public class QuestionMaster extends BasicEntity{
	
	@Id
	@Column(name="QUESTION")
	private String question;
	
	@Column(name = "CLIENT_ID")
	private Long clientId;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

}
