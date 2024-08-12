package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterReplicationUtils;

public class CheckerApproveCreateNpaTraqCodeMasterOperation extends AbstractNpaTraqCodeMasterTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_NPA_TRAQ_CODE_MASTER;
    }
	
	/**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        INpaTraqCodeMasterTrxValue trxValue = getNpaTraqCodeMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualNpaTraqCodeMaster(trxValue);
        trxValue = updateNpaTraqCodeMasterTrx(trxValue);
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }
    
    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws ConcurrentUpdateException 
     * @throws TransactionException 
     * @throws TrxParameterException 
     * @throws NpaTraqCodeMasterException 
     */
    private INpaTraqCodeMasterTrxValue createActualNpaTraqCodeMaster(INpaTraqCodeMasterTrxValue idxTrxValue) throws NpaTraqCodeMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            INpaTraqCodeMaster staging = idxTrxValue.getStagingNpaTraqCodeMaster();
            // Replicating is necessary or else stale object error will arise
            INpaTraqCodeMaster replicatedNpaTraqCodeMaster = NpaTraqCodeMasterReplicationUtils.replicateNpaTraqCodeMasterForCreateStagingCopy(staging);
            INpaTraqCodeMaster actual = getNpaTraqCodeMasterBusManager().createNpaTraqCodeMaster(replicatedNpaTraqCodeMaster);
            idxTrxValue.setNpaTraqCodeMaster(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getNpaTraqCodeMasterBusManager().updateNpaTraqCodeMaster(actual);
            return idxTrxValue;
        }
        catch (NpaTraqCodeMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
