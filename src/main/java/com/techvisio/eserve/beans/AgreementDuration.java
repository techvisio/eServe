package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AGREEMENT_DURATION")
public class AgreementDuration extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AGREEMENT_DURATION_ID")
	private Long agreementDurationId;
	@Column(name="DURATION")
	private int duration;
	@Column(name="DISPLAY_VALUE")
	private String displayValue;
	public Long getAgreementDuration() {
		return agreementDurationId;
	}
	public void setAgreementDuration(Long agreementDuration) {
		this.agreementDurationId = agreementDuration;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
