/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/CheckerRejectUOMOperation.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public abstract class CheckerRejectUOMOperation extends AbstractUOMTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerRejectUOMOperation() {
		super();
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param aValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue aValue) throws TrxOperationException {
		try {
			IUnitofMeasureTrxValue trxValue = super.getUnitofMeasureTrxValue(aValue);
			trxValue = super.updateTransaction(trxValue);
			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException toe) {
			throw toe;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

}