package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerApproveUpdateOperation extends AbstractTrxOperation{
	
	public CheckerApproveUpdateOperation() {
		super();
	}
	
	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IBankingArrangementFacExclusionTrxValue trxValue = getTrxValue(anITrxValue);
		trxValue = updateActual(trxValue);
		trxValue = updateTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private IBankingArrangementFacExclusionTrxValue updateActual(IBankingArrangementFacExclusionTrxValue trx)
			throws TrxOperationException {
		try {
			IBankingArrangementFacExclusion staging = trx.getStaging();
			IBankingArrangementFacExclusion actual = trx.getActual();

			IBankingArrangementFacExclusion updated = getBusManager().updateToWorkingCopy(actual, staging);
			trx.setActual(updated);

			return trx;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}