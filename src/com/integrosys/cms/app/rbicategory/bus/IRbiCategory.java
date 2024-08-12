package com.integrosys.cms.app.rbicategory.bus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Govind.Sahu 
 */
public interface IRbiCategory extends Serializable, IValueObject {

	
	public long getId();
	/**
	 * @param id the id to set
	 */
	public void setId(long id);
	/**
	 * @return the industryNameId
	 */
	public String getIndustryNameId();
	/**
	 * @param industryNameId the industryNameId to set
	 */
	public void setIndustryNameId(String industryNameId);
	
	/**
	 * @return the versionTime
	 */
	public long getVersionTime() ;
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime);
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() ;
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) ;
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate();
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) ;
	/**
	 * @return the createBy
	 */
	public String getCreateBy();
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) ;
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() ;
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) ;
	/**
	 * @return the status
	 */
	public String getStatus() ;
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) ;
	/**
	 * @return the deprecated
	 */
	public String getDeprecated();
	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated);
	
	/**
	 * @return the stageIndustryNameSet
	 */
	public Set getStageIndustryNameSet();
	/**
	 * @param stageIndustryNameSet the stageIndustryNameSet to set
	 */
	public void setStageIndustryNameSet(Set stageIndustryNameSet);
	
}
