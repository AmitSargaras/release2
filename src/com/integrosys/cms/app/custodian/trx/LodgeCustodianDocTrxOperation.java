/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/LodgeCustodianDocTrxOperation.java,v 1.5 2005/05/11 06:04:01 kchua Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the updating of custodian doc transaction
 * 
 * @author $Author: kchua $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/05/11 06:04:01 $ Tag: $Name: $
 */
public class LodgeCustodianDocTrxOperation extends AbstractCreateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public LodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation This will overwrite the
	 * method from the super class
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_LODGE_CUSTODIAN_DOC;
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
	 * Process the transaction 1. Create the staging record 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "Before Perform TrxValue: " + anITrxValue);
		ICustodianTrxValue trxValue = createStagingCustodianDoc(anITrxValue);
		trxValue = createCustodianTransaction(anITrxValue);
		return super.constructITrxResult(anITrxValue, trxValue);
	}
}
