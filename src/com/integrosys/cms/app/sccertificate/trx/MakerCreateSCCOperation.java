/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/MakerCreateSCCOperation.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending document item
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class MakerCreateSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_GENERATE_SCC;
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
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anITrxValue);
		ISCCertificate staging = trxValue.getStagingSCCertificate();
		try {
			if (staging != null) {
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
							String.valueOf(staging.getLimitProfileID()), ICMSConstant.INSTANCE_LIMIT_PROFILE);
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
		ISCCertificateTrxValue trxValue = super.getSCCertificateTrxValue(anITrxValue);
		trxValue = createStagingSCCertificate(trxValue);
		trxValue = createSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a document item transaction
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ISCCertificateTrxValue createSCCertificateTransaction(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws TrxOperationException {
		try {
			anISCCertificateTrxValue = prepareTrxValue(anISCCertificateTrxValue);
			ICMSTrxValue trxValue = createTransaction(anISCCertificateTrxValue);
			OBSCCertificateTrxValue sccTrxValue = new OBSCCertificateTrxValue(trxValue);
			sccTrxValue.setStagingSCCertificate(anISCCertificateTrxValue.getStagingSCCertificate());
			sccTrxValue.setSCCertificate(anISCCertificateTrxValue.getSCCertificate());
			return sccTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}