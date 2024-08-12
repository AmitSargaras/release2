package com.integrosys.cms.ui.custgrpi.groupmember;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;

import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 6:50:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareGroupMemberCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
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

        DefaultLogger.debug(this, "Inside of doExecute()");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        ICustGrpIdentifierTrxValue itrxValue = ((ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue));
        String event = (String) map.get("from_event");

        ICustGrpIdentifier stagingObj;
        if (itrxValue != null) {
            stagingObj = itrxValue.getStagingCustGrpIdentifier();
//            System.out.println("PrepareGroupMemberCommand  >> stagingObj.getGroupName() = " + stagingObj.getGroupName());
        }

        result.put("from_event", event);
        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        DefaultLogger.debug(this, "Going out of doExecute()");
        return temp;
    }
}
