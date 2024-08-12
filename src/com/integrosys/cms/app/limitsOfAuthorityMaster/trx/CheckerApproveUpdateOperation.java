package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;

public class CheckerApproveUpdateOperation extends AbstractTrxOperation{
	
	public CheckerApproveUpdateOperation() {
		super();
	}
	
	public String getOperationName() {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_CHECKER_APPROVE_UPDATE_LIMITS_OF_AUTHORITY;
    }
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ILimitsOfAuthorityMasterTrxValue trxValue = getTrxValue(anITrxValue);
		trxValue = updateActual(trxValue);
		trxValue = updateTrx(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private ILimitsOfAuthorityMasterTrxValue updateActual(ILimitsOfAuthorityMasterTrxValue trx)
			throws TrxOperationException {
		try {
			ILimitsOfAuthorityMaster staging = trx.getStaging();
			ILimitsOfAuthorityMaster actual = trx.getActual();

			ILimitsOfAuthorityMaster updated = getBusManager().updateToWorkingCopy(actual, staging);
			trx.setActual(updated);

			return trx;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}
	
}