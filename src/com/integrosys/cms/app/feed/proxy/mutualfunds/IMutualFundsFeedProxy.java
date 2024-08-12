/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/bond/IBondFeedProxy.java,v 1.6 2005/07/28 08:02:41 lyng Exp $
 */
package com.integrosys.cms.app.feed.proxy.mutualfunds;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedEntryException;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsFeedProxy extends java.io.Serializable {
	/**
	 * Get feed price for bond.
	 * 
	 * @return IMutualFundsFeedGroup
	 * @throws MutualFundsFeedGroupException on errors retrieving the bond feed
	 */
	public IMutualFundsFeedGroup getActualMutualFundsFeedGroup() throws MutualFundsFeedGroupException;

	/**
	 * Gets the one and only bond feed group.
	 * @return The bond feed group.
	 * @throws com.integrosys.cms.app.feed.bus.bond.MutualFundsFeedGroupException on
	 *         errors.
	 */
	IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroup() throws MutualFundsFeedGroupException;

	/**
	 * Get the transaction value containing MutualFundsFeedGroup This method will
	 * create a transaction if it is not already present, when this module is
	 * first used by user and system is first setup.
	 */
	IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroup(long groupID) throws MutualFundsFeedGroupException;

	/**
	 * Get the transaction value containing MutualFundsFeedGroup by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing IMutualFundsFeedGroupTrxValue
	 */
	IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroupByTrxID(long trxID) throws MutualFundsFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue makerUpdateMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue,
			IMutualFundsFeedGroup aFeedGroup) throws MutualFundsFeedGroupException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue makerSubmitMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue,
			IMutualFundsFeedGroup aFeedGroup) throws MutualFundsFeedGroupException;

	/**
	 * Submit rejected for approval.
	 * @param anITrxContext
	 * @param aTrxValue
	 * @return
	 * @throws MutualFundsFeedGroupException
	 */
	IMutualFundsFeedGroupTrxValue makerSubmitRejectedMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * This is essentially the same as makerUpdateMutualFundsFeedGroup except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue makerUpdateRejectedMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * Cancels an initiated transaction on a MutualFundsFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue makerCloseRejectedMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * Cancels an initiated transaction on a MutualFundsFeedGroup to return it to last
	 * 'EFFECTIVE'
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue makerCloseDraftMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */
	IMutualFundsFeedGroupTrxValue checkerApproveMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the MutualFundsFeedGroup object
	 */

	IMutualFundsFeedGroupTrxValue checkerRejectMutualFundsFeedGroup(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue aTrxValue)
			throws MutualFundsFeedGroupException;

	/**
	 * Gets the feed entry by ric.
	 * @param ric The RIC.
	 * @return The feed entry or <code>null</code>.
	 * @throws MutualFundsFeedEntryException
	 */
	IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String ric) throws MutualFundsFeedEntryException;
	
	public boolean isValidSchemeCode(String schemeCode);

	public IMutualFundsFeedEntry getIMutualFundsFeed (String schemeCode) throws MutualFundsFeedEntryException;
	public static final String NO_FEED_GROUP = "no.feed.group";
	//For File Upload,Add on 30-Aug-2011
	public boolean isPrevFileUploadPending() throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
 	public IMutualFundsFeedGroupTrxValue makerInsertMapperMutualFundsFeedEntry(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	public int insertMutualFundsFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	public IMutualFundsFeedGroupTrxValue getInsertFileByTrxID(String aTrxID) throws MutualFundsFeedGroupException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	public IMutualFundsFeedGroupTrxValue checkerApproveInsertMutualFundsFeedEntry(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws MutualFundsFeedEntryException,TrxParameterException,TransactionException;
	public List insertActualMutualFundsFeedEntry(String sysId) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IMutualFundsFeedGroupTrxValue checkerCreateMutualFundsFeedEntry(ITrxContext anITrxContext,IMutualFundsFeedGroup anICCMutualFundsFeedEntry, String refStage)throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	public IMutualFundsFeedGroupTrxValue checkerRejectInsertMutualFundsFeedEntry(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	public IMutualFundsFeedGroupTrxValue makerInsertCloseRejectedMutualFundsFeedEntry(ITrxContext anITrxContext, IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException;
	public void updateMutualFundsFeedEntryItem(List mutualfundsFeedEntryList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public boolean isMutualFundsCodeExist(List schemeCodeList) throws MutualFundsFeedGroupException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;  //A govind 270811
	//end File Upload,Add on 30-Aug-2011
}
