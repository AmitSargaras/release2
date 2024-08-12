package com.integrosys.cms.ui.profile;

public class OBPasswordChange implements java.io.Serializable {

	// basic info
	private String oldPasswd;

	private String newPasswd;

	private String confirmPasswd;

	// secondary info
	private String loginId;

	private String role;

	private String pinType;

	private String realm;

	// Set
	public void setOldPasswd(String a) {
		this.oldPasswd = a;
	}

	public void setNewPasswd(String a) {
		this.newPasswd = a;
	}

	public void setConfirmPasswd(String a) {
		this.confirmPasswd = a;
	}

	public void setLoginId(String lId) {
		this.loginId = lId;
	}

	public void setRole(String rol) {
		this.role = rol;
	}

	public void setPinType(String pType) {
		this.pinType = pType;
	}

	public void setRealm(String rlm) {
		this.realm = rlm;
	}

	// Get
	public String getOldPasswd() {
		return this.oldPasswd;
	}

	public String getNewPasswd() {
		return this.newPasswd;
	}

	public String getConfirmPasswd() {
		return this.confirmPasswd;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public String getRole() {
		return this.role;
	}

	public String getPinType() {
		return this.pinType;
	}

	public String getRealm() {
		return this.realm;
	}

}
