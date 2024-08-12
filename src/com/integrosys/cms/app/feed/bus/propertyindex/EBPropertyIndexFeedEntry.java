/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedEntry.java,v 1.2 2003/08/20 13:33:03 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 13:33:03 $ Tag: $Name: $
 * 
 */
public interface EBPropertyIndexFeedEntry extends EJBObject {

	/**
	 * Returns the primary key
	 * @return String - the primary key ID
	 */
	long getPropertyIndexFeedEntryID() throws java.rmi.RemoteException;

	long getPropertyIndexFeedEntryRef() throws RemoteException;

	boolean isDeletedInd() throws RemoteException;

	void setDeletedInd(boolean deletedInd) throws RemoteException;

	/**
	 * Returns the value object representing this entity bean
	 */
	IPropertyIndexFeedEntry getValue() throws java.rmi.RemoteException;

	/**
	 * Sets the entity using its corresponding value object.
	 */
	void setValue(IPropertyIndexFeedEntry aIPropertyIndexFeedEntry) throws java.rmi.RemoteException,
			ConcurrentUpdateException;
}
