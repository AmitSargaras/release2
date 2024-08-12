/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/MakerUpdateCustodianDocTrxOperation.java,v 1.4 2005/03/10 04:34:34 wltan Exp $
 */

package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Maker update custodian doc
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/03/10 04:34:34 $ Tag: $Name: $
 */
public class MakerUpdateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {
	public MakerUpdateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CUSTODIAN_DOC;
	}

	/**
	 * Process the transaction 1.Create staging custodian doc 2.Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICustodianTrxValue trxValue = super.getCustodianTrxValue(anITrxValue);
		trxValue = super.createStagingCustodianDoc(trxValue);
		trxValue = super.updateCustodianDocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
