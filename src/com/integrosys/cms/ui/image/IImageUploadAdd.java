package com.integrosys.cms.ui.image;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/07 11:32:23 $ Tag: $Name: $
 */
public interface IImageUploadAdd extends Serializable, IValueObject {

	public long getImgId();
	
	public void setImgId(long imgId);

	public String getImgFileName();

	public void setImgFileName(String imgFileName);

	public long getImgSize();

	public void setImgSize(long imgSize);
	
	public String getImgDepricated() ;

	public void setImgDepricated(String imgDepricated);
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	public String getCustId();

	public void setCustId(String custId);

	public String getCustName();

	public void setCustName(String custName);
	
	public String getImageFilePath();

	public void setImageFilePath(String imageFilePath);
	
	public String getSendForAppFlag();
	
	public void setSendForAppFlag(String sendForAppFlag);
	
	public long getImageApprId();
	public void setImageApprId(long imageApprId);
	
	public String getCategory();
	public void setCategory(String category);
	
	public String getHasSubfolder();
	public void setHasSubfolder(String hasSubfolder);
	
	public String getSubfolderName();
	public void setSubfolderName(String subfolderName);

	public String getSelectedArray() ;

	public void setSelectedArray(String selectedArray) ;
	
	
	public String getUnCheckArray() ;
	public void setUnCheckArray(String unCheckArray);
	
	/**
	 * Represents error that occured while inserting image into DMS.
	 */
	public String getError();
	/**
	 * Represents error that occured while inserting image into DMS.
	 */
	public void setError(String error);

	/**
	 * Indicates the status of image being passed into DMS.
	 * 1 - On filesystem
	 * 2 - Being Read
	 * 3 - Passed to DMS
	 * 4 - Error has occured while inserting into DMS.
	 */
	public long getStatus();
	/**
	 * Indicates the status of image being passed into DMS.
	 * 1 - On filesystem
	 * 2 - Being Read
	 * 3 - Passed to DMS
	 * 4 - Error has occured while inserting into DMS.
	 */
	public void setStatus(long status);
	
	public long getCreateBy();
	
	public void setCreateBy(long createdBy);
	
	public Date getCreationDate();
	
	public void setCreationDate(Date creationDate) ;

	public String getDocumentName() ;
	public void setDocumentName(String documentName);
	
	public String getFromServer() ;
	public void setFromServer(String fromServer) ;

	/**
	 * Description : get method for form to get the Image File
	 * 
	 * @return imageFile
	 */
	public FormFile getImageFile();
	public void setImageFile(FormFile imageFile);
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getBank() ;
	public void setBank(String bank) ;
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	public String getHCPFileName() ;
	public void setHCPFileName(String hCPFileName) ;
	
	public String getHCPStatus();
	public void setHCPStatus(String hCPStatus) ;
	
	
	public String getHCPStatusCode();
	public void setHCPStatusCode(String hCPStatusCode);
	
	public Timestamp getHCPMoveDate();
	public void setHCPMoveDate(Timestamp sqlTime);
	
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	
	public String getFacilityDocName();
	public void setFacilityDocName(String facilityDocName);
	
	public String getOtherDocName();
	public void setOtherDocName(String otherDocName);
	
	public String getSecurityNameId();
	public void setSecurityNameId(String securityNameId);
	
	public String getSecurityDocName();
	public void setSecurityDocName(String securityDocName);
	
	public String getOtherSecDocName();
	public void setOtherSecDocName(String otherSecDocName);
	
	public String getHasFacility();
	public void setHasFacility(String hasFacility);

	public String getHasSecurity();
	public void setHasSecurity(String hasSecurity);

	public String getHasCam();
	public void setHasCam(String hasCam);
	
	public String getTypeOfDocument();
	public void setTypeOfDocument(String typeOfDocument);
	
	public String getStatementTyped();
	public void setStatementTyped(String statementTyped);
	
	public String getStatementDocName();
	public void setStatementDocName(String statementDocName);

//	public String getCamName();
//	public void setCamName(String camName);
	
	public String getCamDocName();
	public void setCamDocName(String camDocName);

	public String getOthersDocsName();
	public void setOthersDocsName(String othersDocsName);
	
	public String getSecurityIdi();
	public void setSecurityIdi(String securityIdi);

	public String getSubTypeSecurity();
	public void setSubTypeSecurity(String subTypeSecurity);
	//Added by Prachit
	public String getFormFile();
	public void setFormFile(String formFile);
}
