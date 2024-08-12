package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;

public class MakerCloseRejectedCreateGoodsMasterOperation extends AbstractGoodsMasterTrxOperation{

	 private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_GOODS_MASTER;
	 private String operationName;
	  
	 public MakerCloseRejectedCreateGoodsMasterOperation(){
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
      IGoodsMasterTrxValue trxValue = super.getGoodsMasterTrxValue(anITrxValue);
      trxValue = super.updateGoodsMasterTrx(trxValue);
      return super.prepareResult(trxValue);
  }
}
