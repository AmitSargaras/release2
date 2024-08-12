package com.integrosys.cms.ui.custgrpi.groupsublimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.OBCustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSubLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierUIHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: May 8, 2007
 * Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class AddGroupSubLimitCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_groupSubLimitObj, "com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit", FORM_SCOPE},
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

        IGroupSubLimit groupObj = (IGroupSubLimit) map.get(CustGroupUIHelper.form_groupSubLimitObj);
        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }

        Debug("groupTrxValue = " + groupTrxValue);

        ICustGrpIdentifier stagingGroupObj = groupTrxValue.getStagingCustGrpIdentifier();

        //Andy Wong, 4 July 2008: validate for duplicate sub limit type and descr
        if (!ArrayUtils.isEmpty(stagingGroupObj.getGroupSubLimit())) {
            for (int i = 0; i < stagingGroupObj.getGroupSubLimit().length; i++) {
                IGroupSubLimit iGroupSubLimit = stagingGroupObj.getGroupSubLimit()[i];

                if (iGroupSubLimit.getDescription().equals(groupObj.getDescription())) {
                    exceptionMap.put("desc", new ActionMessage("error.group.sub.limit.duplicate"));
                    break;
                }

            }
        }

        //derive limit amount for sub limit when GP5 or Capital Fund % selected
        if ((CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT.equals(stagingGroupObj.getInternalLmt())
                || CustGroupUIHelper.INT_LMT_GP5_REQ.equals(stagingGroupObj.getInternalLmt())) && CustGroupUIHelper.INT_LMT_SUBTYP_AB_ENTITY.equals(groupObj.getSubLimitTypeCD())) {
            groupObj.setLimitAmt(CustGrpIdentifierUIHelper.getGroupLimit(stagingGroupObj.getInternalLmt(), groupObj.getDescription()));
        }

        if (exceptionMap.size() == 0) {
            this.addChildRecord(stagingGroupObj, groupObj);

            if (stagingGroupObj != null) {
                Debug("checkOBj.get = " + stagingGroupObj.getGroupName());
            } else {
                Debug("checkOBj.getGroupName is  null");
            }

            groupTrxValue.setStagingCustGrpIdentifier(stagingGroupObj);
        }
        
        result.put(CustGroupUIHelper.service_groupTrxValue, groupTrxValue);
        result.put("itemType", map.get("itemType"));

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

    private void addChildRecord(ICustGrpIdentifier stagingGroupObj, IGroupSubLimit childdOBj) {
        if (stagingGroupObj == null) {
            stagingGroupObj = new OBCustGrpIdentifier();
            Debug("stagingGroupObj = null");
        } else {
            Debug("stagingGroupObj.getGroupName = " + stagingGroupObj.getGroupName());
            IGroupSubLimit[] list = stagingGroupObj.getGroupSubLimit();
            Debug("stagingGroupObj IGroupCreditGrade length = " + (list == null ? 0 : list.length));
        }

        Debug("mainObj = " + stagingGroupObj);

        IGroupSubLimit[] existingArray = stagingGroupObj.getGroupSubLimit();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        IGroupSubLimit[] newArray = new IGroupSubLimit[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        if (childdOBj != null) {
            Debug("childdOBj = " + ((OBGroupSubLimit) childdOBj).toString());
            newArray[arrayLength] = childdOBj;
        } else {
            Debug("childdOBj is null ");
        }

        stagingGroupObj.setGroupSubLimit(newArray);
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AddGroupSubLimitCommand = " + msg);
    }

}
