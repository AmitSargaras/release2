/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/IUnitTrustFeedGroupTrxValue.java,v 1.1 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.unittrust;

import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 */
public interface IUnitTrustFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IUnitTrustFeedGroup busines entity
	 * 
	 * @return IUnitTrustFeedGroup
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroup();

	/**
	 * Get the staging IUnitTrustFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IUnitTrustFeedGroup getStagingUnitTrustFeedGroup();

	/**
	 * Set the IUnitTrustFeedGroup busines entity
	 * 
	 * @param value is of type IUnitTrustFeedGroup
	 */
	public void setUnitTrustFeedGroup(IUnitTrustFeedGroup value);

	/**
	 * Set the staging IUnitTrustFeedGroup business entity
	 * 
	 * @param value is of type IUnitTrustFeedGroup
	 */
	public void setStagingUnitTrustFeedGroup(IUnitTrustFeedGroup value);
}