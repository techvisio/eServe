package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.PrePersist;

import com.techvisio.eserve.util.CommonUtil;

public class BasicFieldsCallBack {


	@PrePersist
	public void prePersist(BasicFileds basicFileds) {
		String userName;
		if(CommonUtil.getCurrentUser()!=null){
			userName = 	 CommonUtil.getCurrentUser().getUserName();
		}
		else{
			userName="system";
		}
		basicFileds.setCreatedBy(userName);
		basicFileds.setCreatedOn(new Date());
		basicFileds.setClient(CommonUtil.getCurrentClient());
	}

}
