/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/MakerCloseUpdateCustodianDocTrxOperation.java,v 1.5 2005/02/22 09:39:50 wltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Maker close update custodian doc
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/02/22 09:39:50 $ Tag: $Name: $
 */
public class MakerCloseUpdateCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	public MakerCloseUpdateCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUSTODIAN_DOC;
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
		trxValue.setStagingCustodianDoc(trxValue.getCustodianDoc());
		trxValue = super.createStagingCustodianDoc(trxValue);
		trxValue = super.updateCustodianDocTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}