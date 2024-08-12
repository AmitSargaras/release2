package com.integrosys.cms.ui.relationshipmgr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.region.bus.IRegion;

/**
 * Purpose : Defines methods for operating on Relationship Manager 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-04-06 15:13:16 +0800  $
 * Tag : $Name$
 */

public interface IRelationshipMgr extends Serializable, IValueObject{
	
	/**
	 * @return the relationshipMgrCode
	 */
	public String getRelationshipMgrCode();
	/**
	 * @param relationshipMgrCode the relationshipMgrCode to set
	 */
	public void setRelationshipMgrCode(String relationshipMgrCode) ;
	
	/**
	 * @return the relationshipMgrName
	 */
	public String getRelationshipMgrName();

	/**
	 * @param relationshipMgrName the relationshipMgrName to set
	 */
	public void setRelationshipMgrName(String relationshipMgrName);

	/**
	 * @return the reportingHeadName
	 */
	public String getReportingHeadName();

	/**
	 * @param reportingHeadName the reportingHeadName to set
	 */
	public void setReportingHeadName(String reportingHeadName);

	/**
	 * @return the relationshipMgrMailId
	 */
	public String getRelationshipMgrMailId() ;

	/**
	 * @param relationshipMgrMailId the relationshipMgrMailId to set
	 */
	public void setRelationshipMgrMailId(String relationshipMgrMailId);

	/**
	 * @return the reportingHeadMailId
	 */
	public String getReportingHeadMailId();

	/**
	 * @param reportingHeadMailId the reportingHeadMailId to set
	 */
	public void setReportingHeadMailId(String reportingHeadMailId);
	
	/**
	 * @return the region
	 */
	public IRegion getRegion() ;
	/**
	 * @param region the region to set
	 */
	public void setRegion(IRegion region) ;
	
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
	
	public String getCpsId();
	public void setCpsId(String cpsId);
	
	public String getEmployeeId() ;
	public void setEmployeeId(String employeeId) ;
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload() ;
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload) ;
	
	public String getRelationshipMgrMobileNo();
	
	public void setRelationshipMgrMobileNo(String relationshipMgrMobileNo);
	
	public String getReportingHeadMobileNo();
	
	public void setReportingHeadMobileNo(String reportingHeadMobileNo);
	
	public String getLocalCAD();
	
	public void setLocalCAD(String localCAD);

	public List getLocalCADs();
	
	public void setLocalCADs(List localCADs);
	
	public String getRelationshipMgrCity();
	
	public void setRelationshipMgrCity(String relationshipMgrCity);
	
	public String getRelationshipMgrState();
	
	public void setRelationshipMgrState(String relationshipMgrState);
	
	public String getWboRegion();
	
	public void setWboRegion(String wboRegion);
	
	public String getReportingHeadEmployeeCode();
	
	public void setReportingHeadEmployeeCode(String reportingHeadEmployeeCode);
	
	public String getReportingHeadRegion();
	
	public void setReportingHeadRegion(String reportingHeadRegion);
}