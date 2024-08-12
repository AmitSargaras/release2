package com.integrosys.cms.ui.creditriskparam.exemptFacility;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;


public class RefreshExemptFacilityCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public RefreshExemptFacilityCommand() {
    }

    /**
     * Defines a two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"losSystem", "java.lang.String", REQUEST_SCOPE},

        }
        );
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
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"facilityCodes", "java.util.Collection", REQUEST_SCOPE},
                {"facilityLabels", "java.util.Collection", REQUEST_SCOPE},
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Inside doExecute()");

        String event = (String) map.get("event");
        String losSystem = (String) map.get("losSystem");

        HashMap resultMap = new HashMap();
        HashMap result = new HashMap();


        Collection facilityCodes = new ArrayList();
        Collection facilityLabels = new ArrayList();

        try {
            if (losSystem != null && !"".equals(losSystem)) {
                facilityLabels = CommonCodeList.getInstance(null,null, ICategoryEntryConstant.FACILITY_DESCRIPTION, losSystem).getCommonCodeLabels();
                facilityCodes = CommonCodeList.getInstance(null,null, ICategoryEntryConstant.FACILITY_DESCRIPTION, losSystem).getCommonCodeValues();
            }
        }
        catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }

        result.put("event", event);
        result.put("facilityCodes", facilityCodes);
        result.put("facilityLabels", facilityLabels);

        DefaultLogger.debug(this, "Going out of doExecute()");

        resultMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        return resultMap;

    }
}

