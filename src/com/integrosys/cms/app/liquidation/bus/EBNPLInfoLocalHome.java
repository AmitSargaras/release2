/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBNPLInfoBean.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBNPLInfoLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param nPLInfo of type INPLInfo
	 * @return local NPL Info ejb object
	 * @throws javax.ejb.CreateException on error while creating the ejb
	 */
	public EBNPLInfoLocal create(INPLInfo nPLInfo) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the NPL Info ID.
	 * 
	 * @param nPLInfoID NPL Info ID
	 * @return local NPL Info ejb object
	 * @throws javax.ejb.FinderException on error while finding the ejb
	 */
	public EBNPLInfoLocal findByPrimaryKey(Long nPLInfoID) throws FinderException;

	/**
	 * Find NPL Info by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBNPLInfo</code>s
	 * @throws javax.ejb.FinderException on error finding the NPL Info
	 */
	// public Collection findByGroupID (long groupID) throws FinderException;
}