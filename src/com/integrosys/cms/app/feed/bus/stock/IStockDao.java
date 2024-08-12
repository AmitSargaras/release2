package com.integrosys.cms.app.feed.bus.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * interface of data access object for Stock
 * 
 * @author Chong Jun Yong
 * @since 1.0
 */
public interface IStockDao {

	/**
	 * entity name for OBStockFeedEntry stored in actual table
	 */
	public static final String ACTUAL_STOCK_FEED_ENTRY_ENTITY_NAME = "actualStockFeedEntry";

	/**
	 * entity name for OBStockFeedGroup stored in actual table
	 */
	public static final String ACTUAL_STOCK_FEED_GROUP_ENTITY_NAME = "actualStockFeedGroup";

	/**
	 * entity name for OBStockFeedEntry stored in staging table
	 */
	public static final String STAGE_STOCK_FEED_ENTRY_ENTITY_NAME = "stageStockFeedEntry";

	/**
	 * entity name for OBStockFeedGroup stored in staging table
	 */
	public static final String STAGE_STOCK_FEED_GROUP_ENTITY_NAME = "stageStockFeedGroup";

	
	/*
	 * Add For File Upload Master
	 */
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	/**
	 * Retrieve stock feed entry using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param key the primary key of stock feed entry, usually is
	 *        {@link java.lang.Long} type.
	 * @return the stock feed entry with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IStockFeedEntry getStockFeedEntryByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create stock feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param aStockFeedEntry stock feed entry instance to be created
	 * @return created instance of stock feed entry.
	 */
	public IStockFeedEntry createStockFeedEntry(String entityName, IStockFeedEntry aStockFeedEntry);

	/**
	 * Update stock feed entry into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param aStockFeedEntry stock feed entry instance to be updated
	 * @return updated instance of stock feed entry.
	 */
	public IStockFeedEntry updateStockFeedEntry(String entityName, IStockFeedEntry aStockFeedEntry);

	/**
	 * delete stock feed entry
	 * 
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param aStockFeedEntry stock feed entry instance to be deleted
	 */
	public void deleteStockFeedEntry(String entityName, IStockFeedEntry aStockFeedEntry);

	/**
	 * Retrieve stock feed group using primary key provided
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param key the primary key of stock feed group, usually is
	 *        {@link java.lang.Long} type.
	 * @return the stock feed group with the primary key provided, else
	 *         <code>null</code> will be return
	 */
	public IStockFeedGroup getStockFeedGroupByPrimaryKey(String entityName, Serializable key);

	/**
	 * Create stock feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param aStockFeedGroup stock feed group instance to be created
	 * @return created instance of stock feed group.
	 */
	public IStockFeedGroup createStockFeedGroup(String entityName, IStockFeedGroup aStockFeedGroup);

	/**
	 * Update stock feed group into persistent storage
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param aStockFeedGroup stock feed group instance to be created
	 * @return updated instance of stock feed group.
	 */
	public IStockFeedGroup updateStockFeedGroup(String entityName, IStockFeedGroup aStockFeedGroup);

	/**
	 * Delete stock feed group
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param aStockFeedGroup stock feed group instance to be created
	 */
	public void deleteStockFeedGroup(String entityName, IStockFeedGroup aStockFeedGroup);

	/**
	 * Retrieve stock feed group using type and subtype provided
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param type the feed group type, which is normally
	 *        <code>STOCK_INDEX</code>
	 * @param subType the feed group sub type, whcih is normally the country
	 *        code
	 * @return instance of stock feed group with criteria provided, else
	 *         <code>null</code> will be returned.
	 */
	public IStockFeedGroup getStockFeedGroupByTypeAndSubType(String entityName, String type, String subType);

	/**
	 * Retrieve stock feed group using type, sub type, and stock type provided
	 * 
	 * @param entityName the persistence entity name of stock feed group, which
	 *        can be actual or staging name.
	 * @param type the feed group type, which is normally
	 *        <code>STOCK_INDEX</code>
	 * @param subType the feed group sub type, which is normally the country
	 *        code
	 * @param stockType stock type, that is 'loan stocks', 'warrants', 'normal
	 *        shares'
	 * @return instance of stock feed group with criteria provided, else
	 *         <code>null</code> will be returned.
	 */
	public IStockFeedGroup getStockFeedGroupByTypeAndSubTypeAndStockType(String entityName, String type,
			String subType, String stockType);

	/**
	 * Retrieve stock feed entry using Ric code provided
	 * 
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param ric RIC(Reuters Identifier Code) code of the stock feed entry.
	 * @return instance of stock feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IStockFeedEntry getStockFeedEntryByRic(String entityName, String ric);

	/**
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param ticker ticker code of the stock
	 * @return instance of stock feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IStockFeedEntry getStockFeedEntryByTicker(String entityName, String ticker);

	/**
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param isinCode ISIN (International Securities Identifying Number) code
	 *        of the stock
	 * @return instance of stock feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IStockFeedEntry getStockFeedEntryByIsinCode(String entityName, String isinCode);
	
	/**
	 * @param entityName the persistence entity name of stock feed entry, which
	 *        can be actual or staging name.
	 * @param stockExchange  (Name of stock exchange) code
	 *        of the stock
	 * @param scriptCode
	 * @return instance of stock feed entry, else <code>null</code> will be
	 *         returned.
	 */
	public IStockFeedEntry getStockFeedEntryByStockExcScri(String entityName, String stockExchange ,String scriptCode);

	 //File Upload
    boolean isPrevFileUploadPending(String stockType) throws StockFeedEntryException;
	List getAllStageStockFeedEntry (String searchBy, String login)throws StockFeedEntryException;
	List getFileMasterList(String searchBy)throws StockFeedEntryException;
	//IStockFeedEntry createStockFeedEntry(String entityName, IStockFeedEntry creditApproval)throws StockFeedEntryException;
	IFileMapperId insertStockFeedEntry(String entityName, IFileMapperId fileId, IStockFeedGroupTrxValue trxValue)
	throws StockFeedEntryException;
	int insertStockFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result , String stockType);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws StockFeedEntryException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws StockFeedEntryException;
	List insertActualStockFeedEntry(String sysId)
	throws StockFeedEntryException;
	IStockFeedGroup insertStockFeedEntry(String entityName, IStockFeedGroup creditApproval)
	throws StockFeedEntryException;
    void updateStockFeedEntryItem(String entityName,List stockFeedEntryList) throws StockFeedEntryException;
    boolean isStockCodeExist(String entityName,List scriptCodeList, String stockType) throws StockFeedEntryException;
    void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException;  //A govind 270811
    //End File Upload
    
    public boolean isExistScriptCode(String entityName,String stockCode) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
