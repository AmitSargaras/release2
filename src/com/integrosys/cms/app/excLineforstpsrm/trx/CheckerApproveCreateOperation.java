package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.bus.ReplicationUtils;

public class CheckerApproveCreateOperation extends AbstractTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_EXC_LINE_FR_STP_SRM;
    }
	
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IExcLineForSTPSRMTrxValue trxValue = getTrxValue(anITrxValue);
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
    
    private IExcLineForSTPSRMTrxValue createActual(IExcLineForSTPSRMTrxValue idxTrxValue) throws ExcLineForSTPSRMException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
        	IExcLineForSTPSRM staging = idxTrxValue.getStaging();
        	IExcLineForSTPSRM replicated = ReplicationUtils.replicateForCreateStagingCopy(staging);
        	IExcLineForSTPSRM actual = getBusManager().create(replicated);
            idxTrxValue.setActual(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getBusManager().update(actual);
            return idxTrxValue;
        }
        catch (ExcLineForSTPSRMException ex) {
            throw new TrxOperationException(ex);
        }
    }
    
}