package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * This transaction controller is to be used for reading liquidation.
 * //new Read controlller added here on 19th Dec 2007
 *   This is used to read only transaction  by transaction Id
 * @author   $Author: Jitu<br>
 * @version  $Revision: $
 * @since    $Date: $   19th Dec 2007
 * Tag:      $Name: $  Jitendra
 */


public class CCICounterpartyDetailsReadController extends AbstractTrxController implements ITrxOperationFactory
{
    /**
     * Default constructor.
     */
    public CCICounterpartyDetailsReadController()
    {}

    /**
     * Return the instance name associated to this ITrxController.
     * The instance name refers to the instance of the state transition table.
     *
     * @return instance of liquidation
     */
    public String getInstanceName() {
        return ICMSConstant.INSTANCE_CCI_COUNTER_PARTY;
    }

    /**
     * This operate method invokes the operation for a read operation.
     *
     * @param trxVal is of type ITrxValue
     * @param param is of type ITrxParameter
     * @return transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if the transaction value and param is invalid
     * @throws com.integrosys.base.businfra.transaction.TransactionException on error operating the transaction
     * @throws com.integrosys.base.businfra.transaction.TrxControllerException on any other errors encountered
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
        //DefaultLogger.debug(this, "Instance Name: " + trxVal.getInstanceName());
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
     * @throws com.integrosys.base.businfra.transaction.TrxParameterException if the transaction parameter is invalid
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

        if (action.equals(ICMSConstant.ACTION_READ_CCI_COUNTER_PARTY) ) {
            return new ReadCounterpartyDetailsByTrxIDOperation();
        }else if (action.equals (ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID)) {
            return new ReadCounterpartyDetailsByTrxIDOperation();
        }else if (action.equals (ICMSConstant.ACTION_READ_CCIN_BY_GROUP_CCINO)) {
            return new ReadCounterpartyDetailsByGroupCCINoOperation();
        }else {
            throw new TrxParameterException ("Unknow Action: " + action + ".");
        }
    }
}
