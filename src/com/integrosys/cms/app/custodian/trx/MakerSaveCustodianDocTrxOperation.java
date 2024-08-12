/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/MakerSaveCustodianDocTrxOperation.java,v 1.3 2005/02/01 05:23:13 whuang Exp $
 */

package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Maker save custodian doc
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/02/01 05:23:13 $ Tag: $Name: $
 */
public class MakerSaveCustodianDocTrxOperation extends AbstractCustodianTrxOperation {

	public MakerSaveCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_CUSTODIAN_DOC;
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
		try {
			ICustodianTrxValue trxValue = super.getCustodianTrxValue(anITrxValue);
			trxValue = super.createStagingCustodianDoc(trxValue);
			if (trxValue.getTransactionID() == null) {
				trxValue = super.createCustodianDocTransaction(trxValue);
			}
			else {
				trxValue = super.updateCustodianDocTransaction(trxValue);
			}
			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception ex) {
			throw new TrxOperationException("Caught Exception", ex);
		}
	}
}
