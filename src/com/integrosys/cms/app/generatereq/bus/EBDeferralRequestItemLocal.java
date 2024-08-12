/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestItemLocal.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBDeferralRequestItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface EBDeferralRequestItemLocal extends EJBLocalObject {
	/**
	 * Return the request item ID of the request item
	 * @return long - the request item ID
	 */
	public long getRequestItemID();

	/**
	 * Return the request item reference of the request item
	 * @return long - the request item reference
	 */
	public long getRequestItemRef();

	/**
	 * Return an object representation of the request item information.
	 * 
	 * @return IRequestItem
	 */
	public IDeferralRequestItem getValue();

	/**
	 * Persist a request item information
	 * 
	 * @param value is of type IRequestItem
	 */
	public void setValue(IDeferralRequestItem value);

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();
}
