/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the approval of custodian doc lodgement
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/18 03:34:41 $ Tag: $Name: $
 */
public class EditRejectTempUpliftCustodianDocTrxOperation extends AbstractUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public EditRejectTempUpliftCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_EDIT_REJECT_TEMP_UPLIFT_CUSTODIAN_DOC;
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
		ICustodianTrxValue trxValue = createStagingCustodianDoc(anITrxValue);
		trxValue = updateCustodianTransaction(trxValue);
		return super.constructITrxResult(anITrxValue, trxValue);
	}
}
