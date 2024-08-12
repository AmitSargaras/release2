package com.integrosys.cms.ui.custexposure.group;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.GenerateLimitHelper;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.custexposure.proxy.group.GroupExposureProxyFactory;
import com.integrosys.cms.app.custexposure.proxy.group.IGroupExposureProxy;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.OBGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.OBGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.bus.group.*;
import com.integrosys.cms.app.limit.bus.*;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.custexposure.CustExposureUIHelper;


import java.util.*;

/**
* Describe this class.
* Purpose:This class implements command
* Description: For retrieving value in group svc
*
* @author $Grace Teh$<br>
* @version $Revision$
* @since $30 July 2008$
* Tag: $Name$
*/

public class ReadALLGroupExposureCommand extends AbstractCommand {

    public ReadALLGroupExposureCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"grpID", "java.lang.String", REQUEST_SCOPE},
                {"session.IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"GroupExposureTrxValue", "com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue", SERVICE_SCOPE},
               // {CustExposureUIHelper.service_CustExposureSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
              //  {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custexposure.bus.CustExposureSearchCriteria", GLOBAL_SCOPE},
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", FORM_SCOPE},
                {"session.IGrpExposure", "com.integrosys.cms.app.custexposure.bus.group.IGroupExposure", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},  
               // {CustExposureUIHelper.service_CustExposureSearchListObj, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
               // {IGlobalConstant.GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ, "com.integrosys.cms.app.custexposure.bus.CustExposureSearchCriteria", GLOBAL_SCOPE},
                {"GroupExposureTrxValue", "com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue", SERVICE_SCOPE},
                {"grpID", "java.lang.String", REQUEST_SCOPE},

        }
        );
    }

    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) inputMap.get("event");
        String grpID = (String) inputMap.get("grpID");

        OBTrxContext theOBTrxContext = (OBTrxContext) inputMap.get("theOBTrxContext");
        IGroupExposureTrxValue trxValue = (IGroupExposureTrxValue) inputMap.get("GroupExposureTrxValue");
        IGroupExposure aIGrpExposure = (IGroupExposure)inputMap.get("session.IGrpExposure");
        
        if (trxValue == null){
            trxValue = new OBGroupExposureTrxValue();
        }

        IGroupExposureProxy proxy = GroupExposureProxyFactory.getProxy();
        
        try {
           // if (grpID != null && !"".equals(grpID)){
           //     trxValue = proxy.getGroupExposure((Long.parseLong(grpID)));

           // }
            
            if(aIGrpExposure != null){
            IGroupExpCustGrp grpExpCustGrp = aIGrpExposure.getGroupExpCustGrp();
            
            IGroupExpBankEntity[] grpBankEntity = aIGrpExposure.getGroupExpBankEntity();
                
            IGroupExpCustGrpSubLimit[] grpSubLimit = grpExpCustGrp.getGroupExpCustGrpSubLimit();

            IGroupExpCustGrpOtrLimit[] grpOtrLimit = grpExpCustGrp.getGroupExpCustGrpOtrLimit();
    
            IGroupExpCustGrpEntityLimit[] grpEntityLimit = grpExpCustGrp.getGroupExpCustGrpEntityLimit();
            }
            
            trxValue.setGroupExposure(aIGrpExposure);

            resultMap.put("event", event);
            resultMap.put("IGrpExposure", aIGrpExposure);
            resultMap.put("session.IGrpExposure", aIGrpExposure);
            resultMap.put("GroupExposureTrxValue", trxValue);
            resultMap.put("grpID", grpID);

        } catch (Exception e) {
            //  throw (new CommandProcessingException(e.getMessage()));
            Debug("error in ");
        }
        DefaultLogger.debug(this, "Existing doExecute()\n");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


    /**
     * @param msg
     */
    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadALLGroupExposureCommand.class.getName() + " = " + msg);
    }


}
