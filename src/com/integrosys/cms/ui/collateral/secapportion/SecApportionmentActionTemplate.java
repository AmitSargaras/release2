/*
 * Created on Jun 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentActionTemplate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */

	public static ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (CollateralAction.EVENT_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadApportionmentCommand();
		}
		else if (CollateralAction.EVENT_PREPARE_UPDATE_SUB.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadApportionmentCommand();
		}
		else if (CollateralAction.EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareApportionmentCommand();
		}
		else if (CollateralAction.EVENT_REFRESH.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareApportionmentCommand();
			objArray[1] = new RefreshApportionmentCommand();
		}
		else if (CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
		}
		else if (CollateralAction.EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddApportionmentCommand();
		}
		else if (CollateralAction.EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateApportionmentCommand();
		}
		return objArray;
	}

	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SecApportionmentValidator.validateInput((SecApportionmentForm) aForm, locale);
	}

	public static boolean isValidationRequired(String event) {
		boolean result = false;
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)) {
			result = true;
		}
		return result;
	}

	public static String getErrorEvent(String event) {
		String errorEvent = event;
		if (CollateralAction.EVENT_CREATE.equals(event)) {
			errorEvent = CollateralAction.EVENT_REFRESH;
		}
		else if (CollateralAction.EVENT_UPDATE.equals(event)) {
			errorEvent = CollateralAction.EVENT_REFRESH;
		}

		return errorEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	public static IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (CollateralAction.EVENT_CREATE.equals(event) || CollateralAction.EVENT_UPDATE.equals(event)
				|| CollateralAction.EVENT_READ_RETURN.equals(event) || CollateralAction.EVENT_CANCEL.equals(event)) {
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				throw new RuntimeException("URL passed is wrong");
			}
			else if (CollateralAction.EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		else {
			String from_event = (String) resultMap.get("from_event");
			if (CollateralAction.EVENT_READ.equals(event) && (from_event != null) && from_event.equals("process")) {
				aPage.setPageReference(CollateralAction.EVENT_PROCESS);
			}
			else {
				aPage.setPageReference(event);
			}
		}
		return aPage;
	}
}
