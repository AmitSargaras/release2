/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.rmAndCreditApprover;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for System Bank Branch Master
 */

public class RMAndCreditApproverForm extends TrxContextForm implements Serializable {

	private String cpsId;
	private String userName;
	private String region;
	private String deprecated;
	private String userEmail;
	private String loginId;
	private String supervisorId;
	private String seniorApproval;
	private String dpValue;
	private String status;
	private String event;
	private String createBy;
	private String lastUpdateBy;
	private String operationName;
	private String userUnitType;
	private String userRole;
	
	
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getSeniorApproval() {
		return seniorApproval;
	}
	public void setSeniorApproval(String seniorApproval) {
		this.seniorApproval = seniorApproval;
	}
	public String getDpValue() {
		return dpValue;
	}
	public void setDpValue(String dpValue) {
		this.dpValue = dpValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getUserUnitType() {
		return userUnitType;
	}
	public void setUserUnitType(String userUnitType) {
		this.userUnitType = userUnitType;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	

}
