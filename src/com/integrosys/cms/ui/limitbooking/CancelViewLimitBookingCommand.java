/**
 * 
 */
package com.integrosys.cms.ui.limitbooking;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;

/**
 * @author priya
 *
 */

public class CancelViewLimitBookingCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public CancelViewLimitBookingCommand() {
        DefaultLogger.debug(this,"Inside Cancel View Limit Booking Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
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
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
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
        HashMap excpMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
        	
            ILimitBooking sesslimitbooking = (ILimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);

            resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, sesslimitbooking);
            resultMap.put(LimitBookingAction.LIMIT_BOOKING, sesslimitbooking);
            resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));
        
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, excpMap);
        return returnMap;
    }

    
}


