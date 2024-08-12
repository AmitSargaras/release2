/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/IStockFeedBusManager.java,v 1.3 2003/09/18 07:25:31 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.exception.OFAException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/18 07:25:31 $ Tag: $Name: $
 */
public interface IStockFeedBusManager {

	/**
	 * Gets all the entries in the stock group.
	 * @param id Identifies the stock group.
	 * @return The group containing all the entries.
	 * @throws StockFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IStockFeedGroup getStockFeedGroup(long id) throws StockFeedGroupException;

	/**
	 * Gets all the entries in the stock group.
	 * @param groupType Identifies the stock group.
	 * @param subType Identifies the subtype.
	 * @return The group containing all the entries.
	 * @throws StockFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IStockFeedGroup getStockFeedGroup(String groupType, String subType, String stockType)
			throws StockFeedGroupException;

	/**
	 * Gets all the entries in the stock group.
	 * @param groupType Identifies the stock group.
	 * @param subType Identifies the subtype.
	 * @return The group containing all the entries.
	 * @throws StockFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IStockFeedGroup getStockFeedGroup(String groupType, String subType) throws StockFeedGroupException;

	/**
	 * Creates the stock feed group with all the entries.
	 * @param group The stock feed group to be created.
	 * @return The created stock feed group.
	 * @throws StockFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IStockFeedGroup createStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException;

	/**
	 * Updates the stock feed group with the entries. This is a replacement
	 * action.
	 * @param group The stock feed group to update with.
	 * @return The updated stock feed group.
	 * @throws StockFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IStockFeedGroup updateStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException;

	/**
	 * Deletes the stock feed group and all its entries.
	 * @param group The stock feed group to delete with all its entries.
	 * @return The deleted stock feed group.
	 * @throws StockFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IStockFeedGroup deleteStockFeedGroup(IStockFeedGroup group) throws StockFeedGroupException;

	/**
	 * Gets the stock feed entry by type, ie ric, isin code or ticker code.
	 * 
	 * @param condition the ric code, isin code or ticker code
	 * @param type whether search by ticker, isin or ric code
	 * @return The stock feed entry having the condition passed in or
	 *         <code>null</code>.
	 * @throws StockFeedEntryException on errors.
	 */
	public IStockFeedEntry getStockFeedEntry(String condition, int type) throws StockFeedEntryException;

	/**
	 * Gets all the stock exchanges' information.
	 * @return Array of stock exchanges' info. Can be empty array.
	 * @throws Exception
	 */
	public IStockExchange[] getAllStockExchanges() throws OFAException;

	/**
	 * <p>
	 * Update from a image copy to a working copy, will be used for updating
	 * actual copy using staging copy.
	 * 
	 * <p>
	 * This normally will get called from transaction engine, operation which is
	 * checker approve update.
	 * 
	 * @param workingCopy working copy, which in cms context is actual copy
	 * @param imageCopy image copy, which in cms context is staging copy
	 * @return the updated working copy
	 * @throws StockFeedGroupException
	 */
	public IStockFeedGroup updateToWorkingCopy(IStockFeedGroup workingCopy, IStockFeedGroup imageCopy)
			throws StockFeedGroupException;
	
	/*
	 * This method return StockFeedEntry with respect to 
	 */
	public IStockFeedEntry getStockFeedEntryStockExc(String stockExchange, String scriptCode) throws StockFeedEntryException;
	
	//For File Upload add by govind on 25-Aug-2011	
	boolean isPrevFileUploadPending(String stockType) throws StockFeedEntryException;
	int insertStockFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList , String stockType)throws StockFeedEntryException;
	IFileMapperId insertStockFeedEntry(IFileMapperId fileId, IStockFeedGroupTrxValue idxTrxValue)throws StockFeedEntryException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws StockFeedEntryException;
	IFileMapperId getInsertFileById(long id) throws StockFeedEntryException,TrxParameterException,TransactionException;
	List getAllStageStockFeedEntry(String searchBy, String login)throws StockFeedEntryException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws StockFeedEntryException,TrxParameterException,TransactionException;
	List insertActualStockFeedEntry(String sysId)throws StockFeedEntryException;
	IStockFeedGroup insertStockFeedEntry(IStockFeedGroup bondFeedEntry)throws StockFeedEntryException;
	void updateStockFeedEntryItem(List bondFeedEntryList) throws StockFeedGroupException,TrxParameterException,TransactionException;
	boolean isStockCodeExist(List scriptCodeList, String stockType) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException; //A govind 270811
	//end File Upload add by govind on 25-Aug-2011
	
	public boolean isExistScriptCode(String stockCode) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
