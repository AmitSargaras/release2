/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.uiinfra.common.CommonAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author priya
 *
 */

public class LimitBookingAction extends CommonAction {

	public static final String EVENT_PREPARE_ADD_BOOKING     = "prepare_add_booking";
	public static final String EVENT_ADD_BOOKING             = "add_booking";
    public static final String EVENT_PREPARE_EDIT_BOOKING    = "prepare_edit_booking";
    public static final String EVENT_RETRIEVE_BGEL_ADD           = "retrieve_bgel_add";
    public static final String EVENT_RETRIEVE_BGEL_EDIT           = "retrieve_bgel_edit";
    public static final String EVENT_VIEW_RESULT_ADD     	 = "view_result_add";
    public static final String EVENT_VIEW_RESULT_EDIT    	 = "view_result_edit";
    public static final String EVENT_VIEW_RESULT_ADD_ERROR     	 = "view_result_add_error";
    public static final String EVENT_VIEW_RESULT_EDIT_ERROR    	 = "view_result_edit_error";
    public static final String EVENT_CANCEL_VIEW_RESULT      = "view_cancel_result";

    public static final String EVENT_PREPARE_SEARCH_GROUP_ADD    = "prepare_search_group_add";
    public static final String EVENT_PREPARE_SEARCH_GROUP_EDIT    = "prepare_search_group_edit";
    public static final String EVENT_PREPARE_SEARCH_GROUP    = "prepare_search_group";
    public static final String EVENT_SEARCH_GROUP            = "search_group";
    public static final String EVENT_SEARCH_GROUP_ERROR      = "search_group_error";
    public static final String EVENT_ADD_GROUP               = "add_group";
    public static final String EVENT_ADD_GROUP_ERROR         = "add_group_error";
    public static final String EVENT_REMOVE_GROUP_ADD            = "remove_group_add";
    public static final String EVENT_REMOVE_GROUP_EDIT           = "remove_group_edit";
      
    public static final String EVENT_PREPARE_ADD_POL_ADD     	 = "prepare_add_pol_add";
    public static final String EVENT_PREPARE_ADD_POL_EDIT     	 = "prepare_add_pol_edit";
    public static final String EVENT_PREPARE_ADD_POL     	 = "prepare_add_pol";
    public static final String EVENT_ADD_POL                 = "add_pol";
    public static final String EVENT_ADD_POL_ERROR          = "add_pol_error";
    
    public static final String EVENT_PREPARE_EDIT_POL_ADD    	 = "prepare_edit_pol_add";
    public static final String EVENT_PREPARE_EDIT_POL_EDIT    	 = "prepare_edit_pol_edit";
    public static final String EVENT_PREPARE_EDIT_POL     	 = "prepare_edit_pol";
    public static final String EVENT_EDIT_POL                = "edit_pol";    
    public static final String EVENT_EDIT_POL_ERROR          = "edit_pol_error";
    
    public static final String EVENT_REMOVE_POL_ADD              = "remove_pol_add";
    public static final String EVENT_REMOVE_POL_EDIT             = "remove_pol_edit";
   
    public static final String EVENT_PREPARE_SEARCH_BOOKING  = "prepare_search_booking";
    public static final String EVENT_SEARCH_BOOKING          = "search_booking";
    public static final String EVENT_SEARCH_BOOKING_ERROR    = "search_booking_error";

    // Constants for use in commands parameter/result descriptor
    public static final String SEARCH_CRITERIA               = "searchCriteria";
    public static final String SESSION_SEARCH_CRITERIA       = "sess.searchCriteria";
    public static final String SEARCH_RESULT                 = "searchResult";
    public static final String SESSION_SEARCH_RESULT         = "sess.searchResult";
    public static final String LIMIT_BOOKING                 = "limitBooking";
    public static final String SESSION_LIMIT_BOOKING         = "sess.limitBooking";
    public static final String LIMIT_BOOKING_DETAIL          = "limitBookingDetail";

    public static final String BOOKING_SEARCH_CRITERIA         = "bookingSearchCriteria";
    public static final String SESSION_BOOKING_SEARCH_CRITERIA = "sess.bookingSearchCriteria";
    public static final String BOOKING_SEARCH_RESULT           = "bookingSearchResult";
    public static final String SESSION_BOOKING_SEARCH_RESULT   = "sess.bookingSearchResult";

    public static final String SEARCH_TYPE_DATE              = "searchByDate";
    public static final String SEARCH_TYPE_TICKET_NO         = "searchByTicketNo";
    public static final String SEARCH_TYPE_ID_NO             = "searchByIDNo";
    public static final String SEARCH_TYPE_GROUP_NAME        = "searchByGroupName";
    public static final String SEARCH_TYPE_CUSTOMER_NAME     = "searchByCustomerName";

    public static final String EVENT_PREPARE_DELETE_BOOKING      = "prepare_delete_booking";
    public static final String EVENT_PREPARE_SUCCESSFUL_BOOKING  = "prepare_successful_booking";

    public static final String EVENT_EDIT_BOOKING            = "edit_booking";
    public static final String EVENT_DELETE_BOOKING      = "delete_booking";
    public static final String EVENT_SUCCESSFUL_BOOKING  = "successful_booking";
    
    public static final String LIMIT_BOOKING_FROM_EVENT_ADD              = "add";
    public static final String LIMIT_BOOKING_FROM_EVENT_EDIT             = "edit";
    public static final String LIMIT_BOOKING_FROM_EVENT             = "fromEvent";
    
    public static final String LIMIT_BOOKING_TRX_LIMIT_BOOKING  = "ILimitBookingTrxValue";
    
    public static final String LIMIT_BOOKING_FIRST_TIME_FLAG  = "flag";
    public static final String LIMIT_BOOKING_FIRST_TIME_FLAG_TRUE  = "t";
    
    public static final String EVENT_LIMIT_BOOKING_POPUP      = "limit_booking_popup";
    
    public static final String LIMIT_BOOKING_GROUP_RETRIEVED_FLAG      = "isGroupRetrieved";

   /**
     * This method return a Array of Commad Objects responsible for a event
     *
     * @param event is of type String
     * @return Icommand Array
     */
    public ICommand[] getCommandChain(String event) {
        ICommand objArray [] = null;
        if (EVENT_PREPARE_ADD_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareAddLimitBookingCommand();
        }
        else if (EVENT_ADD_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddLimitBookingCommand();
        }
        else if (EVENT_RETRIEVE_BGEL_ADD.equals(event) || EVENT_RETRIEVE_BGEL_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RetrieveBGELCommand();
        }
        else if (EVENT_PREPARE_SEARCH_GROUP_ADD.equals(event) || EVENT_PREPARE_SEARCH_GROUP_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareSearchGroupCommand();
        }
        else if (EVENT_SEARCH_GROUP.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SearchGroupCommand();
        }
        else if (EVENT_ADD_GROUP.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddGroupCommand();
        }
        else if (EVENT_REMOVE_GROUP_ADD.equals(event) || EVENT_REMOVE_GROUP_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RemoveGroupCommand();
        }
        else if (EVENT_PREPARE_ADD_POL_ADD.equals(event) || EVENT_PREPARE_ADD_POL_EDIT.equals(event) || EVENT_ADD_POL_ERROR.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareAddPOLCommand();
        }
        else if (EVENT_PREPARE_EDIT_POL_ADD.equals(event) || EVENT_PREPARE_EDIT_POL_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareEditPOLCommand();
        }
        else if (EVENT_ADD_POL.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddPOLCommand();
        }
        else if (EVENT_EDIT_POL.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new EditPOLCommand();
        }
        else if (EVENT_REMOVE_POL_ADD.equals(event) || EVENT_REMOVE_POL_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new RemovePOLCommand();
        }
        else if (EVENT_PREPARE_SEARCH_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareSearchLimitBookingCommand();
        }
        else if (EVENT_SEARCH_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SearchLimitBookingCommand();
        } 
        else if (EVENT_PREPARE_EDIT_BOOKING.equals(event) || EVENT_PREPARE_DELETE_BOOKING.equals(event) || EVENT_PREPARE_SUCCESSFUL_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReadLimitBookingCommand();
        }
        else if (EVENT_EDIT_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new EditLimitBookingCommand();
        } 
        else if (EVENT_DELETE_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new DeleteLimitBookingCommand();
        } 
        else if (EVENT_SUCCESSFUL_BOOKING.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new SuccessfulLimitBookingCommand();
        } 
        else if (EVENT_VIEW_RESULT_ADD.equals(event) || EVENT_VIEW_RESULT_EDIT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ViewLimitBookingResultCommand();
        } 
        else if (EVENT_CANCEL_VIEW_RESULT.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new CancelViewLimitBookingCommand();
        } 
        else if (EVENT_LIMIT_BOOKING_POPUP.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new LimitBookingPopupCommand();
        }
        return (objArray);
    }

    /**
     * This method is called only for create and Update command to validate the form
     * and return the ActionErrors object.
     *
     * @param aForm is of type ActionForm
     * @param locale of type Locale
     * @return ActionErrors
     */
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return LimitBookingValidator.validateInput((LimitBookingForm)aForm,locale);
    }

    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (event.equals(EVENT_VIEW_RESULT_ADD) || event.equals(EVENT_VIEW_RESULT_EDIT) || event.equals(EVENT_ADD_POL) 
        		|| event.equals(EVENT_EDIT_POL) ||event.equals(EVENT_SEARCH_GROUP)  
        		|| event.equals(EVENT_REMOVE_POL_ADD) || event.equals(EVENT_REMOVE_POL_EDIT) 
        		|| event.equals(EVENT_REMOVE_GROUP_ADD) || event.equals(EVENT_REMOVE_GROUP_EDIT)
        		|| event.equals(EVENT_RETRIEVE_BGEL_ADD) || event.equals(EVENT_RETRIEVE_BGEL_EDIT)
        		|| event.equals(EVENT_SEARCH_BOOKING) || event.equals(EVENT_PREPARE_ADD_POL_ADD)
        		|| event.equals(EVENT_PREPARE_ADD_POL_EDIT) || event.equals(EVENT_PREPARE_EDIT_POL_ADD)
        		|| event.equals(EVENT_PREPARE_EDIT_POL_EDIT) || event.equals(EVENT_PREPARE_SEARCH_GROUP_ADD)
        		|| event.equals(EVENT_PREPARE_SEARCH_GROUP_EDIT))
            result = true;
        return result;
    }

    /**
     * This method is used to determine which the page to be displayed next using the event
     * Result hashmap and exception hashmap.It returns the page object .
     *
     * @param event  is of type String
     * @param resultMap is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
     public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        
        String forwardName = EVENT_PREPARE_ADD_BOOKING;
        
        String fromEvent = (String)(resultMap.get(LIMIT_BOOKING_FROM_EVENT));
        
        if (EVENT_ADD_BOOKING.equals(event) || EVENT_EDIT_BOOKING.equals(event)
        		|| EVENT_RETRIEVE_BGEL_ADD.equals(event) || EVENT_RETRIEVE_BGEL_EDIT.equals(event)
        		|| EVENT_REMOVE_POL_ADD.equals(event) || EVENT_REMOVE_POL_EDIT.equals(event)
        		|| EVENT_REMOVE_GROUP_ADD.equals(event) || EVENT_REMOVE_GROUP_EDIT.equals(event)) {
        	
        	if (fromEvent != null && fromEvent.equals(LIMIT_BOOKING_FROM_EVENT_ADD)) {
            	forwardName = EVENT_PREPARE_ADD_BOOKING;
            }
            else if (fromEvent != null && fromEvent.equals(LIMIT_BOOKING_FROM_EVENT_EDIT)) {
            	forwardName = EVENT_PREPARE_EDIT_BOOKING;
            }
            else {
            	forwardName = event;
            }
        }
        else if (EVENT_ADD_POL.equals(event) || EVENT_EDIT_POL.equals(event)
        		|| EVENT_ADD_GROUP.equals(event) || EVENT_CANCEL_VIEW_RESULT.equals(event)) {
        	if (fromEvent != null && fromEvent.equals(LIMIT_BOOKING_FROM_EVENT_ADD)) {
            	forwardName = LIMIT_BOOKING_FROM_EVENT_ADD;
            }
            else if (fromEvent != null && fromEvent.equals(LIMIT_BOOKING_FROM_EVENT_EDIT)) {
            	forwardName = LIMIT_BOOKING_FROM_EVENT_EDIT;
            }
            else {
            	forwardName = event;
            }
        }
        else if (EVENT_PREPARE_ADD_POL_ADD.equals(event) || EVENT_PREPARE_ADD_POL_EDIT.equals(event) ||
        		EVENT_ADD_POL_ERROR.equals(event)) {
            forwardName = EVENT_PREPARE_ADD_POL;
        }
        else if (EVENT_PREPARE_EDIT_POL_ADD.equals(event) || EVENT_PREPARE_EDIT_POL_EDIT.equals(event) ||
        		EVENT_EDIT_POL_ERROR.equals(event)) {
            forwardName = EVENT_PREPARE_EDIT_POL;
        }
        else if (EVENT_PREPARE_SEARCH_GROUP_ADD.equals(event) || EVENT_PREPARE_SEARCH_GROUP_EDIT.equals(event) ||
        		EVENT_SEARCH_GROUP_ERROR.equals(event)) {
            forwardName = EVENT_PREPARE_SEARCH_GROUP;
        }
        else if (EVENT_ADD_GROUP_ERROR.equals(event)) {
            forwardName = EVENT_SEARCH_GROUP;
        }
        else if (EVENT_VIEW_RESULT_ADD_ERROR.equals(event)) {
            forwardName = EVENT_PREPARE_ADD_BOOKING;
        }
        else if (EVENT_VIEW_RESULT_EDIT_ERROR.equals(event)) {
            forwardName = EVENT_PREPARE_EDIT_BOOKING;
        }
        else if (EVENT_SEARCH_BOOKING_ERROR.equals(event)){
            forwardName = EVENT_PREPARE_SEARCH_BOOKING;
        }
        else {
            forwardName = event;
        }

        DefaultLogger.debug(this, ">>>>>>>>>>> " + forwardName);
        
        Page page = new Page();
        page.setPageReference(forwardName);
        return page;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = null;
        
        if (EVENT_VIEW_RESULT_ADD.equals(event) || EVENT_RETRIEVE_BGEL_ADD.equals(event) || 
        		EVENT_REMOVE_POL_ADD.equals(event) || EVENT_REMOVE_GROUP_ADD.equals(event) ||
        		EVENT_PREPARE_ADD_POL_ADD.equals(event) || EVENT_PREPARE_EDIT_POL_ADD.equals(event) ||
        		EVENT_PREPARE_SEARCH_GROUP_ADD.equals(event)) {
            errorEvent =  EVENT_VIEW_RESULT_ADD_ERROR;
        }
        else if (EVENT_VIEW_RESULT_EDIT.equals(event) || EVENT_RETRIEVE_BGEL_EDIT.equals(event) || 
        		EVENT_REMOVE_POL_EDIT.equals(event) || EVENT_REMOVE_GROUP_EDIT.equals(event) || 
        		EVENT_PREPARE_ADD_POL_EDIT.equals(event) || EVENT_PREPARE_EDIT_POL_EDIT.equals(event) ||
        		EVENT_PREPARE_SEARCH_GROUP_EDIT.equals(event)) {
            errorEvent =  EVENT_VIEW_RESULT_EDIT_ERROR;
        }
        else if (EVENT_ADD_POL.equals(event)) {
            errorEvent =  EVENT_ADD_POL_ERROR;
        }
        else if (EVENT_EDIT_POL.equals(event)) {
            errorEvent =  EVENT_EDIT_POL_ERROR;
        }
        else if (EVENT_SEARCH_GROUP.equals(event)) {
            errorEvent =  EVENT_SEARCH_GROUP_ERROR;
        }
        else if (EVENT_ADD_GROUP.equals(event)) {
        	errorEvent =  EVENT_ADD_GROUP_ERROR;
        }
        else if (EVENT_SEARCH_BOOKING.equals(event)) {
            errorEvent =  EVENT_SEARCH_BOOKING_ERROR;
        }
        
        return errorEvent;
    }

}