package com.integrosys.cms.ui.createfacupload;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;


public class CreateFacilityLineUploadAction extends CommonAction{

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public static final String EXCELUPLOAD = "excelupload";
	public static final String CREATEFACILITYLINEUPLOAD ="createFacilitylineupload";
	//public static final String EVENT_PAGINATION = "paginate";
//	public static final String ERROR_REJECT_RELEASELINEDETAILS="error_reject_releaselinedetails";
	public static final String MAKER_PREPARE_RELEASELINEDETAILS_CLOSE = "maker_prepare_releaselinedetails_close";
	//public static final String MAKER_CLOSE_RELEASELINEDETAILS_FILE = "maker_close_releaselinedetails_file";
	public static final String ERROR_EXCELUPLOAD="error_excelupload";
	
	//public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	
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
		if (event.equals(EXCELUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CreateFacilityLineUploadCmd");
		} else if (event.equals(CREATEFACILITYLINEUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CreateFacilitylineFileUploadCmd");
		}/* else if (event.equals(EVENT_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}else if (event.equals(MAKER_PREPARE_RELEASELINEDETAILS_CLOSE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadReleaselinedetailsRejectCmd");
		}else if (event.equals(REJECTED_DELETE_READ)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadProcessReleaselinedetailsFile");
		}*/
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
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		
		if ("createfacilitylineupload".equals(errorEvent)) {
			errorEvent = ERROR_EXCELUPLOAD;
		}
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
		if ((event != null) && (event.equals(EXCELUPLOAD))) {
			forwardName = "excelupload";
		}else if ((event != null) && (event.equals(CREATEFACILITYLINEUPLOAD) /*|| (event.equals(EVENT_PAGINATION))*/)) {
			forwardName = "createfacilitylineupload";
		}/*else if (ERROR_REJECT_RELEASELINEDETAILS.equals(event)){
			forwardName=ERROR_REJECT_RELEASELINEDETAILS;
		}*/else if (ERROR_EXCELUPLOAD.equals(event)){
			forwardName=ERROR_EXCELUPLOAD;
		}
		return forwardName;
	}
}