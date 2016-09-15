package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.techvisio.eserve.util.CommonUtil;

public class BasicEntityCallback {


	@PrePersist
	public void prePersist(BasicEntity basicEntity) {

		String userName;
		if(CommonUtil.getCurrentUser()!=null){
			userName = 	 CommonUtil.getCurrentUser().getUserName();
		}
		else{
			userName="system";
		}

		basicEntity.setClient(CommonUtil.getCurrentClient());
		basicEntity.setCreatedBy(userName);
		basicEntity.setCreatedOn(new Date());
		basicEntity.setUpdatedBy(userName);
		basicEntity.setUpdatedOn(new Date());
	}

	@PreUpdate
	public void preUpdate(BasicEntity basicEntity) {
		String userName;
		if(CommonUtil.getCurrentUser()!=null){
			userName = 	 CommonUtil.getCurrentUser().getUserName();
		}
		else{
			userName="system";
		}
		basicEntity.setUpdatedBy(userName);
		basicEntity.setUpdatedOn(new Date());
	}
}
