/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/IBondFeedProxy.java,v 1.6 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.bond;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.feed.bus.bond.BondFeedEntryException;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/28 08:02:41 $ Tag: $Name: $
 */
public interface IBondFeedProxy extends java.io.Serializable {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IBondFeedGroup
	 * @throws BondFeedGroupException on errors retrieving the bond feed
	 */
	public IBondFeedGroup getActualBondFeedGroup() throws BondFeedGroupException;

	/**
	 * Gets the one and only bond feed group.
	 * @return The bond feed group.
	 * @throws com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException on
	 *         errors.
	 */
	IBondFeedGroupTrxValue getBondFeedGroup() throws BondFeedGroupException;

	/**
	 * Get the transaction value containing BondFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IBondFeedGroupTrxValue getBondFeedGroup(long groupID) throws BondFeedGroupException;

	/**
	 * Get the transaction value containing BondFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IBondFeedGroupTrxValue
	 */
	IBondFeedGroupTrxValue getBondFeedGroupByTrxID(long trxID) throws BondFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue makerUpdateBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue,
			IBondFeedGroup aFeedGroup) throws BondFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue makerSubmitBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue,
			IBondFeedGroup aFeedGroup) throws BondFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws BondFeedGroupException
	 */
	IBondFeedGroupTrxValue makerSubmitRejectedBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateBondFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue makerUpdateRejectedBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * Cancels an initiated transaction on a BondFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue makerCloseRejectedBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * Cancels an initiated transaction on a BondFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue makerCloseDraftBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */
	IBondFeedGroupTrxValue checkerApproveBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the BondFeedGroup object
	 */

	IBondFeedGroupTrxValue checkerRejectBondFeedGroup(ITrxContext anITrxContext, IBondFeedGroupTrxValue aTrxValue)
			throws BondFeedGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws BondFeedEntryException
	 */
	IBondFeedEntry getBondFeedEntryByRic(String ric) throws BondFeedEntryException;
	
	/**
	 * Method added for Marketable Securities(Bond)
	 * 
	 */
	IBondFeedEntry getBondFeedEntry(String bondCode) throws BondFeedEntryException;

	public static final String NO_FEED_GROUP = "no.feed.group";
	
	//For File Upload,Add on 24-aug-2011
	public boolean isPrevFileUploadPending() throws BondFeedEntryException,TrxParameterException,TransactionException;
 	public IBondFeedGroupTrxValue makerInsertMapperBondFeedEntry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws BondFeedGroupException,TrxParameterException,TransactionException;
	public int insertBondFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws BondFeedEntryException,TrxParameterException,TransactionException;
	public IBondFeedGroupTrxValue getInsertFileByTrxID(String aTrxID) throws BondFeedGroupException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws BondFeedEntryException,TrxParameterException,TransactionException;
	public IBondFeedGroupTrxValue checkerApproveInsertBondFeedEntry(ITrxContext anITrxContext, IBondFeedGroupTrxValue anIBondFeedGroupTrxValue) throws BondFeedGroupException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws BondFeedEntryException,TrxParameterException,TransactionException;
	public List insertActualBondFeedEntry(String sysId) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IBondFeedGroupTrxValue checkerCreateBondFeedEntry(ITrxContext anITrxContext,IBondFeedGroup anICCBondFeedEntry, String refStage)throws BondFeedGroupException,TrxParameterException,TransactionException;
	public IBondFeedGroupTrxValue checkerRejectInsertBondFeedEntry(ITrxContext anITrxContext, IBondFeedGroupTrxValue anIBondFeedGroupTrxValue) throws BondFeedGroupException,TrxParameterException,TransactionException;
	public IBondFeedGroupTrxValue makerInsertCloseRejectedBondFeedEntry(ITrxContext anITrxContext, IBondFeedGroupTrxValue anIBondFeedGroupTrxValue) throws BondFeedGroupException,TrxParameterException,TransactionException;
	public void updateBondFeedEntryItem(List bondFeedEntryList) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isBondCodeExist(List bondCodeList) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws  BondFeedGroupException, TrxParameterException, TransactionException;
	//end File Upload,Add on 24-aug-2011
	
	public boolean isExistBondCode(String bondCode) throws BondFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
}
