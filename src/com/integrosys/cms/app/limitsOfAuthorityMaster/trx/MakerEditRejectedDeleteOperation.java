package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;


public class MakerEditRejectedDeleteOperation extends AbstractTrxOperation{

	public MakerEditRejectedDeleteOperation()
    {
        super();
    }

    public String getOperationName()
    {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_EDIT_REJECTED_DELETE_LIMITS_OF_AUTHORITY;
    }
    
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
    	ILimitsOfAuthorityMasterTrxValue idxTrxValue = getTrxValue(anITrxValue);
    	ILimitsOfAuthorityMaster stage = idxTrxValue.getStaging();
        idxTrxValue.setStaging(stage);

        ILimitsOfAuthorityMasterTrxValue trxValue = createStaging(idxTrxValue);
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}