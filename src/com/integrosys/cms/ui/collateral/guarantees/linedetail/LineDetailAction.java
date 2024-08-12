package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.guarantees.GuaranteesAction;

public class LineDetailAction extends GuaranteesAction {

	public static final String EVENT_PREPARE_CREATE_LINE_DETAIL = "prepare_create_line_detail";
	public static final String EVENT_CREATE_LINE_DETAIL = "create_line_detail";
	public static final String EVENT_REFRESH_FACILITY_DROPDOWN = "refresh_facility_dropdown";
	public static final String EVENT_CANCEL_LINE_DETAIL = "cancel_line_detail";
	public static final String EVENT_DELETE_LINE_DETAIL = "delete_line_detail";
	public static final String EVENT_PREPARE_EDIT_LINE_DETAIL = "prepare_edit_line_detail";
	public static final String EVENT_EDIT_LINE_DETAIL = "edit_line_detail";
	public static final String EVENT_VIEW_LINE_DETAIL = "view_line_detail";
	
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if(EVENT_PREPARE_CREATE_LINE_DETAIL.equals(event) || EVENT_PREPARE_EDIT_LINE_DETAIL.equals(event) ||
				EVENT_VIEW_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLineDetailCommand();
		}
		else if(EVENT_REFRESH_FACILITY_DROPDOWN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFacilityDropdownCommand();
		}
		else if(EVENT_CANCEL_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BackToCollateralCommand();
		}else if(EVENT_CREATE_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddLineDetailCommand();
			objArray[1] = new BackToCollateralCommand();
		}else if(EVENT_EDIT_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditLineDetailCommand();
			objArray[1] = new BackToCollateralCommand();
		}else if(EVENT_DELETE_LINE_DETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteLineDetailCommand();
			objArray[1] = new BackToCollateralCommand();
		}else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		
		if(EVENT_CREATE_LINE_DETAIL.equals(event) || EVENT_EDIT_LINE_DETAIL.equals(event)) {
			result = true;
		}
		
		return result;
	}
	
	protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return LineDetailValidator.validateInput( (LineDetailForm) aForm, locale);
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE_LINE_DETAIL.equals(event)) {
			errorEvent = EVENT_PREPARE_CREATE_LINE_DETAIL;
		}
		else if(EVENT_EDIT_LINE_DETAIL.equals(event)) {
			errorEvent = EVENT_PREPARE_EDIT_LINE_DETAIL;
		}
		return errorEvent;
	}
	
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.info(this, "Event is  " +event);
				if(EVENT_CANCEL_LINE_DETAIL.equals(event) || EVENT_CREATE_LINE_DETAIL.equals(event)
				|| EVENT_DELETE_LINE_DETAIL.equals(event) || EVENT_EDIT_LINE_DETAIL.equals(event)) {
			String subtype = (String) resultMap.get("subtype");
			DefaultLogger.info(this, "LineDetailAction subtype = " + subtype);
			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			DefaultLogger.info(this, "Page is forwared to " + subtype + "_" + (String) resultMap.get("from_event"));
			aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
		
		}else if("saveDetailsOfGuaranteeForBank".equals(event)) {
			aPage.setPageReference("saveDetailsOfGuaranteeForBank");
		}else if("saveDetailsOfGuaranteeForLC".equals(event)){
			aPage.setPageReference("saveDetailsOfGuaranteeForLC");
		}else {
			aPage.setPageReference(event);
		}
		return aPage;		
	}
	
}
