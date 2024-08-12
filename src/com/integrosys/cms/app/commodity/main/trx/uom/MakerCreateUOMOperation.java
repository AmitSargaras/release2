/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/MakerCreateUOMOperation.java,v 1.3 2004/11/03 09:07:54 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/11/03 09:07:54 $ Tag: $Name: $
 */
public class MakerCreateUOMOperation extends AbstractUOMTrxOperation {
	/**
	 * Default Constructor
	 */
	public MakerCreateUOMOperation() {
		super();
	}

	/**
	 * Get the name of the current operation
	 * 
	 * @return String - the name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * 
	 * @param aValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         any error during the processing of the transaction is encountered
	 */
	public ITrxResult performProcess(ITrxValue aValue) throws TrxOperationException {
		IUnitofMeasureTrxValue trxValue = super.getUnitofMeasureTrxValue(aValue);
		trxValue = super.createStaging(trxValue);

		if (aValue.getTransactionID() == null) {
			trxValue = super.createTransaction(trxValue);
		}
		else {
			trxValue = super.updateTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}

}