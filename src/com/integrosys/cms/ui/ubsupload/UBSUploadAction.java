/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.ubsupload;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.holiday.bus.HolidayException;

/**
 * @author $Author: Abhijeet J Action For UBS Upload
 */
public class UBSUploadAction extends CommonAction{

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public static final String CSVUPLOAD = "csvupload";
	public static final String UBSUPLOAD ="ubsupload";
	public static final String EVENT_PAGINATION = "paginate";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event
	 *            is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		DefaultLogger.debug(this, "In Action with event --" + event);
		ICommand objArray[] = null;
		if (event.equals(CSVUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UbsUploadCmd");
		} else if (event.equals(UBSUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UbsFileUploadCmd");
		} else if (event.equals(EVENT_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}

		return (objArray);
	}


	protected boolean isValidationRequired(String event) {
		boolean result = false;

		return result;
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event
	 *            is of type String
	 * @param resultMap
	 *            is of type HashMap
	 * @param exceptionMap
	 *            is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;

	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();

		return errorEvent;
	}

	/**
	 * method which determines the forward name for a particular event
	 * 
	 * @param event
	 *            as String
	 * @return String
	 */
	private String getReference(String event) {
		String forwardName = null;
		if ((event != null) && (event.equals(CSVUPLOAD))) {
			forwardName = "csvupload";
		}
		if ((event != null) && (event.equals(UBSUPLOAD) || (event.equals(EVENT_PAGINATION)))) {
			forwardName = "ubsupload";
		}
		return forwardName;
	}

}