package com.integrosys.cms.app.riskType.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author dattatray.thorat
 * Maker Close operation to remove  delete rejected by checker
 */

public class MakerCloseRejectedDeleteRiskTypeOperation extends AbstractRiskTypeTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_DELETE_RISK_TYPE;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedDeleteRiskTypeOperation()
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
        IRiskTypeTrxValue trxValue = super.getRiskTypeTrxValue (anITrxValue);
        OBRiskType st = (OBRiskType) trxValue.getStagingRiskType();
      
        
        trxValue = updateRiskTypeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
