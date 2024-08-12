package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.ReplicationUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerDeleteOperation extends AbstractTrxOperation {

	public MakerDeleteOperation() {
        super();
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }

    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IBankingArrangementFacExclusionTrxValue trxValue = getTrxValue(anITrxValue);
        IBankingArrangementFacExclusion staging = trxValue.getStaging();
        try {
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
        }
    }

    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IBankingArrangementFacExclusionTrxValue idxTrxValue = getTrxValue(anITrxValue);
        IBankingArrangementFacExclusion stage = idxTrxValue.getStaging();
        IBankingArrangementFacExclusion replicated = ReplicationUtils.replicateForCreateStagingCopy(stage);
   
        idxTrxValue.setStaging(replicated);

        IBankingArrangementFacExclusionTrxValue trxValue = createStaging(idxTrxValue);
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}