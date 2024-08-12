package com.integrosys.cms.ui.relationshipmgr;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;

public class LocalCADForm extends TrxContextForm implements Serializable {

	private String localCADEmployeeCode;
	private String localCADName;
	private String localCADEmailID;
	private String localCADmobileNo;
	private String localCADSupervisorName;
	private String localCADSupervisorEmail;
	private String localCADSupervisorMobileNo;
	private String relationshipMgrID;

	private String id;
	private long versionTime;
	private String status;
	private String deprecated;
	private String createdBy;
	private Date creationDate;
	private String lastUpdateBy;
	private String lastUpdateDate;

	public String getLocalCADEmployeeCode() {
		return localCADEmployeeCode;
	}

	public void setLocalCADEmployeeCode(String localCADEmployeeCode) {
		this.localCADEmployeeCode = localCADEmployeeCode;
	}

	public String getLocalCADName() {
		return localCADName;
	}

	public void setLocalCADName(String localCADName) {
		this.localCADName = localCADName;
	}

	public String getLocalCADEmailID() {
		return localCADEmailID;
	}

	public void setLocalCADEmailID(String localCADEmailID) {
		this.localCADEmailID = localCADEmailID;
	}

	public String getLocalCADmobileNo() {
		return localCADmobileNo;
	}

	public void setLocalCADmobileNo(String localCADmobileNo) {
		this.localCADmobileNo = localCADmobileNo;
	}

	public String getLocalCADSupervisorName() {
		return localCADSupervisorName;
	}

	public void setLocalCADSupervisorName(String localCADSupervisorName) {
		this.localCADSupervisorName = localCADSupervisorName;
	}

	public String getLocalCADSupervisorEmail() {
		return localCADSupervisorEmail;
	}

	public void setLocalCADSupervisorEmail(String localCADSupervisorEmail) {
		this.localCADSupervisorEmail = localCADSupervisorEmail;
	}

	public String getLocalCADSupervisorMobileNo() {
		return localCADSupervisorMobileNo;
	}

	public void setLocalCADSupervisorMobileNo(String localCADSupervisorMobileNo) {
		this.localCADSupervisorMobileNo = localCADSupervisorMobileNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getRelationshipMgrID() {
		return relationshipMgrID;
	}

	public void setRelationshipMgrID(String relationshipMgrID) {
		this.relationshipMgrID = relationshipMgrID;
	}

}
