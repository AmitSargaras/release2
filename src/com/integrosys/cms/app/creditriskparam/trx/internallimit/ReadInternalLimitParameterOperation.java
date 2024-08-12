package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

public class ReadInternalLimitParameterOperation extends CMSTrxOperation
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
		return ICMSConstant.ACTION_READ_INTERNAL_LIMIT;
	}


	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		
		try
        {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue (value);

			List stagingILPList = getStagingInternalLimitParameterBusManager().getALLInternalLimitParameter();
			List actualILPList = getInternalLimitParameterBusManager().getALLInternalLimitParameter();

			String actualRefID = null;
			if (actualILPList != null && actualILPList.size() != 0) {
				actualRefID = String.valueOf (((IInternalLimitParameter)actualILPList.get(0)).getGroupID());
			}
			if (actualRefID != null) {
				DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

            try {
                cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_INTERNAL_LIMIT);
            }
            catch (Exception e) {
            }
        }

        OBInternalLimitParameterTrxValue internalLimitTrx = new OBInternalLimitParameterTrxValue (cmsTrxValue);

        internalLimitTrx.setActualILPList(actualILPList);

        if (stagingILPList == null || stagingILPList.size() == 0)
        	stagingILPList = actualILPList;

        internalLimitTrx.setStagingILPList(stagingILPList);

        return internalLimitTrx;
		
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
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
