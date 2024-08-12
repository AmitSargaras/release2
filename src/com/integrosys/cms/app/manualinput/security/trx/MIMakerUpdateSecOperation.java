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
public class MIMakerUpdateSecOperation extends AbstractMISecOperation {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#getOperationName()
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return TransactionActionConst.ACTION_MANUAL_UPDATE_SEC;
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
			colTrxValue = super.updateTransaction(colTrxValue);
			return helper.prepareResult(colTrxValue);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Error in MIUpdateSecOperation perform process");
		}
	}

}
