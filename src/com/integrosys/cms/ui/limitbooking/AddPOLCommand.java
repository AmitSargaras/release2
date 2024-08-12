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
import com.integrosys.cms.app.limitbooking.bus.ILoanSectorDetail;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessage;

/**
 * @author priya
 *
 */

public class AddPOLCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public AddPOLCommand() {
        DefaultLogger.debug(this,"Inside AddPOLCommand >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {LimitBookingAction.LIMIT_BOOKING_DETAIL, "com.integrosys.cms.app.limitbooking.bus.OBLimitBookingDetail", FORM_SCOPE},
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
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
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
        HashMap exceptionMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");

        ILoanSectorDetail bkgDtl = (ILoanSectorDetail)map.get(LimitBookingAction.LIMIT_BOOKING_DETAIL);
        OBLimitBooking sessLimitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);

        // get the POL collection from All bookings and add the new POL to it
        List pol = sessLimitBooking.getLoanSectorList();
        
        if (pol == null) {
        	 pol = new ArrayList();
        }
           
        boolean exist = validateDuplicate(pol, bkgDtl, exceptionMap);
        
        if (exist) {
        	exceptionMap.put("duplicatePolEntryError", new ActionMessage("error.pol.duplicate"));
        }
        else {
        	pol.add(bkgDtl);
        }
        
        sessLimitBooking.setLoanSectorList(pol);

        resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING,sessLimitBooking);
        
        resultMap.put(LimitBookingAction.LIMIT_BOOKING,sessLimitBooking);
        
        resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));

        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
    
    private boolean validateDuplicate(List jspIDoc, ILoanSectorDetail dbIDoc, HashMap exceptionMap) {
        boolean valid = false;

        if (jspIDoc == null || jspIDoc.size() == 0){
            return false;
        }
        
        for (int i = 0; i < jspIDoc.size(); i++) {
        	if (((ILoanSectorDetail)jspIDoc.get(i)).getBkgTypeCode()!= null && ((ILoanSectorDetail)jspIDoc.get(i)).getBkgTypeCode().equals(dbIDoc.getBkgTypeCode())){
        		valid = true;
        	}
        }
        return valid;
    }
}



