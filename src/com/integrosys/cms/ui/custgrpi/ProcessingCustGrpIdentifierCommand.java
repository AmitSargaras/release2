package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.IGroupCreditGrade;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.IGroupOtrLimit;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;


public class ProcessingCustGrpIdentifierCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
                {CustGroupUIHelper.form_custGrpIdentifierObj, "com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier", FORM_SCOPE},
                {"itemType", "java.lang.String", REQUEST_SCOPE}
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        });

    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, " - BEGIN. ");
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        ICustGrpIdentifier stagingGroupObj = null;
        IGroupCreditGrade[] stagingCreditGrade = null;
        IGroupSubLimit[] stagingGrpSubLimit = null;
        IGroupOtrLimit[] stagingGrpOtrLimit = null;
        ICustGrpIdentifier frmObj = (ICustGrpIdentifier) map.get(CustGroupUIHelper.form_custGrpIdentifierObj);
        ICustGrpIdentifierTrxValue itrxValue = (ICustGrpIdentifierTrxValue) map.get(CustGroupUIHelper.service_groupTrxValue);
        if (itrxValue != null) {
        } else {
            itrxValue = new OBCustGrpIdentifierTrxValue();
            DefaultLogger.debug(this,"itrxValue Created= " + itrxValue);

        }
        stagingGroupObj = itrxValue.getStagingCustGrpIdentifier();
        if (stagingGroupObj != null) {
            stagingCreditGrade = stagingGroupObj.getGroupCreditGrade();
            stagingGrpSubLimit = stagingGroupObj.getGroupSubLimit();
            stagingGrpOtrLimit = stagingGroupObj.getGroupOtrLimit();
        }
        frmObj.setGroupCreditGrade(stagingCreditGrade);
        frmObj.setGroupSubLimit(stagingGrpSubLimit);
        frmObj.setGroupOtrLimit(stagingGrpOtrLimit);
        itrxValue.setStagingCustGrpIdentifier(frmObj);
        String itemType = (String) map.get("itemType");
        DefaultLogger.debug(this, "<<<<<<<<<<<<< itemType: " + itemType);
        if (itemType != null) {
            result.put("itemType", itemType);
        }

        //Andy Wong: validate BGEL indicator for sub limit creation
        if(!frmObj.getIsBGEL() && CustGroupUIHelper.GROUPSUBLIMIT.equals(itemType)){
            exceptionMap.put("isBGELInd", new ActionMessage("error.group.bgel.not.maintain"));
        }

        result.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, " - END. ");
        return temp;
    }

}
