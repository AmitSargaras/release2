
package com.integrosys.cms.app.rbicategory.bus;

//app

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Govind.Sahu 
 */
public class OBRbiCategory implements IRbiCategory {
	
	/**
	 * constructor
	 */
	public OBRbiCategory() {
		
	}
	
	private long id;
	private String industryNameId;
	private long versionTime;
	private Date lastUpdateDate;
	private Date creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String status;
	private String deprecated;
	private Set stageIndustryNameSet;
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
	 * @return the stageIndustryNameSet
	 */
	public Set getStageIndustryNameSet() {
		return stageIndustryNameSet;
	}
	/**
	 * @param stageIndustryNameSet the stageIndustryNameSet to set
	 */
	public void setStageIndustryNameSet(Set stageIndustryNameSet) {
		this.stageIndustryNameSet = stageIndustryNameSet;
	}
	/**
	 * @return the industryNameId
	 */
	public String getIndustryNameId() {
		return industryNameId;
	}
	/**
	 * @param industryNameId the industryNameId to set
	 */
	public void setIndustryNameId(String industryNameId) {
		this.industryNameId = industryNameId;
	}
	

	
}