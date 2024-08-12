/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/stock/IStockFeedProxy.java,v 1.6 2005/07/29 03:30:32 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.stock;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/29 03:30:32 $ Tag: $Name: $
 */
public interface IStockFeedProxy extends java.io.Serializable {
	/**
	 * Get actual stock feed price given the stock exchange.
	 * 
	 * @param subType stock exchange
	 * @return stock feed group
	 * @throws StockFeedGroupException on any errors encountered
	 */
	public IStockFeedGroup getActualStockFeedGroup(String subType) throws StockFeedGroupException;

	/**
	 * Gets the one and only stock feed group.
	 * @param subType Identifies the subtype of the stock feed group.
	 * @return The stock feed group.
	 * @throws com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException on
	 *         errors.
	 */
	IStockFeedGroupTrxValue getStockFeedGroup(String subType, String stockType) throws StockFeedGroupException;

	/**
	 * Get the transaction value containing StockFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IStockFeedGroupTrxValue getStockFeedGroup(long groupID) throws StockFeedGroupException;

	/**
	 * Get the transaction value containing StockFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IStockFeedGroupTrxValue
	 */
	IStockFeedGroupTrxValue getStockFeedGroupByTrxID(long trxID) throws StockFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue makerUpdateStockFeedGroup(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue,
			IStockFeedGroup aFeedGroup) throws StockFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue makerSubmitStockFeedGroup(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue,
			IStockFeedGroup aFeedGroup) throws StockFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws StockFeedGroupException
	 */
	IStockFeedGroupTrxValue makerSubmitRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateStockFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue makerUpdateRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException;

	/**
	 * Cancels an initiated transaction on a StockFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue makerCloseRejectedStockFeedGroup(ITrxContext anITrxContext,
			IStockFeedGroupTrxValue aTrxValue) throws StockFeedGroupException;

	/**
	 * Cancels an initiated transaction on a StockFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue makerCloseDraftStockFeedGroup(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue)
			throws StockFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */
	IStockFeedGroupTrxValue checkerApproveStockFeedGroup(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue)
			throws StockFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the StockFeedGroup object
	 */

	IStockFeedGroupTrxValue checkerRejectStockFeedGroup(ITrxContext anITrxContext, IStockFeedGroupTrxValue aTrxValue)
			throws StockFeedGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws StockFeedEntryException
	 */
	IStockFeedEntry getStockFeedEntry(String condition, int type) throws StockFeedEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";
	
	
	IStockFeedEntry getStockFeedEntryStockExc(String stockExchange, String scriptCode) throws StockFeedEntryException;
	
	//For File Upload,Add on 25-Aug-2011
	public boolean isPrevFileUploadPending(String stockType) throws StockFeedEntryException,TrxParameterException,TransactionException;
 	public IStockFeedGroupTrxValue makerInsertMapperStockFeedEntry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID,String stockType)throws StockFeedGroupException,TrxParameterException,TransactionException;
	public int insertStockFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList , String stockType) throws StockFeedEntryException,TrxParameterException,TransactionException;
	public IStockFeedGroupTrxValue getInsertFileByTrxID(String aTrxID) throws StockFeedGroupException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws StockFeedEntryException,TrxParameterException,TransactionException;
	public IStockFeedGroupTrxValue checkerApproveInsertStockFeedEntry(ITrxContext anITrxContext, IStockFeedGroupTrxValue anIStockFeedGroupTrxValue) throws StockFeedGroupException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws StockFeedEntryException,TrxParameterException,TransactionException;
	public List insertActualStockFeedEntry(String sysId) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IStockFeedGroupTrxValue checkerCreateStockFeedEntry(ITrxContext anITrxContext,IStockFeedGroup anICCStockFeedEntry, String refStage)throws StockFeedGroupException,TrxParameterException,TransactionException;
	public IStockFeedGroupTrxValue checkerRejectInsertStockFeedEntry(ITrxContext anITrxContext, IStockFeedGroupTrxValue anIStockFeedGroupTrxValue) throws StockFeedGroupException,TrxParameterException,TransactionException;
	public IStockFeedGroupTrxValue makerInsertCloseRejectedStockFeedEntry(ITrxContext anITrxContext, IStockFeedGroupTrxValue anIStockFeedGroupTrxValue) throws StockFeedGroupException,TrxParameterException,TransactionException;
	public void updateStockFeedEntryItem(List stockFeedEntryList) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isStockCodeExist(List scriptCodeList, String stockType) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;  //A govind 270811
	//end File Upload,Add on 25-Aug-2011
	
	public boolean isExistScriptCode(String stockCode) throws StockFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	
}
