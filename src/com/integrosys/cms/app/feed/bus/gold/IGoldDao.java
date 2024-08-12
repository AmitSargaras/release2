package com.integrosys.cms.app.feed.bus.gold;

import java.io.Serializable;

public interface IGoldDao {

	/**
	 * entity name for OBGoldFeedGroup stored in actual table
	 */
	public static final String ACTUAL_GOLD_FEED_GROUP = "actualGoldFeedGroup";
	
	/**
	 * entity name for OBGoldFeedGroup stored in stage table
	 */
	public static final String STAGE_GOLD_FEED_GROUP = "stageGoldFeedGroup";
	
	public IGoldFeedGroup createGoldFeedGroup(String entityName, IGoldFeedGroup goldFeedGroup);
	public IGoldFeedGroup getGoldFeedGroupByPrimaryKey(String entityName, Serializable key);
	public IGoldFeedGroup updateGoldFeedGroup(String entityName, IGoldFeedGroup goldFeedGroup);
	public IGoldFeedGroup getGoldFeedGroupByType(String entityName, String groupType);
	public void deleteGoldFeedGroup(String entityName, IGoldFeedGroup group);
}
