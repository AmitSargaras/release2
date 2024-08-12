package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 9, 2007
 * Time: 1:33:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveCustGrpIdentifierCommand extends AbstractCommand {


    /**
     * Defines a two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"deleteItem", "java.lang.String", REQUEST_SCOPE},
        });
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
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
        });
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside  doExecute()");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        //String deleteIds = (String) map.get("deleteItem");
        String itemType = (String) map.get("itemType");

        Debug("itemType = " + itemType);

        ICustGrpIdentifier frmObj = (ICustGrpIdentifier) map.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        CustGroupUIHelper.printChildMembers("RemoveCustGrpIdentifierCommand Before Staging " , itrxValue.getStagingCustGrpIdentifier());

//        ICustGrpIdentifier actual = itrxValue.getCustGrpIdentifier();
        if (itrxValue != null && itrxValue.isHasDeleteErr()) {
            if (CustGroupUIHelper.GROUPSUBLIMIT.equals(itemType)) {
                exceptionMap.put("hasLimitBookSubErr", new ActionMessage("error.group.haslimitbooking"));
            }
            if (CustGroupUIHelper.GROUPOTRLIMIT.equals(itemType)) {
                exceptionMap.put("hasLimitBookOthErr", new ActionMessage("error.group.haslimitbooking"));
            }
        }


        IGroupCreditGrade[] oldGradeList = null;
        IGroupCreditGrade[] newGradeList = null;

        if (frmObj != null) {
            oldGradeList = frmObj.getGroupCreditGrade();
            if (oldGradeList != null && oldGradeList.length > 0) {
                Debug(" newGradeList= " + oldGradeList.length);
                for (int i = 0; i < oldGradeList.length; i++) {
                	DefaultLogger.debug(this," itrxValue Grade TypeCD[" + i + "] = " + oldGradeList[i].getTypeCD());
                }
            } else {
               Debug(" oldGradeList = null");
            }
        } else {
            Debug(" itrxValue Grp profile IS  NULL");
        }

        itrxValue.setStagingCustGrpIdentifier(frmObj);

        CustGroupUIHelper.printChildMembers("RemoveCustGrpIdentifierCommand After Staging " , itrxValue.getStagingCustGrpIdentifier())  ;

        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);
        result.put(CustGroupUIHelper.form_custGrpIdentifierObj, frmObj);
        result.put("itemType", map.get("itemType"));

        DefaultLogger.debug(this, "Inside  doExecute()");
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"RemoveCustGrpIdentifierCommand = " + msg);
    }
}
