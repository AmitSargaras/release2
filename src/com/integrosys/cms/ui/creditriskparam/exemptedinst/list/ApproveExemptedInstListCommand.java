/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.ExemptedInstProxyFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptedinst.IExemptedInstProxy;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author siew kheat
 * 
 */
public class ApproveExemptedInstListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the current exmpted Inst entries to be saved as a whole.
              {"ExemptedInstTrxValue", 
                	"com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", SERVICE_SCOPE}, // Produce the offset.
              {"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }


    public String[][] getResultDescriptor() {
        return new String[][]{{"request.ITrxValue",
            "com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue", REQUEST_SCOPE}};
    }


    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

        	IExemptedInstTrxValue value = (IExemptedInstTrxValue)map.get(
                    "ExemptedInstTrxValue");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when going from "view limits" to "manual feeds
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);
            if (trxContext == null) {
                DefaultLogger.debug(this,
                        "trxContext obtained from map is null.");
            }

			IExemptedInst[] actualExemptedInstArr = value.getExemptedInst();
			IExemptedInst[] stagingExemptedInstArr = value.getStagingExemptedInst();
			
			DefaultLogger.debug(this, "actualExemptedInstArr : " + actualExemptedInstArr);
			DefaultLogger.debug(this, "stagingExemptedInstArr : " + stagingExemptedInstArr);
            
            IExemptedInstProxy proxy = ExemptedInstProxyFactory.getProxy();
            value = proxy.checkerApproveUpdateExemptedInst(trxContext, value);

            resultMap.put("request.ITrxValue", value);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
     
}
