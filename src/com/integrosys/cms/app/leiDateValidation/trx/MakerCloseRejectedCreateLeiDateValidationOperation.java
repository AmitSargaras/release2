package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class MakerCloseRejectedCreateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation{

	 private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_LEI_DATE_VALIDATION;
	 private String operationName;
	  
	 public MakerCloseRejectedCreateLeiDateValidationOperation(){
		 operationName = DEFAULT_OPERATION_NAME;
	 }
	  
	 public String getOperationName() {
		 return operationName;
	 }

	 public void setOperationName(String operationName) {
		 this.operationName = operationName;
	 }
	
	    /**
	    * Process the transaction
	    * 1.    Update the transaction record
	    * @param anITrxValue - ITrxValue
	    * @return ITrxResult - the transaction result
	    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
	    */
 public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
     ILeiDateValidationTrxValue trxValue = super.getLeiDateValidationTrxValue(anITrxValue);
     trxValue = super.updateLeiDateValidationTrx(trxValue);
     return super.prepareResult(trxValue);
 }
}
