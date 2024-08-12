package com.integrosys.cms.ui.custgrpi.groupcreditgrade;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.OBCustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.OBGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierUIHelper;

import java.util.HashMap;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class AddGroupCreditGradeCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", FORM_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
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
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
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

        DefaultLogger.debug(this, "inside doExecute ");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        String event = (String) map.get("event");
        String itemType = (String) map.get("itemType");

        IGroupCreditGrade groupCreditGradeObj = (IGroupCreditGrade) map.get(CustGroupUIHelper.form_groupCreditGradeObj);
        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }

        Debug("groupTrxValue = " + groupTrxValue);
        Debug("itemType = " + itemType);
        Debug("event = " + event);

        ICustGrpIdentifier stagingGroupObj = groupTrxValue.getStagingCustGrpIdentifier();

        //Andy Wong, 2 July 2008: default BGEL field upon credit grade insertion
        stagingGroupObj.setIsBGEL(true);
        stagingGroupObj.setInternalLmt(CustGroupUIHelper.INT_LMT_CREDIT_RATE); //to Internal Credit Rating
        stagingGroupObj.setLastReviewDt(groupCreditGradeObj.getRatingDt());
        Amount grpLmt = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_CREDIT_RATE, groupCreditGradeObj.getRatingCD());
        if(grpLmt.getCurrencyCodeAsObject()==null) {
            grpLmt.setAmountAsBigDecimal(new BigDecimal(0));
            grpLmt.setCurrencyCode("MYR");
        }
        stagingGroupObj.setGroupLmt(grpLmt);
        stagingGroupObj.setGroupCurrency(grpLmt.getCurrencyCode());

        if (!"cancel".equals(event)) {
            this.addChildRecord(stagingGroupObj, groupCreditGradeObj);
        }

        if (stagingGroupObj != null) {
            Debug("checkOBj.get = " + stagingGroupObj.getGroupName());
        } else {
            Debug("checkOBj.getGroupName is  null");
        }
        groupTrxValue.setStagingCustGrpIdentifier(stagingGroupObj);

        result.put(CustGroupUIHelper.service_groupTrxValue, groupTrxValue);
        result.put("itemType", itemType);

        ICustGrpIdentifier checkOBj = groupTrxValue.getStagingCustGrpIdentifier();
        if (checkOBj != null) {
            Debug("checkOBj.get = " + checkOBj.getGroupName());
        } else {
            Debug("checkOBj.getGroupName is  null");
        }

        DefaultLogger.debug(this, "Existing doExecute ");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    /**
     * helper class for adding new child record to mainObj
     *
     * @param stagingGroupObj
     * @param childdOBj
     */

    private void addChildRecord(ICustGrpIdentifier stagingGroupObj, IGroupCreditGrade childdOBj) {
        if (stagingGroupObj == null) {
            stagingGroupObj = new OBCustGrpIdentifier();
            Debug("AddGroupCreditGradeCommand stagingGroupObj =null");
        } else {
            Debug("stagingGroupObj.getGroupName = " + stagingGroupObj.getGroupName());
            IGroupCreditGrade[] list = stagingGroupObj.getGroupCreditGrade();
            Debug("stagingGroupObj IGroupCreditGrade length = " + (list == null ? 0 : list.length));
        }

        Debug("mainObj = " + stagingGroupObj);

        IGroupCreditGrade[] existingArray = stagingGroupObj.getGroupCreditGrade();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        IGroupCreditGrade[] newArray = new IGroupCreditGrade[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        if (childdOBj != null) {
            Debug("childdOBj = " + ((OBGroupCreditGrade) childdOBj).toString());
            newArray[arrayLength] = childdOBj;
        } else {
            Debug("childdOBj is null ");
        }

        stagingGroupObj.setGroupCreditGrade(newArray);
    }

    private void Debug(String msg) {
        DefaultLogger.debug(this, " AddGroupCreditGradeCommand = " + msg);
    }

}
