package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;


public class MakerEditRejectedDeleteOperation extends AbstractTrxOperation{

	public MakerEditRejectedDeleteOperation()
    {
        super();
    }

    public String getOperationName()
    {
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_EXC_LINE_FR_STP_SRM;
    }
    
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
    	IExcLineForSTPSRMTrxValue idxTrxValue = getTrxValue(anITrxValue);
    	IExcLineForSTPSRM stage = idxTrxValue.getStaging();
        idxTrxValue.setStaging(stage);

        IExcLineForSTPSRMTrxValue trxValue = createStaging(idxTrxValue);
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}