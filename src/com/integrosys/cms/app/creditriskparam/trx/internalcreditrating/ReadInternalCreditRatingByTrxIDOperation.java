/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingBusManager;
//import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.InternalCreditRatingBusManagerFactory;
//import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.SBInternalCreditRatingBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author priya
 *
 */
public class ReadInternalCreditRatingByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

    private IInternalCreditRatingBusManager internalCreditRatingBusManager;

    private IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager;

    public IInternalCreditRatingBusManager getInternalCreditRatingBusManager() {
        return internalCreditRatingBusManager;
    }

    public void setInternalCreditRatingBusManager(IInternalCreditRatingBusManager internalCreditRatingBusManager) {
        this.internalCreditRatingBusManager = internalCreditRatingBusManager;
    }

    public IInternalCreditRatingBusManager getStagingInternalCreditRatingBusManager() {
        return stagingInternalCreditRatingBusManager;
    }

    public void setStagingInternalCreditRatingBusManager(IInternalCreditRatingBusManager stagingInternalCreditRatingBusManager) {
        this.stagingInternalCreditRatingBusManager = stagingInternalCreditRatingBusManager;
    }

    private static final long serialVersionUID = 1L;
	
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING_BY_TRXID;
	}

    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        try
        {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBInternalCreditRatingTrxValue trxVal = new OBInternalCreditRatingTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
//				SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getStagingInternalCreditRatingBusManager();
                IInternalCreditRatingBusManager mgr = getStagingInternalCreditRatingBusManager();
                List internalCreditRatingList = mgr.getInternalCreditRatingByGroupId(Long.parseLong(stagingRef));
                trxVal.setStagingICRList(internalCreditRatingList);
				
            }

			if (actualRef != null) {
//				SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getActualInternalCreditRatingBusManager();
                IInternalCreditRatingBusManager mgr = getInternalCreditRatingBusManager();
                List internalCreditRatingList = mgr.getInternalCreditRatingByGroupId (Long.parseLong (actualRef));
                trxVal.setActualICRList(internalCreditRatingList);
                
            }
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}
