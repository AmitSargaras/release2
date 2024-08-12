/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainAction.java,v 1.6 2005/07/18 08:11:21 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralAction;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/18 08:11:21 $ Tag: $Name: $
 */

public class CommodityMainAction extends CollateralAction implements IPin {
	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_EXPOSURE_SUMMARY = "summary";

	public static final String EVENT_VIEW = "view";

	public static final String EVENT_PROCESS_VIEW_ERROR = "process_view_error";

	public static final String EVENT_CLOSE_VIEW_ERROR = "close_view_error";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this + " getCommandChain", "event is: " + event);
		ICommand objArray[] = null;
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommodityMainCreateCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommodityMainCommand();
			objArray[1] = new PrepareCommodityMainCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)
				|| EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCommodityMainCommand();
			objArray[1] = new PrepareCommodityMainCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnCommodityMainCommand();
			objArray[1] = new PrepareCommodityMainCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnCommodityMainCommand();
			objArray[1] = new PrepareCommodityMainCommand();
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessingCommodityMainCommand();
		}
		else if (event.endsWith(EVENT_DELETE_ITEM)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteCommodityMainCommand();
			objArray[1] = new PrepareCommodityMainCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCommodityMainCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SubmitCommodityMainCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ApproveCommodityMainCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RejectCommodityMainCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateCommodityMainCommand();
		}
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CloseCommodityMainCommand();
		}
		else if (EVENT_EXPOSURE_SUMMARY.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ProcessingCommodityMainCommand();
			objArray[1] = new ReadExposureSummaryCommand();
		}
		else if (event.endsWith(EVENT_VIEW)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCommodityMainCommand();
		}
		else if (EVENT_PROCESS_VIEW_ERROR.equals(event) || EVENT_CLOSE_VIEW_ERROR.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ViewCommodityMainCommand();
			objArray[1] = new ReturnCommodityMainCommand();
			objArray[2] = new PrepareCommodityMainCommand();
		}

		return objArray;
	}

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return CommodityMainValidator.validateInput((CommodityMainForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		else if (EVENT_APPROVE.equals(event) || EVENT_REJECT.equals(event)) {
			errorEvent = EVENT_PROCESS_VIEW_ERROR;
		}
		else if (EVENT_CLOSE.equals(event)) {
			errorEvent = EVENT_CLOSE_VIEW_ERROR;
		}

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| event.endsWith(EVENT_DELETE_ITEM) || event.endsWith(EVENT_PREPARE) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event) || EVENT_CLOSE.equals(event)) {
			result = true;
		}
		return result;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		DefaultLogger.debug(this, "at next page method: event: " + event);
		Page aPage = new Page();
		if ((resultMap.get("wip") != null) && (resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if (event.endsWith(EVENT_PREPARE)) {
			aPage.setPageReference(getAddReference(event));
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB)) {
			aPage.setPageReference(getUpdateReference(event));
		}
		else if (event.endsWith(EVENT_VIEW)) {
			aPage.setPageReference(getViewReference(event));
		}
		else {
			aPage.setPageReference(getReference(event));
		}
		return aPage;
	}

	/**
	 * method which determines the forward name for a particular event
	 * @param event as String
	 * @return String
	 */
	private String getReference(String event) {
		if (EVENT_PREPARE_FORM.equals(event) || event.endsWith(EVENT_DELETE_ITEM) || EVENT_UPDATE_RETURN.equals(event)
				|| EVENT_PROCESS_UPDATE.equals(event)) {
			return EVENT_PREPARE_UPDATE;
		}
		if (EVENT_TRACK_RETURN.equals(event)) {
			return EVENT_TRACK;
		}
		if (EVENT_PROCESS_RETURN.equals(event) || EVENT_PROCESS_VIEW_ERROR.equals(event)) {
			return EVENT_PROCESS;
		}
		if (EVENT_READ_RETURN.equals(event)) {
			return EVENT_READ;
		}
		if (EVENT_CLOSE_RETURN.equals(event) || EVENT_CLOSE_VIEW_ERROR.equals(event)) {
			return EVENT_PREPARE_CLOSE;
		}
		return event;
	}

	private String getViewReference(String event) {
		if (event.startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
			return "view_" + CommodityMainConstant.APPROVED_COMMODITY;
		}
		if (event.startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
			return "view_" + CommodityMainConstant.COMMODITY_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
			return "view_" + CommodityMainConstant.HEDGED_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.SECURITY_DETAILS)) {
			return "view_" + CommodityMainConstant.SECURITY_DETAILS;
		}
		if (event.startsWith(CommodityMainConstant.LOAN_AGENCY)) {
			return "view_" + CommodityMainConstant.LOAN_AGENCY;
		}
		return event;
	}

	private String getUpdateReference(String event) {
		if (event.startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
			return "update_" + CommodityMainConstant.APPROVED_COMMODITY;
		}
		if (event.startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
			return "update_" + CommodityMainConstant.COMMODITY_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
			return "update_" + CommodityMainConstant.HEDGED_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.SECURITY_DETAILS)) {
			return "update_" + CommodityMainConstant.SECURITY_DETAILS;
		}
		if (event.startsWith(CommodityMainConstant.LOAN_AGENCY)) {
			return "update_" + CommodityMainConstant.LOAN_AGENCY;
		}
		return event;
	}

	private String getAddReference(String event) {
		if (event.startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
			return "add_" + CommodityMainConstant.APPROVED_COMMODITY;
		}
		if (event.startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
			return "add_" + CommodityMainConstant.COMMODITY_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
			return "add_" + CommodityMainConstant.HEDGED_CONTRACT;
		}
		if (event.startsWith(CommodityMainConstant.LOAN_AGENCY)) {
			return "add_" + CommodityMainConstant.LOAN_AGENCY;
		}
		return event;
	}

}
