package com.integrosys.cms.ui.login;

import com.integrosys.component.login.ui.LoginForm;

public class LoginRoleForm extends LoginForm implements java.io.Serializable {

	protected String userName;

	protected String password;

	protected String role;

	protected String companyName;

	protected String realm;

	private String localeStr;

	private String teamMemberShipID; // CR-33

	private String membershipID;

	protected String loginError;

	public LoginRoleForm() {

	}

	public void setUserName(String uName) {
		this.userName = uName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String pwd) {
		this.password = pwd;
	}

	public String getPassword() {
		return password;
	}

	public void setRole(String rl) {
		this.role = rl;
	}

	public String getRole() {
		return role;
	}

	public void setCompanyName(String compName) {
		this.companyName = compName;
	}

	public String getCompanyName() {
		return companyName;
	}

	// login error descriptor
	public void setLoginError(String errMessage) {
		this.loginError = errMessage;
	}

	public String getLoginError() {
		return loginError;
	}

	// to identify as SME/Retail
	public void setRealm(String rlm) {
		this.realm = rlm;
	}

	public String getRealm() {
		return realm;
	}

	public void setLocale(String locale) {
		localeStr = locale;
	}

	public String getLocale() {
		return localeStr;
	}

	public String getTeamMemberShipID() {
		return teamMemberShipID;
	}

	public void setTeamMemberShipID(String teamMemberShipID) {
		this.teamMemberShipID = teamMemberShipID;
	}

	public String getMembershipID() {
		return membershipID;
	}

	public void setMembershipID(String membershipID) {
		this.membershipID = membershipID;
	}
}
