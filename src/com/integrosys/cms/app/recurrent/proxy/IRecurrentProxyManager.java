package com.integrosys.cms.app.recurrent.proxy;

import java.rmi.RemoteException;
import java.util.Date;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.107 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public interface IRecurrentProxyManager {

	/**
	 * Get the recurrent checklist based on limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckList - the recurrent checklist of the limit
	 *         profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListTrx(long aLimitProfileID, long aSubProfileID)
			throws RecurrentException;
	
	
	/**
	 * Get the recurrent checklist based on limit profile ID
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return IRecurrentCheckList - the recurrent checklist of the limit
	 *         profile
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListTrxByAnnexureId(long aLimitProfileID, long aSubProfileID, String annexureId)
			throws RecurrentException;
	
	/**
	 * Get the recurrent checklist trx by checklist ID
	 * @param aTrxID of long type
	 * @return IRecurrentCheckListTrxValue - the checklist trx value
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue getRecurrentCheckListByTrxID(String aTrxID) throws RecurrentException;

	/**
	 * To return false if there is any pending trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @return boolean - true if there already exist and false otherwise
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public boolean allowRecurrentCheckListTrx(long aLimitProfileID, long aSubProfileID) throws RecurrentException;

	/**
	 * System creation of a recurrent checklist
	 * @param anITrxContext
	 * @param anICheckList
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws RecurrentException
	 */
	public IRecurrentCheckListTrxValue systemCreateCheckList(IRecurrentCheckListTrxValue recTrxVal)
			throws RecurrentException;

	/**
	 * Maker creation of a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCreateCheckList(ITrxContext anITrxContext, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker creation of a recurrent checklist without checker's approval, ie,
	 * directly create a actual copy.
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCreateCheckListWithoutApproval(ITrxContext anITrxContext,
			IRecurrentCheckList anICheckList) throws RecurrentException;

	/**
	 * Maker creation of a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Copy Recurrent Checklist due to BCA Renewal
	 * @param anITrxContext of ITrxContext type
	 * @param anIRecurrentCheckList of ICheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue copyCheckList(ITrxContext anITrxContext,
			IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException;

	/**
	 * Checker approves a recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Checker rejects a recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Maker closes a recurrent checklist trx that has been rejected by the
	 * checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCloseCheckListTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Maker edits a rejected recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedCheckListTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker updates a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker updates a recurrent checklist without checker approval, ie, update
	 * the actual copy.
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateCheckListWithoutApproval(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker save a recurrent checklist
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker updates a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;
	
	
	/**
	 * Maker updates a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerUpdateAnnexureReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Maker save a recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return ICheckListTrxValue - the interface representing the recurrent
	 *         checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerSaveCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * Checker approves a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;
	
	
	/**
	 * Checker approves a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerApproveCheckListAnnexure(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Checker rejects a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectCheckListReceipt(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;
	
	
	/**
	 * Checker rejects a recurrent checklist receipt trx
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue checkerRejectAnnexureCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Maker closes a recurrent checklist receipt trx that has been rejected by
	 * the checker
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerCloseCheckListReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue) throws RecurrentException;

	/**
	 * Maker edits a rejected recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedCheckListReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;
	
	
	/**
	 * Maker edits a rejected recurrent checklist receipt
	 * @param anITrxContext of ITrxContext type
	 * @param anICheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @param anICheckList of IRecurrentCheckList type
	 * @return IRecurrentCheckListTrxValue - the interface representing the
	 *         recurrent checklist trx obj
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue makerEditRejectedAnnexureReceiptTrx(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anICheckListTrxValue, IRecurrentCheckList anICheckList)
			throws RecurrentException;

	/**
	 * To close the recurrent checklist trx
	 * @param anITrxContext of ITrxContext type
	 * @param anIRecurrentCheckListTrxValue of IRecurrentCheckListTrxValue type
	 * @return IRecurrentCheckListTrxValue - the recurrent checklist trx value
	 *         that is being closed
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListTrxValue systemCloseCheckList(ITrxContext anITrxContext,
			IRecurrentCheckListTrxValue anIRecurrentCheckListTrxValue) throws RecurrentException;

	/**
	 * System close recurrent checklist that is under a limit profile
	 * @param aLimitProfileID of long type
	 * @param aCustomerID of long type
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public void systemCloseRecurrentCheckList(long aLimitProfileID, long aCustomerID) throws RecurrentException;

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on DAO
	 *         errors
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckListItem[] getRecurrentItemHistory(long anItemReference) throws SearchDAOException,
			RecurrentException;

	public void createDueDateEntries(IRecurrentCheckListItem anItem) throws RecurrentException;

	public void createDueDateEntries(IConvenant anItem) throws RecurrentException;

	// cms 1465
	public Date recomputeDueDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException;

	// cms 1465
	public Date recomputeDate(Date aDate, int aFreq, String aFreqUnit) throws RecurrentException;
	
	public long getRecurrentDocId(long limitProfileId, long subProfileId)throws RecurrentException,RemoteException;
	
	public String getBankingType(long limitProfileId, long subProfileId)throws RecurrentException,RemoteException;
	
	public void insertAnnexures(ILimitProfile limitProfile)throws RecurrentException,RemoteException;
	
// 	Added for DP
	public void createDefaultDueDateEntries(IRecurrentCheckList anICheckList) throws RecurrentException;
}
