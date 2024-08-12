package com.integrosys.cms.app.insurancecoveragedtls.bus;

import java.util.Date;

import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * Purpose: Used for defining attributes for Insurance Coverage Details
 * 
 * @author Dattatray Thorat
 * @version $Revision: 1.0 $
 */

public class OBInsuranceCoverageDtls implements IInsuranceCoverageDtls{
	
	private static final long serialVersionUID = 1L;
	
	private IInsuranceCoverage insuranceCoverageCode ;
	private String insuranceType;
    private String insuranceCategoryName;
    
	private String status;
	private long id;
	private String createdBy;
	private String lastUpdateBy;
	private Date creationDate;
	private Date lastUpdateDate;
	private String deprecated;
	private long versionTime;
	
	
	/**
	 * @return the insuranceCoverageCode
	 */
	public IInsuranceCoverage getInsuranceCoverageCode() {
		return insuranceCoverageCode;
	}
	/**
	 * @param insuranceCoverageCode the insuranceCoverageCode to set
	 */
	public void setInsuranceCoverageCode(IInsuranceCoverage insuranceCoverageCode) {
		this.insuranceCoverageCode = insuranceCoverageCode;
	}
	/**
	 * @return the insuranceType
	 */
	public String getInsuranceType() {
		return insuranceType;
	}
	/**
	 * @param insuranceType the insuranceType to set
	 */
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}
	/**
	 * @return the insuranceCategoryName
	 */
	public String getInsuranceCategoryName() {
		return insuranceCategoryName;
	}
	/**
	 * @param insuranceCategoryName the insuranceCategoryName to set
	 */
	public void setInsuranceCategoryName(String insuranceCategoryName) {
		this.insuranceCategoryName = insuranceCategoryName;
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
