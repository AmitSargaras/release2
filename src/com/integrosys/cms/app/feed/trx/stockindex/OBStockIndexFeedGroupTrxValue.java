/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/OBStockIndexFeedGroupTrxValue.java,v 1.1 2003/08/18 10:13:16 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.stockindex;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBStockIndexFeedGroupTrxValue extends OBCMSTrxValue implements IStockIndexFeedGroupTrxValue {

	/**
	 * Get the IStockIndexFeedGroup busines entity
	 * 
	 * @return IStockIndexFeedGroup
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBStockIndexFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBStockIndexFeedGroupTrxValue() {
		// Follow "limit".
		//super.setTransactionType(ICMSConstant.INSTANCE_STOCK_INDEX_FEED_GROUP)
		// ;
	}

	/**
	 * Get the staging IStockIndexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IStockIndexFeedGroup getStagingStockIndexFeedGroup() {
		return staging;
	}

	/**
	 * Set the IStockIndexFeedGroup busines entity
	 * 
	 * @param value is of type IStockIndexFeedGroup
	 */
	public void setStockIndexFeedGroup(IStockIndexFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IStockIndexFeedGroup business entity
	 * 
	 * @param value is of type IStockIndexFeedGroup
	 */
	public void setStagingStockIndexFeedGroup(IStockIndexFeedGroup value) {
		staging = value;
	}

	private IStockIndexFeedGroup actual;

	private IStockIndexFeedGroup staging;
}
