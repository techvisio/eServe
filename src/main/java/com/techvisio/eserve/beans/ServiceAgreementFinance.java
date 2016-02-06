package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SERVICE_AGREEMENT_FINANCE")
public class ServiceAgreementFinance extends BasicEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SERVICE_AGREEMENT_FINANCE_ID")
	private Long serviceAgreementFinanceId;
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

	public Long getServiceAgreementFinanceId() {
		return serviceAgreementFinanceId;
	}

	public void setServiceAgreementFinanceId(Long serviceAgreementFinanceId) {
		this.serviceAgreementFinanceId = serviceAgreementFinanceId;
	}

	public Long getAgreementAmount() {
		return agreementAmount;
	}

	public void setAgreementAmount(Long agreementAmount) {
		this.agreementAmount = agreementAmount;
	}
	
}
