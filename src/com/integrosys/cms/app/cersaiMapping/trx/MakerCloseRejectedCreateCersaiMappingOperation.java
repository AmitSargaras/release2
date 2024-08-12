package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;

public class MakerCloseRejectedCreateCersaiMappingOperation extends AbstractCersaiMappingTrxOperation {
	
private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_CERSAI_MAPPING;
private String operationName;

public MakerCloseRejectedCreateCersaiMappingOperation(){
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
 ICersaiMappingTrxValue trxValue = super.getCersaiMappingTrxValue(anITrxValue);
 trxValue = super.updateCersaiMappingTrx(trxValue);
 return super.prepareResult(trxValue);
}
}