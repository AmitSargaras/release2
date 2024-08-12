package com.integrosys.cms.ui.cci;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterparty;
import com.integrosys.cms.app.cci.proxy.ICCICustomerProxy;
import com.integrosys.cms.app.cci.proxy.CCICustomerProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.struts.action.ActionMessage;

/**
 * Describe this class.
 * Purpose: for checker to approve the transaction
 * Description: command that let the checker to approve the transaction that being make by the maker
 *
 * @author $Author: Jitu<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$
 *        Tag: $Name$
 */

public class CheckerApproveEditCounterpartyCommand extends AbstractCommand implements ICommonEventConstant {

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
                        {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
        HashMap exceptionMap = new HashMap();

        OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
        ICCICounterpartyDetailsTrxValue trxVal = (ICCICounterpartyDetailsTrxValue) map.get("ICCICounterpartyDetailsTrxValue");
        DefaultLogger.debug(this, "Inside doExecute()  = " + trxVal);

        if (trxVal == null){
            throw (new CommandProcessingException("ICCICounterpartyDetailsTrxValue is null"));
        }

        try {
            ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
            if (isExistCCICustomer(trxVal, exceptionMap)) {
                DefaultLogger.debug(this,"customer exist in other CCI");
                exceptionMap.put("isExistCCICustomer", new ActionMessage("error.string.cci.custonmerexist"));
            }else{
                custproxy.checkerApproveUpdateCCI(trxContext, trxVal);
            }
            resultMap.put("request.ITrxValue", trxVal);

        } catch (CCICounterpartyDetailsException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
         returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }

    private boolean isExistCCICustomer(ICCICounterpartyDetailsTrxValue trxVal,HashMap exceptionMap) {
        long groupCCINo = ICMSConstant.LONG_INVALID_VALUE;
        ICCICustomerProxy custproxy = CCICustomerProxyFactory.getProxy();
        ICCICounterpartyDetails details = trxVal.getStagingCCICounterpartyDetails();
        ICCICounterpartyDetails actual = trxVal.getCCICounterpartyDetails();
        if (details == null)
            return false;
         if (actual != null){
            groupCCINo = actual.getGroupCCINo();
         }


        ICCICounterparty[]  cciObj = details.getICCICounterparty();
        if (cciObj == null)
        return false;

        //System.out.println("CheckerApproveEditCounterpartyCommand obj.length Before= " + cciObj.length);
        List list = new ArrayList();

        for (int i=0 ; i< cciObj.length ; i++){
            if (!cciObj[i].getDeletedInd())
            list.add(cciObj[i].getSubProfileID()+"") ;
        }
       String[] obj = (String[]) list.toArray(new String[0]);

       if (obj != null && obj.length> 0)
        try {
//             System.out.println("CheckerApproveEditCounterpartyCommand isExistCCICustomer = " + list);
            HashMap map = custproxy.isExistCCICustomer(groupCCINo, obj);
            if ( ICMSConstant.TRUE_VALUE.equals(map.get("isExistCCICustomer"))){
                List errorList = (List)  map.get("ERRORMSG" );
                if (errorList != null && !errorList.isEmpty()){
                    for (int i=0 ; i < errorList.size() ; i++){
                        String errorExistCCICustomer = (String) errorList.get(i) ;
                        exceptionMap.put("errorExistCCICustomer", new ActionMessage("error.string.cci.errormsg",errorExistCCICustomer));
//                        System.out.println("ERRORMSG = " + errorExistCCICustomer);
                    }
                }
                return true;
            }
        } catch (CCICounterpartyDetailsException e) {

        }
        return false;
    }

}
