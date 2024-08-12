/**
 * 
 */
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





/**
 * @author siewkheat
 *
 */
public class AddShareHolderListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{// Consume the input fields as a feed group OB.
            {"shareHolderMap", "java.util.HashMap", FORM_SCOPE}, 
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, // Consume the current feed entries to be saved as a whole.
            {"length", "java.lang.Integer", SERVICE_SCOPE},
            {"sub_profile_id", "java.lang.String", SERVICE_SCOPE},
            {"customerType", "java.lang.String", SERVICE_SCOPE},
			{"from_event", "java.lang.String", SERVICE_SCOPE},
            {"CustShareHolderTrxValue", 
            	"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue", SERVICE_SCOPE}, // Produce the offset.
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
        	HashMap shareHolderMap = (HashMap)map.get("shareHolderMap");
            ICustShareholder[] custRelArr = (ICustShareholder[])shareHolderMap.get("shareHolder");

            int offset = ((Integer)map.get("offset")).intValue();

            ICustShareholderTrxValue value = (ICustShareholderTrxValue)map.get(
                    "CustShareHolderTrxValue");
            ICustShareholder[] stagingShareHolderArr = value.getStagingCustShareholder();

            if (custRelArr != null) {
                DefaultLogger.debug(this,
                        "number of existing entries = " + custRelArr.length);

                for (int i = 0; i < custRelArr.length; i++) {
                	stagingShareHolderArr[offset + i].setPercentageOwn(
                			custRelArr[i].getPercentageOwn());
                }
            }

            value.setStagingCustShareholder(stagingShareHolderArr);

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
