package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;

public class CheckerApproveDeleteOperation extends AbstractTrxOperation {

	public CheckerApproveDeleteOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_EXC_LINE_FR_STP_SRM;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IExcLineForSTPSRMTrxValue trxValue = getTrxValue(anITrxValue);
		trxValue = updateActual(trxValue);
		trxValue = updateTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private IExcLineForSTPSRMTrxValue updateActual(IExcLineForSTPSRMTrxValue trx)
			throws TrxOperationException {
		try {
			IExcLineForSTPSRM staging = trx.getStaging();
			IExcLineForSTPSRM actual = trx.getActual();

			IExcLineForSTPSRM updated = getBusManager().deleteToWorkingCopy(actual, staging);
			trx.setActual(updated);

			return trx;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}