package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ReplicationUtils;

public class MakerDeleteOperation extends AbstractTrxOperation {

	public MakerDeleteOperation() {
        super();
    }

    public String getOperationName() {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_DELETE_LIMITS_OF_AUTHORITY;
    }

    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        ILimitsOfAuthorityMasterTrxValue trxValue = getTrxValue(anITrxValue);
        ILimitsOfAuthorityMaster staging = trxValue.getStaging();
        try {
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
        }
    }

    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ILimitsOfAuthorityMasterTrxValue idxTrxValue = getTrxValue(anITrxValue);
        ILimitsOfAuthorityMaster stage = idxTrxValue.getStaging();
        ILimitsOfAuthorityMaster replicated = ReplicationUtils.replicateForCreateStagingCopy(stage);
   
        idxTrxValue.setStaging(replicated);

        ILimitsOfAuthorityMasterTrxValue trxValue = createStaging(idxTrxValue);
        trxValue = updateTrx(trxValue);
        return super.prepareResult(trxValue);
    }

}