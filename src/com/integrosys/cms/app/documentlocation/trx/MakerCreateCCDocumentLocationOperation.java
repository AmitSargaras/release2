/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/MakerCreateCCDocumentLocationOperation.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending CC document location
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public class MakerCreateCCDocumentLocationOperation extends AbstractCCDocumentLocationTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCCDocumentLocationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_CC_DOC_LOC;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICCDocumentLocationTrxValue trxValue = getCCDocumentLocationTrxValue(anITrxValue);
		ICCDocumentLocation staging = trxValue.getStagingCCDocumentLocation();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = null;
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getLimitProfileID()),
							ICMSConstant.INSTANCE_LIMIT_PROFILE);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				else if (staging.getCustomerID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getCustomerID()),
							ICMSConstant.INSTANCE_CUSTOMER);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				return trxValue;
			}
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
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCDocumentLocationTrxValue trxValue = super.getCCDocumentLocationTrxValue(anITrxValue);
		trxValue = createStagingCCDocumentLocation(trxValue);
		trxValue = createCCDocumentLocationTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a CC document location transaction
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the CC document location specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICCDocumentLocationTrxValue createCCDocumentLocationTransaction(
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws TrxOperationException {
		try {
			anICCDocumentLocationTrxValue = prepareTrxValue(anICCDocumentLocationTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCDocumentLocationTrxValue);
			OBCCDocumentLocationTrxValue colDocumentLocationTrxValue = new OBCCDocumentLocationTrxValue(trxValue);
			colDocumentLocationTrxValue.setStagingCCDocumentLocation(anICCDocumentLocationTrxValue
					.getStagingCCDocumentLocation());
			colDocumentLocationTrxValue.setCCDocumentLocation(anICCDocumentLocationTrxValue.getCCDocumentLocation());
			return colDocumentLocationTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}