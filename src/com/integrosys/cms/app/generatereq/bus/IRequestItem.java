/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestItem.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;

import com.integrosys.cms.app.checklist.bus.ICheckListItem;

/**
 * This interface defines the list of attributes that is required for request
 * item
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IRequestItem extends Serializable {
	public long getRequestItemID();

	public long getRequestItemRef();

	public long getCheckListID();

	public ICheckListItem getCheckListItem();

	public void setRequestItemID(long aRequestItemID);

	public void setRequestItemRef(long aRequestItemRef);

	public void setCheckListID(long aCheckListID);

	public void setCheckListItem(ICheckListItem aCheckListItem);
}
