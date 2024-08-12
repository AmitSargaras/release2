package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.productMaster.bus.ProductMasterReplicationUtils;

public class CheckerApproveCreateProductMasterOperation extends AbstractProductMasterTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_PRODUCT_MASTER;
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
        IProductMasterTrxValue trxValue = getProductMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualProductMaster(trxValue);
        trxValue = updateProductMasterTrx(trxValue);
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
     * @throws ProductMasterException 
     */
    private IProductMasterTrxValue createActualProductMaster(IProductMasterTrxValue idxTrxValue) throws ProductMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IProductMaster staging = idxTrxValue.getStagingProductMaster();
            // Replicating is necessary or else stale object error will arise
            IProductMaster replicatedProductMaster = ProductMasterReplicationUtils.replicateProductMasterForCreateStagingCopy(staging);
            IProductMaster actual = getProductMasterBusManager().createProductMaster(replicatedProductMaster);
            idxTrxValue.setProductMaster(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getProductMasterBusManager().updateProductMaster(actual);
            return idxTrxValue;
        }
        catch (ProductMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
