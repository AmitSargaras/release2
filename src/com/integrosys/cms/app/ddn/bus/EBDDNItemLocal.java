/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNItemLocal.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBDDNItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */
public interface EBDDNItemLocal extends EJBLocalObject {
	/**
	 * Return the ddn ID
	 * @return Long - the ddn ID
	 */
	public Long getCMPDDNID();

	/**
	 * Return the ddn item ID of the ddn item
	 * @return long - the ddn item ID
	 */
	public long getDDNItemID();

	/**
	 * Return the ddn item reference of the ddn item
	 * @return long - the ddn item reference
	 */
	public long getDDNItemRef();

	/**
	 * Return an object representation of the ddn item information.
	 * 
	 * @return IDDNItem
	 */
	public IDDNItem getValue();

	/**
	 * Persist a ddn item information
	 * 
	 * @param value is of type IDDNItem
	 */
	public void setValue(IDDNItem value);

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
