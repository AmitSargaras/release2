/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/IStockIndexFeedGroupTrxValue.java,v 1.1 2003/08/18 10:13:16 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stockindex;

import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 10:13:16 $ Tag: $Name: $
 */
public interface IStockIndexFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IStockIndexFeedGroup busines entity
	 * 
	 * @return IStockIndexFeedGroup
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroup();

	/**
	 * Get the staging IStockIndexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IStockIndexFeedGroup getStagingStockIndexFeedGroup();

	/**
	 * Set the IStockIndexFeedGroup busines entity
	 * 
	 * @param value is of type IStockIndexFeedGroup
	 */
	public void setStockIndexFeedGroup(IStockIndexFeedGroup value);

	/**
	 * Set the staging IStockIndexFeedGroup business entity
	 * 
	 * @param value is of type IStockIndexFeedGroup
	 */
	public void setStagingStockIndexFeedGroup(IStockIndexFeedGroup value);
}