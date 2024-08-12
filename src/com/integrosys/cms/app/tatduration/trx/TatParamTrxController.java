package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 6:08:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class TatParamTrxController extends CMSTrxController {


    private Map nameTrxOperationMap;

    // ****************************
    // Getter and Setter Methods
    // ****************************
    /**
     * @return &lt;name, ITrxOperation&gt; pair map to be injected, name and
     *         ITrxOperation name will be the same.
     */
    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }


    // ****************************
    // Original Methods
    // ****************************
    /**
     * Default Constructor
     */
    public TatParamTrxController() 
    {
    }

    
    /**
     * Return the instance name associated to this ITrxController. The instance
     * name refers to the instance of the state transition table
     *
     * @return String
     */
    public String getInstanceName() 
    {
        return ITatParamConstant.INSTANCE_TAT_DURATION;
    }

    /**
     * Get transaction operation given the transaction value and parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if
     *         transaction parameter is invalid
     */
    public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException 
    {
        ITrxOperation op = factoryOperation(value, param);
        return op;
    }


    /**
     * Helper method to get the operation given the transaction value and
     * transaction parameter.
     *
     * @param value is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction operation
     * @throws TrxParameterException if the transaction parameter is invalid
     */
    private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
        String action = param.getAction();
        if (null == action) {
            throw new TrxParameterException("Action is null in ITrxParameter!");
        }

        String toState = value.getToState();

        if (action.equals(ITatParamConstant.ACTION_MAKER_SUBMIT_TAT_DURATION)) 
        {
        	 return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitTatParamDurationOperation");
        }
        
        if (action.equals(ITatParamConstant.ACTION_CHECKER_APPROVE_TAT_DURATION)) 
        {
        	 return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveTatParamOperation");
        }
        
        if (action.equals(ITatParamConstant.ACTION_CHECKER_REJECT_TAT_DURATION)) 
        {
        	 return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectTatParamOperation");
        }
        
        if (action.equals(ITatParamConstant.ACTION_MAKER_CLOSE_TAT_DURATION)) 
        {
        	 return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedTatParamOperation");
        }
        
        DefaultLogger.debug(this, "No operations found !");

        throw new TrxParameterException("No operations found!");
    }



}
