package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IGoldFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IGoldFeedGroup busines entity
	 * 
	 * @return IGoldFeedGroup
	 */
	public IGoldFeedGroup getGoldFeedGroup();

	/**
	 * Get the staging IGoldFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IGoldFeedGroup getStagingGoldFeedGroup();

	/**
	 * Set the IGoldFeedGroup busines entity
	 * 
	 * @param value is of type IGoldFeedGroup
	 */
	public void setGoldFeedGroup(IGoldFeedGroup value);

	/**
	 * Set the staging IGoldFeedGroup business entity
	 * 
	 * @param value is of type IGoldFeedGroup
	 */
	public void setStagingGoldFeedGroup(IGoldFeedGroup value);
}
