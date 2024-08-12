package com.integrosys.cms.ui.custrelationship.shareholder;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;

import java.util.HashMap;

/**
 * The Intermediary class that prepare the CustRelationshipTrxValue from one action
 * to another action.
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class CancelShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            {"CustShareHolderTrxValue", "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"offset", "java.lang.Integer", SERVICE_SCOPE},
            {"length", "java.lang.Integer", SERVICE_SCOPE},
        };
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
        return (new String[][]{      // Produce all the feed entries.
            {"CustShareHolderTrxValue", "com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},             {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.util.Integer", SERVICE_SCOPE}, // To populate the form.
             {"offset", "java.lang.Integer", SERVICE_SCOPE},
        });
    }


    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        ICustShareholderTrxValue value  = null;
        try {
           value = (ICustShareholderTrxValue)map.get( "CustShareHolderTrxValue");
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        resultMap.put("offset", new Integer(0));
        resultMap.put("length", new Integer(10));
        resultMap.put("CustShareHolderTrxValue", value);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
