package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerEditRejectedCreateCersaiMappingOperation extends AbstractCersaiMappingTrxOperation{

	 public MakerEditRejectedCreateCersaiMappingOperation(){
		 super();
	 }
	  
	public String getOperationName() {
     return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_CERSAI_MAPPING;
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
	  
     ICersaiMappingTrxValue idxTrxValue = super.getCersaiMappingTrxValue(anITrxValue);
     ICersaiMappingTrxValue trxValue = createStagingCersaiMapping(idxTrxValue);
     trxValue = super.updateCersaiMappingTrx(trxValue);
     return super.prepareResult(trxValue);
 }
}
