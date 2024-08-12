/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/AbstractCustodianTrxOperation.java,v 1.34 2006/04/18 07:56:09 priya Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.Long;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.CustodianDAO;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.bus.OBCustodianDocItem;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManager;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManagerHome;
import com.integrosys.cms.app.eventmonitor.reversalcustodian.ReversalCustodianListener;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of custodian
 * trx operations
 * 
 * @author $Author: priya $
 * @version $Revision: 1.34 $
 * @since $Date: 2006/04/18 07:56:09 $ Tag: $Name: $
 */
public abstract class AbstractCustodianTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Create the staging custodian doc
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the trx object containing the created
	 *         staging custodian doc
	 * @throws TrxOperationException if errors
	 */
	protected ICustodianTrxValue createStagingCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			updateMakerIDAndTrxDate(trxValue);
			ICustodianDoc custDoc = trxValue.getStagingCustodianDoc();
			// for CR34
			ArrayList docItemList = custDoc.getCustodianDocItems();
			boolean isReversal = false;
			if (docItemList != null) {
				for (int i = 0; i < docItemList.size(); i++) {
					ICustodianDocItem item = (ICustodianDocItem) docItemList.get(i);
					String status = item.getStatus();
					/*if (status.equals(ICMSConstant.STATE_LODGED_REVERSAL)
							|| status.equals(ICMSConstant.STATE_TEMP_UPLIFT_REVERSAL)
							|| status.equals(ICMSConstant.STATE_PERM_UPLIFT_REVERSAL)
							|| status.equals(ICMSConstant.STATE_RELODGE_REVERSAL)) { */
                    if (status.equals(ICMSConstant.STATE_CUST_PENDING_lODGE_REVERSAL)
                            || status.equals(ICMSConstant.STATE_CUST_LODGED_REVERSAL)
                            || status.equals(ICMSConstant.STATE_CUST_PENDING_TEMP_UPLIFT_REVERSAL)
                            || status.equals(ICMSConstant.STATE_CUST_PENDING_PERM_UPLIFT_REVERSAL)
                            || status.equals(ICMSConstant.STATE_CUST_PENDING_RELODGE_REVERSAL)){
                        
						isReversal = true;
						break;
					}
				}
			}
			if (isReversal) {
				custDoc.setReversalID(custDoc.getCustodianDocID());
			}
			ICustodianDoc stagingCustDoc = getSBStagingCustodianBusManager().create(custDoc);
			trxValue.setStagingCustodianDoc(stagingCustDoc);
			trxValue.setStagingReferenceID(String.valueOf(stagingCustDoc.getCustodianDocID()));
			return trxValue;
		}
		catch (CustodianException ex) {
			throw new TrxOperationException(ex);
		}
		catch (java.rmi.RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Create the actual custodian doc
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the trx object containing the created actual
	 *         custodian doc
	 * @throws TrxOperationException if errors
	 */
	protected ICustodianTrxValue createActualCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			// get from staging to create actual
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			updateCheckerIDAndTrxDate(trxValue);
			ICustodianDoc custodianDoc = trxValue.getStagingCustodianDoc();
			convertItemPendingStatus(custodianDoc);
			// CR34
			HashMap reversalMap = getReversalMap(custodianDoc);
			ArrayList reverseList = (ArrayList) reversalMap.get("reversalList");
			ArrayList chkListItemRefIDList = (ArrayList) reversalMap.get("chkListItemRefIDList");
			if (reverseList.size() != 0) {
				custodianDoc.setReversalID(custodianDoc.getCustodianDocID());
			}
			custodianDoc = getSBCustodianBusManager().create(custodianDoc);
			trxValue.setCustodianDoc(custodianDoc);
			trxValue.setReferenceID(String.valueOf(custodianDoc.getCustodianDocID()));

            /* commented lines below:
              - 1) the updating of checklist not suitable for alliance
              - 2) alliance doesn't need the send notification function

			// update checklist and send notification
			DefaultLogger.debug(this, "the reverseList size is " + reverseList.size());
			if (reverseList.size() != 0) {
				updateCheckList(custodianDoc, reverseList);
				List revItemList = saveReversedChecklistItems(chkListItemRefIDList, custodianDoc.getReversalRemarks(),
						custodianDoc.getDocSubType());
				sendNotification(revItemList, custodianDoc.getReversalRemarks());
			}
			*/

			return trxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}

	}

	/**
	 * Helper method to convert pending status for all items in a custodian doc
	 * @param custodianDoc - ICustodianDoc
	 */
	private void convertItemPendingStatus(ICustodianDoc custodianDoc) {
		if (custodianDoc == null) {
			return;
		}
		Iterator custodianDocItems = custodianDoc.getCustodianDocItems().iterator();
		while (custodianDocItems.hasNext()) {
			ICustodianDocItem item = (ICustodianDocItem) custodianDocItems.next();
            OBCustodianDocItem.convertPendingStatus(item);
		}
	}

	/**
	 * Create the actual custodian doc
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the trx object containing the created actual
	 *         custodian doc
	 * @throws TrxOperationException if errors
	 */
	protected ICustodianTrxValue updateActualCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			updateCheckerIDAndTrxDate(trxValue);
			ICustodianDoc stagingCustodianDoc = trxValue.getStagingCustodianDoc();
			ICustodianDoc actualCustodianDoc = trxValue.getCustodianDoc();
			reverseLastUpdateDate(stagingCustodianDoc, actualCustodianDoc);
            reverseRelodgeReversalActualStatus(stagingCustodianDoc, actualCustodianDoc);
			ICustodianDoc mergedCustodianDoc = merge(actualCustodianDoc, stagingCustodianDoc);
			convertItemPendingStatus(mergedCustodianDoc);
			// for CR34
			HashMap reversalMap = getReversalMap(mergedCustodianDoc);
            untieDocItemAndEnvelopeBarcode(mergedCustodianDoc);
			ArrayList reverseList = (ArrayList) reversalMap.get("reversalList");
			ArrayList chkListItemRefIDList = (ArrayList) reversalMap.get("chkListItemRefIDList");
			mergedCustodianDoc.setReversalRmkUpdatedUserInfo(stagingCustodianDoc.getReversalRmkUpdatedUserInfo());
			mergedCustodianDoc.setReversalRemarks(stagingCustodianDoc.getReversalRemarks());
			mergedCustodianDoc.setRevRemarksUpdatedBy(stagingCustodianDoc.getRevRemarksUpdatedBy());
			if (reverseList.size() != 0) {
				mergedCustodianDoc.setReversalID(stagingCustodianDoc.getReversalID());
			}

            ICustodianDoc updatedCustodianDoc = getSBCustodianBusManager().update(mergedCustodianDoc);
			trxValue.setCustodianDoc(updatedCustodianDoc);

            /* commented lines below:
              - 1) the updating of checklist not suitable for alliance
              - 2) alliance doesn't need the send notification function
              
			// update checklist and send notification
			DefaultLogger.debug(this, "the reverseList size is " + reverseList.size());
			if (reverseList.size() != 0) {
				updateCheckList(mergedCustodianDoc, reverseList);
				List revItemList = saveReversedChecklistItems(chkListItemRefIDList, mergedCustodianDoc
						.getReversalRemarks(), mergedCustodianDoc.getDocSubType());
				sendNotification(revItemList, mergedCustodianDoc.getReversalRemarks());
			}
            */
			return trxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * reverse last update date
	 * @param stagingDoc
	 * @param actualDoc
	 */
	private void reverseLastUpdateDate(ICustodianDoc stagingDoc, ICustodianDoc actualDoc) {
		ArrayList stagingItemList = stagingDoc.getCustodianDocItems();
		ArrayList actualItemList = actualDoc.getCustodianDocItems();
		if ((actualItemList != null) && (stagingItemList != null)) {// CMSSP-677
																	// -
																	// Modified
																	// the IF
																	// condition
																	// -
																	// Transaction
																	// Denied
																	// while
																	// custodian
																	// checker
																	// approves
																	// for
																	// 11153307
			for (int i = 0; i < stagingItemList.size(); i++) {
				ICustodianDocItem stagingItem = (ICustodianDocItem) stagingItemList.get(i);
				if (stagingItem.getStatus().indexOf("REVERSAL") != -1) {
					for (int x = 0; x < actualItemList.size(); x++) {
						ICustodianDocItem actualItem = (ICustodianDocItem) actualItemList.get(x);
						if (stagingItem.getCheckListItemRefID() == actualItem.getCheckListItemRefID()) {
							stagingItem.setLastUpdateDate(actualItem.getLastUpdateDate());
							break;
						}
					}
				}
			}
		}
	}

    /**
	 * Reverse to last status either to 'perm_uplifted' or 'temp_uplifted' from relodge_reversal
	 * @param stagingDoc
	 * @param actualDoc
	 */
    private void reverseRelodgeReversalActualStatus(ICustodianDoc stagingDoc, ICustodianDoc actualDoc){
        ArrayList stagingItemList = stagingDoc.getCustodianDocItems();
		ArrayList actualItemList = actualDoc.getCustodianDocItems();
		if ((actualItemList != null) && (stagingItemList != null)) {
			for (int i = 0; i < stagingItemList.size(); i++) {
				ICustodianDocItem stagingItem = (ICustodianDocItem) stagingItemList.get(i);
				if (stagingItem.getStatus().equals(ICMSConstant.STATE_CUST_PENDING_RELODGE_REVERSAL)) {
					for (int x = 0; x < actualItemList.size(); x++) {
						ICustodianDocItem actualItem = (ICustodianDocItem) actualItemList.get(x);
						if (stagingItem.getCheckListItemRefID() == actualItem.getCheckListItemRefID()){
							stagingItem.setStatus(actualItem.getStatus());
							break;
						}
					}
				}
			}
		}
    }

   /**
	* Untie document item and envelope barcode for the following status:
    * a) lodge_reversal, b) temp_uplifted, c)  perm_uplifted
	* @param mergedCustodianDoc
	*/
    public void untieDocItemAndEnvelopeBarcode(ICustodianDoc mergedCustodianDoc){
        ArrayList mergedCustDocItemList = mergedCustodianDoc.getCustodianDocItems();
        if((mergedCustDocItemList != null)){
            for (int i = 0; i < mergedCustDocItemList.size(); i++) {
                ICustodianDocItem mergeCustDocItem = (ICustodianDocItem) mergedCustDocItemList.get(i);
//                if (mergeCustDocItem.getStatus().equals(ICMSConstant.STATE_PENDING_LODGE)
//                        || mergeCustDocItem.getStatus().equals(ICMSConstant.STATE_TEMP_UPLIFTED)
//                        || mergeCustDocItem.getStatus().equals(ICMSConstant.STATE_PERM_UPLIFTED)){
                if (mergeCustDocItem.getStatus().equals(ICMSConstant.STATE_PENDING_LODGE)) {
                    mergeCustDocItem.setCustodianDocItemBarcode(null);
                    mergeCustDocItem.setSecEnvelopeBarcode(null);
                    mergedCustDocItemList.set(i,mergeCustDocItem);
                }
            }
        }
        mergedCustodianDoc.setCustodianDocItems(mergedCustDocItemList);
    }

	/**
	 * get reversal list
	 * @param custDoc
	 * @return
	 */
	private HashMap getReversalMap(ICustodianDoc custDoc) {
		ArrayList docItemList = custDoc.getCustodianDocItems();
		HashMap reversalMap = new HashMap();
		ArrayList reverseList = new ArrayList();
		ArrayList chkListItemRefIDList = new ArrayList();
		if (docItemList != null) {
			for (int i = 0; i < docItemList.size(); i++) {
				ICustodianDocItem item = (ICustodianDocItem) docItemList.get(i);
				String status = item.getStatus();
				//if (status.equals(ICMSConstant.STATE_LODGED_REVERSAL)) {
                  if (status.equals(ICMSConstant.STATE_CUST_PENDING_lODGE_REVERSAL)) {
					item.setStatus(ICMSConstant.STATE_DELETED);
					item.setReversalDate(DateUtil.getDate());
					item.setCheckerTrxDate(null);
					reverseList.add(item);
					chkListItemRefIDList.add(String.valueOf(item.getCheckListItemRefID()));
				}
                else if (status.equals(ICMSConstant.STATE_CUST_LODGED_REVERSAL)) {
					item.setStatus(ICMSConstant.STATE_PENDING_LODGE);  
					item.setReversalDate(DateUtil.getDate());
					reverseList.add(item);
					chkListItemRefIDList.add(String.valueOf(item.getCheckListItemRefID()));
				}
                /*
				else if (status.equals(ICMSConstant.STATE_TEMP_UPLIFT_REVERSAL)
						|| status.equals(ICMSConstant.STATE_PERM_UPLIFT_REVERSAL)) {
                */    
                else if (status.equals(ICMSConstant.STATE_CUST_PENDING_TEMP_UPLIFT_REVERSAL)
						|| status.equals(ICMSConstant.STATE_CUST_PENDING_PERM_UPLIFT_REVERSAL)) {					
                    item.setStatus(ICMSConstant.STATE_LODGED);
					item.setReversalDate(DateUtil.getDate());
					reverseList.add(item);
					chkListItemRefIDList.add(String.valueOf(item.getCheckListItemRefID()));
				}
				//else if (status.equals(ICMSConstant.STATE_RELODGE_REVERSAL)) {
                else if (status.equals(ICMSConstant.STATE_CUST_PENDING_RELODGE_REVERSAL)){
					item.setReversalDate(DateUtil.getDate());
					reverseList.add(item);
					chkListItemRefIDList.add(String.valueOf(item.getCheckListItemRefID()));
				}
			}
		}
		reversalMap.put("reversalList", reverseList);
		reversalMap.put("chkListItemRefIDList", chkListItemRefIDList);
		return reversalMap;
	}

	/**
	 * send notification
	 * @param reversalID
	 * @throws CustodianException
	 */

	private void sendNotification(List revItemList, String reversalRemarks) throws CustodianException {
		List params = new ArrayList();
		params.add(revItemList);
		params.add(reversalRemarks);
		try {
			ReversalCustodianListener listener = new ReversalCustodianListener();
			listener.fireEvent(ReversalCustodianListener.EVENT_ID, params);
		}
		catch (EventHandlingException e) {
			throw new CustodianException(e);
		}
	}

	/**
	 * To Save Reversed Checklist Items into a separate table for Reports
	 * retrieval
	 * @param chkListItemRefIDList
	 * @throws CustodianException
	 */

	private List saveReversedChecklistItems(List chkListItemRefIDList, String reversalRemarks, String subType)
			throws CustodianException {
		List revItemList = new ArrayList();
		try {
			CustodianDAO custDAO = new CustodianDAO();
			revItemList = custDAO.getCustodianNotificationInfo(chkListItemRefIDList, subType);
			custDAO.saveReversedChecklistItem(revItemList, reversalRemarks, subType);
			return revItemList;
		}
		catch (SearchDAOException e) {
			throw new CustodianException(e);
		}
	}

    /**
     * update cpc_custodian_status and status with status of cms_cust_doc_item
     * @param anITrxValue
     * @param action
     * @throws CustodianException
     */
    protected void syncCheckListItemCPCStatus(ITrxValue anITrxValue, String action) throws TrxOperationException {
        /**
         * action : create - flow from documentation module to custodian module. Hence the document status & custodian doc status should also inherit from documentation module.
         * action : update - it could be update in custodian module or flow from custodian back to documentation module. No update in document status should allow.
         * */
        ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);

        ICustodianDoc stagingCustodianDoc = trxValue.getStagingCustodianDoc();
        ICustodianDoc actualCustodianDoc = trxValue.getCustodianDoc();

        boolean actionCreate = action.equals("create");

        try {
            SBCheckListBusManager sbCheckListBusMgr = this.getSBCheckListBusManager();
            ICheckList checkList = sbCheckListBusMgr.getCheckListByID(actualCustodianDoc.getCheckListID());
            ICheckListItem[] checklistDocItemList = checkList.getCheckListItemList();

            ArrayList custDocItemList = actualCustodianDoc.getCustodianDocItems();

            for (int i = 0; i < checklistDocItemList.length; i++) {
                ICheckListItem checkListItem = checklistDocItemList[i];

                if (actionCreate) {
                    //#create
                    if (custDocItemList == null) {
                        //not yet lodge to Custodian module but yet revert back to Documentation module
                        custDocItemList = stagingCustodianDoc.getCustodianDocItems();

                        for (int j = 0; j < custDocItemList.size(); j++) {
                            ICustodianDocItem stagingItem = (ICustodianDocItem) custDocItemList.get(j);

                            if (checkListItem.getCheckListItemRef() != stagingItem.getCheckListItemRefID()) continue;
                            
                            if (!stagingItem.getStatus().equals(ICMSConstant.STATE_RECEIVED)) {
                                if (stagingItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                                    checkListItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
                                } else {
                                    checkListItem.setCPCCustodianStatus(stagingItem.getStatus());
                                }
                                checkListItem.setCPCCustodianStatusLastUpdateDate(DateUtil.getDate());
                                checkListItem.setLastUpdateDate(DateUtil.getDate());
                            }
                        }
                    } else {
                        for (int j = 0; j < custDocItemList.size(); j++) {
                            ICustodianDocItem actualItem = (ICustodianDocItem) custDocItemList.get(j);

                            if (checkListItem.getCheckListItemRef() != actualItem.getCheckListItemRefID()) continue;

                            if (!actualItem.getStatus().equals(ICMSConstant.STATE_RECEIVED)) {
                                if (actualItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                                    checkListItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
                                } else {
                                    checkListItem.setCPCCustodianStatus(actualItem.getStatus());
                                }
                                checkListItem.setCPCCustodianStatusLastUpdateDate(DateUtil.getDate());
                                checkListItem.setLastUpdateDate(DateUtil.getDate());
                            }
                        }
                    }
                }

                if (!actionCreate) {
                    //#update
                    if (custDocItemList == null) {
                        //revert back to Documentation module                        
                        custDocItemList = stagingCustodianDoc.getCustodianDocItems();

                        for (int x = 0; x < custDocItemList.size(); x++) {
                            ICustodianDocItem stagingItem = (ICustodianDocItem) custDocItemList.get(x);

                            if (checkListItem.getCheckListItemRef() != stagingItem.getCheckListItemRefID()) continue;

                            //boolean needToUpdate = isStagingDiffActual(stagingCustodianDoc, actualCustodianDoc, stagingItem.getCheckListItemRefID());
                            //if(needToUpdate){
                                if (stagingItem.getStatus().equals(ICMSConstant.STATE_CUST_PENDING_lODGE_REVERSAL) ||
                                        stagingItem.getStatus().equals(ICMSConstant.STATE_CUST_PENDING_RELODGE_REVERSAL) ||
                                        stagingItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                                    checkListItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
                                } else {
                                    checkListItem.setCPCCustodianStatus(stagingItem.getStatus());
                                }
                                checkListItem.setCPCCustodianStatusLastUpdateDate(DateUtil.getDate());
                                checkListItem.setLastUpdateDate(DateUtil.getDate());
                            //}
                        }
                    } else {
                        for (int x = 0; x < custDocItemList.size(); x++) {
                            ICustodianDocItem actualItem = (ICustodianDocItem) custDocItemList.get(x);

                            if (checkListItem.getCheckListItemRef() != actualItem.getCheckListItemRefID()) continue;

                            //boolean needToUpdate = isStagingDiffActual(stagingCustodianDoc, actualCustodianDoc, actualItem.getCheckListItemRefID());
                            //if (needToUpdate) {
                                if (actualItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
                                    checkListItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
                                } else {
                                    checkListItem.setCPCCustodianStatus(actualItem.getStatus());
                                }
                                checkListItem.setCPCCustodianStatusLastUpdateDate(DateUtil.getDate());
                                checkListItem.setLastUpdateDate(DateUtil.getDate());
                            //}                            
                        }
                    }

                }

                checklistDocItemList[i] = checkListItem;
            }

            checkList.setCheckListItemList(checklistDocItemList);
            sbCheckListBusMgr.updateCheckList(checkList);
        } catch (Exception e) {
            throw new CustodianException(e);
        }
    }

   /**
     * update cpc_custodian_status with status of cms_cust_doc_item
     * @param anITrxValue
     * @throws CustodianException
    */
    protected void syncCheckListItemCPCStatusCreate(ITrxValue anITrxValue) throws TrxOperationException{
         ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
         ICustodianDoc actualCustodianDoc = trxValue.getStagingCustodianDoc();
         ICustodianDoc stagingCustodianDoc = trxValue.getStagingCustodianDoc();

         SBCheckListBusManager sbCheckListBusMgr = this.getSBCheckListBusManager();
         try {
            ICheckList checkList = sbCheckListBusMgr.getCheckListByID(stagingCustodianDoc.getCheckListID());

            ArrayList custDocItemList = actualCustodianDoc.getCustodianDocItems();
            ICheckListItem[] checklistDocItemList = checkList.getCheckListItemList();

            for(int i = 0; i < checklistDocItemList.length; i++){
                ICheckListItem checkListItem = checklistDocItemList[i];

                for(int j = 0; j < custDocItemList.size(); j++){
                    ICustodianDocItem actualItem = (ICustodianDocItem) custDocItemList.get(j);

                    if(checkListItem.getCheckListItemRef() == actualItem.getCheckListItemRefID()){
                        if (!actualItem.getStatus().equals(ICMSConstant.STATE_RECEIVED)){

                            if (actualItem.getStatus().equals(ICMSConstant.STATE_DELETED)){
                                checkListItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
                                checkListItem.setItemStatus(ICMSConstant.STATE_RECEIVED);
                            }else{
                                checkListItem.setCPCCustodianStatus(actualItem.getStatus());
                                checkListItem.setItemStatus(actualItem.getStatus());
                            }

                            checkListItem.setCPCCustodianStatusLastUpdateDate(DateUtil.getDate());
                            checkListItem.setLastUpdateDate(DateUtil.getDate());
                        }
                    }
                }
                checklistDocItemList[i] = checkListItem;
            }
            checkList.setCheckListItemList(checklistDocItemList);
            sbCheckListBusMgr.updateCheckList(checkList);
         }catch (Exception e) {
			throw new CustodianException(e);
		}
    }

    private boolean isStagingDiffActual(ICustodianDoc stagingCustodianDoc, ICustodianDoc actualCustodianDoc, long checkListItemRefID){
        boolean isDifferent = false;

        ArrayList stagingItemList = stagingCustodianDoc.getCustodianDocItems();
	    ArrayList actualItemList = actualCustodianDoc.getCustodianDocItems();
        String stageStatus = "";
        String actualStatus = "";

        if ((actualItemList != null) && (stagingItemList != null)) {

			for (int i = 0; i < stagingItemList.size(); i++) {
				ICustodianDocItem stagingItem = (ICustodianDocItem) stagingItemList.get(i);
                if (stagingItem.getCheckListItemRefID() == checkListItemRefID){
                    stageStatus = stagingItem.getStatus();
			    }
			}

            for (int i = 0; i < actualItemList.size(); i++) {
				ICustodianDocItem actualItem = (ICustodianDocItem) actualItemList.get(i);
                if (actualItem.getCheckListItemRefID() == checkListItemRefID){
                    actualStatus = actualItem.getStatus();
			    }
			}

            if(!stageStatus.equals(actualStatus)){
                isDifferent = true;
            }
		}
        return isDifferent;
    }


	/**
	 * update check list item if reverse custodian doc item
	 * @param mergedCustodianDoc
	 * @param reverseList
	 * @throws CustodianException
	 */
	private void updateCheckList(ICustodianDoc mergedCustodianDoc, ArrayList reverseList) throws CustodianException {
		ICheckListProxyManager checklistProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		try {
			DefaultLogger
					.debug(this, "the checkListID in mergedCustodianDoc is " + mergedCustodianDoc.getCheckListID());
			ICheckListTrxValue checklistTrxValue = checklistProxy.getCheckList(mergedCustodianDoc.getCheckListID());
			ICheckList checkList = checklistTrxValue.getCheckList();
			ICheckList stagingCheckList = checklistTrxValue.getStagingCheckList();
			checkList = resetCheckList(checkList, reverseList);
			stagingCheckList = resetCheckList(stagingCheckList, reverseList);
			// checklistTrxValue.setCheckList(checkList);
			checklistProxy.directUpdateCheckList(checklistTrxValue);
		}
		catch (Exception e) {
			throw new CustodianException(e);
		}
	}

	/**
	 * reset checklist status
	 * @param checkList
	 * @return
	 */
	private ICheckList resetCheckList(ICheckList checkList, ArrayList reverseList) {
		ICheckListItem[] originalchklistItems = checkList.getCheckListItemList();
		if (originalchklistItems != null) {
			// update checklist item status
			for (int x = 0; x < originalchklistItems.length; x++) {
				ICheckListItem originalItem = originalchklistItems[x];
				for (int y = 0; y < reverseList.size(); y++) {
					ICustodianDocItem reverseItem = (ICustodianDocItem) reverseList.get(y);
					if (originalItem.getCheckListItemRef() == reverseItem.getCheckListItemRefID()) {
						if (reverseItem.getStatus().equals(ICMSConstant.STATE_DELETED)) {
							originalItem.setDocRef("");
							originalItem.setFormNo("");
							originalItem.setDocDate(null);
							originalItem.setExpiryDate(null);
							originalItem.setActionParty("");
							originalItem.setIdentifyDate(null);
							originalItem.setRemarks("");
							originalItem.setItemStatus(ICMSConstant.STATE_AWAITING);
							originalItem.setLastUpdateDate(null);
							originalItem.setCPCCustodianStatus(null);
						}
						else if (reverseItem.getStatus().equals(ICMSConstant.STATE_LODGED)) {
							originalItem.setCPCCustodianStatus(ICMSConstant.STATE_RECEIVED);
							originalItem.setCPCCustodianStatusUpdateDate(originalItem
									.getCPCCustodianStatusLastUpdateDate());
						}
						else if (reverseItem.getStatus().equals(ICMSConstant.STATE_TEMP_UPLIFTED)) {
							originalItem.setCPCCustodianStatus(ICMSConstant.STATE_AUTHZ_TEMP_UPLIFTED);
							originalItem.setCPCCustodianStatusUpdateDate(originalItem
									.getCPCCustodianStatusLastUpdateDate());
						}
						break;
					}
				}
			}
			// update checklist status
			boolean isDeferred = false;
			boolean isWaived = false;
			ArrayList mandatoryList = new ArrayList();
			int mandatoryCount = 0;
			int totalCount = 0;
			for (int i = 0; i < originalchklistItems.length; i++) {
				ICheckListItem originalItem = originalchklistItems[i];
				if (originalItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
					isDeferred = true;
					break;
				}
				else if (originalItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_WAIVED)) {
					isWaived = true;
					break;
				}
				else if (originalItem.getIsMandatoryInd()) {
					mandatoryList.add(originalItem);
				}
				else if (originalItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_COMPLETED)
						|| originalItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)
						|| originalItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_RENEWED)) {
					totalCount++;
				}
			}
			// check mandatory list
			for (int i = 0; i < mandatoryList.size(); i++) {
				ICheckListItem mandatoryItem = (ICheckListItem) mandatoryList.get(i);
				if (mandatoryItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_COMPLETED)
						|| mandatoryItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)
						|| mandatoryItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_RENEWED)) {
					mandatoryCount++;
				}
			}
			DefaultLogger.debug(this, "the originalchklistItems size is -------- " + originalchklistItems.length);
			DefaultLogger.debug(this, "the mandatoryList size is ------ " + mandatoryList.size());
			DefaultLogger.debug(this, "the mandatoryCount is ------ " + mandatoryCount);
			DefaultLogger.debug(this, "the totalCount is ------ " + totalCount);
			if (!ICMSConstant.STATE_CHECKLIST_DELETED.equals(checkList.getCheckListStatus())
					&& !ICMSConstant.STATE_CHECKLIST_OBSOLETE.equals(checkList.getCheckListStatus())) {
				if (isDeferred) {
					checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_DEFERRED);
				}
				else if (isWaived) {
					checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_WAIVED);
				}
				else if ((mandatoryList.size() == 0)
						|| ((mandatoryCount != 0) && (mandatoryCount == mandatoryList.size()))) {
					checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED);
				}
				else if ((totalCount != 0) && (totalCount == originalchklistItems.length)) {
					checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_COMPLETED);
				}
				else {
					checkList.setCheckListStatus(ICMSConstant.STATE_CHECKLIST_IN_PROGRESS);
				}
			}
		}
		return checkList;
	}

	/**
	 * Helper method to merge staging custodian doc into actual custodian doc
	 * @param actualCustodianDoc - ICustodianDoc
	 * @param stagingCustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - merged custodian doc
	 * @throws Exception if errors
	 */
	protected ICustodianDoc merge(ICustodianDoc actualCustodianDoc, ICustodianDoc stagingCustodianDoc) throws Exception {
		String[] EXCLUDE_METHOD_ITEM = new String[] { "getCustodianDocItemID", "getCustodianDocID" };
        String[] EXCLUDE_METHOD_ITEM_BARCODE = new String[] {"getCustodianDocItemID", "getCustodianDocID", "getSecEnvelopeBarcode", "getCustodianDocItemBarcode"};

		// no need to merge staging custodian doc into actual custodian doc
		// merge staging custodian doc items into actual custodian doc items
		ArrayList stagingCustodianDocItemList = stagingCustodianDoc.getCustodianDocItems();
		ArrayList actualCustodianDocItemList = actualCustodianDoc.getCustodianDocItems();

		HashMap actualMap = new HashMap();
		if (actualCustodianDocItemList != null) {
			for (int j = 0; j < actualCustodianDocItemList.size(); j++) {
				ICustodianDocItem actualCustodianDocItem = (ICustodianDocItem) actualCustodianDocItemList.get(j);
				actualMap.put(new Long(actualCustodianDocItem.getCheckListItemRefID()), actualCustodianDocItem);
			}
		}

		Iterator iterator = stagingCustodianDocItemList.iterator();
		ICustodianDocItem stagingDocItem = null;
		ICustodianDocItem actualDocItem = null;
		while (iterator.hasNext()) {
			stagingDocItem = (ICustodianDocItem) iterator.next();
			long refID = stagingDocItem.getCheckListItemRefID();
			actualDocItem = (ICustodianDocItem) actualMap.get(new Long(refID));

			// check if staging item exists in actual doc
			if (actualDocItem != null) {
				// item exists - update actual item
                if(stagingDocItem.getStatus().equals(ICMSConstant.STATE_CUST_PENDING_TEMP_UPLIFT_REVERSAL) || stagingDocItem.getStatus().equals(ICMSConstant.STATE_CUST_PENDING_PERM_UPLIFT_REVERSAL))
				    AccessorUtil.copyValue(stagingDocItem, actualDocItem, EXCLUDE_METHOD_ITEM_BARCODE);
                else{
                    AccessorUtil.copyValue(stagingDocItem, actualDocItem, EXCLUDE_METHOD_ITEM);
                }
			}
			else {
				// new item - add new actual item
				stagingDocItem.setCustodianDocID(actualCustodianDoc.getCustodianDocID());
				actualCustodianDoc.addCustodianDocItem(stagingDocItem);
			}
		}

		return actualCustodianDoc;
	}

	/**
	 * Create a Custodian Doc transaction
	 * 
	 * @param aTrxValue - ITrxValue
	 * @return ICustodianTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected ICustodianTrxValue createCustodianDocTransaction(ICustodianTrxValue aTrxValue)
			throws TrxOperationException {
		try {
			aTrxValue = prepareTrxValue(aTrxValue);
			ICMSTrxValue tempValue = super.createTransaction(aTrxValue);
			OBCustodianTrxValue newValue = new OBCustodianTrxValue(tempValue);
			newValue.setCustodianDoc(aTrxValue.getCustodianDoc());
			newValue.setStagingCustodianDoc(aTrxValue.getStagingCustodianDoc());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update a Custodian Doc transaction
	 * 
	 * @param aTrxValue - ITrxValue
	 * @return ICustodianTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected ICustodianTrxValue updateCustodianDocTransaction(ICustodianTrxValue aTrxValue)
			throws TrxOperationException {
		try {
			aTrxValue = prepareTrxValue(aTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(aTrxValue);
			OBCustodianTrxValue newValue = new OBCustodianTrxValue(tempValue);
			newValue.setCustodianDoc(aTrxValue.getCustodianDoc());
			newValue.setStagingCustodianDoc(aTrxValue.getStagingCustodianDoc());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Prepares a trx object. Updates the trx object with the correct actual and
	 * staging reference id
	 * 
	 * @param aTrxValue is of type ICustodianTrxValue
	 * @return ICustodianTrxValue
	 */
	protected ICustodianTrxValue prepareTrxValue(ICustodianTrxValue aTrxValue) {
		if (aTrxValue != null) {
			ICustodianDoc actualValue = aTrxValue.getCustodianDoc();
			ICustodianDoc stagingValue = aTrxValue.getStagingCustodianDoc();
			String referenceID = (actualValue != null) ? String.valueOf(actualValue.getCustodianDocID()) : null;
			aTrxValue.setReferenceID(referenceID);
			String stagingReferenceID = (stagingValue != null) ? String.valueOf(stagingValue.getCustodianDocID())
					: null;
			aTrxValue.setStagingReferenceID(stagingReferenceID);
			return aTrxValue;
		}
		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a custodian specific
	 * trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the custodian specific trx value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICustodianTrxValue getCustodianTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICustodianTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCustodianTrxValue: " + cex.toString());
		}
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param aTrxValue is of type ICustodianDocTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICustodianTrxValue aTrxValue) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(aTrxValue);
		return result;
	}

	/**
	 * To get the remote handler for the Custodian Doc session bean
	 * @return SBCustodianBusManager - the home handler for the custodian doc
	 *         entity bean
	 */
	protected SBCustodianBusManager getSBCustodianBusManager() {
		SBCustodianBusManager remote = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the Staging Custodian Doc session bean
	 * @return SBCustodianBusManager - the home handler for the staging
	 *         custodian doc entity bean
	 */
	protected SBCustodianBusManager getSBStagingCustodianBusManager() {
		SBCustodianBusManager remote = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());
		return remote;
	}

    /**
	 * To get the remote handler for the checklist session bean
	 * @return SBCheckListBusManager - the remote handler for the checklist
	 *         session bean
	 */
	protected SBCheckListBusManager getSBCheckListBusManager() {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Helper method to update staging item with the maker id with the user id
	 * in the trxvalue if the item status has changed.
	 * @param trxValue - ICustodianDocTrxValue
	 */
	private void updateMakerIDAndTrxDate(ICustodianTrxValue trxValue) {
		// iterate thru all the staging items
		// update the item maker id with the user id in the trxValue
		List stagingItems = trxValue.getStagingCustodianDoc().getCustodianDocItems();
		if (stagingItems != null) {
			Iterator itemsIterator = stagingItems.iterator();
			ICustodianDocItem item = null;
			while (itemsIterator.hasNext()) {
				item = (ICustodianDocItem) itemsIterator.next();
				DefaultLogger.debug(this, "updateMkIDAndTrxDate : staging ref - " + item.getCheckListItemRefID()
						+ " status - " + item.getStatus());
				if (item.isStatusChanged()) {
					DefaultLogger.debug(this,
							"updateMkIDAndTrxDate : set maker id and trx for staging ref : makerid - "
									+ trxValue.getUID() + " trxDate - " + trxValue.getTransactionDate());
					item.setMakerID(trxValue.getUID());
					item.setMakerTrxDate(trxValue.getTransactionDate());

					// cms-2183 : only items changed are to be timestamped.
					// no need to timestamp when maker submit
					// timestamped in ui
				}
			}
		}
	}

	/**
	 * Helper method to update staging item with the checker id with the user id
	 * in the trxvalue if the item status has changed.
	 * @param trxValue - ICustodianDocTrxValue
	 */
	private void updateCheckerIDAndTrxDate(ICustodianTrxValue trxValue) {
		List stagingItems = trxValue.getStagingCustodianDoc().getCustodianDocItems();
		// need to compare the staging item against the actual item
		if (stagingItems != null) {
			// iterate thru staging list to find status change
			Iterator stagingItemsIterator = stagingItems.iterator();
			ICustodianDocItem stagingItem = null;
			while (stagingItemsIterator.hasNext()) {
				stagingItem = (ICustodianDocItem) stagingItemsIterator.next();
				DefaultLogger.debug(this, "updateChkerIDAndTrxDate : staging ref - "
						+ stagingItem.getCheckListItemRefID() + " status - " + stagingItem.getStatus());
				if (stagingItem.isStatusChanged()) {
					DefaultLogger.debug(this,
							"updateChkerIDAndTrxDate : set checker id and trx for staging ref : checkerid - "
									+ trxValue.getUID() + " trxDate - " + trxValue.getTransactionDate());
					stagingItem.setCheckerID(trxValue.getUID());
					stagingItem.setCheckerTrxDate(trxValue.getTransactionDate());

					// cms-2183 : only items changed are to be timestamped.
					// done in trx by comparing staging and actual.
					stagingItem.setLastUpdateDate(trxValue.getTransactionDate());
				}
			}
		}
	}
}