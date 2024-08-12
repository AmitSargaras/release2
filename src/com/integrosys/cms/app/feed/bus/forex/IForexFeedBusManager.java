/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedEntryException;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.batch.forex.OBForex;

/**
 * Skeleton of Forex Business Manager on how to interface with Persistent
 * Storage
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/11
 */
public interface IForexFeedBusManager {

	/**
	 * Gets all the entries in the forex group.
	 * @param id Identifies the forex group.
	 * @return The group containing all the entries.
	 * @throws ForexFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IForexFeedGroup getForexFeedGroup(long id) throws ForexFeedGroupException;

	/**
	 * Gets all the entries in the forex group.
	 * @param groupType Identifies the forex group.
	 * @return The group containing all the entries.
	 * @throws ForexFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IForexFeedGroup getForexFeedGroup(String groupType) throws ForexFeedGroupException;

	/**
	 * Creates the forex feed group with all the entries.
	 * @param group The forex feed group to be created.
	 * @return The created forex feed group.
	 * @throws ForexFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IForexFeedGroup createForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException;

	/**
	 * Updates the forex feed group with the entries. This is a replacement
	 * action.
	 * @param group The forex feed group to update with.
	 * @return The updated forex feed group.
	 * @throws ForexFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IForexFeedGroup updateForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException;

	/**
	 * Deletes the forex feed group and all its entries.
	 * @param group The forex feed group to delete with all its entries.
	 * @return The deleted forex feed group.
	 * @throws ForexFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IForexFeedGroup deleteForexFeedGroup(IForexFeedGroup group) throws ForexFeedGroupException;

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
	 * @throws StockIndexFeedEntryException
	 */
	public IForexFeedGroup updateToWorkingCopy(IForexFeedGroup workingCopy, IForexFeedGroup imageCopy)
			throws ForexFeedGroupException;
	
	
	//For File Upload add by govind on 16-jun-2011	
	boolean isPrevFileUploadPending() throws ForexFeedEntryException;
	int insertForexFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws ForexFeedEntryException;
	HashMap insertForexFeedEntryAuto(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws ForexFeedEntryException;
	IFileMapperId insertForexFeedEntry(IFileMapperId fileId, IForexFeedGroupTrxValue idxTrxValue)throws ForexFeedEntryException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws ForexFeedEntryException;
	IFileMapperId getInsertFileById(long id) throws ForexFeedEntryException,TrxParameterException,TransactionException;
	List getAllStageForexFeedEntry(String searchBy, String login)throws ForexFeedEntryException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws ForexFeedEntryException,TrxParameterException,TransactionException;
	List insertActualForexFeedEntry(String sysId)throws ForexFeedEntryException;
	IForexFeedGroup insertForexFeedEntry(IForexFeedGroup forexFeedEntry)throws ForexFeedEntryException;
	void updateForexFeedEntryExchangeRate(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException;
	void updateForexFeedEntryExchangeRateAuto(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException;
	boolean isCurrencyCodeExist(List currencyCodeList) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public BigDecimal getExchangeRateWithINR (String currencyCode) throws ForexFeedGroupException;
	
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ForexFeedGroupException, TrxParameterException, TransactionException; 
	
	public OBForex retriveCurrency(String currencyCode) throws ForexFeedGroupException;

}
