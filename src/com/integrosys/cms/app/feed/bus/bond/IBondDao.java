package com.integrosys.cms.app.feed.bus.bond;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;

/**
 * interface of data access object for Bond
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IBondDao {

	/**
	 * entity name for OBBondFeedEntry stored in actual table
	 */
	public static final String ACTUAL_BOND_FEED_ENTRY_ENTITY_NAME = "actualBondFeedEntry";

	/**
	 * entity name for OBBondFeedGroup stored in actual table
	 */
	public static final String ACTUAL_BOND_FEED_GROUP_ENTITY_NAME = "actualBondFeedGroup";

	/**
	 * entity name for OBBondFeedEntry stored in staging table
	 */
	public static final String STAGE_BOND_FEED_ENTRY_ENTITY_NAME = "stageBondFeedEntry";

	/**
	 * entity name for OBBondFeedGroup stored in staging table
	 */
	public static final String STAGE_BOND_FEED_GROUP_ENTITY_NAME = "stageBondFeedGroup";
	
	/*
	 * File Upload mapper entity name
	 */
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

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
	public IBondFeedEntry getBondFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aBondFeedEntry bond feed entry instance to be created
	 * @return created instance of bond feed entry.
	 */
	public IBondFeedEntry createBondFeedEntry(String entityName, IBondFeedEntry aBondFeedEntry);

	/**
	 * Update bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aBondFeedEntry bond feed entry instance to be updated
	 * @return updated instance of bond feed entry.
	 */
	public IBondFeedEntry updateBondFeedEntry(String entityName, IBondFeedEntry aBondFeedEntry);

	/**
	 * delete bond feed entry
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aBondFeedEntry bond feed entry instance to be deleted
	 */
	public void deleteBondFeedEntry(String entityName, IBondFeedEntry aBondFeedEntry);

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
	public IBondFeedGroup getBondFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aBondFeedGroup bond feed group instance to be created
	 * @return created instance of bond feed group.
	 */
	public IBondFeedGroup createBondFeedGroup(String entityName, IBondFeedGroup aBondFeedGroup);

	/**
	 * Update bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aBondFeedGroup bond feed group instance to be created
	 * @return updated instance of bond feed group.
	 */
	public IBondFeedGroup updateBondFeedGroup(String entityName, IBondFeedGroup aBondFeedGroup);

	/**
	 * Delete bond feed group
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aBondFeedGroup bond feed group instance to be created
	 */
	public void deleteBondFeedGroup(String entityName, IBondFeedGroup aBondFeedGroup);

	/**
	 * Retrieve bond feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of bond feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>BOND</code>
	 * @return instance of bond feed group, else <code>null</code> will be
	 *         returned.
	 */
	public IBondFeedGroup getBondFeedGroupByGroupType(String entityName, String groupType);

	/**
	 * Retrieve bond feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the bond feed entry.
	 * @return instance of bond feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IBondFeedEntry getBondFeedEntryByRic(String entityName, String ric);
	
	/**
	 * Method added for Marketable Securities(Bond)
	 * 
	 */
	IBondFeedEntry getBondFeedEntry(String bondCode) throws BondFeedEntryException;
	
    //File Upload
    boolean isPrevFileUploadPending() throws BondFeedEntryException;
	List getAllStageBondFeedEntry (String searchBy, String login)throws BondFeedEntryException;
	List getFileMasterList(String searchBy)throws BondFeedEntryException;
	//IBondFeedEntry createBondFeedEntry(String entityName, IBondFeedEntry creditApproval)throws BondFeedEntryException;
	IFileMapperId insertBondFeedEntry(String entityName, IFileMapperId fileId, IBondFeedGroupTrxValue trxValue)
	throws BondFeedEntryException;
	int insertBondFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws BondFeedEntryException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws BondFeedEntryException;
	List insertActualBondFeedEntry(String sysId)
	throws BondFeedEntryException;
	IBondFeedGroup insertBondFeedEntry(String entityName, IBondFeedGroup creditApproval)
	throws BondFeedEntryException;
    void updateBondFeedEntryItem(String entityName,List bondFeedEntryList) throws BondFeedEntryException;
    boolean isBondCodeExist(String entityName,List bondCodeList) throws BondFeedEntryException;
    void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws BondFeedEntryException, TrxParameterException, TransactionException;
    //End File Upload
    
    public boolean isExistBondCode(String entityName,String bondCode) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
