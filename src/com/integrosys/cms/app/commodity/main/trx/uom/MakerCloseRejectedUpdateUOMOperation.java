/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/MakerCloseRejectedUpdateUOMOperation.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public class MakerCloseRejectedUpdateUOMOperation extends AbstractUOMTrxOperation {
	/**
	 * Default Constructor
	 */
	public MakerCloseRejectedUpdateUOMOperation() {
		super();
	}

	/**
	 * Get the name of the current operation
	 * 
	 * @return String - the name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN;
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
		trxValue = super.updateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}