package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SERVICE_AGREEMENT_FINANCE_HISTORY")
public class ServiceAgreementFinanceHistory extends BasicFileds{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SRVC_AGRMNT_FINANC_HSTORY_ID")
	private Long SrvcAgrmntFinancHstoryId;
	@Column(name="AGREEMENT_AMOUNT")
	private Double agreementAmount;
	@Column(name="UNIT_ID")
	private Long unitId;
	@Column(name="VERSION_ID")
	private Double versionId;
	
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
	public Double getAgreementAmount() {
		return agreementAmount;
	}
	public void setAgreementAmount(Double agreementAmount) {
		this.agreementAmount = agreementAmount;
	}
	public Double getVersionId() {
		return versionId;
	}
	public void setVersionId(Double versionId) {
		this.versionId = versionId;
	}
	
}
