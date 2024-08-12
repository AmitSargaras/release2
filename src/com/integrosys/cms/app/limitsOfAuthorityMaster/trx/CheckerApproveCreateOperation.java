package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.LimitsOfAuthorityMasterException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ReplicationUtils;

public class CheckerApproveCreateOperation extends AbstractTrxOperation{

	public String getOperationName() {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_CHECKER_APPROVE_CREATE_LIMITS_OF_AUTHORITY;
    }
	
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ILimitsOfAuthorityMasterTrxValue trxValue = getTrxValue(anITrxValue);
      try{
        trxValue = createActual(trxValue);
        trxValue = updateTrx(trxValue);
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }
    
    private ILimitsOfAuthorityMasterTrxValue createActual(ILimitsOfAuthorityMasterTrxValue idxTrxValue) throws LimitsOfAuthorityMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
        	ILimitsOfAuthorityMaster staging = idxTrxValue.getStaging();
        	ILimitsOfAuthorityMaster replicated = ReplicationUtils.replicateForCreateStagingCopy(staging);
        	ILimitsOfAuthorityMaster actual = getBusManager().create(replicated);
            idxTrxValue.setActual(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getBusManager().update(actual);
            return idxTrxValue;
        }
        catch (LimitsOfAuthorityMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
    
}