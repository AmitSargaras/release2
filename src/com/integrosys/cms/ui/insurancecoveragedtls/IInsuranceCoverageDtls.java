package com.integrosys.cms.ui.insurancecoveragedtls;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * Purpose : Defines methods for operating on Relationship Manager 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-04-06 15:13:16 +0800  $
 * Tag : $Name$
 */

public interface IInsuranceCoverageDtls extends Serializable, IValueObject{
	
	/**
	 * @return the insuranceCoverageCode
	 */
	public IInsuranceCoverage getInsuranceCoverageCode() ;
	/**
	 * @param insuranceCoverageCode the insuranceCoverageCode to set
	 */
	public void setInsuranceCoverageCode(IInsuranceCoverage insuranceCoverageCode) ;
	/**
	 * @return the insuranceType
	 */
	public String getInsuranceType() ;
	/**
	 * @param insuranceType the insuranceType to set
	 */
	public void setInsuranceType(String insuranceType) ;
	/**
	 * @return the insuranceCategoryName
	 */
	public String getInsuranceCategoryName() ;
	/**
	 * @param insuranceCategoryName the insuranceCategoryName to set
	 */
	public void setInsuranceCategoryName(String insuranceCategoryName) ;
	
	/**
	 * @return the deprecated
	 */
	public String getDeprecated();

	/**
	 * @param deprecated the deprecated to set
	 */
	public void setDeprecated(String deprecated);
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id);
	
	/**
	 * @return the createBy
	 */
	public String getCreatedBy();
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreatedBy(String createdBy);
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy();
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy);
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate();
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) ;
	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate();
	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) ;
	
	/**
	 * @return the status
	 */
	public String getStatus();
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
	
	/**
	 * @return the versionTime
	 */
	public long getVersionTime(); 
	
	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime);
}