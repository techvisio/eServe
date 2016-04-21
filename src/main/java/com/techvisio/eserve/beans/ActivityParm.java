package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ACTIVITY_PARAM")
public class ActivityParm extends BasicEntity {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="INDEX_ID")
	private Long indexId;
	@Column(name="VALUE")
	private String value;
	@Column(name="URL")
	private String url;
	@Column(name="ACTIVITY_PARAM_ID")
	private Long activityParamId;
	@Column(name="ACTIVITY_ID")
	private Long activityId;
	
	
	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}

	public Long getActivityParamId() {
		return activityParamId;
	}

	public void setActivityParamId(Long activityParamId) {
		this.activityParamId = activityParamId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
}
