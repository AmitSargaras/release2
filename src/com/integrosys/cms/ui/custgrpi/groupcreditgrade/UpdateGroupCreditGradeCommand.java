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
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierUIHelper;

import java.util.HashMap;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateGroupCreditGradeCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_groupCreditGradeObj, "com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade", FORM_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
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
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {"from_event", "java.lang.String", REQUEST_SCOPE},
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

        IGroupCreditGrade groupObj = (IGroupCreditGrade) map.get(CustGroupUIHelper.form_groupCreditGradeObj);
        ICustGrpIdentifierTrxValue trxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (trxValue == null) {
            trxValue = new OBCustGrpIdentifierTrxValue();
        }
        ICustGrpIdentifier stagingGroupObj = trxValue.getStagingCustGrpIdentifier();
        if (stagingGroupObj != null) {
            Debug("checkOBj.get = " + stagingGroupObj.getGroupName());
        } else {
            Debug("checkOBj.getGroupName is  null");
        }
        int index = 0;
        if (map.get("indexID") != null) {
            index = Integer.parseInt((String) map.get("indexID"));
        }

        //Andy Wong, 2 July 2008: derive the BGEL amount if Internal Credit Rating selected
        if(StringUtils.equals(stagingGroupObj.getInternalLmt(), CustGroupUIHelper.INT_LMT_CREDIT_RATE))
        {
            stagingGroupObj.setLastReviewDt(groupObj.getRatingDt());
            Amount grpLmt = CustGrpIdentifierUIHelper.getGroupLimit(CustGroupUIHelper.INT_LMT_CREDIT_RATE, groupObj.getRatingCD());
            if(grpLmt.getCurrencyCodeAsObject()==null) {
                grpLmt.setAmountAsBigDecimal(new BigDecimal(0));
                grpLmt.setCurrencyCode("MYR");
            }
            stagingGroupObj.setGroupLmt(grpLmt);
            stagingGroupObj.setGroupCurrency(grpLmt.getCurrencyCode());
        }

        DefaultLogger.debug(this, "Existing index " + index);

        this.updateChildRecord(stagingGroupObj, groupObj, index);

        trxValue.setStagingCustGrpIdentifier(stagingGroupObj);
        result.put(CustGroupUIHelper.service_groupTrxValue, trxValue);
        result.put("itemType", map.get("itemType"));

        DefaultLogger.debug(this, "Existing doExecute ");

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }


    private void updateChildRecord(ICustGrpIdentifier obj, IGroupCreditGrade iFeeDetails, int index) {
        IGroupCreditGrade[] existingArray = obj.getGroupCreditGrade();
        existingArray[index] = iFeeDetails;
        obj.setGroupCreditGrade(existingArray);
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"UpdateGroupCreditGradeCommand = " + msg);
    }

}
