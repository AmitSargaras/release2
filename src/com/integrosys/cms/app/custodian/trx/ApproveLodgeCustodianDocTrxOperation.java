/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ApproveLodgeCustodianDocTrxOperation.java,v 1.7 2005/05/11 06:05:19 kchua Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.CustodianException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the approval of custodian doc lodgement
 * 
 * @author $Author: kchua $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/05/11 06:05:19 $ Tag: $Name: $
 */
public class ApproveLodgeCustodianDocTrxOperation extends AbstractUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public ApproveLodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_APPROVE_LODGE_CUSTODIAN_DOC;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * checklist transaction ID to be appended as trx parent ref
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		ICustodianTrxValue trxValue = getCustodianTrxValue(value);
		ICustodianDoc custDoc = trxValue.getStagingCustodianDoc(); // get from
																	// staging
		long checkListID = custDoc.getCheckListID();
		if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == checkListID) {
			throw new TrxOperationException("CheckList ID is undefined in ICustodianDoc: " + checkListID);
		}
		try {
			ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(checkListID),
					ICMSConstant.INSTANCE_CHECKLIST);
			trxValue.setTrxReferenceID(parentTrx.getTransactionID());
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging record 2. Create the actual
	 * record 2. Create the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "Before Perform TrxValue: " + anITrxValue);
		ICustodianTrxValue trxValue = createCustodianDoc(anITrxValue);
		trxValue = updateCustodianTransaction(anITrxValue);
		return super.constructITrxResult(anITrxValue, trxValue);
	}

	/**
	 * Invoke the custodian service provider to create a staging custodian doc
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the custodian specific trx object containing
	 *         the staging object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICustodianTrxValue createCustodianDoc(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			ICustodianTrxValue trxValue = getCustodianTrxValue(anITrxValue);
			ICustodianDoc custDoc = trxValue.getStagingCustodianDoc();

			// CR34 - No longer relevant. There is now one custodian trx for all
			// items belonging to one checklist.
			// custDoc.setStatus(trxValue.getToState());

			custDoc.setLastUpdateDate(DateUtil.getDate()); // bernard - added
															// for CMS-1394

			// Create staging
			/*
			 * ICustodianDoc stagingCustDoc =
			 * getSBStagingCustodianBusManager().create(custDoc);
			 * trxValue.setStagingCustodianDoc(stagingCustDoc);
			 * trxValue.setStagingReferenceID
			 * (String.valueOf(stagingCustDoc.getCustodianDocID()));
			 */

			// Create Actual
			ICustodianDoc actualCustDoc = getSBCustodianBusManager().create(custDoc);
			trxValue.setCustodianDoc(actualCustDoc);
			trxValue.setReferenceID(String.valueOf(actualCustDoc.getCustodianDocID()));

			DefaultLogger.debug(this, "Previous State: " + trxValue.getPreviousState());
			DefaultLogger.debug(this, "Instance: " + trxValue.getInstanceName());
			return trxValue;
		}
		catch (CustodianException cex) {
			throw new TrxOperationException(cex);
		}
		catch (java.rmi.RemoteException rex) {
			throw new TrxOperationException(rex.toString());
		}
	}
}
