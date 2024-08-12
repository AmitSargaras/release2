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
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
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
public class ReadInternalCreditRatingOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	        return ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING;
	}
	
	public ITrxValue getTransaction (ITrxValue val) throws TransactionException {
	
		try {
			
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue (val);

//			SBInternalCreditRatingBusManager mgr = InternalCreditRatingBusManagerFactory.getActualInternalCreditRatingBusManager();
            IInternalCreditRatingBusManager mgr = getInternalCreditRatingBusManager();
			List actualICRList = mgr.getAllInternalCreditRating();
				
			String actualRefID = null;
			String stagingRefID = null;
				
			if (actualICRList != null && actualICRList.size() != 0) {
				actualRefID = String.valueOf (((IInternalCreditRating)actualICRList.get(0)).getGroupId());
			}
				
			if (actualRefID != null) {
				DefaultLogger.debug (this,"************ group id/ actualRefID" + actualRefID);

				try {
					cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_INTERNAL_CREDIT_RATING);
					stagingRefID = cmsTrxValue.getStagingReferenceID();	
				}
				catch (Exception e) {
					throw new TrxOperationException(e);
	            }

			}
			else
			{				
				ICMSTrxValue cmsTrxValueTemp = getTrxManager().getWorkingTrxByTrxType (ICMSConstant.INSTANCE_INTERNAL_CREDIT_RATING);
				if (cmsTrxValueTemp != null) {
					cmsTrxValue = cmsTrxValueTemp;
					stagingRefID = cmsTrxValue.getStagingReferenceID();
				}
			}
			
			OBInternalCreditRatingTrxValue trxVal = new OBInternalCreditRatingTrxValue (cmsTrxValue);
			trxVal.setActualICRList(actualICRList);            
			
			if(stagingRefID!=null) {
//				SBInternalCreditRatingBusManager stagingMgr = InternalCreditRatingBusManagerFactory.getStagingInternalCreditRatingBusManager();
                IInternalCreditRatingBusManager stagingMgr = getStagingInternalCreditRatingBusManager();
				List stagingICRList = stagingMgr.getInternalCreditRatingByGroupId (Long.parseLong (stagingRefID));
				trxVal.setStagingICRList(stagingICRList);			
			}	

		    return trxVal;
				
		}
		catch (Exception e) {
		    throw new TrxOperationException(e);
		}
	}
		 
}



