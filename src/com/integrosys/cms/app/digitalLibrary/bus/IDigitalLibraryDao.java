package com.integrosys.cms.app.digitalLibrary.bus;

import java.io.Serializable;

/**
 * interface of data access object for Bond
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IDigitalLibraryDao {

	/**
	 * entity name for OBDigitalLibraryEntry stored in actual table
	 */
	public static final String ACTUAL_DIGITAL_LIBRARY_ENTRY_ENTITY_NAME = "actualDigitalLibraryEntry";

	/**
	 * entity name for OBDigitalLibraryGroup stored in actual table
	 */
	public static final String ACTUAL_DIGITAL_LIBRARY_GROUP_ENTITY_NAME = "actualDigitalLibraryGroup";

	/**
	 * entity name for OBDigitalLibraryEntry stored in staging table
	 */
	public static final String STAGE_DIGITAL_LIBRARY_ENTRY_ENTITY_NAME = "stageDigitalLibraryEntry";

	/**
	 * entity name for OBDigitalLibraryGroup stored in staging table
	 */
	public static final String STAGE_DIGITAL_LIBRARY_GROUP_ENTITY_NAME = "stageDigitalLibraryGroup";

	/**
	 * Retrieve bond feed entry using primary key provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param key the primary key of bond feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the bond feed entry with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IDigitalLibraryEntry getDigitalLibraryEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aDigitalLibraryEntry bond feed entry instance to be created
	 * @return created instance of bond feed entry.
	 */
	public IDigitalLibraryEntry createDigitalLibraryEntry(String entityName, IDigitalLibraryEntry aDigitalLibraryEntry);

	/**
	 * Update bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aDigitalLibraryEntry bond feed entry instance to be updated
	 * @return updated instance of bond feed entry.
	 */
	public IDigitalLibraryEntry updateDigitalLibraryEntry(String entityName, IDigitalLibraryEntry aDigitalLibraryEntry);

	/**
	 * delete bond feed entry
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aDigitalLibraryEntry bond feed entry instance to be deleted
	 */
	public void deleteDigitalLibraryEntry(String entityName, IDigitalLibraryEntry aDigitalLibraryEntry);

	/**
	 * Retrieve bond feed group using primary key provided
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param key the primary key of bond feed group, usually is
	 *        {@link java.lang.Long} type.
	 * @return the bond feed group with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IDigitalLibraryGroup getDigitalLibraryGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aDigitalLibraryGroup bond feed group instance to be created
	 * @return created instance of bond feed group.
	 */
	public IDigitalLibraryGroup createDigitalLibraryGroup(String entityName, IDigitalLibraryGroup aDigitalLibraryGroup);

	/**
	 * Update bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aDigitalLibraryGroup bond feed group instance to be created
	 * @return updated instance of bond feed group.
	 */
	public IDigitalLibraryGroup updateDigitalLibraryGroup(String entityName, IDigitalLibraryGroup aDigitalLibraryGroup);

	/**
	 * Delete bond feed group
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aDigitalLibraryGroup bond feed group instance to be created
	 */
	public void deleteDigitalLibraryGroup(String entityName, IDigitalLibraryGroup aDigitalLibraryGroup);

	/**
	 * Retrieve bond feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of bond feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>BOND</code>
	 * @return instance of bond feed group, else <code>null</code> will be
	 *         returned.
	 */
	public IDigitalLibraryGroup getDigitalLibraryGroupByGroupType(String entityName, String groupType);

	/**
	 * Retrieve bond feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the bond feed entry.
	 * @return instance of bond feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IDigitalLibraryEntry getDigitalLibraryEntryByRic(String entityName, String ric);
}
