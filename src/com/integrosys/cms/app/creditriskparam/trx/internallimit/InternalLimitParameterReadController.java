/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/ForexFeedGroupReadController.java,v 1.3 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.internallimit;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * This controller controls document item related read-operations.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public class InternalLimitParameterReadController extends AbstractTrxController implements ITrxOperationFactory {

	private static final long serialVersionUID = 1L;

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

	public InternalLimitParameterReadController() {
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_INTERNAL_LIMIT;
	}

	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException, TrxControllerException, TransactionException {
		
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		
		value = setInstanceName(value);
		DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
		ITrxOperation op = getOperation(value, param);
		DefaultLogger.debug(this, "From state " + value.getFromState());
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;
		try {
			result = mgr.operateTransaction(op, value);
			return result;
		} catch (TransactionException te) {
			throw te;
		} catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: "
					+ re.toString());
		}
		
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (ICMSConstant.ACTION_READ_INTERNAL_LIMIT.equals(action)) {
//			return new ReadInternalLimitParameterOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadInternalLimitParameterOperation");
		}
		else if (action.equals (ICMSConstant.ACTION_READ_INTERNAL_LIMIT_BY_TRX_ID)) {
//            return new ReadInternalLimitParameterByTrxIDOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadInternalLimitParameterByTrxIDOperation");
        }
		throw new TrxParameterException("Unknow Action: " + action + ".");
	}
	
}
