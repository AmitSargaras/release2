package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class MakerCloseRejectedDeleteBaselOperation extends AbstractBaselTrxOperation{
	
	 private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_BASEL;

	    private String operationName;

	    /**
	    * Defaulc Constructor
	    */
	    public MakerCloseRejectedDeleteBaselOperation()
	    {
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
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	    {
	    	IBaselMasterTrxValue trxValue = super.getBaselTrxValue (anITrxValue);
	        trxValue = updateBaselTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }

}
