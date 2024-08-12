package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadInternalLimitParameterByTrxIDOperation extends CMSTrxOperation
		implements ITrxReadOperation {

	private static final long serialVersionUID = 1L;

    private IInternalLimitParameterBusManager internalLimitParameterBusManager;
    private IInternalLimitParameterBusManager stagingInternalLimitParameterBusManager;

    public IInternalLimitParameterBusManager getInternalLimitParameterBusManager() {
        return internalLimitParameterBusManager;
    }

    public void setInternalLimitParameterBusManager(IInternalLimitParameterBusManager internalLimitParameterBusManager) {
        this.internalLimitParameterBusManager = internalLimitParameterBusManager;
    }

    public IInternalLimitParameterBusManager getStagingInternalLimitParameterBusManager() {
        return stagingInternalLimitParameterBusManager;
    }

    public void setStagingInternalLimitParameterBusManager(IInternalLimitParameterBusManager stagingInternalLimitParameterBusManager) {
        this.stagingInternalLimitParameterBusManager = stagingInternalLimitParameterBusManager;
    }

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_INTERNAL_LIMIT_BY_TRX_ID;
	}

	public ITrxValue getTransaction(ITrxValue anITrxValue)
			throws TransactionException {
		try {
			
			ICMSTrxValue trxValue = (ICMSTrxValue) anITrxValue;
			trxValue = getTrxManager().getTransaction(trxValue.getTransactionID());
			
			OBInternalLimitParameterTrxValue limitParameterTrx = new OBInternalLimitParameterTrxValue (trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				
//				SBInternalLimitParameterBusManager mgr = getStageSBInternalLimitParameterLocal();
//	            List stagingIlPList = mgr.getInternalLimitParameterByGroupID (new Long(stagingRef));

                IInternalLimitParameterBusManager mgr = getStagingInternalLimitParameterBusManager();
                List stagingIlPList = mgr.getInternalLimitParameterByGroupID(Long.parseLong(stagingRef));
                limitParameterTrx.setStagingILPList(stagingIlPList);
            }

			if (actualRef != null) {
//				SBInternalLimitParameterBusManager mgr = getActualSBInternalLimitParameterLocal();
//	            List actualIlPList = mgr.getInternalLimitParameterByGroupID (new Long(actualRef));

                IInternalLimitParameterBusManager mgr = getInternalLimitParameterBusManager();
                List actualIlPList = mgr.getInternalLimitParameterByGroupID(Long.parseLong(actualRef));
                limitParameterTrx.setActualILPList(actualIlPList);
            }
            return limitParameterTrx;
			
		} catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}
	
//	protected SBInternalLimitParameterBusManager getActualSBInternalLimitParameterLocal() {
//		return (SBInternalLimitParameterBusManager) BeanController.getEJB(
//				ICMSJNDIConstant.SB_ACTUAL_INTERNAL_LIMIT_JNDI,
//				SBInternalLimitParameterBusManagerHome.class.getName());
//	}
//
//	protected SBInternalLimitParameterBusManager getStageSBInternalLimitParameterLocal() {
//		return (SBInternalLimitParameterBusManager) BeanController.getEJB(
//				ICMSJNDIConstant.SB_STAGE_INTERNAL_LIMIT_JNDI,
//				SBInternalLimitParameterBusManagerHome.class.getName());
//	}
}
