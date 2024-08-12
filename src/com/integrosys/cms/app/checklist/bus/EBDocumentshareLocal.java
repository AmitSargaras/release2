package com.integrosys.cms.app.checklist.bus;

import javax.ejb.EJBLocalObject;

public interface EBDocumentshareLocal extends EJBLocalObject {

	public Long getCMPDocShareId();

	public Long getCMPCheckListItemID();

	public long getDocShareIdRef();

	public IShareDoc getValue();

	public void setValue(IShareDoc iShareDoc);

	// public void setIsDeletedInd(boolean anIsDeletedInd);
	// public boolean getIsDeletedInd();

	public String getStatus();

	public void setStatus(String anItemStatus);
}
