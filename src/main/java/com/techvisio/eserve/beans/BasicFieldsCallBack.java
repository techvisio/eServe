package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.PrePersist;

import com.techvisio.eserve.util.CommonUtil;

public class BasicFieldsCallBack {


	@PrePersist
	public void prePersist(BasicFileds basicFileds) {
		basicFileds.setCreatedBy(CommonUtil.getCurrentUser().getUserName());
		basicFileds.setCreatedOn(new Date());
		basicFileds.setClient(CommonUtil.getCurrentClient());
	}

}
