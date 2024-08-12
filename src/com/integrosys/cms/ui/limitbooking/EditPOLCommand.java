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
import com.integrosys.cms.app.limitbooking.bus.OBLoanSectorDetail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author priya
 *
 */

public class EditPOLCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public EditPOLCommand() {
        DefaultLogger.debug(this,"Inside EditPOLCommand >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {"polID", "java.lang.String", REQUEST_SCOPE},
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
        DefaultLogger.debug(this, "Inside doExecute()");

        ILoanSectorDetail bkgDtl = (OBLoanSectorDetail)map.get(LimitBookingAction.LIMIT_BOOKING_DETAIL);
       
        String polID = (String)map.get("polID");
        OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
       
        List pol = limitBooking.getLoanSectorList();
        ILoanSectorDetail dtl = null;
        for (Iterator iterator = pol.iterator(); iterator.hasNext();) {
            dtl = (OBLoanSectorDetail) iterator.next();
            if (dtl.getBkgTypeCode().equals(polID))
            {
                dtl.setBkgAmount  (bkgDtl.getBkgAmount());
                dtl.setBkgTypeCode(bkgDtl.getBkgTypeCode());
                dtl.setBkgTypeDesc(bkgDtl.getBkgTypeDesc());
                dtl.setBkgProdTypeCode(bkgDtl.getBkgProdTypeCode());
                dtl.setBkgProdTypeDesc(bkgDtl.getBkgProdTypeDesc());
                break;
            }
        }
        limitBooking.setLoanSectorList(pol);
        resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING,limitBooking);
        resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));

        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



