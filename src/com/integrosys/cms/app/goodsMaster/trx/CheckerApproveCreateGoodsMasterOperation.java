package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterReplicationUtils;

public class CheckerApproveCreateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_GOODS_MASTER;
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
        IGoodsMasterTrxValue trxValue = getGoodsMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualGoodsMaster(trxValue);
        trxValue = updateGoodsMasterTrx(trxValue);
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
     * @throws GoodsMasterException 
     */
    private IGoodsMasterTrxValue createActualGoodsMaster(IGoodsMasterTrxValue idxTrxValue) throws GoodsMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IGoodsMaster staging = idxTrxValue.getStagingGoodsMaster();
            // Replicating is necessary or else stale object error will arise
            IGoodsMaster replicatedGoodsMaster = GoodsMasterReplicationUtils.replicateGoodsMasterForCreateStagingCopy(staging);
            IGoodsMaster actual = getGoodsMasterBusManager().createGoodsMaster(replicatedGoodsMaster);
            idxTrxValue.setGoodsMaster(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getGoodsMasterBusManager().updateGoodsMaster(actual);
            return idxTrxValue;
        }
        catch (GoodsMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
