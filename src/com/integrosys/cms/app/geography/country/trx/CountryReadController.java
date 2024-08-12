package com.integrosys.cms.app.geography.country.trx;

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

public class CountryReadController extends AbstractTrxController implements ITrxOperationFactory {

	 private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}
	
	/**
     * Default Constructor
     */
    public CountryReadController() {
    }

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param)throws TrxParameterException {
		 if (null == param) {
	            throw new TrxParameterException("ITrxParameter is null!");
	        }
        String action = param.getAction();
        if (action != null) {
            if (action.equals(ICMSConstant.ACTION_READ_COUNTRY)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadCountryOperation");
            } else if (action.equals(ICMSConstant.ACTION_READ_COUNTRY_ID)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadCountryIDOperation");
            }else{

            throw new TrxParameterException("Unknow Action: " + action + ".");
            }
        }else{
        throw new TrxParameterException("Action is null!");
        }
	}


	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COUNTRY;
	}


	public ITrxResult operate(ITrxValue value, ITrxParameter param)throws TrxParameterException, TrxControllerException,TransactionException {
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
        try {
        	ITrxResult result = mgr.operateTransaction(op, value);
            return result;
        }
        catch (Exception re) {
            throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
        }
	}

}
