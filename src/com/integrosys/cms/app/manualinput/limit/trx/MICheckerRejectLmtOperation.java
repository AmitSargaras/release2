/*
 * Created on Feb 15, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MICheckerRejectLmtOperation extends AbstractMILmtOperation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#getOperationName()
	 */
	public String getOperationName() {
		// TODO Auto-generated method stub
		return TransactionActionConst.ACTION_MANUAL_REJECT_LMT;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			MILmtTrxHelper helper = new MILmtTrxHelper();
			ILimitTrxValue lmtTrxValue = (ILimitTrxValue) value;
			lmtTrxValue = super.updateTransaction(lmtTrxValue);
			return helper.prepareResult(lmtTrxValue);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Error in MIApproveLmtOperation perform process");
		}
	}

}
