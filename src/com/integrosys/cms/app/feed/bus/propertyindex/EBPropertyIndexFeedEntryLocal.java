/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedEntryLocal.java,v 1.2 2003/08/20 13:33:03 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 13:33:03 $ Tag: $Name: $
 * 
 */
public interface EBPropertyIndexFeedEntryLocal extends EJBLocalObject {

	/**
	 * Returns the primary key
	 * @return String - the primary key ID
	 */
	long getPropertyIndexFeedEntryID();

	long getPropertyIndexFeedEntryRef();

	boolean isDeletedInd();

	void setDeletedInd(boolean deletedInd);

	/**
	 * Returns the value object representing this entity bean
	 */
	IPropertyIndexFeedEntry getValue();

	/**
	 * Sets the entity using its corresponding value object.
	 */
	void setValue(IPropertyIndexFeedEntry aIPropertyIndexFeedEntry) throws ConcurrentUpdateException;
}
