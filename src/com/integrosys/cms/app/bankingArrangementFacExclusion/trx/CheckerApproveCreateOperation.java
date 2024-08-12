package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.BankingArrangementFacExclusionException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.ReplicationUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerApproveCreateOperation extends AbstractTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
    }
	
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IBankingArrangementFacExclusionTrxValue trxValue = getTrxValue(anITrxValue);
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
    
    private IBankingArrangementFacExclusionTrxValue createActual(IBankingArrangementFacExclusionTrxValue idxTrxValue) throws BankingArrangementFacExclusionException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
        	IBankingArrangementFacExclusion staging = idxTrxValue.getStaging();
        	IBankingArrangementFacExclusion replicated = ReplicationUtils.replicateForCreateStagingCopy(staging);
        	IBankingArrangementFacExclusion actual = getBusManager().create(replicated);
            idxTrxValue.setActual(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getBusManager().update(actual);
            return idxTrxValue;
        }
        catch (BankingArrangementFacExclusionException ex) {
            throw new TrxOperationException(ex);
        }
    }
    
}