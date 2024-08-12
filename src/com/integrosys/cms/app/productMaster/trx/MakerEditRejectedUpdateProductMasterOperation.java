package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.OBProductMaster;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateProductMasterOperation extends AbstractProductMasterTrxOperation{

	 public MakerEditRejectedUpdateProductMasterOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_PRODUCT_MASTER;
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
       IProductMasterTrxValue idxTrxValue = super.getProductMasterTrxValue(anITrxValue);
       IProductMaster stage = idxTrxValue.getStagingProductMaster();
       IProductMaster replicatedProductMaster= new OBProductMaster();
       
       replicatedProductMaster.setProductCode(stage.getProductCode());
       replicatedProductMaster.setProductName(stage.getProductName());
       replicatedProductMaster.setDeprecated(stage.getDeprecated());
       replicatedProductMaster.setVersionTime(stage.getVersionTime());
       replicatedProductMaster.setId(stage.getId());
       replicatedProductMaster.setStatus(stage.getStatus());
       replicatedProductMaster.setMasterId(stage.getMasterId());
       replicatedProductMaster.setVersionTime(stage.getVersionTime());
       replicatedProductMaster.setCreationDate(stage.getCreationDate());
       replicatedProductMaster.setCreateBy(stage.getCreateBy());
       replicatedProductMaster.setLastUpdateDate(stage.getLastUpdateDate());
       replicatedProductMaster.setLastUpdateBy(stage.getLastUpdateBy());
       replicatedProductMaster.setCpsId(stage.getCpsId());
       replicatedProductMaster.setOperationName(stage.getOperationName());
       
       idxTrxValue.setStagingProductMaster(replicatedProductMaster);
       IProductMasterTrxValue trxValue = createStagingProductMaster(idxTrxValue);
       trxValue = super.updateProductMasterTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
