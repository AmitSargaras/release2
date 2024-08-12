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
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * @author priya
 *
 */

public class SuccessfulLimitBookingCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public SuccessfulLimitBookingCommand() {
        DefaultLogger.debug(this,"Inside Success Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        	{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING,"com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue",SERVICE_SCOPE}
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
        	{"request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
        
        try {
            
        	OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
        	ILimitBookingTrxValue trxVal = (ILimitBookingTrxValue)map.get(LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING);
            
        	ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
        	ILimitBookingTrxValue newTrxValue = proxy.makerSuccessLimitBooking(ctx, trxVal, Long.toString(ctx.getUser().getUserID()));
        
            resultMap.put("request.ITrxValue", newTrxValue);
        
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



