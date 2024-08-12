/**
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;

import java.util.HashMap;

/**
 * @author priya
 *
 */

public class PrepareAddLimitBookingCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public PrepareAddLimitBookingCommand() {
        DefaultLogger.debug(this,"Inside Prepare Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        	{LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
        	{LimitBookingAction.LIMIT_BOOKING_FIRST_TIME_FLAG, "java.lang.String", REQUEST_SCOPE}
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
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE}
        });
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
        OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
        
        String firstTime = (String)map.get(LimitBookingAction.LIMIT_BOOKING_FIRST_TIME_FLAG);
        if ((firstTime!=null && firstTime.equals(LimitBookingAction.LIMIT_BOOKING_FIRST_TIME_FLAG_TRUE)) || limitBooking == null) {
            limitBooking = new OBLimitBooking();
        }
        resultMap.put(LimitBookingAction.LIMIT_BOOKING,limitBooking);
        resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING,limitBooking);
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



