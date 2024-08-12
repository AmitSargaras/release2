/**
 * 
 */
package com.integrosys.cms.ui.limitbooking;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.ILimitBookingDetail;

/**
 * @author priya
 *
 */

public class LimitBookingPopupCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public LimitBookingPopupCommand() {
        DefaultLogger.debug(this,"Inside Limit Booking Popup Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        	{"rowIdx", "java.lang.String", REQUEST_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE}
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
        	{LimitBookingAction.LIMIT_BOOKING_DETAIL, "com.integrosys.cms.app.limitbooking.bus.OBLimitBookingDetail", SERVICE_SCOPE}
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
        	
        	String rowIndexString = (String)(map.get("rowIdx"));
        	
        	int rowIndex = Integer.parseInt(rowIndexString);
        	
        	ILimitBooking sesslimitbooking = (ILimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
        	List allLimitBookings = sesslimitbooking.getAllBkgs();
        	
        	ILimitBookingDetail limitBookingDetail = null;
        	
        	limitBookingDetail = (ILimitBookingDetail)allLimitBookings.get(rowIndex);
        		
        	resultMap.put(LimitBookingAction.LIMIT_BOOKING_DETAIL, limitBookingDetail);
        
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


