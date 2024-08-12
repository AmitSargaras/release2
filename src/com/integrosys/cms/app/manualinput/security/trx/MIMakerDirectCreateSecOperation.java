/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.manualinput.security.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * This operation class is invoked by maker to direct create a security without
 * approval.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class MIMakerDirectCreateSecOperation extends AbstractMISecOperation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#getOperationName()
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return TransactionActionConst.ACTION_MAKER_DIRECT_CREATE_SEC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#performProcess
	 * (com.integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			MISecTrxHelper helper = new MISecTrxHelper();
			ICollateralTrxValue colTrxValue = (ICollateralTrxValue) value;
			colTrxValue = helper.createStagingCollateral(colTrxValue);
			colTrxValue = helper.createActualColFromStaging(colTrxValue);

			// remember to create a CMS_SOURCE_SECURITY record where SOURCE_ID =
			// GCMS
			helper.createShareSecForGCMS(Long.parseLong(colTrxValue.getReferenceID()), colTrxValue
					.getStagingCollateral().getSCISecurityID());

			colTrxValue = super.createTransaction(colTrxValue);
			return helper.prepareResult(colTrxValue);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Error in MIMakerDirectCreateSecOperation perform process");
		}
	}
}
