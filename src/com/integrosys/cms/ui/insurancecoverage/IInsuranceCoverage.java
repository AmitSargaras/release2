package com.integrosys.cms.ui.insurancecoverage;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Purpose : Defines methods for operating on Relationship Manager 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-04-06 15:13:16 +0800  $
 * Tag : $Name$
 */

public interface IInsuranceCoverage extends Serializable, IValueObject{
	
	/**
	 * @return the insuranceCoverageCode
	 */
	public String getInsuranceCoverageCode() ;
	/**
	 * @param insuranceCoverageCode the insuranceCoverageCode to set
	 */
	public void setInsuranceCoverageCode(String insuranceCoverageCode) ;
	/**
	 * @return the companyName
	 */
	public String getCompanyName() ;
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) ;
	/**
	 * @return the address
	 */
	public String getAddress() ;
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) ;
	/**
	 * @return the contactNumber
	 */
	public long getContactNumber() ;
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(long contactNumber) ;
	
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
	
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload();
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload);
}