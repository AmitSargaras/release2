package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.OBGroupCreditGrade;
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
public class ReadGroupCreditGradeCommand extends AbstractCommand {



    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"status", "java.lang.String", REQUEST_SCOPE},
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
                {"approvedBy", "java.lang.String", REQUEST_SCOPE},
                {"lastReviewDt", "java.lang.String", REQUEST_SCOPE},
                {"status", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.act_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", REQUEST_SCOPE},
                {CustGroupUIHelper.stg_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", REQUEST_SCOPE},
                {CustGroupUIHelper.form_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", REQUEST_SCOPE},
                {CustGroupUIHelper.form_groupCreditGradeObj, "java.lang.Object", FORM_SCOPE},
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

        DefaultLogger.debug(this, "Inside doExecute()");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String status = (String) map.get("status");
        String indexID = (String) map.get("indexID");
        String itemType = (String) map.get("itemType");
        String event = (String) map.get("event");
        String from_event = (String) map.get("from_event");

        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);

        Debug(" event =" + event);
        Debug(" from_event= " + from_event);

        ICustGrpIdentifier groupObj = null;
        IGroupCreditGrade groupCreditGradeObj = null;

        long index = 0;
        if (indexID != null && !indexID.equals("")) {
            index = Long.parseLong(indexID);
        } else {
        	DefaultLogger.debug(this,"indexID is null ");
        }
        IGroupCreditGrade actualObj = null;
        IGroupCreditGrade stageObj = null;

        if ("read".equals(from_event)
                || "process".equals(from_event)
                ||  "prepare_delete".equals(from_event)
                ||  "prepare_delete_return".equals(from_event)
                ) {
            groupObj = itrxValue.getCustGrpIdentifier();
            //groupCreditGradeObj = getItem(groupObj.getGroupCreditGrade(), index);
            if (groupObj != null && groupObj.getGroupCreditGrade() != null){
                    groupCreditGradeObj = groupObj.getGroupCreditGrade()[(int) index];
            }

            ICustGrpIdentifier act = itrxValue.getCustGrpIdentifier();
            ICustGrpIdentifier stg = itrxValue.getStagingCustGrpIdentifier();
            if (stg != null) {
                stageObj = getItem(stg.getGroupCreditGrade(), index);
            }
            if (act != null) {
                actualObj = getItem(act.getGroupCreditGrade(), index);
            }
        } else {
            if ("indexdel".equals(status)){    // If the record is deleted by the maker then need to get from Actual
                 groupObj = itrxValue.getCustGrpIdentifier();
                 if (groupObj != null && groupObj.getGroupCreditGrade() != null){
                    groupCreditGradeObj = groupObj.getGroupCreditGrade()[(int) index];
                }
          }else{
                groupObj = itrxValue.getStagingCustGrpIdentifier();
                if (groupObj != null && groupObj.getGroupCreditGrade() != null){
                    groupCreditGradeObj = groupObj.getGroupCreditGrade()[(int) index];
                }
            }
        }


        String return_event = event ;
         if ("maker_close_cgid".equals(from_event)) {
            return_event = "maker_close_return";
        } else if ("checker_edit_cgid".equals(from_event)) {
            return_event = "process_return";
        }else if ("checker2_edit_cgid".equals(from_event)) {
            return_event = "process2_return";
        }else  if ("to_track".equals(from_event) || "track_return".equals(from_event)) {
            return_event = "track_return";
         }else  if ("maker2_edit".equals(from_event) || "maker2_edit_return".equals(from_event)) {
            return_event = "maker2_edit_return";
        }else  if ("process2_update".equals(from_event) || "process2_update_return".equals(from_event)
                 || "prepare2".equals(from_event)       || "prepare2_update".equals(from_event)
                 ) {
            return_event = "process2_update_return";
         }else  if ("maker2_close_cgid".equals(from_event) || "maker2_close_return".equals(from_event)) {
            return_event = "maker2_close_return";
         }else  if ("maker2_edit_cgid_reject".equals(from_event) || "maker2_edit_cgid_reject_return".equals(from_event)) {
            return_event = "maker2_edit_cgid_reject_return";
         }else  if ("prepare_delete".equals(from_event) || "prepare_delete_return".equals(from_event)) {
            return_event = "prepare_delete_return";
         }

        Debug(" $$$$$$$$$$$$$$ Event = " + event  +" to [return_event] = " + return_event);

        result.put("event", return_event);
        result.put("indexID", indexID);
        result.put("itemType", CustGroupUIHelper.GROUPCREDITGRADE);
        result.put("from_event", from_event);

        Debug(" groupCreditGradeObj =" + (groupCreditGradeObj != null ? groupCreditGradeObj.getRatingCD() : "null"));


        result.put(CustGroupUIHelper.form_groupCreditGradeObj, groupCreditGradeObj);
        result.put(CustGroupUIHelper.act_groupCreditGradeObj, actualObj);
        result.put(CustGroupUIHelper.stg_groupCreditGradeObj, stageObj);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return temp;
    }


    private IGroupCreditGrade getItem(IGroupCreditGrade temp[], long itemRef) {
        IGroupCreditGrade item = null;
        if (temp == null) {
            return item;
        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].getGroupCreditGradeIDRef() == itemRef) {
                item = temp[i];
                break;
            }
        }
        return item;
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,ReadGroupCreditGradeCommand.class.getName() +  " = " + msg);
    }

}
