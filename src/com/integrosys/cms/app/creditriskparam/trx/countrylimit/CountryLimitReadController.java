/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

import java.util.Map;

/**
 * This transaction controller is to be used for reading Country Limit.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CountryLimitReadController extends AbstractTrxController implements ITrxOperationFactory
{

    private Map nameTrxOperationMap;

    public Map getNameTrxOperationMap() {
        return nameTrxOperationMap;
    }

    public void setNameTrxOperationMap(Map nameTrxOperationMap) {
        this.nameTrxOperationMap = nameTrxOperationMap;
    }

    /**
     * Default constructor.
     */
    public CountryLimitReadController()
    {}

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     *
     * @return instance of Country Limit For GP5 Exposure
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_COUNTRY_LIMIT;
    }

    /**
     * This operate method invokes the operation for a read operation.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction result
     * @throws TrxParameterException if the transaction value and param is invalid
     * @throws TransactionException on error operating the transaction
     * @throws TrxControllerException on any other errors encountered
     */
    public ITrxResult operate (ITrxValue trxVal, ITrxParameter param)
        throws TrxParameterException, TrxControllerException, TransactionException
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

    /**
     * Get operation for the transaction given the value and param.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction operation
     * @throws TrxParameterException if the transaction parameter is invalid
     */
    public ITrxOperation getOperation (ITrxValue trxVal, ITrxParameter param)
        throws TrxParameterException
    {
        if (param == null) {
            throw new TrxParameterException("ITrxParameter is null!");
        }
        String action = param.getAction();

        DefaultLogger.debug (this, "Action is " + action);

        if (action == null) {
            throw new TrxParameterException ("Action is null!");
        }

        if (action.equals(ICMSConstant.ACTION_READ_COUNTRY_LIMIT) ) {
//            return new ReadCountryLimitOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadCountryLimitOperation");
        }
        else if (action.equals (ICMSConstant.ACTION_READ_COUNTRY_LIMIT_BY_TRXID)) {
//            return new ReadCountryLimitByTrxIDOperation();
            return (ITrxOperation) getNameTrxOperationMap().get("ReadCountryLimitByTrxIDOperation");
        }
        else {
            throw new TrxParameterException ("Unknow Action: " + action + ".");
        }
    }
}
