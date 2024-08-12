/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/IForexFeedProxy.java,v 1.9 2003/09/12 09:24:00 btchng Exp $
 */
package com.integrosys.cms.app.feed.proxy.forex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedEntryException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.forex.OBForex;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 09:24:00 $ Tag: $Name: $
 */
public interface IForexFeedProxy extends java.io.Serializable {

	/**
	 * Gets the one and only forex feed group.
	 * @return The forex feed group.
	 * @throws ForexFeedGroupException on errors.
	 */
	IForexFeedGroupTrxValue getForexFeedGroup() throws ForexFeedGroupException;

	/**
	 * Get the transaction value containing ForexFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IForexFeedGroupTrxValue getForexFeedGroup(long groupID) throws ForexFeedGroupException;

	/**
	 * Get the transaction value containing ForexFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IForexFeedGroupTrxValue
	 */
	IForexFeedGroupTrxValue getForexFeedGroupByTrxID(long trxID) throws ForexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue makerUpdateForexFeedGroup(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue,
			IForexFeedGroup aFeedGroup) throws ForexFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue makerSubmitForexFeedGroup(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue,
			IForexFeedGroup aFeedGroup) throws ForexFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws ForexFeedGroupException
	 */
	IForexFeedGroupTrxValue makerSubmitRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateForexFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue makerUpdateRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a ForexFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue makerCloseRejectedForexFeedGroup(ITrxContext anITrxContext,
			IForexFeedGroupTrxValue aTrxValue) throws ForexFeedGroupException;

	/**
	 * Cancels an initiated transaction on a ForexFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue makerCloseDraftForexFeedGroup(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue)
			throws ForexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */
	IForexFeedGroupTrxValue checkerApproveForexFeedGroup(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue)
			throws ForexFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the ForexFeedGroup object
	 */

	IForexFeedGroupTrxValue checkerRejectForexFeedGroup(ITrxContext anITrxContext, IForexFeedGroupTrxValue aTrxValue)
			throws ForexFeedGroupException;

	public static final String NO_FEED_GROUP = "no.feed.group";
	
	//For File Upload,Add on 16/06/2011
	public boolean isPrevFileUploadPending() throws ForexFeedEntryException,TrxParameterException,TransactionException;
 	public IForexFeedGroupTrxValue makerInsertMapperForexFeedEntry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws ForexFeedGroupException,TrxParameterException,TransactionException;
	public int insertForexFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws ForexFeedEntryException,TrxParameterException,TransactionException;
	public HashMap insertForexFeedEntryAuto(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws ForexFeedEntryException,TrxParameterException,TransactionException;
	public IForexFeedGroupTrxValue getInsertFileByTrxID(String aTrxID) throws ForexFeedGroupException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws ForexFeedEntryException,TrxParameterException,TransactionException;
	public IForexFeedGroupTrxValue checkerApproveInsertForexFeedEntry(ITrxContext anITrxContext, IForexFeedGroupTrxValue anIForexFeedGroupTrxValue) throws ForexFeedGroupException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws ForexFeedEntryException,TrxParameterException,TransactionException;
	public List insertActualForexFeedEntry(String sysId) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IForexFeedGroupTrxValue checkerCreateForexFeedEntry(ITrxContext anITrxContext,IForexFeedGroup anICCForexFeedEntry, String refStage)throws ForexFeedGroupException,TrxParameterException,TransactionException;
	public IForexFeedGroupTrxValue checkerRejectInsertForexFeedEntry(ITrxContext anITrxContext, IForexFeedGroupTrxValue anIForexFeedGroupTrxValue) throws ForexFeedGroupException,TrxParameterException,TransactionException;
	public IForexFeedGroupTrxValue makerInsertCloseRejectedForexFeedEntry(ITrxContext anITrxContext, IForexFeedGroupTrxValue anIForexFeedGroupTrxValue) throws ForexFeedGroupException,TrxParameterException,TransactionException;
	public void updateForexFeedEntryExchangeRate(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public void updateForexFeedEntryExchangeRateAuto(List forexFeedEntryList) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isCurrencyCodeExist(List currencyCodeList) throws ForexFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public BigDecimal getExchangeRateWithINR (String currencyCode) throws ForexFeedGroupException;
		
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws  ForexFeedGroupException, TrxParameterException, TransactionException; 
	
	 public OBForex retriveCurrency (String currencyCode) throws ForexFeedGroupException;

	public IForexFeedGroupTrxValue makerInsertMapperForexFeedEntryAuto(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws ForexFeedGroupException,TrxParameterException,TransactionException;
}
