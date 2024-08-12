package com.integrosys.cms.app.creditriskparam.trx.productlimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * Author: Priya
 * Author: KC Chin
 * Date: Oct 9, 2009
 */
public class ProductLimitParameterReadController extends AbstractTrxController implements ITrxOperationFactory {
	
	private static final long serialVersionUID = 1L;

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    public ProductLimitParameterReadController() {
    }

    public String getInstanceName() {
        return ICMSConstant.INSTANCE_PRODUCT_LIMIT_PARAMETER;
    }

    public ITrxResult operate(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException, TrxControllerException, TransactionException {
        if (iTrxValue == null)
            throw new TrxParameterException("ITrxValue is NULL");

        if (iTrxParameter == null)
            throw new TrxParameterException("ITrxParameter is NULL");

        iTrxValue = setInstanceName(iTrxValue);
        ITrxOperation op = getOperation(iTrxValue, iTrxParameter);
        CMSReadTrxManager mgr =  new CMSReadTrxManager();

        ITrxResult result = mgr.operateTransaction(op, iTrxValue);
        return result;
    }


    public ITrxOperation getOperation(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
        if (iTrxParameter == null)
            throw new TrxParameterException("ITrxParameter is NULL");

        String action = iTrxParameter.getAction();

        if (action != null) {
            if (ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadProductLimitParameterOperation");
            } else if (ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_TRXID.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadProductLimitParameterByTrxIdOperation");
            } else if (ICMSConstant.ACTION_READ_PRODUCT_LIMIT_PARAMETER_BY_ID.equals(action)) {
                return (ITrxOperation) getNameTrxOperationMap().get("ReadProductLimitParameterByIdOperation");
            }

            throw new TrxParameterException("Unknow Action: " + action + ".");
        }
        
        throw new TrxParameterException("Action is null!");
    }
}
