package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: ssathish
 * Date: Jul 2, 2003
 * Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ReadReturnGroupCreditGradeCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},

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
                {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
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

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        ICustGrpIdentifierTrxValue itrxValue = ((ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue));

        String from_event = (String) map.get("from_event");
        String event = (String) map.get("event");

        result.put("from_event", from_event);
        result.put("itemType", map.get("itemType"));
        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
