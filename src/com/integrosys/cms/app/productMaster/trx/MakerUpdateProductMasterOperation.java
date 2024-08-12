package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.ProductMasterReplicationUtils;

/**
 * @author Santosh.Sonmankar
 * Maker Update operation to  update FCCBranch 
 */
public class MakerUpdateProductMasterOperation extends AbstractProductMasterTrxOperation{
	 /**
     * Defaulc Constructor
     */
    public MakerUpdateProductMasterOperation() {
        super();
    }
    
    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_PRODUCT_MASTER;
    }
    
    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IProductMasterTrxValue trxValue = getProductMasterTrxValue(anITrxValue);
            return trxValue;
        }
    
    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IProductMasterTrxValue idxTrxValue = getProductMasterTrxValue(anITrxValue);
        IProductMaster stage = idxTrxValue.getStagingProductMaster();
        IProductMaster replicatedFCCBranch = ProductMasterReplicationUtils.replicateProductMasterForCreateStagingCopy(stage);
        idxTrxValue.setStagingProductMaster(replicatedFCCBranch);
        IProductMasterTrxValue trxValue = createStagingProductMaster(idxTrxValue);
        trxValue = updateProductMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
