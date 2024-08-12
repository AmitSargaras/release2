/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/CheckerApproveCreateCustodianDocTrxOperation.java,v 1.6 2005/08/11 03:12:14 whuang Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;

/**
 * Checker approve create custodian doc
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/11 03:12:14 $ Tag: $Name: $
 */
public class CheckerApproveCreateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	public CheckerApproveCreateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CUSTODIAN_DOC;
	}

	/**
	 * Process the transaction 1.Create the actual data 2. Create the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICustodianTrxValue trxValue = super.getCustodianTrxValue(anITrxValue);
		// CR34
		ICustodianDoc stagingCustodianDoc = trxValue.getStagingCustodianDoc();
		try {
			ICustodianDoc custodianDoc = super.getSBStagingCustodianBusManager().create(stagingCustodianDoc);
			trxValue.setStagingCustodianDoc(custodianDoc);
			trxValue.setStagingReferenceID(String.valueOf(custodianDoc.getCustodianDocID()));
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
		trxValue = super.createActualCustodianDoc(trxValue);
		trxValue = super.updateCustodianDocTransaction(trxValue);
        //super.syncCheckListItemCPCStatusCreate(trxValue);
        super.syncCheckListItemCPCStatus(trxValue, "create");
		return super.prepareResult(trxValue);
	}
}
