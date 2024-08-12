package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.app.creditriskparam.proxy.internallimit.IInternalLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;


public class InternalLmtMakerEditCommand extends InternalLimitCommand implements ICommonEventConstant {

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
                {"obInternalLmtParam", "java.util.Collection", FORM_SCOPE},   //com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter
                {"internalLmtParamTrxValue", "com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
              //  {"internalLmtParam", "com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter", FORM_SCOPE},
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
            {"request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
                }
           );
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();

       // OBInternalLimitParameter internalLmtParam = (OBInternalLimitParameter)map.get("internalLmtParam");
        //    DefaultLogger.debug(this, "internalLmtParam before edit "+ internalLmtParam);
            
        Collection internalLmt = (Collection)map.get("obInternalLmtParam");
         DefaultLogger.debug(this, "internalLmt before edit "+ internalLmt);
        OBInternalLimitParameter[] ObInternalLimitParameter = null;
        ObInternalLimitParameter =  new OBInternalLimitParameter[internalLmt.size()];
        ObInternalLimitParameter = (OBInternalLimitParameter[])internalLmt.toArray(ObInternalLimitParameter);
        DefaultLogger.debug(this, "@@@Debug:::OBInternalLimitParameter:::" + internalLmt);
        OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");

        IInternalLimitParameterTrxValue internalLmtParamTrxValue = (IInternalLimitParameterTrxValue)map.get("internalLmtParamTrxValue");
    

        try {
            IInternalLimitProxy proxy = getInternalLimitProxy();
           OBInternalLimitParameter ObInternalLmtParameter = null;
            //for (int i = 0; i < internalLmtParam.length; i++) {
            //   ObInternalLmtParameter = internalLmtParam[i];
            //}
            ArrayList list = (ArrayList) internalLmt;
            List internalLmtParam = (List) list;
		        internalLmtParamTrxValue.setStagingILPList(internalLmtParam);
		        DefaultLogger.debug(this, "setStagingILPList ... ::::" + internalLmtParamTrxValue.getStagingILPList());
            IInternalLimitParameterTrxValue trxValue = proxy.makerUpdateILP(trxContext, internalLmtParamTrxValue);
            
            resultMap.put("request.ITrxValue",trxValue);
            //resultMap.put("internalLmtParamTrxValue",trxValue);

        }catch (InternalLimitException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Skipping ...");
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
        return returnMap;
    }

}
