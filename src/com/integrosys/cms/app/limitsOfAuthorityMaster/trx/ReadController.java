package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import java.util.Map;

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

public class ReadController extends AbstractTrxController implements ITrxOperationFactory{

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public ReadController() {
	}

	public String getInstanceName() {
        return ICMSConstant.LimitsOfAuthorityMaster.INSTANCE_LIMITS_OF_AUTHORITY_MASTER;
    }
    
    public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException, TrxControllerException, TransactionException {
        if ( value==null) {
            throw new TrxParameterException("ITrxValue is null!");
        }
        if (param==null) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        value = setInstanceName(value);
        DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
        ITrxOperation op = getOperation(value, param);
        DefaultLogger.debug(this, "From state " + value.getFromState());
        CMSReadTrxManager mgr = new CMSReadTrxManager();
         
        try {
        	ITrxResult result = mgr.operateTransaction(op, value);
            return result;
        }
        catch (Exception re) {
            throw new TrxControllerException("Caught Unknown Exception: " + re.getMessage());
        }
    }
    
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        if (null == param) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        String action = param.getAction();
        if (action != null) {
            if (action.equals(ICMSConstant.LimitsOfAuthorityMaster.ACTION_READ_LIMITS_OF_AUTHORITY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadOperation");
            } else if (action.equals(ICMSConstant.LimitsOfAuthorityMaster.ACTION_READ_LIMITS_OF_AUTHORITY_ID)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadIDOperation");
            }else{

            throw new TrxParameterException("Unknow Action: " + action + ".");
            }
        }else{
        throw new TrxParameterException("Action is null!");
        }
	}

}