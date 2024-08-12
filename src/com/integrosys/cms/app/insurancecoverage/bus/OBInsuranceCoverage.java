package com.integrosys.cms.app.insurancecoverage.bus;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * Purpose: Used for defining attributes for Relationship Manager
 * 
 * @author Dattatray Thorat
 * @version $Revision: 1.0 $
 */

public class OBInsuranceCoverage implements IInsuranceCoverage{
	
	private static final long serialVersionUID = 1L;
	
	private String insuranceCoverageCode ;
	private String companyName;
    private String address;
    private long contactNumber;
    
	private String status;
	private long id;
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deprecated;
	private long versionTime;
	
	private FormFile fileUpload;
	
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
	/**
	 * @return the insuranceCoverageCode
	 */
	public String getInsuranceCoverageCode() {
		return insuranceCoverageCode;
	}
	/**
	 * @param insuranceCoverageCode the insuranceCoverageCode to set
	 */
	public void setInsuranceCoverageCode(String insuranceCoverageCode) {
		this.insuranceCoverageCode = insuranceCoverageCode;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the contactNumber
	 */
	public long getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
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
}
