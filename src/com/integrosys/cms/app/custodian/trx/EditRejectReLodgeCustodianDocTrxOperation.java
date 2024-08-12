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
 * This operation is responsible for the approval of custodian doc relodgement
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/22 02:21:11 $ Tag: $Name: $
 */
public class EditRejectReLodgeCustodianDocTrxOperation extends AbstractUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public EditRejectReLodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_EDIT_REJECT_RELODGE_CUSTODIAN_DOC;
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
