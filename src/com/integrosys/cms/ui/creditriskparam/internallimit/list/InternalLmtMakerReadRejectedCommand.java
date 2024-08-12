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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

public class InternalLmtMakerReadRejectedCommand extends InternalLimitCommand implements ICommonEventConstant {

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
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"obInternalLmtParam", "com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter", FORM_SCOPE},
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
            {"obInternalLmtParam", "java.util.list", REQUEST_SCOPE},
          //  {"trxID", "java.lang.String", REQUEST_SCOPE }
        }
                );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Asset Life is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        
        String trxId = (String) map.get("trxID");
        trxId = trxId.trim();
        OBTrxContext  trxContext = (OBTrxContext ) map.get("theOBTrxContext");

        try {
            IInternalLimitProxy proxy = getInternalLimitProxy();
            
            // Get Trx By TrxID
            IInternalLimitParameterTrxValue internalLimitParameterTrxValue = proxy.getILParamTrxValueByTrxID(trxId);

            // if current status is other than ACTIVE & REJECTED, then show workInProgress.
            // i.e. allow edit only if status is either ACTIVE or REJECTED
            if (
                    (!internalLimitParameterTrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE))
                    &&
                    (!internalLimitParameterTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED))

            ) {
                resultMap.put("wip", "wip");
                resultMap.put("obInternalLmtParam", internalLimitParameterTrxValue.getStagingILPList());
            } else {
                resultMap.put("internalLmtParamTrxValue", internalLimitParameterTrxValue);
            }

            resultMap.put("obInternalLmtParam", internalLimitParameterTrxValue.getStagingILPList());
          //  resultMap.put("trxID",trxId );

        } catch (InternalLimitException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

}
