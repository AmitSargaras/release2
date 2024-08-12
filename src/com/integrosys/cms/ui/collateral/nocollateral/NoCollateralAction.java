package com.integrosys.cms.ui.collateral.nocollateral;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.pledge.DeletePledgeCommand;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 26, 2007 Time: 4:17:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoCollateralAction extends CollateralAction implements IPin{

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCollateralCreateCommand();
			objArray[1] = new PrepareNoCollateralCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new PrepareNoCollateralCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareNoCollateralCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareNoCollateralCommand();
			objArray[1] = new ReturnCollateralCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteNoCollateralCommand();
			objArray[1] = new PrepareNoCollateralCommand();
		}
		else if (EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareNoCollateralCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareNoCollateralCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event)) {
			result = true;
		}
		return result;
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
		return NoCollateralValidator.validateInput((NoCollateralForm) aForm, locale);
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (EVENT_REFRESH.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_UPDATE);
		} else {
			aPage = (Page)super.getNextPage(event, resultMap, exceptionMap);
		}
		return aPage;
	}

}
