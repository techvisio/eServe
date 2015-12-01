package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SECURITY_QUESTION")
public class SecurityQuestion extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long userId;
	@Column(name="IS_CUSTOM_QUESTION")
    private boolean customQuestion;	
	@Column(name="QUESTION")
	private String question;
	@Column(name="ANSWER")
	private String answer;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isCustomQuestion() {
		return customQuestion;
	}

	public void setCustomQuestion(boolean customQuestion) {
		this.customQuestion = customQuestion;
	}
	
}
