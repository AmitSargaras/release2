package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.app.creditriskparam.proxy.internallimit.IInternalLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

public class InternalLmtCheckerReadCommand extends InternalLimitCommand implements ICommonEventConstant {

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"trxID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"internalLmtParamTrxValue", "com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue", SERVICE_SCOPE},
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
        return (new String[][]{
            {"internalLmtParamTrxValue", "com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue", SERVICE_SCOPE},
          // {"countries.map", "java.util.HashMap", SERVICE_SCOPE},
          //  {"obInternalLmtParam", "java.util.list", FORM_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE}
                }
           );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here checker get the value for Asset Life is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
//        System.out.println("-------------------B4 trxId----------------------");

        String trxId = (String)map.get("trxID");
       
        DefaultLogger.debug(this, "Inside doExecute()  = "+trxId );

       // OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");

        try {
            IInternalLimitProxy proxy = getInternalLimitProxy();
            
            IInternalLimitParameterTrxValue internalLimitParameterTrxValue = null;
              if (trxId == null) {
            		internalLimitParameterTrxValue = (IInternalLimitParameterTrxValue)map.get("internalLmtParamTrxValue");
            	}
            	else {
            		internalLimitParameterTrxValue =  proxy.getILParamTrxValueByTrxID(trxId);
            	}

            //IInternalLimitParameterTrxValue internalLimitParameterTrxValue =  proxy.getILParamTrxValueByTrxID(trxId);
            resultMap.put("internalLmtParamTrxValue", internalLimitParameterTrxValue );
            //resultMap.put("obInternalLmtParam", internalLimitParameterTrxValue.getStagingILPList());
            //DefaultLogger.debug(this, " getStagingILPList::::" + internalLimitParameterTrxValue.getStagingILPList() );

        }catch (InternalLimitException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            DefaultLogger.error(this,e);
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
        return returnMap;
    }

}
