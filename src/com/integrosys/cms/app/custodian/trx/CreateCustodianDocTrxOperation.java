/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/CreateCustodianDocTrxOperation.java,v 1.6 2003/07/10 10:20:36 ravi Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation is responsible for the creation of a custodian doc transaction
 * 
 * @author $Author: ravi $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/07/10 10:20:36 $ Tag: $Name: $
 */
public class CreateCustodianDocTrxOperation extends AbstractCreateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public CreateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_CUSTODIAN_DOC;
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
		DefaultLogger.debug(this, "Start Perform TrxValue: " + anITrxValue);
		ICustodianTrxValue trxValue = createStagingCustodianDoc(anITrxValue);
		trxValue = createActualCustodianDoc(anITrxValue);
		trxValue = createCustodianTransaction(anITrxValue);
		return super.constructITrxResult(anITrxValue, trxValue);
	}

	/**
	 * Update a custodian transaction
	 * @param anITrxValue - ITrxValue
	 * @return ICustodianTrxValue - the custodian specific transaction interface
	 *         created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICustodianTrxValue updateCustodianTransaction(ITrxValue anITrxValue) throws TrxOperationException {
		ICMSTrxValue trxValue = getCMSTrxValue(anITrxValue);
		try {
			trxValue = getTrxManager().updateTransaction(trxValue);
			OBCustodianTrxValue custodianTrxValue = null;
			if (!(trxValue instanceof OBCustodianTrxValue)) {
				custodianTrxValue = new OBCustodianTrxValue(trxValue);
				custodianTrxValue.setStagingCustodianDoc(getCustodianTrxValue(anITrxValue).getStagingCustodianDoc());
				custodianTrxValue.setCustodianDoc(getCustodianTrxValue(anITrxValue).getCustodianDoc());
				return custodianTrxValue;
			}
			return getCustodianTrxValue(trxValue);
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
