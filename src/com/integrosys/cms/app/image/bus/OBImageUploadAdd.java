package com.integrosys.cms.app.image.bus;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.image.IImageUploadAdd;


/**
 * This interface defines the operations that are provided by a image 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 11:00:00 $ Tag: $Name: $
 */
public class OBImageUploadAdd implements IImageUploadAdd {
	
	private long imgId;
	
    private long createBy;
	
	private Date creationDate;
	
	private String lastUpdateBy;
	
	private String lastUpdateDate;

	private String imgFileName;

	private long imgSize ;

	private String imgDepricated;
	
	private long versionTime;
	
	private String custId ;
	
	private String custName;
	
	private String imageFilePath;
	
	private String sendForAppFlag;
	
	private long imageApprId;
	
	/** Added by Anil	 */
	private String category = "";
	
	private String selectedArray;
	private String unCheckArray;
	
	private String hasSubfolder = "";
	private String subfolderName = "";
	private String documentName;
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	private String bank="";
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	/**
	 * To idetify the request from server for ContentMeanger scheduler to resolve NLB issue
	 */
	private String fromServer;

	/**
	 * Represents error that occured while inserting image into DMS.
	 */
	private String error;
	
	/**
	 * Indicates the status of image being passed into DMS.
	 * 1 - On filesystem
	 * 2 - Being Read
	 * 3 - Passed to DMS
	 * 4 - Error has occured while inserting into DMS.
	 */
	private long status;

	/**
	 * Represents error that occured while inserting image into DMS.
	 */
	
	private String HCPFileName = "";
	private String HCPStatus = "";
	private Timestamp HCPMoveDate ;
	private String HCPStatusCode = "";
	
	
	private String facilityName="";
	
	private String facilityDocName="";
	
	private String otherDocName = "";
	
	private String securityNameId = "";
	
	private String securityDocName = "";
	
	private String otherSecDocName = "";
	
	private String hasFacility = "";
	
	private String hasSecurity = "";
	
	private String hasCam = "";
	
	private String typeOfDocument = "";
	
	private String statementTyped="";
	
	private String statementDocName = "";
	
//	private String camName = "";
	
	private String camDocName = "";
	
	private String othersDocsName = "";
	
	private String securityIdi = "";
	
	private String subTypeSecurity = "";
	

	public String getSecurityIdi() {
		return securityIdi;
	}

	public void setSecurityIdi(String securityIdi) {
		this.securityIdi = securityIdi;
	}

	public String getSubTypeSecurity() {
		return subTypeSecurity;
	}

	public void setSubTypeSecurity(String subTypeSecurity) {
		this.subTypeSecurity = subTypeSecurity;
	}

	public String getStatementTyped() {
		return statementTyped;
	}

	public void setStatementTyped(String statementTyped) {
		this.statementTyped = statementTyped;
	}

	public String getStatementDocName() {
		return statementDocName;
	}

	public void setStatementDocName(String statementDocName) {
		this.statementDocName = statementDocName;
	}

	/*public String getCamName() {
		return camName;
	}

	public void setCamName(String camName) {
		this.camName = camName;
	}*/

	public String getCamDocName() {
		return camDocName;
	}

	public void setCamDocName(String camDocName) {
		this.camDocName = camDocName;
	}

	public String getOthersDocsName() {
		return othersDocsName;
	}

	public void setOthersDocsName(String othersDocsName) {
		this.othersDocsName = othersDocsName;
	}

	public String getTypeOfDocument() {
		return typeOfDocument;
	}

	public void setTypeOfDocument(String typeOfDocument) {
		this.typeOfDocument = typeOfDocument;
	}

	public String getHasFacility() {
		return hasFacility;
	}

	public void setHasFacility(String hasFacility) {
		this.hasFacility = hasFacility;
	}

	public String getHasSecurity() {
		return hasSecurity;
	}

	public void setHasSecurity(String hasSecurity) {
		this.hasSecurity = hasSecurity;
	}

	public String getHasCam() {
		return hasCam;
	}

	public void setHasCam(String hasCam) {
		this.hasCam = hasCam;
	}

	public String getOtherSecDocName() {
		return otherSecDocName;
	}

	public void setOtherSecDocName(String otherSecDocName) {
		this.otherSecDocName = otherSecDocName;
	}

	public String getSecurityDocName() {
		return securityDocName;
	}

	public void setSecurityDocName(String securityDocName) {
		this.securityDocName = securityDocName;
	}

	public String getSecurityNameId() {
		return securityNameId;
	}

	public void setSecurityNameId(String securityNameId) {
		this.securityNameId = securityNameId;
	}

	public String getOtherDocName() {
		return otherDocName;
	}

	public void setOtherDocName(String otherDocName) {
		this.otherDocName = otherDocName;
	}


	public String getFacilityDocName() {
		return facilityDocName;
	}

	public void setFacilityDocName(String facilityDocName) {
		this.facilityDocName = facilityDocName;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public String getError() {
		return error;
	}
	/**
	 * Represents error that occured while inserting image into DMS.
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Indicates the status of image being passed into DMS.
	 * 1 - On filesystem
	 * 2 - Being Read
	 * 3 - Passed to DMS
	 * 4 - Error has occured while inserting into DMS.
	 */
	public long getStatus() {
		return status;
	}
	/**
	 * Indicates the status of image being passed into DMS.
	 * 1 - On filesystem
	 * 2 - Being Read
	 * 3 - Passed to DMS
	 * 4 - Error has occured while inserting into DMS.
	 */
	public void setStatus(long status) {
		this.status = status;
	}

	/**
	 * @return the imageApprId
	 */
	public long getImageApprId() {
		return imageApprId;
	}

	/**
	 * @param imageApprId the imageApprId to set
	 */
	public void setImageApprId(long imageApprId) {
		this.imageApprId = imageApprId;
	}

	/**
	 * @return the imgId
	 */
	public long getImgId() {
		return imgId;
	}

	/**
	 * @param imgId the imgId to set
	 */
	public void setImgId(long imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return the imgFileName
	 */
	public String getImgFileName() {
		return imgFileName;
	}

	/**
	 * @param imgFileName the imgFileName to set
	 */
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	/**
	 * @return the imgSize
	 */
	public long getImgSize() {
		return imgSize;
	}

	/**
	 * @param imgSize the imgSize to set
	 */
	public void setImgSize(long imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * @return the imgDepricated
	 */
	public String getImgDepricated() {
		return imgDepricated;
	}

	/**
	 * @param imgDepricated the imgDepricated to set
	 */
	public void setImgDepricated(String imgDepricated) {
		this.imgDepricated = imgDepricated;
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
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the imageFilePath
	 */
	public String getImageFilePath() {
		return imageFilePath;
	}

	/**
	 * @param imageFilePath the imageFilePath to set
	 */
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	/**
	 * @return the sendForAppFlag
	 */
	public String getSendForAppFlag() {
		return sendForAppFlag;
	}

	/**
	 * @param sendForAppFlag the sendForAppFlag to set
	 */
	public void setSendForAppFlag(String sendForAppFlag) {
		this.sendForAppFlag = sendForAppFlag;
	}

	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}


	/**
	 * @return the createBy
	 */
	
	public long getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
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
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the hasSubfolder
	 */
	public String getHasSubfolder() {
		return hasSubfolder;
	}

	/**
	 * @param hasSubfolder the hasSubfolder to set
	 */
	public void setHasSubfolder(String hasSubfolder) {
		this.hasSubfolder = hasSubfolder;
	}

	/**
	 * @return the subfolderName
	 */
	public String getSubfolderName() {
		return subfolderName;
	}

	/**
	 * @param subfolderName the subfolderName to set
	 */
	public void setSubfolderName(String subfolderName) {
		this.subfolderName = subfolderName;
	}
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	/**
	 * @return the fromServer
	 */
	public String getFromServer() {
		return fromServer;
	}
	/**
	 * @param fromServer the fromServer to set
	 */
	public void setFromServer(String fromServer) {
		this.fromServer = fromServer;
	}

	private FormFile imageFile;
	/**
	 * Description : get method for form to get the Image File
	 * 
	 * @return imageFile
	 */
	public FormFile getImageFile() {
		return imageFile;
	}
	
	/**
	 * Description : set the Image File
	 * 
	 * @param imageFile is the Image File
	 */
	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}
	public String getSelectedArray() {
		return selectedArray;
	}
	public void setSelectedArray(String selectedArray) {
		this.selectedArray = selectedArray;
	}
	public String getUnCheckArray() {
		return unCheckArray;
	}
	public void setUnCheckArray(String unCheckArray) {
		this.unCheckArray = unCheckArray;
	}
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	public String getHCPFileName() {
		return HCPFileName;
	}
	public void setHCPFileName(String hCPFileName) {
		HCPFileName = hCPFileName;
	}
	public String getHCPStatus() {
		return HCPStatus;
	}
	public void setHCPStatus(String hCPStatus) {
		HCPStatus = hCPStatus;
	}
	public Timestamp getHCPMoveDate() {
		return HCPMoveDate;
	}
	public void setHCPMoveDate(Timestamp sqlTime) {
		HCPMoveDate = sqlTime;
	}
	public String getHCPStatusCode() {
		return HCPStatusCode;
	}
	public void setHCPStatusCode(String hCPStatusCode) {
		HCPStatusCode = hCPStatusCode;
	}
	
	String formFile;

	public String getFormFile() {
		return formFile;
	}

	public void setFormFile(String formFile) {
		this.formFile = formFile;
	}
	
}
