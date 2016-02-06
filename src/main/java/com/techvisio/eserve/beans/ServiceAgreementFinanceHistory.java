package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SERVICE_AGREEMENT_FINANCE_HISTORY")
public class ServiceAgreementFinanceHistory extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SRVC_AGRMNT_FINANC_HSTORY_ID")
	private Long SrvcAgrmntFinancHstoryId;
	@Column(name="AGREEMENT_AMOUNT")
	private Long agreementAmount;
	@Column(name="UNIT_ID")
	private Long unitId;
	
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Long getSrvcAgrmntFinancHstoryId() {
		return SrvcAgrmntFinancHstoryId;
	}
	public void setSrvcAgrmntFinancHstoryId(Long srvcAgrmntFinancHstoryId) {
		SrvcAgrmntFinancHstoryId = srvcAgrmntFinancHstoryId;
	}
	public Long getAgreementAmount() {
		return agreementAmount;
	}
	public void setAgreementAmount(Long agreementAmount) {
		this.agreementAmount = agreementAmount;
	}
	
}
