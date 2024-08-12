package com.integrosys.cms.app.relationshipmgr.bus;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * Purpose: Used for defining attributes for Relationship Manager
 * 
 * @author Dattatray Thorat
 * @version $Revision: 1.0 $
 */

public class OBRelationshipMgr implements IRelationshipMgr{
	
	private static final long serialVersionUID = 1L;
	
	private String relationshipMgrCode ;
	private String relationshipMgrName;
    private String relationshipMgrMailId;
    private String relationshipMgrMobileNo;
    private IRegion region;
    private String relationshipMgrCity;
    private String relationshipMgrState;
    private String wboRegion;
    private String reportingHeadName;
    private String reportingHeadEmployeeCode;
    private String reportingHeadMailId;
    private String reportingHeadMobileNo;
    private String reportingHeadRegion;
    private Set relationshipMgrHrmsSet;
    
	private String status;
	private long id;
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deprecated;
	private long versionTime;
	
	private FormFile fileUpload;
	private String cpsId;
	private String operationName;
	private String employeeId;
	private String localCAD;
	private List localCADs;
	/**
	 * @return the fileUpload
	 */
	
	public Set getRelationshipMgrHrmsSet() {
		return relationshipMgrHrmsSet;
	}
	public void setRelationshipMgrHrmsSet(Set relationshipMgrHrmsSet) {
		this.relationshipMgrHrmsSet = relationshipMgrHrmsSet;
	}
	
	
	public FormFile getFileUpload() {
		return fileUpload;
	}
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	/**
	 * @return the relationshipMgrCode
	 */
	public String getRelationshipMgrCode() {
		return relationshipMgrCode;
	}
	/**
	 * @param relationshipMgrCode the relationshipMgrCode to set
	 */
	public void setRelationshipMgrCode(String relationshipMgrCode) {
		this.relationshipMgrCode = relationshipMgrCode;
	}
	/**
	 * @return the relationshipMgrName
	 */
	public String getRelationshipMgrName() {
		return relationshipMgrName;
	}
	/**
	 * @param relationshipMgrName the relationshipMgrName to set
	 */
	public void setRelationshipMgrName(String relationshipMgrName) {
		this.relationshipMgrName = relationshipMgrName;
	}
	/**
	 * @return the reportingHeadName
	 */
	public String getReportingHeadName() {
		return reportingHeadName;
	}
	/**
	 * @param reportingHeadName the reportingHeadName to set
	 */
	public void setReportingHeadName(String reportingHeadName) {
		this.reportingHeadName = reportingHeadName;
	}
	/**
	 * @return the relationshipMgrMailId
	 */
	public String getRelationshipMgrMailId() {
		return relationshipMgrMailId;
	}
	/**
	 * @param relationshipMgrMailId the relationshipMgrMailId to set
	 */
	public void setRelationshipMgrMailId(String relationshipMgrMailId) {
		this.relationshipMgrMailId = relationshipMgrMailId;
	}
	/**
	 * @return the reportingHeadMailId
	 */
	public String getReportingHeadMailId() {
		return reportingHeadMailId;
	}
	/**
	 * @param reportingHeadMailId the reportingHeadMailId to set
	 */
	public void setReportingHeadMailId(String reportingHeadMailId) {
		this.reportingHeadMailId = reportingHeadMailId;
	}
	/**
	 * @return the region
	 */
	public IRegion getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(IRegion region) {
		this.region = region;
	}
	/**
	 * @return the createBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	 
	/**
	 * @return the deprecated
	 */
	public String getDeprecated() {
		return deprecated;
	}
	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	/**
	 * @return the versionTime
	 */
	public long getVersionTime() {
		return versionTime;
	}
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getCpsId() {
		return cpsId;
	}
	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getRelationshipMgrMobileNo() {
		return relationshipMgrMobileNo;
	}
	public void setRelationshipMgrMobileNo(String relationshipMgrMobileNo) {
		this.relationshipMgrMobileNo = relationshipMgrMobileNo;
	}
	public String getRelationshipMgrCity() {
		return relationshipMgrCity;
	}
	public void setRelationshipMgrCity(String relationshipMgrCity) {
		this.relationshipMgrCity = relationshipMgrCity;
	}
	public String getRelationshipMgrState() {
		return relationshipMgrState;
	}
	public void setRelationshipMgrState(String relationshipMgrState) {
		this.relationshipMgrState = relationshipMgrState;
	}
	public String getWboRegion() {
		return wboRegion;
	}
	public void setWboRegion(String wboRegion) {
		this.wboRegion = wboRegion;
	}
	public String getReportingHeadEmployeeCode() {
		return reportingHeadEmployeeCode;
	}
	public void setReportingHeadEmployeeCode(String reportingHeadEmployeeCode) {
		this.reportingHeadEmployeeCode = reportingHeadEmployeeCode;
	}
	public String getReportingHeadMobileNo() {
		return reportingHeadMobileNo;
	}
	public void setReportingHeadMobileNo(String reportingHeadMobileNo) {
		this.reportingHeadMobileNo = reportingHeadMobileNo;
	}
	public String getReportingHeadRegion() {
		return reportingHeadRegion;
	}
	public void setReportingHeadRegion(String reportingHeadRegion) {
		this.reportingHeadRegion = reportingHeadRegion;
	}
	public String getLocalCAD() {
		return localCAD;
	}
	public void setLocalCAD(String localCAD) {
		this.localCAD = localCAD;
	}

	public List getLocalCADs() {
		return localCADs;
	}

	public void setLocalCADs(List localCADs) {
		this.localCADs = localCADs;
	}
		
}
