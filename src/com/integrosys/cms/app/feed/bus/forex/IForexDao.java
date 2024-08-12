package com.integrosys.cms.app.feed.bus.forex;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.forex.OBForex;

/**
 * interface of data access object for Forex (Foreign Exchange)
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IForexDao {

	/**
	 * entity name for OBForexFeedEntry stored in actual table
	 */
	public static final String ACTUAL_FOREX_FEED_ENTRY_ENTITY_NAME = "actualForexFeedEntry";

	/**
	 * entity name for OBForexFeedGroup stored in actual table
	 */
	public static final String ACTUAL_FOREX_FEED_GROUP_ENTITY_NAME = "actualForexFeedGroup";

	/**
	 * entity name for OBForexFeedEntry stored in staging table
	 */
	public static final String STAGE_FOREX_FEED_ENTRY_ENTITY_NAME = "stageForexFeedEntry";

	/**
	 * entity name for OBForexFeedGroup stored in staging table
	 */
	public static final String STAGE_FOREX_FEED_GROUP_ENTITY_NAME = "stageForexFeedGroup";
	
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	/**
	 * Retrieve forex feed entry using primary key provided
	 * 
	 * @param entityName the persistence entity name of forex feed entry, which
	 *        can be actual or staging name.
	 * @param key the primary key of forex feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the forex feed entry with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IForexFeedEntry getForexFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create forex feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of forex feed entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedEntry forex feed entry instance to be created
	 * @return created instance of forex feed entry.
	 */
	public IForexFeedEntry createForexFeedEntry(String entityName, IForexFeedEntry aForexFeedEntry);

	/**
	 * Update forex feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of forex feed entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedEntry forex feed entry instance to be updated
	 * @return updated instance of forex feed entry.
	 */
	public IForexFeedEntry updateForexFeedEntry(String entityName, IForexFeedEntry aForexFeedEntry);

	/**
	 * delete forex feed entry
	 * 
	 * @param entityName the persistence entity name of forex feed entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedEntry forex feed entry instance to be deleted
	 */
	public void deleteForexFeedEntry(String entityName, IForexFeedEntry aForexFeedEntry);

	/**
	 * Retrieve forex feed group using primary key provided
	 * 
	 * @param entityName the persistence entity name of forex group entry, which
	 *        can be actual or staging name.
	 * @param key the primary key of forex feed group, usually is
	 *        {@link java.lang.Long} type.
	 * @return the forex feed group with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IForexFeedGroup getForexFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Retrieve forex feed group using group type provided
	 * 
	 * @param entityName the persistence entity name of forex group entry, which
	 *        can be actual or staging name.
	 * @param groupType the group type of forex, which is <code>FOREX</code>
	 * @return the forex feed group with the group type provided, else
	 *         <code>null</code> will be return
	 */
	public IForexFeedGroup getForexFeedGroupByGroupType(String entityName, String groupType);

	/**
	 * Create forex feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of forex group entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedGroup forex feed group instance to be created
	 * @return created instance of forex feed group.
	 */
	public IForexFeedGroup createForexFeedGroup(String entityName, IForexFeedGroup aForexFeedGroup);

	/**
	 * Update forex feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of forex group entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedGroup forex feed group instance to be created
	 * @return updated instance of forex feed group.
	 */
	public IForexFeedGroup updateForexFeedGroup(String entityName, IForexFeedGroup aForexFeedGroup);

	/**
	 * Delete forex feed group
	 * 
	 * @param entityName the persistence entity name of forex group entry, which
	 *        can be actual or staging name.
	 * @param aForexFeedGroup forex feed group instance to be created
	 */
	public void deleteForexFeedGroup(String entityName, IForexFeedGroup aForexFeedGroup);

    /**
     * Retrieve forex feed entry using currency code provided
     *
     * @param entityName the persistence entity name of forex group entry, which
     *        can be actual or staging name.
     * @param currencyCode the currency code that want to find the exchange rate to MYR
     * @return the forex feed entry with currency code provided, else
     *         <code>null</code> will be return
     */
    public IForexFeedEntry getForexFeedByCurrencyCode(String entityName, String currencyCode);
    
    //File Upload
    boolean isPrevFileUploadPending() throws ForexFeedEntryException;
	List getAllStageForexFeedEntry (String searchBy, String login)throws ForexFeedEntryException;
	List getFileMasterList(String searchBy)throws ForexFeedEntryException;
	//IForexFeedEntry createForexFeedEntry(String entityName, IForexFeedEntry creditApproval)throws ForexFeedEntryException;
	IFileMapperId insertForexFeedEntry(String entityName, IFileMapperId fileId, IForexFeedGroupTrxValue trxValue)
	throws ForexFeedEntryException;
	int insertForexFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	HashMap insertForexFeedEntryAuto(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws ForexFeedEntryException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws ForexFeedEntryException;
	List insertActualForexFeedEntry(String sysId)
	throws ForexFeedEntryException;
	IForexFeedGroup insertForexFeedEntry(String entityName, IForexFeedGroup creditApproval)
	throws ForexFeedEntryException;
    void updateForexFeedEntryExchangeRate(String entityName,List forexFeedEntryList) throws ForexFeedGroupException;
    void updateForexFeedEntryExchangeRateAuto(String entityName,List forexFeedEntryList) throws ForexFeedGroupException;
    boolean isCurrencyCodeExist(String entityName,List currencyCodeList) throws ForexFeedGroupException;
    
    public BigDecimal getExchangeRateWithINR (String entityName,String currencyCode) throws ForexFeedGroupException;
    
    void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ForexFeedGroupException, TrxParameterException, TransactionException; 
    
    public OBForex retriveCurrency(String currency)throws ForexFeedGroupException;
}
