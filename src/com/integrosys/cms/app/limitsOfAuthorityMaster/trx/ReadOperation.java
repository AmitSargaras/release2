package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadOperation extends AbstractTrxOperation implements ITrxReadOperation  {

	public ReadOperation() {
		super();
	}

	public String getOperationName() {
        return ICMSConstant.LimitsOfAuthorityMaster.ACTION_READ_LIMITS_OF_AUTHORITY;
    }
    
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
    	try {
            ICMSTrxValue trxValue = super.getCMSTrxValue(anITrxValue);
            trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            OBLimitsOfAuthorityMasterTrxValue newValue = new OBLimitsOfAuthorityMasterTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (null != stagingRef) {
                long stagingPK = Long.parseLong(stagingRef);
                newValue.setStaging(getStagingBusManager().getById(stagingPK));
            }else{
            	throw new TrxOperationException("Staging Reference Id is null");
            }

            if (null != actualRef) {
                long actualPK = Long.parseLong(actualRef);

                newValue.setActual(getBusManager().getById(actualPK));
            }else{
            	throw new TrxOperationException("Actual Reference Id is null");
            }

            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.getMessage());
        }
    }
}
