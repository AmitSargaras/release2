package com.integrosys.cms.app.relationshipmgr.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.ui.relationshipmgr.ILocalCAD;

public class OBLocalCAD implements ILocalCAD {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String localCADEmployeeCode;
	private String localCADName;
	private String localCADEmailID;
	private String localCADmobileNo;
	private String localCADSupervisorName;
	private String localCADSupervisorEmail;
	private String localCADSupervisorMobileNo;
	private String relationshipMgrID;

	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deprecated;
	private long versionTime;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getVersionTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVersionTime(long arg0) {
		// TODO Auto-generated method stub

	}

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String getRelationshipMgrID() {
		return relationshipMgrID;
	}

	public void setRelationshipMgrID(String relationshipMgrID) {
		this.relationshipMgrID = relationshipMgrID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
