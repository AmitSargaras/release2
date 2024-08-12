package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{

	 public MakerEditRejectedUpdateGoodsMasterOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_GOODS_MASTER;
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
       IGoodsMasterTrxValue idxTrxValue = super.getGoodsMasterTrxValue(anITrxValue);
       IGoodsMaster stage = idxTrxValue.getStagingGoodsMaster();
       IGoodsMaster replicatedGoodsMaster= new OBGoodsMaster();
       
       replicatedGoodsMaster.setGoodsParentCode(stage.getGoodsParentCode());
       replicatedGoodsMaster.setGoodsCode(stage.getGoodsCode());
       replicatedGoodsMaster.setGoodsName(stage.getGoodsName());
       replicatedGoodsMaster.setDeprecated(stage.getDeprecated());
       replicatedGoodsMaster.setVersionTime(stage.getVersionTime());
       replicatedGoodsMaster.setId(stage.getId());
       replicatedGoodsMaster.setStatus(stage.getStatus());
       replicatedGoodsMaster.setMasterId(stage.getMasterId());
       replicatedGoodsMaster.setVersionTime(stage.getVersionTime());
       replicatedGoodsMaster.setCreationDate(stage.getCreationDate());
       replicatedGoodsMaster.setCreateBy(stage.getCreateBy());
       replicatedGoodsMaster.setLastUpdateDate(stage.getLastUpdateDate());
       replicatedGoodsMaster.setLastUpdateBy(stage.getLastUpdateBy());
       replicatedGoodsMaster.setCpsId(stage.getCpsId());
       replicatedGoodsMaster.setOperationName(stage.getOperationName());
       replicatedGoodsMaster.setRestrictionType(stage.getRestrictionType());
       
       idxTrxValue.setStagingGoodsMaster(replicatedGoodsMaster);
       IGoodsMasterTrxValue trxValue = createStagingGoodsMaster(idxTrxValue);
       trxValue = super.updateGoodsMasterTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
