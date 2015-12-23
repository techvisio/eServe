package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_STATE_MASTER")
public class State extends BasicEntity{

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "STATE_ID")
	private Long stateId;
	@Column(name = "STATE_NAME")
	private String stateName;

	public State(){}
	
	public State(Long id){
		this.stateId = id;
	}
	
	public Long getStateId() {
		return stateId;
	}
	
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	
	public String getStateName() {
		return stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
