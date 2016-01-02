package com.techvisio.eserve.beans;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.techvisio.eserve.util.CommonUtil;

public class BasicEntityCallback {

	 
	 @PrePersist
	  public void prePersist(BasicEntity basicEntity) {
		 basicEntity.setClient(CommonUtil.getCurrentClient());
		 basicEntity.setCreatedBy(CommonUtil.getCurrentUser().getUserName());
		 basicEntity.setCreatedOn(new Date());
		 basicEntity.setUpdatedBy(CommonUtil.getCurrentUser().getUserName());
		 basicEntity.setUpdatedOn(new Date());
	 }
	 
	 @PreUpdate
	 public void preUpdate(BasicEntity basicEntity) {
		 basicEntity.setUpdatedBy(CommonUtil.getCurrentUser().getUserName());
		 basicEntity.setUpdatedOn(new Date());
	 }
}
