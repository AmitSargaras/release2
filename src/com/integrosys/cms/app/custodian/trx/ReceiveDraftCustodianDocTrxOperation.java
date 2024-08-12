/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ReceiveDraftCustodianDocTrxOperation.java,v 1.2 2003/06/23 06:39:40 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the updating of custodian doc transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/23 06:39:40 $ Tag: $Name: $
 */
public class ReceiveDraftCustodianDocTrxOperation extends AbstractUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public ReceiveDraftCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation To be implemented by the
	 * concrete class
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_RECEIVE_DRAFT_CUSTODIAN_DOC;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "Before Perform TrxValue: " + anITrxValue);
		ICustodianTrxValue trxValue = createStagingCustodianDoc(anITrxValue);
		trxValue = updateCustodianTransaction(anITrxValue);
		return super.constructITrxResult(anITrxValue, trxValue);
	}
}
