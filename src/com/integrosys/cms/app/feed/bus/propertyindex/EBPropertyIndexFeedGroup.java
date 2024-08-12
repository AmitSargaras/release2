/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedGroup.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public interface EBPropertyIndexFeedGroup extends EJBObject {

	/**
	 * Returns the primary key
	 * @return String - the primary key ID
	 */
	long getPropertyIndexFeedGroupID() throws java.rmi.RemoteException;

	/**
	 * Returns the value object representing this entity bean
	 */
	IPropertyIndexFeedGroup getValue() throws java.rmi.RemoteException, PropertyIndexFeedGroupException;

	/**
	 * Sets the entity using its corresponding value object.
	 */
	void setValue(IPropertyIndexFeedGroup aIPropertyIndexFeedGroup) throws java.rmi.RemoteException,
			PropertyIndexFeedGroupException, ConcurrentUpdateException;
}
