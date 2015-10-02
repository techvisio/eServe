package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SECURITY_QUESTION")
public class SecurityQuestion {

	@Id
	@Column(name="Security_Qustn_Id")
	private Long securityQustnId;
	
	@ManyToOne
	@JoinColumn(name="User_Id")
	private User user;
	
	@Column(name="Question")
	private String question;
	@Column(name="Answer")
	private String answer;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getSecurityQustnId() {
		return securityQustnId;
	}

	public void setSecurityQustnId(Long securityQustnId) {
		this.securityQustnId = securityQustnId;
	}
	
	
}
