package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadBaselOperation extends AbstractBaselTrxOperation implements ITrxReadOperation{
	
	public ReadBaselOperation() {
        super();
    }

	public String getOperationName() {
        return ICMSConstant.ACTION_READ_BASEL;
    }
	
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
    	try {
            ICMSTrxValue trxValue = super.getCMSTrxValue(anITrxValue);
            trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            OBBaselMasterTrxValue newValue = new OBBaselMasterTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (null != stagingRef) {
                long stagingPK = Long.parseLong(stagingRef);
                newValue.setStagingBaselMaster(getStagingBaselBusManager().getBaselById(stagingPK));
            }else{
            	throw new TrxOperationException("Staging Reference Id is null");
            }

            if (null != actualRef) {
                long actualPK = Long.parseLong(actualRef);

                newValue.setBaselMaster(getBaselBusManager().getBaselById(actualPK));
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
