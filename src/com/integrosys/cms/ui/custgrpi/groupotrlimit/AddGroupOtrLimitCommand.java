package com.integrosys.cms.ui.custgrpi.groupotrlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.bus.OBCustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.OBGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: July 1, 2008
 * Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class AddGroupOtrLimitCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_groupOtrLimitObj, "com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit", FORM_SCOPE},
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

        IGroupOtrLimit groupObj = (IGroupOtrLimit) map.get(CustGroupUIHelper.form_groupOtrLimitObj);
        ICustGrpIdentifierTrxValue groupTrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (groupTrxValue == null) {
            groupTrxValue = new OBCustGrpIdentifierTrxValue();
        }

        Debug("groupTrxValue = " + groupTrxValue);

        ICustGrpIdentifier stagingGroupObj = groupTrxValue.getStagingCustGrpIdentifier();

        //Andy Wong, 4 July 2008: validate for duplicate other limit type and descr
        if (!ArrayUtils.isEmpty(stagingGroupObj.getGroupOtrLimit())) {
            for (int i = 0; i < stagingGroupObj.getGroupOtrLimit().length; i++) {
                IGroupOtrLimit iGroupOtrLimit = stagingGroupObj.getGroupOtrLimit()[i];

                if (iGroupOtrLimit.getDescription().equals(groupObj.getDescription())) {
                    exceptionMap.put("desc", new ActionMessage("error.group.other.limit.duplicate"));
                    break;
                }
            }
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

    private void addChildRecord(ICustGrpIdentifier stagingGroupObj, IGroupOtrLimit childdOBj) {
        if (stagingGroupObj == null) {
            stagingGroupObj = new OBCustGrpIdentifier();
            Debug("stagingGroupObj = null");
        } else {
            Debug("stagingGroupObj.getGroupName = " + stagingGroupObj.getGroupName());
            IGroupOtrLimit[] list = stagingGroupObj.getGroupOtrLimit();
            Debug("stagingGroupObj IGroupOtrLimit length = " + (list == null ? 0 : list.length));
        }

        Debug("mainObj = " + stagingGroupObj);

        IGroupOtrLimit[] existingArray = stagingGroupObj.getGroupOtrLimit();
        int arrayLength = 0;
        if (existingArray != null) {
            arrayLength = existingArray.length;
        }

        IGroupOtrLimit[] newArray = new IGroupOtrLimit[arrayLength + 1];
        if (existingArray != null) {
            System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
        }
        if (childdOBj != null) {
            Debug("childdOBj = " + ((OBGroupOtrLimit) childdOBj).toString());
            newArray[arrayLength] = childdOBj;
        } else {
            Debug("childdOBj is null ");
        }

        stagingGroupObj.setGroupOtrLimit(newArray);
    }

    private void Debug(String msg) {
        DefaultLogger.debug(this, "AddGroupOtrLimitCommand = " + msg);
    }

}
