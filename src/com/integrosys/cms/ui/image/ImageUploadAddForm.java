package com.integrosys.cms.ui.image;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;
/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/03 11:32:23 $ Tag: $Name: $
 */
public class ImageUploadAddForm extends TrxContextForm implements Serializable {

	private String imgId;
	
	private String imgFileName;

	private String imgSize;

	private String imgDepricated;

	private String versionTime;

	private String custId;

	private String custName;
	
	private FormFile imageFile;
	
	private String imageFilePath;
	
	/** Added by Anil	 */
	private String category = "";
	private String hasSubfolder = "";
	private String subfolderName = "";
	private String documentName;
	
	private String createBy;
	
	private String creationDate;
	
	private String selectedArray;
	private String unCheckArray;
	
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	private String bank="";

	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	
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
	// Added for Image Upload 14-10-2019
	
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
	
	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	
	public String getFacilityDocName() {
		return facilityDocName;
	}

	public void setFacilityDocName(String facilityDocName) {
		this.facilityDocName = facilityDocName;
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

	/**
	 * Description : get method for form to get the Image File Name
	 * 
	 * @return imgFileName
	 */
	public String getImgFileName() {
		return imgFileName;
	}
	
	/**
	 * Description : set the Image File Name
	 * 
	 * @param imgFileName is the Image File Name
	 */
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	/**
	 * Description : get method for form to get the Image Depricated
	 * 
	 * @return imgDepricated
	 */
	public String getImgDepricated() {
		return imgDepricated;
	}
	
	/**
	 * Description : set the Image Depricated
	 * 
	 * @param imgDepricated is the Image Depricated
	 */
	public void setImgDepricated(String imgDepricated) {
		this.imgDepricated = imgDepricated;
	}

	/**
	 * Description : get method for form to get the Image Id
	 * 
	 * @return imgId
	 */
	public String getImgId() {
		return imgId;
	}
	
	/**
	 * Description : set the Image Id
	 * 
	 * @param imgId is the Image Id
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	
	/**
	 * Description : get method for form to get the Image Size
	 * 
	 * @return imgSize
	 */
	public String getImgSize() {
		return imgSize;
	}
	
	/**
	 * Description : set the Image File Size
	 * 
	 * @param imgSize is the Image File Size
	 */
	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}
	
	/**
	 * Description : get method for form to get the Version Time
	 * 
	 * @return VersionTime
	 */
	public String getVersionTime() {
		return versionTime;
	}
	
	/**
	 * Description : set the Image VersionTime
	 * 
	 * @param VersionTime is the Image File VersionTime
	 */
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	
	/**
	 * Description : get method for form to get the customer Id
	 * 
	 * @return custId
	 */
	public String getCustId() {
		return custId;
	}
	
	/**
	 * Description : set the customer Id
	 * 
	 * @param custId is the  customer Id
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	/**
	 * Description : get method for form to get the customer Name
	 * 
	 * @return custName
	 */
	public String getCustName() {
		return custName;
	}
	
	/**
	 * Description : set the customer Name
	 * 
	 * @param custName is the  customer Name
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */
	public String[][] getMapper() {
		String[][] input = { { "ImageUploadAddObj", IMAGEUPLOADADD_MAPPER }	,
				{"event","java.lang.String"},
				{ "obImageUploadAddList", "java.util.ArrayList"},
				{ "theOBTrxContext", TRX_MAPPER },
				{"imageTrxObj", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue"},
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult"},
				{"IImageUploadTrxValue", "com.integrosys.cms.app.image.trx.IImageUploadTrxValue"},
				{"TrxId", "java.lang.String"}
				};
		return input;
	}
	
/*static variable declaration*/
	
	public static final String IMAGEUPLOADADD_MAPPER = "com.integrosys.cms.ui.image.ImageUploadAddMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

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
	
	/**
	 * Description : get method for form to get the Image File
	 * 
	 * @return imageFile
	 */
	public String getImageFilePath() {
		return imageFilePath;
	}
	
	/**
	 * Description : set the Image File path
	 * 
	 * @param imageFilePath is the Image File path
	 */
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	String formFile;

	public String getFormFile() {
		return formFile;
	}

	public void setFormFile(String formFile) {
		this.formFile = formFile;
	}
	
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
}
