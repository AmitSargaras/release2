//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.EditProcessingCollateralCommand;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.MarketableEquityLineDetailAction;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:49:22 PM
 * To change this template use Options | File Templates.
 */
// this needs to extend from Collateral Action to fit the IPin interface. This
// is a workaround till IPin is removed from
// Marker interface.
public class PortItemAction extends MarketableSecAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];

		if (event.endsWith(EVENT_READ)) {
			objArray[0] = new ReadPortItemCommand();
		}
		else if (event.endsWith(EVENT_PREPARE)) {
			objArray[0] = new PreparePortItemCommand();
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePortItemCommand();
			objArray[1] = new ReadPortItemCommand();
		}else if (event.endsWith(EVENT_PREPARE_UPDATE_CODE)) {
			objArray = new ICommand[1];
			objArray[0] = new EditProcessingCollateralCommand();
			//objArray[1] = new ReadPortItemCommand();
		}else if (event.endsWith(EVENT_PREPARE_UPDATE_SCRIPTCODE)) {
			objArray = new ICommand[2];
			objArray[0] = new PreparePortItemCommand();
			objArray[1] = new EditProcessingCollateralCommand();
			//objArray[1] = new ReadPortItemCommand();
		}
		else if (event.endsWith(EVENT_CREATE)) {
			objArray[0] = new AddPortItemCommand();
		}
		else if (event.endsWith(EVENT_UPDATE)) {
			objArray[0] = new UpdatePortItemCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray[0] = new DeletePortItemCommand();
		}
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray[0] = new ReadReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray[0] = new DeletePortItemCommand();
		}
		else if (event.endsWith(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL)) {
			objArray[0] = new SaveFormToSessionPortItemCommand();
		}

		return objArray;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.endsWith(EVENT_CREATE) || (event.endsWith(EVENT_UPDATE) && !event.endsWith(EVENT_PREPARE_UPDATE)) 
				|| (event.endsWith(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL))) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (event.endsWith(EVENT_CREATE)) {
			int index = event.lastIndexOf(EVENT_CREATE);
			errorEvent = event.substring(0, index - 1) + "_" + EVENT_PREPARE;
			DefaultLogger.debug(this, "errorEvent is :" + errorEvent);
		}
		else if ((event.endsWith(EVENT_UPDATE) && !event.endsWith(EVENT_PREPARE_UPDATE))) {
			int index = event.lastIndexOf(EVENT_UPDATE);
			errorEvent = event.substring(0, index - 1) + "_" + EVENT_PREPARE;
			DefaultLogger.debug(this, "errorEvent is :" + errorEvent);
		}
		else if(event.endsWith(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL)) {
			int index = event.lastIndexOf(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL);
			errorEvent = event.substring(0, index - 1) + "_" + EVENT_PREPARE;
			DefaultLogger.debug(this, "errorEvent is :" + errorEvent);
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String subtype = (String) resultMap.get("subtype");
		if (event.endsWith(EVENT_CREATE) || (event.endsWith(EVENT_UPDATE) && !event.endsWith(EVENT_PREPARE_UPDATE))
				|| EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		else if(EVENT_PREPARE_UPDATE_CODE.equals(event))
		{
			aPage.setPageReference(subtype.toLowerCase()+ "_prepare_update_sub");
		}else if(EVENT_PREPARE_UPDATE_SCRIPTCODE.equals(event))
		{
			aPage.setPageReference(subtype.toLowerCase()+ "_prepare_update_sub");
		}	
		else if(event.endsWith(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL)) {
			aPage.setPageReference(subtype.toLowerCase()+ "_prepare_create_line_detail");
		}
		else {
			aPage.setPageReference(event);
		}
		return aPage;
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
		return PortItemValidator.validateInput((PortItemForm) aForm, locale);
	}
}
