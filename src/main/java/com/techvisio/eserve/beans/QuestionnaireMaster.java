package com.techvisio.eserve.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_QUESTIONNAIRE_MASTER")
public class QuestionnaireMaster extends BasicEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUESTION_ID")	
	private Long questionId;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "QUESTION")
	private String question;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER )
	@JoinColumn(name="OPTION_ID")
	private List<QuestionnnaireAnswerOption> questionnnaireAnswerOptions=new ArrayList<QuestionnnaireAnswerOption>();

	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<QuestionnnaireAnswerOption> getQuestionnnaireAnswerOptions() {
		return questionnnaireAnswerOptions;
	}
	public void setQuestionnnaireAnswerOptions(
			List<QuestionnnaireAnswerOption> questionnnaireAnswerOptions) {
		this.questionnnaireAnswerOptions = questionnnaireAnswerOptions;
	}

}
