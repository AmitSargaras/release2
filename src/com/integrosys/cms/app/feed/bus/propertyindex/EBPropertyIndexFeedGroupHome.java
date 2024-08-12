/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedGroupHome.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public interface EBPropertyIndexFeedGroupHome extends EJBHome {

	/**
	 * Creates a PropertyIndexFeedGroup entry
	 * @param aIPropertyIndexFeedGroup IPropertyIndexFeedGroup
	 * @throws CreateException is error at record creation
	 * @throws RemoteException on run-time errors
	 * @return String - the primary key ID
	 */
	EBPropertyIndexFeedGroup create(IPropertyIndexFeedGroup aIPropertyIndexFeedGroup) throws CreateException,
			RemoteException;

	/*
	 * Find by primary key which is the PropertyIndexFeedGroup's ID
	 * 
	 * @param aIPropertyIndexFeedGroupID long
	 * 
	 * @return EBPropertyIndexFeedGroup
	 * 
	 * @throws FinderException if not found
	 * 
	 * @throws RemoteException on run-time errors
	 */
	EBPropertyIndexFeedGroup findByPrimaryKey(Long aIPropertyIndexFeedGroupID) throws FinderException, RemoteException;

	/**
	 * Find by the group type.
	 * @param groupType The group type.
	 * @param subType Identifies the subtype.
	 * @return The remote EB.
	 * @throws FinderException
	 * @throws RemoteException
	 */
	EBPropertyIndexFeedGroup findByGroupTypeAndSubType(String groupType, String subType) throws FinderException,
			RemoteException;
}
