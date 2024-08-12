/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;

/**
 * The Intermediary class that prepare the CustRelationshipTrxValue from one action
 * to another action.
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class AddNewShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, 
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"length", "java.lang.Integer", SERVICE_SCOPE},
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE}
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
        return (new String[][]{// Produce all the feed entries.
            {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE},             {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Produce the length.
            {"length", "java.util.Integer", SERVICE_SCOPE}, // To populate the form.
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE}
        });
    }	
	

    public HashMap doExecute(HashMap map)
		throws CommandProcessingException, CommandValidationException {
		
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

        try {

			ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
            	"CustShareHolderTrxValue");
			
            resultMap.put("CustShareHolderTrxValue", value);
            
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }
	        

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
	}
}
