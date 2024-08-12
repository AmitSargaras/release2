package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * interface of data access object for MutualFunds
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsDao {

	/**
	 * entity name for OBMutualFundsFeedEntry stored in actual table
	 */
	public static final String ACTUAL_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME = "actualMutualFundsFeedEntry";

	/**
	 * entity name for OBMutualFundsFeedGroup stored in actual table
	 */
	public static final String ACTUAL_MUTUAL_FUNDS_FEED_GROUP_ENTITY_NAME = "actualMutualFundsFeedGroup";

	/**
	 * entity name for OBMutualFundsFeedEntry stored in staging table
	 */
	public static final String STAGE_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME = "stageMutualFundsFeedEntry";

	/**
	 * entity name for OBMutualFundsFeedGroup stored in staging table
	 */
	public static final String STAGE_MUTUAL_FUNDS_FEED_GROUP_ENTITY_NAME = "stageMutualFundsFeedGroup";
	
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
	public IMutualFundsFeedEntry getMutualFundsFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aMutualFundsFeedEntry bond feed entry instance to be created
	 * @return created instance of bond feed entry.
	 */
	public IMutualFundsFeedEntry createMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry aMutualFundsFeedEntry);

	/**
	 * Update bond feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aMutualFundsFeedEntry bond feed entry instance to be updated
	 * @return updated instance of bond feed entry.
	 */
	public IMutualFundsFeedEntry updateMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry aMutualFundsFeedEntry);

	/**
	 * delete bond feed entry
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param aMutualFundsFeedEntry bond feed entry instance to be deleted
	 */
	public void deleteMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry aMutualFundsFeedEntry);

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
	public IMutualFundsFeedGroup getMutualFundsFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aMutualFundsFeedGroup bond feed group instance to be created
	 * @return created instance of bond feed group.
	 */
	public IMutualFundsFeedGroup createMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup aMutualFundsFeedGroup);

	/**
	 * Update bond feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aMutualFundsFeedGroup bond feed group instance to be created
	 * @return updated instance of bond feed group.
	 */
	public IMutualFundsFeedGroup updateMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup aMutualFundsFeedGroup);

	/**
	 * Delete bond feed group
	 * 
	 * @param entityName the persistence entity name of bond feed group entry,
	 *        which can be actual or staging name.
	 * @param aMutualFundsFeedGroup bond feed group instance to be created
	 */
	public void deleteMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup aMutualFundsFeedGroup);

	/**
	 * Retrieve bond feed group using group type code provided
	 * 
	 * @param entityName the persistence entity name of bond feed group, which
	 *        can be actual or staging name.
	 * @param groupType group type of the feed, normally is <code>MUTUAL_FUNDS</code>
	 * @return instance of bond feed group, else <code>null</code> will be
	 *         returned.
	 */
	public IMutualFundsFeedGroup getMutualFundsFeedGroupByGroupType(String entityName, String groupType);

	/**
	 * Retrieve bond feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of bond feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the bond feed entry.
	 * @return instance of bond feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String entityName, String ric);

	 public IMutualFundsFeedEntry getIMutualFundsFeed (String entityName,String schemeCode) ;

	
	public boolean isValidSchemeCode(String schemeCode);
	 //File Upload
    boolean isPrevFileUploadPending() throws MutualFundsFeedEntryException;
	List getAllStageMutualFundsFeedEntry (String searchBy, String login)throws MutualFundsFeedEntryException;
	List getFileMasterList(String searchBy)throws MutualFundsFeedEntryException;
	//IMutualFundsFeedEntry createMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry creditApproval)throws MutualFundsFeedEntryException;
	IFileMapperId insertMutualFundsFeedEntry(String entityName, IFileMapperId fileId, IMutualFundsFeedGroupTrxValue trxValue)
	throws MutualFundsFeedEntryException;
	int insertMutualFundsFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws MutualFundsFeedEntryException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws MutualFundsFeedEntryException;
	List insertActualMutualFundsFeedEntry(String sysId)
	throws MutualFundsFeedEntryException;
	IMutualFundsFeedGroup insertMutualFundsFeedEntry(String entityName, IMutualFundsFeedGroup creditApproval)
	throws MutualFundsFeedEntryException;
    void updateMutualFundsFeedEntryItem(String entityName,List mutualfundsFeedEntryList) throws MutualFundsFeedEntryException;
    boolean isMutualFundsCodeExist(String entityName,List schemeCodeList) throws MutualFundsFeedEntryException;
    void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException;  //A govind 300811
    //End File Upload

}
