package com.integrosys.cms.app.checklist.bus.checklistitemimagedetail;

import javax.ejb.EJBLocalObject;

public interface EBCheckListItemImageDetailLocal extends EJBLocalObject{
	
	public long getCheckListItemImageDetailId();
	public ICheckListItemImageDetail getValue();
	public void setValue(ICheckListItemImageDetail value);

}
