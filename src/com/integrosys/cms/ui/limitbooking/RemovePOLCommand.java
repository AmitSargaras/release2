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
import com.integrosys.cms.app.limitbooking.bus.OBLoanSectorDetail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author priya
 *
 */

public class RemovePOLCommand extends AbstractCommand implements ICommonEventConstant {
     /**
     * Default Constructor
     */
    public RemovePOLCommand() {
        DefaultLogger.debug(this,"Inside RemovePOLCommand >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        	{LimitBookingAction.LIMIT_BOOKING, "java.lang.Object", FORM_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
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
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
        });
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
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
            OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
            
            String[] deletedPolCodeList = (String [])map.get(LimitBookingAction.LIMIT_BOOKING);
           
            List pol = limitBooking.getLoanSectorList();
            
            OBLoanSectorDetail dtl = null;
            
            for (int i=0; i<deletedPolCodeList.length; i++) {  	
            	for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
                    dtl = (OBLoanSectorDetail) iterator.next();
                    if (dtl.getBkgTypeCode().equals(deletedPolCodeList[i]))
                    {
                        break;
                    }
                }
            	if (dtl != null) {
            		pol.remove(dtl);
            	}	
            }
 
            limitBooking.setLoanSectorList(pol);
            resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
            resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));
            DefaultLogger.debug(this,"In removePOL command   limitBooking = " + limitBooking);

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