package com.integrosys.cms.app.checklist.bus.checklistitemimagedetail;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBCheckListItemImageDetail implements ICheckListItemImageDetail {

	private long checkListItemImageDetailId = ICMSConstant.LONG_INVALID_VALUE;
	
	private long checkListItemId = ICMSConstant.LONG_INVALID_VALUE;
	
	private long imageId = ICMSConstant.LONG_INVALID_VALUE;
	
	private String isSelectedInd;

	private String fileName;
	
	private String documentDescription;
	
	private String subFolderName;
	
	
	public String getSubFolderName() {
		return subFolderName;
	}

	public void setSubFolderName(String subFolderName) {
		this.subFolderName = subFolderName;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public long getCheckListItemImageDetailId() {
		return checkListItemImageDetailId;
	}

	public void setCheckListItemImageDetailId(long checkListItemImageDetailId) {
		this.checkListItemImageDetailId = checkListItemImageDetailId;
	}
	
	public long getCheckListItemId() {
		return checkListItemId;
	}

	public void setCheckListItemId(long checkListItemId) {
		this.checkListItemId = checkListItemId;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getIsSelectedInd() {
		return isSelectedInd;
	}

	public void setIsSelectedInd(String isSelectedInd) {
		this.isSelectedInd = isSelectedInd;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
