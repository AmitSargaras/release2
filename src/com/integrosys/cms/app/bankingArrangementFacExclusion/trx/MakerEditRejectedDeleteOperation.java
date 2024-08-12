package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public class MakerEditRejectedDeleteOperation extends AbstractTrxOperation{

	public MakerEditRejectedDeleteOperation()
    {
        super();
    }

    public String getOperationName()
    {
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }
    
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
    	IBankingArrangementFacExclusionTrxValue idxTrxValue = getTrxValue(anITrxValue);
    	IBankingArrangementFacExclusion stage = idxTrxValue.getStaging();
        idxTrxValue.setStaging(stage);

        IBankingArrangementFacExclusionTrxValue trxValue = createStaging(idxTrxValue);
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}