/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ApproveUpdateCustodianDocTrxOperation.java,v 1.1 2003/06/23 06:39:40 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the approval of the maintainence of the
 * custodian doc
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 06:39:40 $ Tag: $Name: $
 */
public class ApproveUpdateCustodianDocTrxOperation extends AbstractUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public ApproveUpdateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_APPROVE_UPDATE_CUSTODIAN_DOC;
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
		DefaultLogger.debug(this, "Before Perform TrxValue: " + anITrxValue);
		ICustodianTrxValue trxValue = updateActualCustodianDoc(anITrxValue);
		trxValue = updateCustodianTransaction(anITrxValue);
		// trxValue.setPreviousState(anITrxValue.getPreviousState());
		// trxValue.setInstanceName(anITrxValue.getInstanceName());
		// DefaultLogger.debug(this, "After Perform TrxValue: " + trxValue);
		// OBCMSTrxResult result = new OBCMSTrxResult();
		// result.setTrxValue(trxValue);
		// return result;
		return super.constructITrxResult(anITrxValue, trxValue);
	}
}
