/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/IPropertyIndexFeedGroupTrxValue.java,v 1.1 2003/08/20 10:59:58 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.propertyindex;

import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:58 $ Tag: $Name: $
 */
public interface IPropertyIndexFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IPropertyIndexFeedGroup busines entity
	 * 
	 * @return IPropertyIndexFeedGroup
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup();

	/**
	 * Get the staging IPropertyIndexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IPropertyIndexFeedGroup getStagingPropertyIndexFeedGroup();

	/**
	 * Set the IPropertyIndexFeedGroup busines entity
	 * 
	 * @param value is of type IPropertyIndexFeedGroup
	 */
	public void setPropertyIndexFeedGroup(IPropertyIndexFeedGroup value);

	/**
	 * Set the staging IPropertyIndexFeedGroup business entity
	 * 
	 * @param value is of type IPropertyIndexFeedGroup
	 */
	public void setStagingPropertyIndexFeedGroup(IPropertyIndexFeedGroup value);
}