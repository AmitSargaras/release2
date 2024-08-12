package com.integrosys.cms.app.checklist.bus.checklistitemimagedetail;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBCheckListItemImageDetailLocalHome extends EJBLocalHome {

	public EBCheckListItemImageDetailLocal create(ICheckListItemImageDetail value) throws CreateException;
	public EBCheckListItemImageDetailLocal findByPrimaryKey(Long pk) throws FinderException;
}
