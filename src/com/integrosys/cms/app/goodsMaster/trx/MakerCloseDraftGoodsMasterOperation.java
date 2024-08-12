package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class MakerCloseDraftGoodsMasterOperation extends AbstractGoodsMasterTrxOperation  {

	 private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOODS_MASTER;
	 private String operationName;
	
	 /**
	 * Defaulc Constructor
	 */
	public MakerCloseDraftGoodsMasterOperation() {
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
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        IGoodsMasterTrxValue trxValue = getGoodsMasterTrxValue(anITrxValue);
        trxValue = updateGoodsMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
