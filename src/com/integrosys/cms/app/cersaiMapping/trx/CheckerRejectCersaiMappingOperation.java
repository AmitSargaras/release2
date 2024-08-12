package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.cersaiMapping.trx.AbstractCersaiMappingTrxOperation;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;

public class CheckerRejectCersaiMappingOperation extends AbstractCersaiMappingTrxOperation {

	 public CheckerRejectCersaiMappingOperation(){
		 super();
	 }
	  
	public String getOperationName() {
       return ICMSConstant.ACTION_CHECKER_REJECT_CERSAI_MAPPING;
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
       ICersaiMappingTrxValue trxValue = super.getCersaiMappingTrxValue(anITrxValue);
       trxValue = super.updateCersaiMappingTrx(trxValue);
       return super.prepareResult(trxValue);
   }
}
