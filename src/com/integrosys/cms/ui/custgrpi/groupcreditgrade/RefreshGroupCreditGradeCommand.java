package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralUiUtil;
import com.integrosys.cms.ui.collateral.RefreshCollateralCommand;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.manualinput.aa.RatingList;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class RefreshGroupCreditGradeCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public RefreshGroupCreditGradeCommand() {
    }

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
             //   {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", FORM_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
                {"typeCD", "java.lang.String", REQUEST_SCOPE},

        }
        );
    }

    /**
     * Defines a two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
              //  {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"ratingCodes", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"ratingLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
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
        HashMap resultMap = new HashMap();

        String event = (String) map.get("event");
        String indexID = (String) map.get("indexID");
        String from_event = (String) map.get("from_event");
        String itemType = (String) map.get("itemType");
        String typeCD = (String) map.get("typeCD");

        IGroupCreditGrade groupCreditGradeObj = (IGroupCreditGrade) map.get(CustGroupUIHelper.form_groupCreditGradeObj);
        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }

        ICustGrpIdentifier stagingGroupObj = groupTrxValue.getStagingCustGrpIdentifier();

         if (typeCD==null) {
            typeCD = groupCreditGradeObj.getTypeCD();
         }

        Debug("event = " + event);
        Debug("from_event = " + from_event);
        Debug("type is " + typeCD);

        //getRatingCodes("ratingCodes", "ratingLabels", typeCD, result);

        if (stagingGroupObj != null) {
            Debug("checkOBj.get = " + stagingGroupObj.getGroupName());
        } else {
            Debug("checkOBj.getGroupName is  null");
        }
       // groupTrxValue.setStagingCustGrpIdentifier(stagingGroupObj);

        if ("refresh_create".equals(event)) {
              result.put("forwardPage", "refresh_create");
              resultMap.put("forwardPage", "refresh_create");
        }else if ("refresh_update".equals(from_event)) {
              result.put("forwardPage", "refresh_update");
              resultMap.put("forwardPage", "refresh_update");
        }else{
             Debug(" Check the event");
        }


        DefaultLogger.debug(this, "Going out of doExecute()");

       // result.put(CustGroupUIHelper.service_groupTrxValue, groupTrxValue);
        result.put("event", event);
        result.put("itemType", itemType);
        result.put("indexID", indexID);

        resultMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        resultMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return resultMap;

    }



     //getRatingCodes
    private void getRatingCodes(String nameValue, String namelabels, String codeType, HashMap result){
         Collection values = null;
         Collection labels = null;
         if(codeType != null){
            RatingList ratingCpt = RatingList.getInstance (codeType);
            values = ratingCpt.getRatingListID();
            labels = ratingCpt.getRatingListValue ();
         }
         if (values == null) {
            values = new ArrayList();
        }
        if (labels == null) {
            labels = new ArrayList();
        }
         result.put(nameValue, values);
         result.put(namelabels, labels);
    }


     private void Debug(String msg) {
    	 DefaultLogger.debug(this," RefreshGroupCreditGradeCommand = " + msg);
    }

}
