/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ReadCheckListOperation.java,v 1.7 2006/06/09 09:49:29 lini Exp $
 */
package com.integrosys.cms.app.checklist.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a checklist doc transaction
 * 
 * @author $Author: lini $
 * @version $Revision: 1.7 $
 * @since $Date: 2006/06/09 09:49:29 $ Tag: $Name: $
 */
public class ReadCheckListOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCheckListOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CHECKLIST;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue - the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

			OBCheckListTrxValue newValue = new OBCheckListTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			ICheckList actualCheckList = null;
			ICheckList stagingCheckList = null;
			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				stagingCheckList = getSBStagingCheckListBusManager().getCheckListByID(
						(new Long(stagingRef)).longValue());
			}

			if (actualRef != null) {
				actualCheckList = getSBCheckListBusManager().getCheckListByID((new Long(actualRef)).longValue());
			}
			setApprovalInd(actualCheckList, stagingCheckList);

			if (actualCheckList != null) {
				if (ICMSConstant.STATE_DELETED.equals(actualCheckList.getCheckListStatus())
						&& (ICMSConstant.TRX_TYPE_OBSOLETE_CC_CHECKLIST.equals(trxValue.getTransactionSubType()) || ICMSConstant.TRX_TYPE_OBSOLETE_COL_CHECKLIST
								.equals(trxValue.getTransactionSubType()))) {
					((OBCheckList) actualCheckList).setObsolete(ICMSConstant.TRUE_VALUE);
				}
				DefaultLogger.debug(this, "Setting Actual Checklist Obsolete : true");
			}
			if (stagingCheckList != null) {
				if (ICMSConstant.STATE_DELETED.equals(stagingCheckList.getCheckListStatus())
						&& (ICMSConstant.TRX_TYPE_OBSOLETE_CC_CHECKLIST.equals(trxValue.getTransactionSubType()) || ICMSConstant.TRX_TYPE_OBSOLETE_COL_CHECKLIST
								.equals(trxValue.getTransactionSubType()))) {
					((OBCheckList) stagingCheckList).setObsolete(ICMSConstant.TRUE_VALUE);
				}
				DefaultLogger.debug(this, "Setting Staging Checklist Obsolete : true");
			}

			newValue.setStagingCheckList(stagingCheckList);
			newValue.setCheckList(actualCheckList);
			// Begin of OFFICE

			
			//TODO: Abhijit needs to check 29th Aug,2011 for HDFC
			//java.util.Collection logs = CMSTransactionDAOFactory.getDAO().getTransactionLogs(newValue.getTransactionID());
			//newValue.setTransactionHistoryCollection(logs);

			// commented as not required for this project
			// TODO: moved to fully dependency injection, and use interception
			// to achieve this piece, then can control whether to query routing
			// info.

//			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
//			criteria.setTransactionID((Long.valueOf(newValue.getTransactionID())).longValue());
//			java.util.Collection routeList = CMSTransactionDAOFactory.getDAO().searchNextRouteList(criteria);
//			newValue.setNextRouteCollection(routeList);
			// End of OFFICE

			return newValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex.toString());
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