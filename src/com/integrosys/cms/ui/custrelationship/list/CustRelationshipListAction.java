package com.integrosys.cms.ui.custrelationship.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.ui.custrelationship.CustRelAction;
import com.integrosys.cms.ui.custrelationship.customer.ProcessDetailsCustomerCommand;


/**
 * @author siew kheat
 *
 */
public class CustRelationshipListAction extends CustRelAction {
    
	protected ICommand[] getCommandChain(String event) {
		
		ICommand objArray [] = null;
		
		if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCustRelationshipListCommand();			
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCustRelationshipListCommand();
        }
		else if (EVENT_READ_MAKER_EDIT.equals(event)
					|| EVENT_REMOVE_NOOP.equals(event)
		) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCustRelationshipListCommand();
			
		}else if (EVENT_ADD.equals(event)
					|| EVENT_ADD_FRAME.equals(event)
		) {
			objArray = new ICommand[1];
			objArray[0] = new AddCustRelationshipListCommand();
		
		} else if (EVENT_ADD_NEW.equals(event)) {
			
			objArray = new ICommand[1];
			objArray[0] = new AddNewCustListCommand();
			
		}else if (EVENT_REMOVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteCustRelationshipListCommand();
			
		} else if (EVENT_SUBMIT.equals(event)) {
            return new ICommand[]{new SubmitCustRelationshipListCommand()};
        } else if (EVENT_SUBMIT_NOOP.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddNewCustListCommand();
        }else if (EVENT_PAGINATE.equals(event)) {
        	objArray = new ICommand[1];
        	objArray[0] = new PaginateCustRelationshipListCommand();
        	
        } else if (EVENT_VIEW.equals(event)) {
            return new ICommand[]{new ReadCustRelationshipListCommand()};
        } else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
            return new ICommand[]{new ReadCustRelationshipListCommand(),
                    new CompareCustRelationshipListCommand()};
        } else if (EVENT_READ_MAKER_CLOSE.equals(event)) {
	        return new ICommand[]{new ReadCustRelationshipListCommand()};
        
        } else if (EVENT_APPROVE.equals(event)) {
            return new ICommand[]{new ApproveCustRelationshipListCommand()};
        } else if (EVENT_REJECT.equals(event)) {
          
        	return new ICommand[]{new RejectCustRelationshipListCommand()};
        } else if (EVENT_TOTRACK.equals(event)) {
          
        	return new ICommand[]{new ReadCustRelationshipListCommand()};
        } 
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCustRelationshipListCommand();			
		} else if (EVENT_VIEW_LIMITPROFILE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessDetailsCustomerCommand();			
		} else if (EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelCustRelationshipListCommand();	
		} else if (EVENT_LIST_VIEW.equals(event) ||
        	EVENT_LIST_READ.equals(event)) {
        	
            return new ICommand[]{new ListCustRelationshipListCommand()};
        } else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
            
        	return new ICommand[]{new ListCompareCustRelationshipListCommand()};
        } else if (EVENT_LIST_MAKER_CLOSE.equals(event)) {
            
        	return new ICommand[]{new ListCustRelationshipListCommand()};
        }
		return objArray;
	}

    /**
     * This method is called only for create and Update command to validate the form
     * and return the ActionErrors object.
     * 
     * @param aForm  is of type ActionForm
     * @param locale of type Locale
     * @return ActionErrors
     */
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        DefaultLogger.debug(this, "Inside validate Input  class " +  ((CustRelationshipListForm) aForm));
        return CustRelationshipListValidator.validateInput( (CustRelationshipListForm) aForm, locale);
    }


    /**
     * This method is used to determine which the page to be displayed next using the event
     * Result hashmap and exception hashmap.It returns the page object .
     * 
     * @param event        is of type String
     * @param resultMap    is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    public IPage getNextPage(String event, HashMap resultMap,
                             HashMap exceptionMap) {

        // String must be one of the struts local forwards.
        String forward = null;

        ICustRelationshipTrxValue value = (ICustRelationshipTrxValue) resultMap.get(
                "CustRelationshipTrxValue");

        boolean isWip = false;
        
        String status = "";
        if (value != null) {
            status = value.getStatus();
            isWip = status.equals(ICMSConstant.STATE_PENDING_UPDATE) ||
		            status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
		            status.equals(ICMSConstant.STATE_PENDING_CREATE) ||
		            status.equals(ICMSConstant.STATE_REJECTED_CREATE);
        }
        
        if (EVENT_PREPARE.equals(event)) {
            if (isWip) {
                forward = "workInProgress";
            } else if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
						status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";
        } else if (EVENT_READ.equals(event)) {
            
            forward = "view";
            
        } else if (EVENT_READ_MAKER_EDIT.equals(event)) {
        	
		   if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        		status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";
        } else if (EVENT_ADD.equals(event)) {
            forward = "add";
		} else if (EVENT_ADD_FRAME.equals(event)) {
            forward = "add_frame";
        } else if (EVENT_ADD_NOOP.equals(event)) {
            forward = "list";
        } else if (EVENT_ADD_NEW.equals(event) || EVENT_CANCEL.equals(event) ) {
        	if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        			status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";        
        } else if (EVENT_REMOVE.equals(event)) {
        	if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        			status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";
        } else if (EVENT_REMOVE_NOOP.equals(event)) {
            if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        			status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";
        } else if (EVENT_SUBMIT.equals(event)) {
            forward = "submit";
        } else if (EVENT_SUBMIT_NOOP.equals(event)) {
        	if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        			status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";
        } else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
            forward = "list2ForChecker";
        } else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
                forward = "list2ForChecker";
        } else if (EVENT_READ_MAKER_CLOSE.equals(event) || EVENT_LIST_MAKER_CLOSE.equals(event)) {
            forward = "view_close";
        } else if (EVENT_PAGINATE.equals(event)) {
        	if (status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
        			status.equals(ICMSConstant.STATE_REJECTED_CREATE))
        		forward = "listForMaker";
        	else
        		forward = "list";	
        } else if (EVENT_APPROVE.equals(event)) {
            forward = "approve";
        } else if (EVENT_REJECT.equals(event)) {
            forward = "reject";
        } else if (EVENT_CLOSE.equals(event)) {
            forward = "close";
        } else if (EVENT_VIEW.equals(event) || EVENT_LIST_VIEW.equals(event)) {
            forward = "view";
        } else if (EVENT_TOTRACK.equals(event) || EVENT_LIST_READ.equals(event)) {
            forward = "track";
        } else if (EVENT_VIEW_LIMITPROFILE.equals(event)) {
        	forward = "viewLimitProfile";
        }

        DefaultLogger.debug(this, "The name of struts forward is " + forward);

        Page page = new Page();
        page.setPageReference(forward);

        return page;
    }


    protected boolean isValidationRequired(String event) {
        return EVENT_REMOVE.equals(event) || 
        		EVENT_SUBMIT.equals(event) || EVENT_APPROVE.equals(event) ||
                EVENT_REJECT.equals(event) | EVENT_CLOSE.equals(event);
    }


    protected String getErrorEvent(String event) {

        if (EVENT_ADD.equals(event)) {
            return EVENT_ADD_NOOP;
        } else if (EVENT_REMOVE.equals(event)) {
            return EVENT_REMOVE_NOOP;
        } else if (EVENT_SUBMIT.equals(event)) {
            return EVENT_SUBMIT_NOOP;
        } else if (EVENT_APPROVE.equals(event)) {
            return EVENT_LIST_CHECKER_APPROVE_REJECT;
        } else if (EVENT_REJECT.equals(event)) {
            return EVENT_LIST_CHECKER_APPROVE_REJECT;
        } else if (EVENT_CLOSE.equals(event)) {
            return EVENT_LIST_MAKER_CLOSE;
        }else
        	return event;
        
    }


    protected String getDefaultEvent() {
        return EVENT_READ;
    }


    /** For reading items. */
    public static final String EVENT_READ = "read";

    public static final String EVENT_READ_MAKER_EDIT = "readMakerEdit";
    
    public static final String EVENT_LIST_READ = "listRead";

    /** For adding new record. */
    public static final String EVENT_ADD = "add";
    public static final String EVENT_ADD_FRAME = "add_frame";
    
    public static final String EVENT_ADD_NEW = "add_new";

    public static final String EVENT_ADD_NOOP = "addNoop";

    /** For removing checked items. */
    public static final String EVENT_REMOVE = "remove";

    /** Just to go back to list page without reexecuting anything. */
    public static final String EVENT_REMOVE_NOOP = "removeNoop";

    /** For saving and then going to notification page. */
    public static final String EVENT_SAVE = "save";

    /** For saving into session and then going to list page. */
    public static final String EVENT_PAGINATE = "paginate";

    /** Just to go back to list page. */
    public static final String EVENT_SAVE_NOOP = "saveNoop";

    /** For submitting. */
    public static final String EVENT_SUBMIT = "submit";

    /** Just to go back to list page. */
    public static final String EVENT_SUBMIT_NOOP = "submitNoop";

    /**
     *
     */
    public static final String EVENT_READ_CHECKER_APPROVE_REJECT = "readCheckerApproveReject";

    public static final String EVENT_READ_MAKER_CLOSE = "readMakerClose";

    /** For checker approving. */
    public static final String EVENT_APPROVE = "approve";

    /** For checker rejecting. */
    public static final String EVENT_REJECT = "reject";

    /** For listing for checker. */
    public static final String EVENT_LIST_CHECKER_APPROVE_REJECT = "listCheckerApproveReject";

    public static final String EVENT_LIST_MAKER_CLOSE = "listMakerClose";

    /** For maker closing. */
    public static final String EVENT_CLOSE = "close";

    public static final String EVENT_LIST_STAGING = "listStaging";

    /** For totrack view. */
    public static final String EVENT_VIEW = "view";

    /** For totrack view pagination. */
    public static final String EVENT_LIST_VIEW = "listView";
    
    public static final String EVENT_TOTRACK = "to_track";
    
    public static final String EVENT_VIEW_LIMITPROFILE = "viewLimitProfile";
}
