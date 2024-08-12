package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail;

public class MakerEditRejectedUpdateSegmentWiseEmailOperation extends AbstractSegmentWiseEmailTrxOperation{

	 public MakerEditRejectedUpdateSegmentWiseEmailOperation(){
		 super();
	 }
	  
	public String getOperationName() {
      return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_SEGMENT_WISE_EMAIL;
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
      ISegmentWiseEmailTrxValue idxTrxValue = super.getSegmentWiseEmailTrxValue(anITrxValue);
      ISegmentWiseEmail stage = idxTrxValue.getStagingSegmentWiseEmail();
      ISegmentWiseEmail replicatedProductMaster= new OBSegmentWiseEmail();
      
      replicatedProductMaster.setSegment(stage.getSegment());
      replicatedProductMaster.setEmail(stage.getEmail());
      replicatedProductMaster.setVersionTime(stage.getVersionTime());
      replicatedProductMaster.setID(stage.getID());
      replicatedProductMaster.setStatus(stage.getStatus());
      replicatedProductMaster.setCreationDate(stage.getCreationDate());
      replicatedProductMaster.setCreatedBy(stage.getCreatedBy());
      replicatedProductMaster.setLastUpdateDate(stage.getLastUpdateDate());
      replicatedProductMaster.setLastUpdatedBy(stage.getLastUpdatedBy());
      
      idxTrxValue.setStagingSegmentWiseEmail(replicatedProductMaster);
      ISegmentWiseEmailTrxValue trxValue = createStagingSegmentWiseEmail(idxTrxValue);
      trxValue = super.updateSegmentWiseEmailTrx(trxValue);
      return super.prepareResult(trxValue);
  }
}
