package com.integrosys.cms.ui.custgrpi.groupsublimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSubLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 7, 2007
 * Time: 7:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadGroupSubLimitCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
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
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.act_groupSubLimitObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", REQUEST_SCOPE},
                {CustGroupUIHelper.stg_groupSubLimitObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", REQUEST_SCOPE},
                {CustGroupUIHelper.form_groupSubLimitObj, "java.lang.Object", FORM_SCOPE},
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

        String indexID = (String) map.get("indexID");
        String itemType = (String) map.get("itemType");
        String event = (String) map.get("event");
        String from_event = (String) map.get("from_event");

        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        Debug(" event =" + event);
        Debug(" from_event= " + from_event);

        ICustGrpIdentifier groupObj = null;
        IGroupSubLimit groupGroupSubLimitObj = null;
        IGroupSubLimit actualObj = null;
        IGroupSubLimit stageObj = null;

        long index = 0;
        if (indexID != null && !indexID.equals("")) {
            index = Long.parseLong(indexID);
        } else {
        	DefaultLogger.debug(this,"indexID is null ");
        }


        if ("read".equals(from_event) || "process".equals(from_event)) {
            groupObj = itrxValue.getCustGrpIdentifier();
            //groupGroupSubLimitObj = getItem(groupObj.getGroupSubLimit(), index);
            groupGroupSubLimitObj = groupObj.getGroupSubLimit()[(int) index];
            ICustGrpIdentifier act = itrxValue.getCustGrpIdentifier();
            ICustGrpIdentifier stg = itrxValue.getStagingCustGrpIdentifier();
            if (act != null) {
                actualObj = getItem(act.getGroupSubLimit(), index);
            }
            if (stg != null) {
                stageObj = getItem(stg.getGroupSubLimit(), index);
            }

        } else {
            groupObj = itrxValue.getStagingCustGrpIdentifier();
            if (groupObj != null){
                groupGroupSubLimitObj = groupObj.getGroupSubLimit()[(int) index];
            }
        }


        if ("maker_close_cgid".equals(from_event)) {
            event = "maker_close_cgid_return";
        } else if ("checker_edit_cgid".equals(from_event)) {
            event = "process_return";
        }
        result.put("indexID", indexID);
        result.put("itemType", CustGroupUIHelper.GROUPSUBLIMIT);
        result.put("event", event);
        result.put("from_event", from_event);


        Debug(" form_groupSubLimitObj =" + (groupGroupSubLimitObj != null ? groupGroupSubLimitObj.getSubLimitTypeCD() : "null"));

        result.put(CustGroupUIHelper.form_groupSubLimitObj, groupGroupSubLimitObj);
        result.put(CustGroupUIHelper.act_groupSubLimitObj, actualObj);
        result.put(CustGroupUIHelper.stg_groupSubLimitObj, stageObj);

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private IGroupSubLimit getItem(IGroupSubLimit temp[], long itemRef) {
        IGroupSubLimit item = null;
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].getGroupSubLimitIDRef() == itemRef) {
                item = temp[i];
                break;
            }
        }
        return item;
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"ReadGroupSubLimitCommand = " + msg);
    }

}
