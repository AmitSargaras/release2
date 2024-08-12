/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/propertyindex/OBPropertyIndexFeedGroupTrxValue.java,v 1.1 2003/08/20 10:59:58 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.propertyindex;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBPropertyIndexFeedGroupTrxValue extends OBCMSTrxValue implements IPropertyIndexFeedGroupTrxValue {

	/**
	 * Get the IPropertyIndexFeedGroup busines entity
	 * 
	 * @return IPropertyIndexFeedGroup
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBPropertyIndexFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBPropertyIndexFeedGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.
		// INSTANCE_PROPERTY_INDEX_FEED_GROUP);
	}

	/**
	 * Get the staging IPropertyIndexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IPropertyIndexFeedGroup getStagingPropertyIndexFeedGroup() {
		return staging;
	}

	/**
	 * Set the IPropertyIndexFeedGroup busines entity
	 * 
	 * @param value is of type IPropertyIndexFeedGroup
	 */
	public void setPropertyIndexFeedGroup(IPropertyIndexFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IPropertyIndexFeedGroup business entity
	 * 
	 * @param value is of type IPropertyIndexFeedGroup
	 */
	public void setStagingPropertyIndexFeedGroup(IPropertyIndexFeedGroup value) {
		staging = value;
	}

	private IPropertyIndexFeedGroup actual;

	private IPropertyIndexFeedGroup staging;
}
