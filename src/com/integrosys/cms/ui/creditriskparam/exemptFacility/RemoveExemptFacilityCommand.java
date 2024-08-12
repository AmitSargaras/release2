/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/21 12:30:04 $
 * Tag: $Name:  $
 */
public class RemoveExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public RemoveExemptFacilityCommand() {
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"removeIndex", "java.lang.String", REQUEST_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
        }
                );
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
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
        }
                );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");
            map.put("theOBTrxContext",trxContext);

            String removeIndex = (String) map.get("removeIndex");
            DefaultLogger.debug(this, "Selected Items to remove" + removeIndex);
            StringTokenizer st = new StringTokenizer(removeIndex, ",");
            int removeAry[] = new int[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                removeAry[i] = Integer.parseInt(st.nextToken());
                i++;
            }
            IExemptFacilityGroupTrxValue trxVal = (IExemptFacilityGroupTrxValue)map.get("exemptFacilityTrxValue");
            IExemptFacilityGroup exFacGroup = trxVal.getStagingExemptFacilityGroup();
            
            exFacGroup.removeItems(removeAry);
            DefaultLogger.debug(this, "removed  items" + removeAry.length + " Group " + exFacGroup);
            DefaultLogger.debug(this, "Group = " + AccessorUtil.printMethodValue(exFacGroup));
            trxVal.setStagingExemptFacilityGroup(exFacGroup);
            

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



