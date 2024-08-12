package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;

import java.util.HashMap;

/**
* Describe this class.
* Purpose: for checker to Reject the transaction
* Description: command that let the checker to Reject the transaction that being make by the maker
*
* @author $Author: Jitu<br
* @version $Revision: 1$
* @since $Date: 2007/02/08$
* Tag: $Name$
*/

public class CheckerRejectEditCounterpartyCommand extends AbstractCommand implements ICommonEventConstant {

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */

    public String[][] getParameterDescriptor() {
        return (new String[][]
          {
          {"ICCICounterpartyDetailsTrxValue", "com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue", SERVICE_SCOPE},
          {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
          }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */

    public String[][] getResultDescriptor() {
        return (new String[][]
          {
            {"request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
          }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here approval for Liquidation is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();

        OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue trxVal = (ICCICounterpartyDetailsTrxValue)map.get("ICCICounterpartyDetailsTrxValue");

        if (trxVal ==null) {
             throw (new CommandProcessingException("ICCICounterpartyDetailsTrxValue is null"));
        }

        try {
            ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
            trxVal = custproxy.checkerRejectUpdateCCI(trxContext, trxVal);
            resultMap.put("request.ITrxValue", trxVal);

        }catch (CCICounterpartyDetailsException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
        return returnMap;
    }

}
