package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.HashMap;

/**
 * @author priya
 *
 */

public class ReadLimitBookingCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public ReadLimitBookingCommand() {

    }

    /**
     * Defines a two dimensional array with the parameter list to be passed to
     * the doExecute method by a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE},
                {"limitBookingID", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING,"com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue",SERVICE_SCOPE}
        });
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
        		{LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
        		{LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE},
        		{LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING,"com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue",SERVICE_SCOPE}
        });

    }


    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here creation for Company Borrower is done.
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

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
        	 
        	 OBTrxContext theOBTrxContext = (OBTrxContext) map .get("theOBTrxContext");
        	 
        	 String limitBookingIDString = (String)(map.get("limitBookingID"));
        	 
        	 ILimitBookingTrxValue newTrxValue = null;
        	 
        	 ILimitBooking limitBooking = null;
        	 
        	 if (limitBookingIDString == null) {       		 
        		 newTrxValue = (ILimitBookingTrxValue)map.get(LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING); 
        		 limitBooking = (ILimitBooking)(map.get(LimitBookingAction.SESSION_LIMIT_BOOKING));
        	 }
        	 else {
        		 long limitBookingID = Long.parseLong(limitBookingIDString);
        		 ILimitBookingProxy limitBookingProxy = LimitBookingProxyFactory.getProxy();
        		 newTrxValue = limitBookingProxy.getLimitBookingTrxValue(theOBTrxContext, limitBookingID);
        		 limitBooking = newTrxValue.getLimitBooking();
        	 }
 
             resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
             resultMap.put(LimitBookingAction.LIMIT_BOOKING, limitBooking);
             resultMap.put(LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING, newTrxValue);
        
        } catch (Exception e) {
        	DefaultLogger.debug(this, "got exception in doExecute" + e);
        	e.printStackTrace();
        	throw (new CommandProcessingException(e.getMessage()));
    	}

        
        DefaultLogger.debug(this, "Existing doExecute()");


        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
       
    }

}
