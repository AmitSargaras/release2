package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.marketablesec.PortItemAction;

public class MarketableEquityLineDetailAction extends PortItemAction {
	
	public static final String EVENT_PREPARE_CREATE_LINE_DETAIL = "prepare_create_line_detail";
	
	public static final String EVENT_CREATE_LINE_DETAIL = "create_line_detail";
	
	public static final String EVENT_REFRESH_FACILITY_DROPDOWN = "refresh_facility_dropdown";
	
	public static final String EVENT_CANCEL_LINE_DETAIL = "cancel_line_detail";
	
	public static final String EVENT_DELETE_LINE_DETAIL = "delete_line_detail";
	
	public static final String EVENT_PREPARE_EDIT_LINE_DETAIL = "prepare_edit_line_detail";
	
	public static final String EVENT_ERROR_EDIT_LINE_DETAIL = "error_edit_line_detail";
	
	public static final String EVENT_EDIT_LINE_DETAIL = "edit_line_detail";
	
	public static final String EVENT_VIEW_LINE_DETAIL = "view_line_detail";

	public ICommand[] getCommandChain(String event) {
		
		ICommand objArray[] = null;
		
		if(EVENT_PREPARE_CREATE_LINE_DETAIL.equals(event) ||
				EVENT_PREPARE_EDIT_LINE_DETAIL.equals(event) || EVENT_VIEW_LINE_DETAIL.equals(event) || EVENT_ERROR_EDIT_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLineDetailCommand();
		}
		else if(EVENT_REFRESH_FACILITY_DROPDOWN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFacilityDropdownCommand();
		}
		else if(EVENT_CANCEL_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CancelLineItemCommand();
		}
		else if(EVENT_CREATE_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CreateLineDetailItemCommand();
			objArray[1] = new CancelLineItemCommand();
		}
		else if(EVENT_EDIT_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditLineDetailCommand();
			objArray[1] = new CancelLineItemCommand();
		}
		else if(EVENT_DELETE_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteLineDetailCommand();
			objArray[1] = new CancelLineItemCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}
		
		return objArray;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if(EVENT_CREATE_LINE_DETAIL.equals(event) || EVENT_EDIT_LINE_DETAIL.equals(event)) {
			return true;
		}
		
		return result;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if(EVENT_CANCEL_LINE_DETAIL.equals(event) || EVENT_CREATE_LINE_DETAIL.equals(event)
				|| EVENT_DELETE_LINE_DETAIL.equals(event) || EVENT_EDIT_LINE_DETAIL.equals(event)) {

			String subtype = (String) resultMap.get("subtype");

			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			String from_event = (String) resultMap.get("from_event");
			if(null != from_event && from_event.endsWith("prepare_update_sub")) {
				aPage.setPageReference(subtype + "_" + "prepare_update_sub");
			}
			else if(null != from_event && from_event.endsWith("prepare")) {
				aPage.setPageReference(subtype + "_" + "prepare");
			}
			else if(null != from_event && (from_event.endsWith("read") || from_event.endsWith("close") || from_event.endsWith("process"))) {
				aPage.setPageReference(subtype + "_" + "read");
			}
		}
		else if(EVENT_ERROR_EDIT_LINE_DETAIL.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_EDIT_LINE_DETAIL);
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MarketableEquityLineDetailValidator.validateInput((MarketableEquityLineDetailForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		
		if(EVENT_CREATE_LINE_DETAIL.equals(errorEvent)) {
			errorEvent = EVENT_PREPARE_CREATE_LINE_DETAIL;
		}
		else if(EVENT_EDIT_LINE_DETAIL.equals(event)) {
			errorEvent = EVENT_ERROR_EDIT_LINE_DETAIL;
		}
		
		return errorEvent;
	}

}
