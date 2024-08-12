package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dattatray.thorat
 * Maker Close operation to remove  create rejected by checker
 */

public class MakerCloseRejectedCreateInsuranceCoverageOperation extends AbstractInsuranceCoverageTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREATE_INSURANCE_COVERAGE;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedCreateInsuranceCoverageOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    /**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}



	/**
	 * @param operationName the operationName to set
	 */
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
        IInsuranceCoverageTrxValue trxValue = super.getInsuranceCoverageTrxValue (anITrxValue);
        trxValue = updateInsuranceCoverageTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
