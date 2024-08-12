package com.integrosys.cms.app.feed.bus.unittrust;

import java.io.Serializable;

/**
 * interface of data access object for Unit Trust
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IUnitTrustDao {

	/**
	 * entity name for OBUnitTrustFeedEntry stored in actual table
	 */
	public static final String ACTUAL_UNIT_TRUST_FEED_ENTRY_ENTITY_NAME = "actualUnitTrustFeedEntry";

	/**
	 * entity name for OBUnitTrustFeedGroup stored in actual table
	 */
	public static final String ACTUAL_UNIT_TRUST_FEED_GROUP_ENTITY_NAME = "actualUnitTrustFeedGroup";

	/**
	 * entity name for OBUnitTrustFeedEntry stored in staging table
	 */
	public static final String STAGE_UNIT_TRUST_FEED_ENTRY_ENTITY_NAME = "stageUnitTrustFeedEntry";

	/**
	 * entity name for OBUnitTrustFeedGroup stored in staging table
	 */
	public static final String STAGE_UNIT_TRUST_FEED_GROUP_ENTITY_NAME = "stageUnitTrustFeedGroup";

	/**
	 * Retrieve unit trust feed entry using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock feed index feed
	 *        entry, which can be actual or staging name.
	 * @param key the primary key of unit trust feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the unit trust feed entry with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IUnitTrustFeedEntry getUnitTrustFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create unit trust feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed index feed
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedEntry unit trust feed entry instance to be created
	 * @return created instance of unit trust feed entry.
	 */
	public IUnitTrustFeedEntry createUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry aUnitTrustFeedEntry);

	/**
	 * Update unit trust feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed index feed
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedEntry unit trust feed entry instance to be updated
	 * @return updated instance of unit trust feed entry.
	 */
	public IUnitTrustFeedEntry updateUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry aUnitTrustFeedEntry);

	/**
	 * delete unit trust feed entry
	 * 
	 * @param entityName the persistence entity name of stock feed index feed
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedEntry unit trust feed entry instance to be deleted
	 */
	public void deleteUnitTrustFeedEntry(String entityName, IUnitTrustFeedEntry aUnitTrustFeedEntry);

	/**
	 * Retrieve unit trust feed group using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock feed index group
	 *        entry, which can be actual or staging name.
	 * @param key the primary key of unit trust feed group, usually is
	 *        {@link java.lang.Long} type.
	 * @return the unit trust feed group with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create unit trust feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed index group
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedGroup unit trust feed group instance to be created
	 * @return created instance of unit trust feed group.
	 */
	public IUnitTrustFeedGroup createUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup aUnitTrustFeedGroup);

	/**
	 * Update unit trust feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed index group
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedGroup unit trust feed group instance to be created
	 * @return updated instance of unit trust feed group.
	 */
	public IUnitTrustFeedGroup updateUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup aUnitTrustFeedGroup);

	/**
	 * Delete unit trust feed group
	 * 
	 * @param entityName the persistence entity name of stock feed index group
	 *        entry, which can be actual or staging name.
	 * @param aUnitTrustFeedGroup unit trust feed group instance to be created
	 */
	public void deleteUnitTrustFeedGroup(String entityName, IUnitTrustFeedGroup aUnitTrustFeedGroup);

	/**
	 * Retrieve unit trust feed group using type and subtype provided
	 * 
	 * @param entityName the persistence entity name of stock feed index group
	 *        entry, which can be actual or staging name.
	 * @param type the feed group type, which is normally
	 *        <code>STOCK_INDEX</code>
	 * @param subType the feed group sub type, whcih is normally the country
	 *        code
	 * @return instance of unit trust feed group with criteria provided, else
	 *         <code>null</code> will be returned.
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroupByTypeAndSubType(String entityName, String type, String subType);

	/**
	 * Retrieve unit trust feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of stock feed index entry
	 *        entry, which can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the unit trust feed
	 *        entry.
	 * @return instance of unit trust feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String entityName, String ric);
}
