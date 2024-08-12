package com.integrosys.cms.app.image.bus;

import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.image.IStageImageUploadAdd;


/**
 * This interface defines the operations that are provided by a image 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 11:00:00 $ Tag: $Name: $
 */
public class OBStageImageUploadAdd implements IStageImageUploadAdd {
	
	private long imgId;
	
    private String createBy;
	
	private String creationDate;
	
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
	
	private String imageApprId;
	

	/**
	 * @return the imageApprId
	 */
	public String getImageApprId() {
		return imageApprId;
	}

	/**
	 * @param imageApprId the imageApprId to set
	 */
	public void setImageApprId(String imageApprId) {
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

	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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

	
}
