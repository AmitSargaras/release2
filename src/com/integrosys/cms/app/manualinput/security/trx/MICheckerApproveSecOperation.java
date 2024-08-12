/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MICheckerApproveSecOperation extends AbstractMISecOperation {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#getOperationName()
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return TransactionActionConst.ACTION_MANUAL_APPROVE_SEC;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			MISecTrxHelper helper = new MISecTrxHelper();
			ICollateralTrxValue secTrxValue = (ICollateralTrxValue) value;
			if (secTrxValue.getCollateral() == null) {
				secTrxValue = helper.createActualColFromStaging(secTrxValue);
			}
			else {
				secTrxValue = helper.updateActualColFromStaging(secTrxValue);
			}
			secTrxValue = super.updateTransaction(secTrxValue);
			// remember to create a CMS_SOURCE_SECURITY record where SOURCE_ID =
			// GCMS
			helper.createShareSecForGCMS(Long.parseLong(secTrxValue.getReferenceID()), secTrxValue
					.getStagingCollateral().getSCISecurityID());
			return helper.prepareResult(secTrxValue);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Error in MIApproveSecOperation perform process");
		}
	}
}
