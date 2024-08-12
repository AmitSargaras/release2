package com.integrosys.cms.app.approvalmatrix.bus;

import java.io.Serializable;

/**
 * interface of data access object for Bond
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IApprovalMatrixDao {

	/**
	 * entity name for OBApprovalMatrixEntry stored in actual table
	 */
	public static final String ACTUAL_APPROVAL_MATRIX_ENTRY_ENTITY_NAME = "actualApprovalMatrixEntry";

	/**
	 * entity name for OBApprovalMatrixGroup stored in actual table
	 */
	public static final String ACTUAL_APPROVAL_MATRIX_GROUP_ENTITY_NAME = "actualApprovalMatrixGroup";

	/**
	 * entity name for OBApprovalMatrixEntry stored in staging table
	 */
	public static final String STAGE_APPROVAL_MATRIX_ENTRY_ENTITY_NAME = "stageApprovalMatrixEntry";

	/**
	 * entity name for OBApprovalMatrixGroup stored in staging table
	 */
	public static final String STAGE_APPROVAL_MATRIX_GROUP_ENTITY_NAME = "stageApprovalMatrixGroup";

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
	public IApprovalMatrixEntry getApprovalMatrixEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aApprovalMatrixEntry bond feed entry instance to be created
	 * @return created instance of bond feed entry.
	 */
	public IApprovalMatrixEntry createApprovalMatrixEntry(String entityName, IApprovalMatrixEntry aApprovalMatrixEntry);

	/**
	 * Update bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aApprovalMatrixEntry bond feed entry instance to be updated
	 * @return updated instance of bond feed entry.
	 */
	public IApprovalMatrixEntry updateApprovalMatrixEntry(String entityName, IApprovalMatrixEntry aApprovalMatrixEntry);

	/**
	 * delete bond feed entry
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aApprovalMatrixEntry bond feed entry instance to be deleted
	 */
	public void deleteApprovalMatrixEntry(String entityName, IApprovalMatrixEntry aApprovalMatrixEntry);

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
	public IApprovalMatrixGroup getApprovalMatrixGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aApprovalMatrixGroup bond feed group instance to be created
	 * @return created instance of bond feed group.
	 */
	public IApprovalMatrixGroup createApprovalMatrixGroup(String entityName, IApprovalMatrixGroup aApprovalMatrixGroup);

	/**
	 * Update bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aApprovalMatrixGroup bond feed group instance to be created
	 * @return updated instance of bond feed group.
	 */
	public IApprovalMatrixGroup updateApprovalMatrixGroup(String entityName, IApprovalMatrixGroup aApprovalMatrixGroup);

	/**
	 * Delete bond feed group
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aApprovalMatrixGroup bond feed group instance to be created
	 */
	public void deleteApprovalMatrixGroup(String entityName, IApprovalMatrixGroup aApprovalMatrixGroup);

	/**
	 * Retrieve bond feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of bond feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>BOND</code>
	 * @return instance of bond feed group, else <code>null</code> will be
	 *         returned.
	 */
	public IApprovalMatrixGroup getApprovalMatrixGroupByGroupType(String entityName, String groupType);

	/**
	 * Retrieve bond feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the bond feed entry.
	 * @return instance of bond feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IApprovalMatrixEntry getApprovalMatrixEntryByRic(String entityName, String ric);
}
