package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadIDOperation extends AbstractTrxOperation implements ITrxReadOperation {

	public ReadIDOperation() {
		 super();
	}

	public String getOperationName() {
        return ICMSConstant.ACTION_READ_EXC_LINE_FR_STP_SRM_ID;
    }
    
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
    	try {
            ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

            OBExcLineForSTPSRMTrxValue newValue = new OBExcLineForSTPSRMTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (stagingRef != null) {
                long stagingPK = (new Long(stagingRef)).longValue();
                IExcLineForSTPSRM obj = getStagingBusManager().getById(stagingPK);
                newValue.setStaging(obj);
            }else{
            	throw new TrxOperationException("Staging Reference Id is null");
            }

            if (actualRef != null) {
                long actualPK = (new Long(actualRef)).longValue();
                newValue.setActual(getBusManager().getById(actualPK));
            }
            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.getMessage());
        }
    }
    
}