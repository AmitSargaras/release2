package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.bus.UdfReplicationUtils;

public class CheckerApproveCreateUdfOperation extends AbstractUdfTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_UDF;
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
        IUdfTrxValue trxValue = getUdfTrxValue(anITrxValue);
      try{
        trxValue = createActualUdf(trxValue);
        trxValue = updateUdfTrx(trxValue);
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
     * @throws UdfException 
     */
    private IUdfTrxValue createActualUdf(IUdfTrxValue idxTrxValue) throws UdfException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IUdf staging = idxTrxValue.getStagingUdf();
            // Replicating is necessary or else stale object error will arise
            IUdf replicatedUdf = UdfReplicationUtils.replicateUdfForCreateStagingCopy(staging);
            IUdf actual = getUdfBusManager().createUdf(replicatedUdf);
            idxTrxValue.setUdf(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getUdfBusManager().updateUdfNew(actual);
            return idxTrxValue;
        }
        catch (UdfException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
