package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;

/**
 * @author Santosh.Sonmankar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateValuationAmountAndRatingOperation extends AbstractValuationAmountAndRatingTrxOperation{

	 public MakerEditRejectedUpdateValuationAmountAndRatingOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_VALUATION_AMOUNT_AND_RATING; 
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
       IValuationAmountAndRatingTrxValue idxTrxValue = super.getValuationAmountAndRatingTrxValue(anITrxValue);
       IValuationAmountAndRating stage = idxTrxValue.getStagingValuationAmountAndRating();
       IValuationAmountAndRating replicatedValuationAmountAndRating= new OBValuationAmountAndRating();
       
       replicatedValuationAmountAndRating.setCriteria(stage.getCriteria());
       replicatedValuationAmountAndRating.setValuationAmount(stage.getValuationAmount());
       replicatedValuationAmountAndRating.setExcludePartyId(stage.getExcludePartyId());
       replicatedValuationAmountAndRating.setRamRating(stage.getRamRating());
       replicatedValuationAmountAndRating.setDeprecated(stage.getDeprecated());
       replicatedValuationAmountAndRating.setVersionTime(stage.getVersionTime());
       replicatedValuationAmountAndRating.setId(stage.getId());
       replicatedValuationAmountAndRating.setStatus(stage.getStatus());
       replicatedValuationAmountAndRating.setMasterId(stage.getMasterId());
       replicatedValuationAmountAndRating.setVersionTime(stage.getVersionTime());
       replicatedValuationAmountAndRating.setCreationDate(stage.getCreationDate());
       replicatedValuationAmountAndRating.setCreateBy(stage.getCreateBy());
       replicatedValuationAmountAndRating.setLastUpdateDate(stage.getLastUpdateDate());
       replicatedValuationAmountAndRating.setLastUpdateBy(stage.getLastUpdateBy());
//       replicatedValuationAmountAndRating.setCpsId(stage.getCpsId());
       replicatedValuationAmountAndRating.setOperationName(stage.getOperationName());
       
       idxTrxValue.setStagingValuationAmountAndRating(replicatedValuationAmountAndRating);
       IValuationAmountAndRatingTrxValue trxValue = createStagingValuationAmountAndRating(idxTrxValue);
       trxValue = super.updateValuationAmountAndRatingTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
