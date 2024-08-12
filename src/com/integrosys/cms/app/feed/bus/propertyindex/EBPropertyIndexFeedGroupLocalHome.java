/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedGroupLocalHome.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public interface EBPropertyIndexFeedGroupLocalHome extends EJBLocalHome {

	/**
	 * Creates a PropertyIndexFeedGroup entry
	 * @param aIPropertyIndexFeedGroup IPropertyIndexFeedGroup
	 * @throws CreateException is error at record creation
	 * @return String - the primary key ID
	 */
	EBPropertyIndexFeedGroupLocal create(IPropertyIndexFeedGroup aIPropertyIndexFeedGroup) throws CreateException;

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
	EBPropertyIndexFeedGroupLocal findByPrimaryKey(Long aIPropertyIndexFeedGroupID) throws FinderException;
}
