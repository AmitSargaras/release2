package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;

//public class PrepareAddNewEntityLimitListCommand extends AbstractCommand {
public class PrepareAddNewEntityLimitListCommand extends EntityLimitCommand {
    public String[][] getParameterDescriptor() {
        return new String[][]{// Consume the input target offset.
        	{"entityLimitMap", "java.util.HashMap", FORM_SCOPE},
            {"EntityLimitTrxValue",
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE},
            };
    }


    public String[][] getResultDescriptor() {
        return new String[][]{            
        	{"EntityLimitTrxValue", 
        	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}};
    }


    public HashMap doExecute(HashMap map)
            throws CommandValidationException, CommandProcessingException,
            AccessDeniedException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

        	HashMap inputHash = (HashMap)map.get("entityLimitMap");
            String editedRemarks = (inputHash.get("editedRemarks") == null) ? "" : (String)inputHash.get("editedRemarks");

            // Session-scoped trx value.
            IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
            	"EntityLimitTrxValue");
            IEntityLimit[] stagingEntityLimit = value.getStagingEntityLimit();
            
            // set the editedRemarks in the trx object
            if (value != null && editedRemarks != null) value.setEditedRemarks(editedRemarks);
            
            resultMap.put("EntityLimitTrxValue", value);
            
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
