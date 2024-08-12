/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/MakerSaveUOMOperation.java,v 1.1 2004/08/13 07:31:05 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/13 07:31:05 $ Tag: $Name: $
 */
public class MakerSaveUOMOperation extends AbstractUOMTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveUOMOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging unit of measure record 2. create/update Transaction
	 * record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IUnitofMeasureTrxValue trxValue = super.getUnitofMeasureTrxValue(value);

			trxValue = super.createStaging(trxValue);

			if (trxValue.getTransactionID() == null) {
				trxValue = (IUnitofMeasureTrxValue) super.createTransaction(trxValue);
			}
			else {
				trxValue = (IUnitofMeasureTrxValue) super.updateTransaction(trxValue);
			}

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
