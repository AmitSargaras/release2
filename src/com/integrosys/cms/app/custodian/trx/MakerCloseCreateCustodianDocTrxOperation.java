/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/MakerCloseCreateCustodianDocTrxOperation.java,v 1.4 2005/02/01 05:19:31 whuang Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Maker close create custodian doc
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/02/01 05:19:31 $ Tag: $Name: $
 */
public class MakerCloseCreateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	public MakerCloseCreateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_CREATE_CUSTODIAN_DOC;
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
		trxValue = super.updateCustodianDocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
