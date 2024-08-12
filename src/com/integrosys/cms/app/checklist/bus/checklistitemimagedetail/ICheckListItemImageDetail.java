package com.integrosys.cms.app.checklist.bus.checklistitemimagedetail;

import java.io.Serializable;

public interface ICheckListItemImageDetail extends Serializable {

	public long getCheckListItemImageDetailId();
	public void setCheckListItemImageDetailId(long checkListItemImageDetailId);
	
	public long getCheckListItemId();
	public void setCheckListItemId(long checkListItemId);
	
	public long getImageId();
	public void setImageId(long imageId);
	
	public String getIsSelectedInd();
	public void setIsSelectedInd(String isSelectedInd);
	
	public String getFileName();
	public void setFileName(String fileName);
	
	public String getDocumentDescription();
	public void setDocumentDescription(String documentDescription);
	
	public String getSubFolderName();
	public void setSubFolderName(String subFolderName);
}
