/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/unittrust/OBUnitTrustFeedGroupTrxValue.java,v 1.1 2003/08/08 04:26:15 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.unittrust;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBUnitTrustFeedGroupTrxValue extends OBCMSTrxValue implements IUnitTrustFeedGroupTrxValue {

	/**
	 * Get the IUnitTrustFeedGroup busines entity
	 * 
	 * @return IUnitTrustFeedGroup
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBUnitTrustFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBUnitTrustFeedGroupTrxValue() {
		// Follow "limit".
		//super.setTransactionType(ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);
	}

	/**
	 * Get the staging IUnitTrustFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IUnitTrustFeedGroup getStagingUnitTrustFeedGroup() {
		return staging;
	}

	/**
	 * Set the IUnitTrustFeedGroup busines entity
	 * 
	 * @param value is of type IUnitTrustFeedGroup
	 */
	public void setUnitTrustFeedGroup(IUnitTrustFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IUnitTrustFeedGroup business entity
	 * 
	 * @param value is of type IUnitTrustFeedGroup
	 */
	public void setStagingUnitTrustFeedGroup(IUnitTrustFeedGroup value) {
		staging = value;
	}

	private IUnitTrustFeedGroup actual;

	private IUnitTrustFeedGroup staging;
}
