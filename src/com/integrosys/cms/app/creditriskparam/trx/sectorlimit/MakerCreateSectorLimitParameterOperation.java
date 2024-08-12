package com.integrosys.cms.app.creditriskparam.trx.sectorlimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Author: Syukri
 * Date: Jun 4, 2008
 */
public class MakerCreateSectorLimitParameterOperation extends AbstractSectorLimitParameterTrxOperation {
	
	private static final long serialVersionUID = 1L;

	public MakerCreateSectorLimitParameterOperation() {
        super();
    }

    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_SECTOR_LIMIT_PARAMETER;
    }

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
    	ISectorLimitParameterTrxValue trxValue = super.getTrxValue(value);
    	 DefaultLogger.debug (this, "SectorLimit >>>>> create Transaction: " + trxValue.getTransactionID());
    	
        trxValue = createStaging(trxValue);

        if (ICMSConstant.STATE_ND.equals(trxValue.getStatus())){
            trxValue = createTransaction(trxValue);
        }
        else{
            trxValue = updateTransaction(trxValue);
        }
        
        return prepareResult(trxValue);
    }
    

}
