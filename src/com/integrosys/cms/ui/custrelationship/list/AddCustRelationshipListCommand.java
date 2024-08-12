/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;




/**
 * @author siewkheat
 *
 */
public class AddCustRelationshipListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{// Consume the input fields as a feed group OB.
            {"custRelationshipMap", "java.util.HashMap", FORM_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Consume the current feed entries to be saved as a whole.
            {"length", "java.lang.Integer", SERVICE_SCOPE},
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE},
			{"from_event", "java.lang.String", SERVICE_SCOPE},
            {"CustRelationshipTrxValue", 
             	"com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue", SERVICE_SCOPE}, 
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}};
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map)
            throws CommandValidationException, CommandProcessingException,
            AccessDeniedException {
        DefaultLogger.debug(this, "Map is " + map);
        
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {
        	HashMap custRelationshipMap = (HashMap)map.get("custRelationshipMap");
            ICustRelationship[] custRelArr = (ICustRelationship[])custRelationshipMap.get("custRel");

            int offset = ((Integer)map.get("offset")).intValue();

            ICustRelationshipTrxValue value = (ICustRelationshipTrxValue)map.get(
                    "CustRelationshipTrxValue");
            ICustRelationship[] stagingCustRelArr = value.getStagingCustRelationship();

            if (custRelArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + custRelArr.length);

                for (int i = 0; i < custRelArr.length; i++) {
                	stagingCustRelArr[offset + i].setRelationshipValue(
                			custRelArr[i].getRelationshipValue());
                	stagingCustRelArr[offset + i].setRemarks(
                			custRelArr[i].getRemarks());
                }
            }

            value.setStagingCustRelationship(stagingCustRelArr);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
