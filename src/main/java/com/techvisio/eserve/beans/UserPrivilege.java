package com.techvisio.eserve.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TB_USER_PRIVILEGE")
public class UserPrivilege extends BasicEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_PRVLG_ID")
	private Long userPrivilegeId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@OneToOne
	@JoinColumn(name="PRIVILEGE_ID")
	private Privilege privilege;
	
	@Transient
	private boolean granted;

	public Long getUserPrivilegeId() {
		return userPrivilegeId;
	}

	public void setUserPrivilegeId(Long userPrivilegeId) {
		this.userPrivilegeId = userPrivilegeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public boolean isGranted() {
		return granted;
	}

	public void setGranted(boolean granted) {
		this.granted = granted;
	}
	
	
}
