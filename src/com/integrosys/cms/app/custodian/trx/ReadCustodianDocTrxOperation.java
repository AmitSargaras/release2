/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ReadCustodianDocTrxOperation.java,v 1.18 2005/11/11 03:14:52 whuang Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManager;
import com.integrosys.cms.app.custodian.bus.SBCustodianBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a custodian doc transaction
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.18 $
 * @since $Date: 2005/11/11 03:14:52 $ Tag: $Name: $
 */
public class ReadCustodianDocTrxOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CUSTODIAN_DOC;
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
			ICMSTrxValue trxValue = (ICMSTrxValue) super.getTrxManager().getTransaction(val.getTransactionID());
			OBCustodianTrxValue custDocTrxValue = new OBCustodianTrxValue(trxValue);
			// get actual custodian doc
			long custDocID = ICMSConstant.LONG_INVALID_VALUE;
			if ((trxValue.getReferenceID() != null)
					&& (Long.parseLong(trxValue.getReferenceID()) != ICMSConstant.LONG_INVALID_VALUE)) {
				custDocID = Long.parseLong(trxValue.getReferenceID());
				custDocTrxValue.setCustodianDoc(getSBCustodianBusManager().getCustodianDoc(custDocID));
			}
			ICustodianDocItem[] newItems = null;
			if (ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())
					|| ICMSConstant.STATE_REQUIRED.equals(trxValue.getStatus())) {
				newItems = getSBCustodianBusManager().getNewItems(Long.parseLong(trxValue.getStagingReferenceID()));
			}
			if ((custDocTrxValue.getCustodianDoc() != null) && (newItems != null)) {
				DefaultLogger.debug(this, "newItems size: -------------------------------- " + newItems.length);
				addNewItems(custDocTrxValue.getCustodianDoc(), newItems);
			}

			// get staging custodian doc
			ICustodianDoc staging = null;
			if (!ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())
					&& (!ICMSConstant.STATE_REQUIRED.equals(trxValue.getStatus()))) {
				DefaultLogger.debug(this, "if: ------------------------------- ");
				custDocID = Long.parseLong(trxValue.getStagingReferenceID());
				staging = getSBStagingCustodianBusManager().getCustodianDoc(custDocID);
				addNewItems(staging, newItems);
			}
			else if (ICMSConstant.STATE_REQUIRED.equals(trxValue.getStatus())) {
				if ((trxValue.getReferenceID() == null)
						|| ((trxValue.getReferenceID() != null) && (Long.parseLong(trxValue.getReferenceID()) == ICMSConstant.LONG_INVALID_VALUE))) {
					DefaultLogger.debug(this, "else if: ------------------------------- ");
					custDocID = Long.parseLong(trxValue.getStagingReferenceID());
					staging = getSBStagingCustodianBusManager().getCustodianDoc(custDocID);
					addNewItems(staging, newItems);
				}
				else {
					DefaultLogger.debug(this, "inner else: ------------------------------- ");
					staging = (ICustodianDoc) AccessorUtil.deepClone(custDocTrxValue.getCustodianDoc());
					// still set staging reference id into staging custodian doc
					// id
					staging.setCustodianDocID(Long.parseLong(trxValue.getStagingReferenceID()));
					DefaultLogger.debug(this, "staging custodian item size: ------------------------- "
							+ staging.getCustodianDocItems().size());
				}
			}
			else {
				DefaultLogger.debug(this, "else: ------------------------------- ");
				staging = (ICustodianDoc) AccessorUtil.deepClone(custDocTrxValue.getCustodianDoc());
				// still set staging reference id into staging custodian doc id
				staging.setCustodianDocID(Long.parseLong(trxValue.getStagingReferenceID()));
			}
			custDocTrxValue.setStagingCustodianDoc(staging);
			return custDocTrxValue;
		}
		catch (TrxOperationException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Method to get the remote handler for the staging custodian session bean
	 * @return SBCustodianBusManager
	 * @throws TransactionException on errors
	 */
	protected SBCustodianBusManager getSBStagingCustodianBusManager() throws TransactionException {
		SBCustodianBusManager remote = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());

		if (null != remote) {
			return remote;
		}
		throw new TransactionException("SBCustodianBusManager is null!");
	}

	/**
	 * Method to get the remote handler for the custodian session bean
	 * @return SBCustodianBusManager
	 * @throws TransactionException on errors
	 */
	protected SBCustodianBusManager getSBCustodianBusManager() throws TransactionException {
		SBCustodianBusManager remote = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());

		if (null != remote) {
			return remote;
		}
		throw new TransactionException("SBCustodianBusManager is null!");
	}

	/**
	 * Helper method to add new items to custodian doc.
	 * 
	 * @param custDoc - ICustodianDoc
	 * @param newCustDocItems - ICustodianDocItem[]
	 */
	private void addNewItems(ICustodianDoc custDoc, ICustodianDocItem[] newCustDocItems) {
		if ((custDoc == null) || (newCustDocItems == null) || (newCustDocItems.length == 0)) {
			return;
		}

		long checkListItemRefID = ICMSConstant.LONG_INVALID_VALUE;
		ICheckListProxyManager checklistProxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		for (int i = 0; i < newCustDocItems.length; i++) {
			try {
				// DefaultLogger.debug(this, "addNewItems - add item : " +
				// newCustDocItems[i].getCheckListItemRefID());
				ICheckListItem chklistItem = checklistProxy
						.getCheckListItem(newCustDocItems[i].getCheckListItemRefID());
				newCustDocItems[i].setCheckListItem(chklistItem);
				custDoc.addCustodianDocItem(newCustDocItems[i]);
			}
			catch (CheckListException cle) {
				DefaultLogger.error(this, "Checklist item cannot be loaded - ref id : " + checkListItemRefID);
				cle.printStackTrace();
				continue;
			}
		}

	}

}
