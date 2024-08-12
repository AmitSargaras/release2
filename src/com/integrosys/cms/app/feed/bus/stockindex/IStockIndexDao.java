package com.integrosys.cms.app.feed.bus.stockindex;

import java.io.Serializable;

/**
 * interface of data access object for Stock Index
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IStockIndexDao {

	/**
	 * entity name for OBStockIndexFeedEntry stored in actual table
	 */
	public static final String ACTUAL_STOCK_INDEX_FEED_ENTRY_ENTITY_NAME = "actualStockIndexFeedEntry";

	/**
	 * entity name for OBStockIndexFeedGroup stored in actual table
	 */
	public static final String ACTUAL_STOCK_INDEX_FEED_GROUP_ENTITY_NAME = "actualStockIndexFeedGroup";

	/**
	 * entity name for OBStockIndexFeedEntry stored in staging table
	 */
	public static final String STAGE_STOCK_INDEX_FEED_ENTRY_ENTITY_NAME = "stageStockIndexFeedEntry";

	/**
	 * entity name for OBStockIndexFeedGroup stored in staging table
	 */
	public static final String STAGE_STOCK_INDEX_FEED_GROUP_ENTITY_NAME = "stageStockIndexFeedGroup";

	/**
	 * Retrieve stock index feed entry using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock index feed entry,
	 *        which can be actual or staging name.
	 * @param key the primary key of stock index feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the stock index feed entry with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IStockIndexFeedEntry getStockIndexFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create stock index feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock index feed entry,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedEntry stock index feed entry instance to be created
	 * @return created instance of stock index feed entry.
	 */
	public IStockIndexFeedEntry createStockIndexFeedEntry(String entityName, IStockIndexFeedEntry aStockIndexFeedEntry);

	/**
	 * Update stock index feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock index feed entry,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedEntry stock index feed entry instance to be updated
	 * @return updated instance of stock index feed entry.
	 */
	public IStockIndexFeedEntry updateStockIndexFeedEntry(String entityName, IStockIndexFeedEntry aStockIndexFeedEntry);

	/**
	 * delete stock index feed entry
	 * 
	 * @param entityName the persistence entity name of stock index feed entry,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedEntry stock index feed entry instance to be deleted
	 */
	public void deleteStockIndexFeedEntry(String entityName, IStockIndexFeedEntry aStockIndexFeedEntry);

	/**
	 * Retrieve stock index feed group using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock index feed group,
	 *        which can be actual or staging name.
	 * @param key the primary key of stock index feed group, usually is
	 *        {@link java.lang.Long} type.
	 * @return the stock index feed group with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create stock index feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock index feed group,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedGroup stock index feed group instance to be created
	 * @return created instance of stock index feed group.
	 */
	public IStockIndexFeedGroup createStockIndexFeedGroup(String entityName, IStockIndexFeedGroup aStockIndexFeedGroup);

	/**
	 * Update stock index feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock index feed group,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedGroup stock index feed group instance to be created
	 * @return updated instance of stock index feed group.
	 */
	public IStockIndexFeedGroup updateStockIndexFeedGroup(String entityName, IStockIndexFeedGroup aStockIndexFeedGroup);

	/**
	 * Delete stock index feed group
	 * 
	 * @param entityName the persistence entity name of stock index feed group,
	 *        which can be actual or staging name.
	 * @param aStockIndexFeedGroup stock index feed group instance to be created
	 */
	public void deleteStockIndexFeedGroup(String entityName, IStockIndexFeedGroup aStockIndexFeedGroup);

	/**
	 * Retrieve stock index feed group using type and subtype provided
	 * 
	 * @param entityName the persistence entity name of stock index feed group,
	 *        which can be actual or staging name.
	 * @param type the feed group type, which is normally
	 *        <code>STOCK_INDEX</code>
	 * @param subType the feed group sub type, whcih is normally the country
	 *        code
	 * @return instance of stock index feed group with criteria provided, else
	 *         <code>null</code> will be returned.
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroupByTypeAndSubType(String entityName, String type, String subType);

	/**
	 * Retrieve stock index feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of stock index feed entry,
	 *        which can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the stock index feed
	 *        entry.
	 * @return instance of stock index feed entry, else <code>null</code> will
	 *         be returned.
	 */
	public IStockIndexFeedEntry getStockIndexFeedEntryByRic(String entityName, String ric);
}
