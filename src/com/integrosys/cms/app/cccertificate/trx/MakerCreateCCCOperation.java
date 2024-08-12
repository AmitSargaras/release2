/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/MakerCreateCCCOperation.java,v 1.3 2003/09/20 09:22:12 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/20 09:22:12 $ Tag: $Name: $
 */
public class MakerCreateCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_GENERATE_CCC;
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
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anITrxValue);
		ICCCertificate staging = trxValue.getStagingCCCertificate();
		try {
			if (staging != null) {
				ICMSTrxValue parentTrx = null;
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getLimitProfileID()),
							ICMSConstant.INSTANCE_LIMIT_PROFILE);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				else if (staging.getSubProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getSubProfileID()),
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
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCCertificateTrxValue trxValue = super.getCCCertificateTrxValue(anITrxValue);
		trxValue = createStagingCCCertificate(trxValue);
		trxValue = createCCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anICCCertificateTrxValue - ICCCertificateTrxValue
	 * @return ICCCertificateTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICCCertificateTrxValue createCCCertificateTransaction(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws TrxOperationException {
		try {
			anICCCertificateTrxValue = prepareTrxValue(anICCCertificateTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCCertificateTrxValue);
			OBCCCertificateTrxValue cccTrxValue = new OBCCCertificateTrxValue(trxValue);
			cccTrxValue.setStagingCCCertificate(anICCCertificateTrxValue.getStagingCCCertificate());
			cccTrxValue.setCCCertificate(anICCCertificateTrxValue.getCCCertificate());
			return cccTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}