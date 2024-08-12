/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.internalcreditrating;

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
 * @author priya
 *
 */
public class InternalCreditRatingReadController extends AbstractTrxController implements ITrxOperationFactory {

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    private static final long serialVersionUID = 1L;
	
	public String getInstanceName() {
        return ICMSConstant.INSTANCE_INTERNAL_CREDIT_RATING;
    }
	
	public ITrxResult operate (ITrxValue trxVal, ITrxParameter param) throws TrxParameterException, TrxControllerException, TransactionException
	{
	    if (trxVal == null) {
	        throw new TrxParameterException ("ITrxValue is null!");
	    }
	    if (param == null) {
	        throw new TrxParameterException("ITrxParameter is null!");
	    }
	
	    trxVal = setInstanceName (trxVal);
	    DefaultLogger.debug(this, "Instance Name: " + trxVal.getInstanceName());
	    ITrxOperation op = getOperation (trxVal, param);
	    CMSReadTrxManager mgr = new CMSReadTrxManager();
	
	    try {
	        ITrxResult result = mgr.operateTransaction(op, trxVal);
	        return result;
	    }
	    catch (TransactionException e) {
	        throw e;
	    }
	    catch (Exception e) {
	        throw new TrxControllerException ("Caught Unknown Exception: " + e.toString());
	    }
	}
	
	public ITrxOperation getOperation (ITrxValue trxVal, ITrxParameter param) throws TrxParameterException
	{
	    if (param == null) {
	        throw new TrxParameterException("ITrxParameter is null!");
	    }
	    String action = param.getAction();
	
	    DefaultLogger.debug (this, "Action is " + action);
	
	    if (action == null) {
	        throw new TrxParameterException ("Action is null!");
	    }
	
	    if (action.equals(ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING) ) {
//	        return new ReadInternalCreditRatingOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadInternalCreditRatingOperation");
	    }
	    else if (action.equals (ICMSConstant.ACTION_READ_INTERNAL_CREDIT_RATING_BY_TRXID)) {
//	        return new ReadInternalCreditRatingByTrxIDOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadInternalCreditRatingByTrxIDOperation");
	    }
	    else {
	        throw new TrxParameterException ("Unknown Action: " + action + ".");
	    }
	}

}
