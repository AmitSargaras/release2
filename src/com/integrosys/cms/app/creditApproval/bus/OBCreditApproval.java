package com.integrosys.cms.app.creditApproval.bus;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 0.0 $
 * @since $Date: 2011/03/31 04:55:19 $ Tag: $Name: $
 * 
 */
public class OBCreditApproval implements ICreditApproval {

	private long id;
	
	private String approvalCode;

	private String approvalName;

	private BigDecimal maximumLimit;

	private BigDecimal minimumLimit;
	
	private String segmentId; 
	
	private String email;

	private String senior;

	private String risk;

	private long versionTime = 0;

	private String createBy;

	private Date creationDate;

	private String lastUpdateBy;

	private Date lastUpdateDate;

	private String deprecated;

	private String status;
	
	private String deferralPowers;
	
	private String waivingPowers;
	
	private long regionId;
	
	private FormFile fileUpload;
	
	private String operationName;
	
	private String cpsId;
	
	private String employeeId;

	/**
	 * @return the deferralPowers
	 */
	public String getDeferralPowers() {
		return deferralPowers;
	}

	/**
	 * @param deferralPowers the deferralPowers to set
	 */
	public void setDeferralPowers(String deferralPowers) {
		this.deferralPowers = deferralPowers;
	}

	/**
	 * @return the waivingPowers
	 */
	public String getWaivingPowers() {
		return waivingPowers;
	}

	/**
	 * @param waivingPowers the waivingPowers to set
	 */
	public void setWaivingPowers(String waivingPowers) {
		this.waivingPowers = waivingPowers;
	}

	/**
	 * @return the regionId
	 */
	public long getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(long regionId) {
		this.regionId = regionId;
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
	 * @return the approvalCode
	 */
	public String getApprovalCode() {
		return approvalCode;
	}

	/**
	 * @param approvalCode the approvalCode to set
	 */
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	/**
	 * @return the approvalName
	 */
	public String getApprovalName() {
		return approvalName;
	}

	/**
	 * @param approvalName the approvalName to set
	 */
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}

	/**
	 * @return the segmentId
	 */
	public String getSegmentId() {
		return segmentId;
	}

	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the senior
	 */
	public String getSenior() {
		return senior;
	}

	/**
	 * @param senior the senior to set
	 */
	public void setSenior(String senior) {
		this.senior = senior;
	}

	/**
	 * @return the risk
	 */
	public String getRisk() {
		return risk;
	}

	/**
	 * @param risk the risk to set
	 */
	public void setRisk(String risk) {
		this.risk = risk;
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
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
	 * @return the maximumLimit
	 */
	public BigDecimal getMaximumLimit() {
		return maximumLimit;
	}

	/**
	 * @param maximumLimit the maximumLimit to set
	 */
	public void setMaximumLimit(BigDecimal maximumLimit) {
		this.maximumLimit = maximumLimit;
	}

	/**
	 * @return the minimumLimit
	 */
	public BigDecimal getMinimumLimit() {
		return minimumLimit;
	}

	/**
	 * @param minimumLimit the minimumLimit to set
	 */
	public void setMinimumLimit(BigDecimal minimumLimit) {
		this.minimumLimit = minimumLimit;
	}

	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	
	
	
}