package com.integrosys.cms.app.generalparam.bus;

import java.io.Serializable;

/**
 * interface of data access object for MutualFunds
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IGeneralParamDao {

	/**
	 * entity name for OBGeneralParamEntry stored in actual table
	 */
	public static final String ACTUAL_GENERAL_PARAM_ENTRY_ENTITY_NAME = "actualGeneralParamEntry";

	/**
	 * entity name for OBGeneralParamGroup stored in actual table
	 */
	public static final String ACTUAL_GENERAL_PARAM_GROUP_ENTITY_NAME = "actualGeneralParamGroup";

	/**
	 * entity name for OBGeneralParamEntry stored in staging table
	 */
	public static final String STAGE_GENERAL_PARAM_ENTRY_ENTITY_NAME = "stageGeneralParamEntry";

	/**
	 * entity name for OBGeneralParamGroup stored in staging table
	 */
	public static final String STAGE_GENERAL_PARAM_GROUP_ENTITY_NAME = "stageGeneralParamGroup";

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
	public IGeneralParamEntry getGeneralParamEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aGeneralParamEntry bond feed entry instance to be created
	 * @return created instance of bond feed entry.
	 */
	public IGeneralParamEntry createGeneralParamEntry(String entityName, IGeneralParamEntry aGeneralParamEntry);

	/**
	 * Update bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aGeneralParamEntry bond feed entry instance to be updated
	 * @return updated instance of bond feed entry.
	 */
	public IGeneralParamEntry updateGeneralParamEntry(String entityName, IGeneralParamEntry aGeneralParamEntry);

	/**
	 * delete bond feed entry
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aGeneralParamEntry bond feed entry instance to be deleted
	 */
	public void deleteGeneralParamEntry(String entityName, IGeneralParamEntry aGeneralParamEntry);

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
	public IGeneralParamGroup getGeneralParamGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aGeneralParamGroup bond feed group instance to be created
	 * @return created instance of bond feed group.
	 */
	public IGeneralParamGroup createGeneralParamGroup(String entityName, IGeneralParamGroup aGeneralParamGroup);

	/**
	 * Update bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aGeneralParamGroup bond feed group instance to be created
	 * @return updated instance of bond feed group.
	 */
	public IGeneralParamGroup updateGeneralParamGroup(String entityName, IGeneralParamGroup aGeneralParamGroup);

	/**
	 * Delete bond feed group
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aGeneralParamGroup bond feed group instance to be created
	 */
	public void deleteGeneralParamGroup(String entityName, IGeneralParamGroup aGeneralParamGroup);

	/**
	 * Retrieve bond feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of bond feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>MUTUAL_FUNDS</code>
	 * @return instance of bond feed group, else <code>null</code> will be
	 *         returned.
	 */
	public IGeneralParamGroup getGeneralParamGroupByGroupType(String entityName, String groupType);

	/**
	 * Retrieve bond feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the bond feed entry.
	 * @return instance of bond feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IGeneralParamEntry getGeneralParamEntryByRic(String entityName, String ric);
	
	
	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode);
	
	public IGeneralParamEntry[] getGeneralParamEntries(String tableName,String loginId);
	
	public void updateGeneralParamAppDate(String appDate);
	
	//Start:Added by Uma Khot for EOY activity.	
	String getActivityPerformedForParamCode(String paramCode);
	//End:Added by Uma Khot for EOY activity.	
	public String getGenralParamValues(String paramCode);
}
