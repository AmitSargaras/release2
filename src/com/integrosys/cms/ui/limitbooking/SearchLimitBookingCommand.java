package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.HashMap;

/**
 * @author priya
 *
 */

public class SearchLimitBookingCommand extends AbstractCommand {
    /**
     * Default Constructor
     */
    public SearchLimitBookingCommand() {

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
                {LimitBookingAction.BOOKING_SEARCH_CRITERIA, "com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria", FORM_SCOPE},
                {LimitBookingAction.SESSION_BOOKING_SEARCH_CRITERIA, "com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"indicator", "java.lang.String", REQUEST_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE}
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
                {"event", "java.lang.String", REQUEST_SCOPE},
                {LimitBookingAction.BOOKING_SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE},
                {LimitBookingAction.SESSION_BOOKING_SEARCH_RESULT, "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
                {LimitBookingAction.SESSION_BOOKING_SEARCH_CRITERIA, "com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria", SERVICE_SCOPE},
                {"startIndex", "java.lang.String", REQUEST_SCOPE}
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

        String event = (String) map.get("event");
        
        String indicator = (String) map.get("indicator");
        String startIndex = (String) map.get("startIndex");
        
        
        LimitBookingSearchCriteria formCriteria = (LimitBookingSearchCriteria) map.get(LimitBookingAction.BOOKING_SEARCH_CRITERIA);
        
        LimitBookingSearchCriteria service_criteria  =null;
        
        if (isSearchCriteriaEmpty (formCriteria) && !"*".equals(indicator)) {
        	service_criteria = (LimitBookingSearchCriteria) map.get(LimitBookingAction.SESSION_BOOKING_SEARCH_CRITERIA);
        	if (service_criteria != null) {
        		service_criteria.setStartIndex(formCriteria.getStartIndex());
        		service_criteria.setNItems(formCriteria.getNItems());
            }
        }
        
        if (service_criteria == null) {
            service_criteria = formCriteria;
        }
        
        LimitBookingSearchCriteria searchCriteria = mergeSearchCriteria(service_criteria,map);

        ILimitBookingProxy custproxy = LimitBookingProxyFactory.getProxy();
        SearchResult sr = null;
        
        try {
            sr = custproxy.searchBooking(searchCriteria);
            resultMap.put(LimitBookingAction.BOOKING_SEARCH_RESULT, sr);
            resultMap.put(LimitBookingAction.SESSION_BOOKING_SEARCH_RESULT, sr);
            resultMap.put(LimitBookingAction.SESSION_BOOKING_SEARCH_CRITERIA, service_criteria);
        } catch (Exception e) {
            throw (new CommandProcessingException(e.getMessage()));
        }

        DefaultLogger.debug(this, "Existing doExecute()");

        resultMap.put("event", event);
        resultMap.put("startIndex", startIndex);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
    
    private LimitBookingSearchCriteria   mergeSearchCriteria(LimitBookingSearchCriteria criteria ,HashMap map) {
    	
    	LimitBookingSearchCriteria  search = new   LimitBookingSearchCriteria();
    	
    	String startIndexStr = (String) map.get("startIndex");
         
    	int startIndex=0;
    	if (startIndexStr != null) {
          startIndex= Integer.parseInt(startIndexStr) ;
    	}

        search.setSearchFromDate(criteria.getSearchFromDate());
        search.setSearchToDate(criteria.getSearchToDate());
        search.setTicketNo(criteria.getTicketNo());
        search.setGroupName(criteria.getGroupName());
        search.setCustomerName(criteria.getCustomerName());
        search.setIdNo(criteria.getIdNo());
        search.setStartIndex(startIndex);
        search.setNItems(criteria.getNItems());
        search.setGobuttonBooking(criteria.getGobuttonBooking());

       return  search;

   }
    
    private boolean isSearchCriteriaEmpty (LimitBookingSearchCriteria formCriteria) {
    	
    	if (formCriteria.getSearchFromDate() == null && formCriteria.getSearchToDate() == null &&
    		formCriteria.getTicketNo() == null && formCriteria.getGroupName() == null && 
    		formCriteria.getCustomerName() == null && formCriteria.getIdNo() == null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }

}
