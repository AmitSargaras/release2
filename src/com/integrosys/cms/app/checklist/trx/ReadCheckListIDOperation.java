/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ReadCheckListIDOperation.java,v 1.7 2005/05/27 02:33:37 wltan Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//java
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a checklist trx based on a
 * checklist ID
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/05/27 02:33:37 $ Tag: $Name: $
 */
public class ReadCheckListIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCheckListIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CHECKLIST_ID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(val);
			trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					trxValue.getTransactionType());
			//DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>trxValue id is " + trxValue.getTransactionID());
			OBCheckListTrxValue newValue = new OBCheckListTrxValue(trxValue);

			// get actual checklist
			long actualRef = (trxValue.getReferenceID() != null) ? Long.parseLong(trxValue.getReferenceID())
					: ICMSConstant.LONG_INVALID_VALUE;
			ICheckList actualCheckList = null;
			if (actualRef != ICMSConstant.LONG_INVALID_VALUE) {
				actualCheckList = getSBCheckListBusManager().getCheckListByID(actualRef);
				
				
				//HDFC Changes for sorting according to date:
				ArrayList items = new ArrayList(); 
				TreeMap treeMap= new TreeMap();
				long incr=0;
				if(actualCheckList.getCheckListItemList()!=null){
				
					if("REC".equals(actualCheckList.getCheckListType().trim())){
						for(int i=0;i<actualCheckList.getCheckListItemList().length;i++){
							ICheckListItem checkListItem=actualCheckList.getCheckListItemList()[i];
							if(!"STOCK_STATEMENT".equals(checkListItem.getStatementType())){
								if(checkListItem.getDocDate()!=null){
									DefaultLogger.debug(this, "checkListItem.getCheckListItemID() for casecreation: " + checkListItem.getCheckListItemID()+"String.valueOf(checkListItem.getDocDate().getTime())"+String.valueOf(checkListItem.getDocDate().getTime()));
									if(treeMap.containsKey(String.valueOf(checkListItem.getDocDate().getTime()))){
										incr++;
										treeMap.put(String.valueOf(checkListItem.getDocDate().getTime()+incr), checkListItem);
									}else{
									
										treeMap.put(String.valueOf(checkListItem.getDocDate().getTime()), checkListItem);
									}
								}else{
									treeMap.put(String.valueOf(checkListItem.getCheckListItemID()), checkListItem);
								}
							}else{
							
							if(checkListItem.getExpiryDate()!=null){
								DefaultLogger.debug(this, "checkListItem.getCheckListItemID() for casecreation: " + checkListItem.getCheckListItemID()+"String.valueOf(checkListItem.getExpiryDate().getTime())"+String.valueOf(checkListItem.getExpiryDate().getTime()));
								if(treeMap.containsKey(String.valueOf(checkListItem.getExpiryDate().getTime()))){
									incr++;
									treeMap.put(String.valueOf(checkListItem.getExpiryDate().getTime()+incr), checkListItem);
								}else{
								
									treeMap.put(String.valueOf(checkListItem.getExpiryDate().getTime()), checkListItem);
								}
							}else{
								treeMap.put(String.valueOf(checkListItem.getCheckListItemID()), checkListItem);
							}
							}
							
						}	
					}else{
					for(int i=0;i<actualCheckList.getCheckListItemList().length;i++){
					ICheckListItem checkListItem=actualCheckList.getCheckListItemList()[i];
						if(checkListItem.getDocDate()!=null){
							DefaultLogger.debug(this, "checkListItem.getCheckListItemID() for casecreation: " + checkListItem.getCheckListItemID()+"String.valueOf(checkListItem.getDocDate().getTime())"+String.valueOf(checkListItem.getDocDate().getTime()));
							if(treeMap.containsKey(String.valueOf(checkListItem.getDocDate().getTime()))){
								incr++;
								treeMap.put(String.valueOf(checkListItem.getDocDate().getTime()+incr), checkListItem);
							}else{
							
								treeMap.put(String.valueOf(checkListItem.getDocDate().getTime()), checkListItem);
							}
						}else{
							treeMap.put(String.valueOf(checkListItem.getCheckListItemID()), checkListItem);
						}
					
					}
					}
				items=new ArrayList(treeMap.values());
				DefaultLogger.debug(this, "actualCheckList.getChecklistId for casecreation: " +actualCheckList.getCheckListID());
				DefaultLogger.debug(this, "actualCheckList.getCheckListItemList().size() for casecreation: " + actualCheckList.getCheckListItemList().length);
				DefaultLogger.debug(this, "items.size() for casecreation: " + items.size());
				actualCheckList.setCheckListItemList((ICheckListItem[])items.toArray(new ICheckListItem[items.size()]));
				}
				
				//DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>actualCheckList.getCheckListID() id is " + actualCheckList.getCheckListID());
			}
			newValue.setCheckList(actualCheckList);

			// get staging checklist
			long stagingRef = (trxValue.getStagingReferenceID() != null) ? Long.parseLong(trxValue
					.getStagingReferenceID()) : ICMSConstant.LONG_INVALID_VALUE;
			ICheckList stagingCheckList = null;
			// To improve performance
			// if trx not ACTIVE, get staging
			// else if trx ACTIVE, deepclone actual for staging.
			if (!ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
				if (stagingRef != ICMSConstant.LONG_INVALID_VALUE) {
					stagingCheckList = getSBStagingCheckListBusManager().getCheckListByID(stagingRef);
				}
			}
			else {
				stagingCheckList = (ICheckList) AccessorUtil.deepClone(newValue.getCheckList());
			}
			newValue.setStagingCheckList(stagingCheckList);
			//DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>newValue.getTransactionID() id is " + newValue.getTransactionID());
			setApprovalInd(actualCheckList, stagingCheckList);
			//DefaultLogger.debug(this, ">>>>>>>after setApproved>>>>>>>>>>>newValue.getTransactionID() id is " + newValue.getTransactionID());
			// TODO : weiling - consider setting the custodian details here
			// instead of in EB to prevent unnecessary db call to get custodian
			// details for both actual and staging

			// get history of comments
			//TODO: Abhijit needs to check this.
			//Collection logs = CMSTransactionDAOFactory.getDAO().getTransactionLogs(newValue.getTransactionID());
			//newValue.setTransactionHistoryCollection(logs);

			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	private void setApprovalInd(ICheckList anActualCheckList, ICheckList aStagingCheckList) {
		if (anActualCheckList == null) {
			ICheckListItem[] actItemList = aStagingCheckList.getCheckListItemList();
			for (int ii = 0; ii < actItemList.length; ii++) {
				actItemList[ii].setIsApprovedInd(false);
			}
			return;
		}

		if (aStagingCheckList != null) {
			ICheckListItem[] stagingItemList = aStagingCheckList.getCheckListItemList();
			ICheckListItem[] actualItemList = anActualCheckList.getCheckListItemList();

			for (int ii = 0; ii < stagingItemList.length; ii++) {
				for (int jj = 0; jj < actualItemList.length; jj++) {
					if (stagingItemList[ii].getCheckListItemRef() == actualItemList[jj].getCheckListItemRef()) {
						if (!stagingItemList[ii].getItemStatus().equals(actualItemList[jj].getItemStatus())) {
							stagingItemList[ii].setIsApprovedInd(false);
						}
						else {
							if ((stagingItemList[ii].getCustodianDocStatus() != null)
									&& (!stagingItemList[ii].getCustodianDocStatus().equals(
											actualItemList[jj].getCustodianDocStatus()))) {
								stagingItemList[ii].setIsApprovedInd(false);
							}
						}
						// To improve performance : break when a match is found
						break;
					}
				}
			}
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * customer data
	 * @return SBCheckListBusManager - the home interface for the staging
	 *         checklist session bean
	 */
	private SBCheckListBusManager getSBStagingCheckListBusManager() throws TransactionException {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCheckListBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCheckListBusManager - the home interface for the checklist
	 *         session bean
	 */
	private SBCheckListBusManager getSBCheckListBusManager() throws TransactionException {
		SBCheckListBusManager remote = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBCheckListBusManager is null!");
	}
}