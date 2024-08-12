/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/CheckerRejectCreateCustodianDocTrxOperation.java,v 1.5 2005/08/11 08:10:21 whuang Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;

/**
 * Checker reject create custodian doc
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/11 08:10:21 $ Tag: $Name: $
 */
public class CheckerRejectCreateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	public CheckerRejectCreateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_CREATE_CUSTODIAN_DOC;
	}

	/**
	 * Process the transaction 1.Update the transaction record
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
		trxValue = super.updateCustodianDocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
