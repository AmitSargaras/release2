/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/ForexFeedProxyImpl.java,v 1.15 2005/01/12 06:36:33 hshii Exp $
 */
package com.integrosys.cms.app.creditriskparam.proxy.internallimit;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameterBusManager;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.InternalLimitParameterTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class InternalLimitProxyImpl implements IInternalLimitProxy {

	private static final long serialVersionUID = 1L;

    private IInternalLimitParameterBusManager internalLimitParameterBusManager;
    private IInternalLimitParameterBusManager stagingInternalLimitParameterBusManager;
    private ITrxControllerFactory internalLimitParameterTrxControllerFactory;

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

    public ITrxControllerFactory getInternalLimitParameterTrxControllerFactory() {
        return internalLimitParameterTrxControllerFactory;
    }

    public void setInternalLimitParameterTrxControllerFactory(ITrxControllerFactory internalLimitParameterTrxControllerFactory) {
        this.internalLimitParameterTrxControllerFactory = internalLimitParameterTrxControllerFactory;
    }

    public IInternalLimitParameterTrxValue getILParamTrxValue() throws InternalLimitException {
		try {
			IInternalLimitParameterTrxValue trxValue = new OBInternalLimitParameterTrxValue();
            trxValue.setTransactionType(ICMSConstant.INSTANCE_INTERNAL_LIMIT);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_READ_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

	public IInternalLimitParameterTrxValue getILParamTrxValueByTrxID(String trxID) throws InternalLimitException {
		try {
		    IInternalLimitParameterTrxValue trxValue = new OBInternalLimitParameterTrxValue();
            trxValue.setTransactionType(ICMSConstant.INSTANCE_INTERNAL_LIMIT);
            trxValue.setTransactionID(trxID);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_READ_INTERNAL_LIMIT_BY_TRX_ID);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

	public IInternalLimitParameterTrxValue checkerApproveILP(ITrxContext trxContext,
                                              IInternalLimitParameterTrxValue trxValue) throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

	public IInternalLimitParameterTrxValue checkerRejectILP(ITrxContext trxContext,
                                              IInternalLimitParameterTrxValue trxValue) throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

	/*public IInternalLimitParameterTrxValue makerCloseILP(
			ITrxContext trxContext, IInternalLimitParameterTrxValue trxValue)
			throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}*/
	
	public IInternalLimitParameterTrxValue makerCancelUpdateILP(ITrxContext trxContext,
			                                IInternalLimitParameterTrxValue trxValue) throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}


	public IInternalLimitParameterTrxValue makerSaveILP(ITrxContext trxContext,
                                            IInternalLimitParameterTrxValue trxValue) throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_MAKER_SAVE_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

	public IInternalLimitParameterTrxValue makerUpdateILP(ITrxContext trxContext,
                                              IInternalLimitParameterTrxValue trxValue) throws InternalLimitException {
		try {
			trxValue = formulateTrxValue(trxContext, trxValue);
            OBCMSTrxParameter param = new OBCMSTrxParameter();
            param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_INTERNAL_LIMIT);
            return operate(trxValue, param);
		} catch (Exception e) {
			throw new InternalLimitException(e);
		}
	}

//	private SBInternalLimitProxy getSBInternalLimitProxy()
//			throws InternalLimitException {
//		return (SBInternalLimitProxy) BeanController.getEJB(
//				ICMSJNDIConstant.SB_INTERNAL_LIMIT_PROXY_JNDI,
//				SBInternalLimitProxyHome.class.getName());
//	}

    private IInternalLimitParameterTrxValue operate(ITrxValue trxVal, 
                                                    ITrxParameter param) throws InternalLimitException {
		try {
//			ITrxController controller = new InternalLimitParameterTrxControllerFactory().getController(trxVal, param);
            ITrxController controller = getInternalLimitParameterTrxControllerFactory().getController(trxVal, param);
			trxVal = controller.operate(trxVal, param).getTrxValue();
			return (IInternalLimitParameterTrxValue) trxVal;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalLimitException(e);
		}
	}

    private IInternalLimitParameterTrxValue formulateTrxValue(ITrxContext anITrxContext,
                                                              IInternalLimitParameterTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_INTERNAL_LIMIT);
		return trxValue;
	}
}
