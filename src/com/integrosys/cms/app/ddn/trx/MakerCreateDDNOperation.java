/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/MakerCreateDDNOperation.java,v 1.3 2005/06/08 06:33:54 htli Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending ddn
 * 
 * @author $Author: htli $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/06/08 06:33:54 $ Tag: $Name: $
 */
public class MakerCreateDDNOperation extends AbstractDDNTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_GENERATE_DDN;
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
		IDDNTrxValue trxValue = getDDNTrxValue(anITrxValue);
		IDDN staging = trxValue.getStagingDDN();
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
		IDDNTrxValue trxValue = super.getDDNTrxValue(anITrxValue);
		trxValue = createStagingDDN(trxValue);
		trxValue = createDDNTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a DDN transaction
	 * @param anIDDNTrxValue of IDDNTrxValue type
	 * @return IDDNTrxValue - the ddn specific transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private IDDNTrxValue createDDNTransaction(IDDNTrxValue anIDDNTrxValue) throws TrxOperationException {
		try {
			anIDDNTrxValue = prepareTrxValue(anIDDNTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIDDNTrxValue);
			OBDDNTrxValue ddnTrxValue = new OBDDNTrxValue(trxValue);
			ddnTrxValue.setStagingDDN(anIDDNTrxValue.getStagingDDN());
			ddnTrxValue.setDDN(anIDDNTrxValue.getDDN());
			return ddnTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}