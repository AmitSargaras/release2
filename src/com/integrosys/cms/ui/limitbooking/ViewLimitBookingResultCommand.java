/**
 * 
 */
package com.integrosys.cms.ui.limitbooking;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.ILoanSectorDetail;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingHelper;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.app.commodity.common.AmountConversionException;

/**
 * @author priya
 *
 */

public class ViewLimitBookingResultCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public ViewLimitBookingResultCommand() {
        DefaultLogger.debug(this,"Inside View Limit Booking Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
        	{LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
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
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE}
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
        	OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.LIMIT_BOOKING);
            ILimitBooking sesslimitbooking = (ILimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
            
            limitBooking.setAllBkgs(sesslimitbooking.getAllBkgs());
            limitBooking.setLoanSectorList(sesslimitbooking.getLoanSectorList());
            limitBooking.setBankGroupList(sesslimitbooking.getBankGroupList());
            
            Amount bookingAmountBase = LimitBookingHelper.convertBaseAmount(limitBooking.getBkgAmount());
            Amount totalLoanSectorAmount = null;
            
            List loanSectorList = limitBooking.getLoanSectorList();
            
            if (loanSectorList != null) {
            	for (int i=0; i< loanSectorList.size(); i++) {
            		Amount loanSectorAmount = ((ILoanSectorDetail)loanSectorList.get(i)).getBkgAmount();
            		totalLoanSectorAmount = CommonUtil.addAmount(totalLoanSectorAmount, loanSectorAmount);
            	}
            }
            
            Amount totalLoanSectorAmountBase = LimitBookingHelper.convertBaseAmount(totalLoanSectorAmount);
            
            if (CommonUtil.compareAmount(totalLoanSectorAmountBase , bookingAmountBase) == 1){	
            	excpMap.put("limitBookingAmountError", new ActionMessage("error.limit.booking.amount.exceed"));
            	resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
            }
            else {
            	ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
                
                if (limitBooking.getBkgIDNo() != null && limitBooking.getSubProfileID() == null) {
                	limitBooking.setSubProfileID(proxy.getSubProfileIDByIDNumber(limitBooking.getBkgIDNo()));
                }
                
                ILimitBooking newLimitBooking = proxy.retrieveLimitBookingResult(limitBooking);
                resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, newLimitBooking);
            }
        
        } catch (AmountConversionException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);            
			
			excpMap.put("rateNotFoundError", new ActionMessage( "error.exchange.rate.NA",  e.getFromCcyCode(), e.getToCcyCode() ) );

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


