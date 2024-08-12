package com.integrosys.cms.app.checklist.bus;

import java.io.Serializable;

public interface IShareDoc extends Serializable {

	public long getDocShareId();

	public void setDocShareId(long docShareId);

	public long getDocShareIdRef();

	public void setDocShareIdRef(long docShareId);

	public long getProfileId();

	public void setProfileId(long profileId);

	public long getSubProfileId();

	public void setSubProfileId(long subProfileId);

	public long getPledgorDtlId();

	public void setPledgorDtlId(long pledgorDtlId);

	public long getCollateralId();

	public void setCollateralId(long collateralId);

	public long getCheckListId();

	public void setCheckListId(long checkListId);

	public String getDetails();

	public void setDetails(String details);

	/*
	 * public boolean getIsDeletedInd(); public void setIsDeletedInd(boolean
	 * anIsDeletedInd);
	 */

	// This is same as DOC_ITEM_ID in Database = foreign Key of
	// CMS_CHECKLIST_ITEM ( PK)
	public long getCheckListItemID();

	public void setCheckListItemID(long aCheckListItemID);

	public String getItemStatus();

	public void setItemStatus(String anItemStatus);

	public String getDeleteCheckListId();

	public void setDeleteCheckListId(String deleteCheckListId);

	public String getExistingChkListId();

	public void setExistingChkListId(String existingChkListId);

	public String getLeID();

	public void setLeID(String leID);

	public String getLeName();

	public void setLeName(String leName);

	public String getSecurityDtlId();

	public void setSecurityDtlId(String securityDtlId);

	public String getSecurityType();

	public void setSecurityType(String securityType);

	public String getSecuritySubType();

	public void setSecuritySubType(String securitySubType);

	public void setStatus(String aShareStatus);

	public String getStatus();
}
