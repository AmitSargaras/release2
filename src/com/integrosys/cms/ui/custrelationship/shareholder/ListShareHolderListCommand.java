package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;



public class ListShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{// Consume the input target offset.
            {ShareHolderListForm.MAPPER, "java.lang.Integer", FORM_SCOPE},	           
            {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},
            {"length", "java.lang.Integer", SERVICE_SCOPE}};
    }


    public String[][] getResultDescriptor() {
        return new String[][]{{"offset", "java.lang.Integer", SERVICE_SCOPE}};
    }


    public HashMap doExecute(HashMap map)
            throws CommandValidationException, CommandProcessingException,
            AccessDeniedException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            int length = ((Integer)map.get("length")).intValue();

            int targetOffset = ((Integer)map.get(ShareHolderListForm.MAPPER)).intValue();

            // Session-scoped trx value.
            ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
            	"CustShareHolderTrxValue");
            ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();

            targetOffset = ShareHolderListMapper.adjustOffset(targetOffset, length,
            		stagingShareHolderArr.length);

            resultMap.put("offset", new Integer(targetOffset));

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
