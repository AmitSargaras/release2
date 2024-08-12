/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/AbstractForexTrxOperation.java,v 1.8 2003/08/11 06:36:51 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/11 06:36:51 $ Tag: $Name: $
 */
public abstract class AbstractInternalLimitParameterOperation extends CMSTrxOperation implements ITrxRouteOperation {

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

    protected ITrxResult prepareResult(IInternalLimitParameterTrxValue trxValue) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(trxValue);
		return result;
	}

	protected IInternalLimitParameterTrxValue createStagingILP(IInternalLimitParameterTrxValue trxValue) throws TrxOperationException {
		
		try {
			
			List ilpList = trxValue.getStagingILPList();

            for (int i=0; i<ilpList.size(); i++) {
                ((IInternalLimitParameter)ilpList.get(i)).setStatus (trxValue.getToState());
            }

//            SBInternalLimitParameterBusManager mgr = getStageSBInternalLimitParameterLocal();
            IInternalLimitParameterBusManager mgr = getStagingInternalLimitParameterBusManager();
            ilpList = mgr.createInternalListParameter (ilpList);
            trxValue.setStagingILPList(ilpList);
			
			return trxValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		
	}

	protected IInternalLimitParameterTrxValue createTransaction(IInternalLimitParameterTrxValue trxValue)throws TrxOperationException {
		
		DefaultLogger.debug(this, "createTransaction - Begin.");
		try {
			trxValue = prepareTrxValue(trxValue);
			ICMSTrxValue tempValue = super.createTransaction(trxValue);
			IInternalLimitParameterTrxValue newTrxValue = new OBInternalLimitParameterTrxValue(
					tempValue);
			newTrxValue.setActualILPList(trxValue.getActualILPList());
			newTrxValue.setStagingILPList(trxValue.getStagingILPList());
			DefaultLogger.debug(this, "createTransaction - End.");
			return newTrxValue;
		} catch (Exception e) {
			throw new TrxOperationException(e);
		}
		
	}

	protected IInternalLimitParameterTrxValue updateTransaction(IInternalLimitParameterTrxValue trxValue)throws TrxOperationException {
		
		DefaultLogger.debug(this, "updateTransaction - Begin.");
		try {
			trxValue = prepareTrxValue(trxValue);
			ICMSTrxValue tempValue = super.updateTransaction(trxValue);
			IInternalLimitParameterTrxValue newTrxValue = new OBInternalLimitParameterTrxValue(tempValue);
			newTrxValue.setActualILPList(trxValue.getActualILPList());
			newTrxValue.setStagingILPList(trxValue.getStagingILPList());
			return newTrxValue;
		} catch (TransactionException e) {
			throw new TrxOperationException(e);
		} finally {
			DefaultLogger.debug(this, "updateTransaction - End.");
		}
		
	}
	
	private IInternalLimitParameterTrxValue prepareTrxValue (IInternalLimitParameterTrxValue value)
    {
        if (value != null)
        {
            List actual = value.getActualILPList();
            List staging = value.getStagingILPList();

            value.setReferenceID ( actual != null && actual.size() != 0 ? String.valueOf (((IInternalLimitParameter)actual.get(0)).getGroupID()) : null );
            value.setStagingReferenceID ( staging != null && staging.size() != 0 ? String.valueOf (((IInternalLimitParameter)staging.get(0)).getGroupID()) : null );
        }
        return value;
    }

	protected IInternalLimitParameterTrxValue getILParamTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IInternalLimitParameterTrxValue) anITrxValue;
		} catch (ClassCastException e) {
			throw new TrxOperationException(
					"The ITrxValue is not of type IInternalLimitParameterTrxValue: "
							+ e.toString());
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