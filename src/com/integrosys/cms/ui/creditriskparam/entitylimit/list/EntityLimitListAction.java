/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitAction;


/**
 * Entity Limit Limit Action class
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class EntityLimitListAction extends EntityLimitAction {
    
	protected ICommand[] getCommandChain(String event) {
		
		ICommand objArray [] = null;
		
		if (EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new ReadEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
		}
		else if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new ReadEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
        }
		else if (EVENT_READ_MAKER_EDIT.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new ReadEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
			
		}else if (EVENT_ADD.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new PrepareAddNewEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareAddNewEntityLimitListCommand");
			
		}else if (EVENT_REMOVE.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new DeleteEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteEntityLimitListCommand");
			
		} else if (EVENT_SUBMIT.equals(event)) {
//            return new ICommand[]{new SubmitEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("SubmitEntityLimitListCommand");
        } else if (EVENT_SUBMIT_NOOP.equals(event)) {
            return null;
            
        } else if (EVENT_VIEW.equals(event)) {
//            return new ICommand[]{new ReadEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
        } else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
//            return new ICommand[]{new ReadEntityLimitListCommand(),
//                    new CompareEntityLimitListCommand()};
            objArray = new ICommand[2];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
            objArray[1] = (ICommand) getNameCommandMap().get("CompareEntityLimitListCommand");
        } else if (EVENT_READ_MAKER_CLOSE.equals(event)) {
//	        return new ICommand[]{new ReadEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
        
        } else if (EVENT_APPROVE.equals(event)) {
//            return new ICommand[]{new ApproveEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ApproveEntityLimitListCommand");
        } else if (EVENT_REJECT.equals(event)) {
          
//        	return new ICommand[]{new RejectEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("RejectEntityLimitListCommand");
        } else if (EVENT_TOTRACK.equals(event)) {
          
//        	return new ICommand[]{new ReadEntityLimitListCommand()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReadEntityLimitListCommand");
        } 
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new CloseEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("CloseEntityLimitListCommand");
		} else if (EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new CancelEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("CancelEntityLimitListCommand");
		} else if (EVENT_EDIT.equals(event) || EVENT_EDIT_NOOP.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new EditEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("EditEntityLimitListCommand");
			
		} else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
//			objArray[0] = new UpdateEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("UpdateEntityLimitListCommand");
		} else if (EVENT_PAGINATE.equals(event)) {
        	objArray = new ICommand[1];
//        	objArray[0] = new PaginateEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("PaginateEntityLimitListCommand");
		} else if (EVENT_LIST_VIEW.equals(event) ||
        	EVENT_LIST_READ.equals(event) || 
        	EVENT_LIST_MAKER_CLOSE.equals(event)) {
			
			objArray = new ICommand[1];
//			objArray[0] = new ListEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ListEntityLimitListCommand");
        } else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
            
        	objArray = new ICommand[1];
//        	objArray[0] = new ListCompareEntityLimitListCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ListCompareEntityLimitListCommand");
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
        DefaultLogger.debug(this, "Inside validate Input  class " +  ((EntityLimitListForm) aForm));
        return EntityLimitListValidator.validateInput( (EntityLimitListForm) aForm, locale);
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

        IEntityLimitTrxValue value = (IEntityLimitTrxValue) resultMap.get(
                "EntityLimitTrxValue");

        boolean isWip = false;
        
        String status = "";
        if (value != null) {
            status = value.getStatus();
            isWip = status.equals(ICMSConstant.STATE_PENDING_UPDATE) ||
                    status.equals(ICMSConstant.STATE_REJECTED_UPDATE) ||
                    status.equals(ICMSConstant.STATE_PENDING_CREATE) ||
                    status.equals(ICMSConstant.STATE_REJECTED_CREATE);
        }
		
		if (resultMap.get("foundError")!=null && (resultMap.get("foundError")).equals("foundError")){
           forward = "list";
		}		
        else if (EVENT_PREPARE.equals(event) || EVENT_READ.equals(event)) {
            if (isWip) {
                forward = "workInProgress";
            } else {
                forward = "list";
            }
        } else if (EVENT_READ.equals(event)) {
            forward = "list";
        } else if (EVENT_READ_MAKER_EDIT.equals(event)) {
            forward = "list";
        } else if (EVENT_ADD.equals(event)) {
            forward = "add";
        } else if (EVENT_ADD_NOOP.equals(event)) {
            forward = "list";
        } else if (EVENT_CANCEL.equals(event) ) {
            forward = "list";        
        } else if (EVENT_REMOVE.equals(event)) {
            forward = "list";
        } else if (EVENT_REMOVE_NOOP.equals(event)) {
            forward = "list";
        } else if (EVENT_SUBMIT.equals(event)) {
            forward = "submit";
        } else if (EVENT_SUBMIT_NOOP.equals(event)) {
            forward = "list";
        } else if (EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)) {
            forward = "list2ForChecker";
        } else if (EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)) {
            forward = "list2ForChecker";
        } else if (EVENT_READ_MAKER_CLOSE.equals(event) || EVENT_LIST_MAKER_CLOSE.equals(event)) {
            forward = "view_close";
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
        } else if (EVENT_ADD_NEW.equals(event)) {
          	forward = "edit";
        } else if (EVENT_UPDATE.equals(event)) {
        	forward = "list";
        } else if (EVENT_EDIT.equals(event)) {
        	forward = "edit";
        } else if (EVENT_EDIT_NOOP.equals(event)) {
        	forward = "edit";
        } else if (EVENT_PAGINATE.equals(event)) {
        	forward = "list";
        }
		
        DefaultLogger.debug(this, "The name of struts forward is " + forward);

        Page page = new Page();
        page.setPageReference(forward);

        return page;
    }


    protected boolean isValidationRequired(String event) {
        return EVENT_REMOVE.equals(event) || 
        		EVENT_SUBMIT.equals(event) || EVENT_APPROVE.equals(event) ||
                EVENT_REJECT.equals(event) || EVENT_CLOSE.equals(event) ||
                EVENT_UPDATE.equals(event);
    }


    protected String getErrorEvent(String event) {

        if (EVENT_ADD.equals(event)) {
            return EVENT_ADD_NOOP;
        } else if (EVENT_REMOVE.equals(event)) {
            return EVENT_REMOVE_NOOP;
        } else if (EVENT_SUBMIT.equals(event)) {
            return EVENT_SUBMIT_NOOP;
        } else if (EVENT_APPROVE.equals(event)) {
            return EVENT_READ_CHECKER_APPROVE_REJECT;
        } else if (EVENT_REJECT.equals(event)) {
            return EVENT_READ_CHECKER_APPROVE_REJECT;
        } else if (EVENT_CLOSE.equals(event)) {
            return EVENT_READ_MAKER_CLOSE;
        } else if (EVENT_UPDATE.equals(event)) {
            return EVENT_EDIT_NOOP;
        } else
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

    /** For edit. */
    public static final String EVENT_EDIT = "edit";    
    
    /** For edit. */
    public static final String EVENT_EDIT_NOOP = "editNoop";    
    
    /** For update edit */
    public static final String EVENT_UPDATE = "update";
    
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
}
