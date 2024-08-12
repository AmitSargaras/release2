package com.integrosys.cms.app.tatduration.trx;

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
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */
public class TatParamReadController extends AbstractTrxController implements ITrxOperationFactory 
{

    private Map nameTrxOperationMap;

    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() 
    {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) 
    {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    /**
     * Default Constructor
     */
    public TatParamReadController() 
    {
    }

    /**
     * Return instance name
     *
     * @return String - the instance name
     */
    public String getInstanceName() 
    {
        return ITatParamConstant.TAT_DURATION;
    }

    /**
     * This operate method invokes the operation for a read operation.
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxResult - the trx result
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException,
     *          TrxControllerException, TransactionException on errors
     */
    public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException, TrxControllerException, TransactionException 
    {
        if (null == value) 
            throw new TrxParameterException("ITrxValue is null!");
        if (null == param) 
            throw new TrxParameterException("ITrxParameter is null!");
        
        value = setInstanceName(value);
        DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
        ITrxOperation op = getOperation(value, param);
        DefaultLogger.debug(this, "From state " + value.getFromState());
        CMSReadTrxManager mgr = new CMSReadTrxManager();

        ITrxResult result = null;
        try 
        {
            result = mgr.operateTransaction(op, value);
            return result;
        }
        catch (TransactionException te) 
        {
            throw te;
        }
        catch (Exception re) 
        {
            throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
        }
    }

    /**
     * Get the ITrxOperation
     *
     * @param value - ITrxValue
     * @param param - ITrxParameter
     * @return ITrxOperation - the trx operation
     * @throws TrxParameterException on errors
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException 
    {
        if (null == param) 
            throw new TrxParameterException("ITrxParameter is null!");
        
        String action = param.getAction();
        if (action != null)
        {
            if (action.equals(ITatParamConstant.ACTION_READ_TAT_PARAM)) 
                return (ITrxOperation) getNameTrxOperationMap().get("ReadTatParamOperation");
            else if (action.equals(ITatParamConstant.ACTION_READ_TAT_PARAM_ID)) 
                return (ITrxOperation) getNameTrxOperationMap().get("ReadTatParamIdOperation");
            

            throw new TrxParameterException("Unknow Action: " + action + ".");
        }
        throw new TrxParameterException("Action is null!");
	}

}
