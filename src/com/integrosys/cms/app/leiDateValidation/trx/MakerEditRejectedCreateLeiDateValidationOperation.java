package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class MakerEditRejectedCreateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation{

	 public MakerEditRejectedCreateLeiDateValidationOperation(){
		 super();
	 }
	  
	public String getOperationName() {
     return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_LEI_DATE_VALIDATION;
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
	  
     ILeiDateValidationTrxValue idxTrxValue = super.getLeiDateValidationTrxValue(anITrxValue);
     ILeiDateValidationTrxValue trxValue = createStagingLeiDateValidation(idxTrxValue);
     trxValue = super.updateLeiDateValidationTrx(trxValue);
     return super.prepareResult(trxValue);
 }
}
