/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.partycamupload;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: Abhijeet J Action For UBS Upload
 */
public class PartyCamUploadAction extends CommonAction{

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public static final String CSVUPLOAD = "csvupload";
	public static final String PARTYCAMUPLOAD ="partycamupload";
	public static final String EVENT_PAGINATION = "paginate";
	public static final String DOWNLOAD_REPORT = "downloadReport";
	public static final String CHECKER_PROCESS_CREATE_PARTYCAM = "checker_process_create_partycam";
	public static final String CHECKER_APPROVE_CREATE_PARTYCAM = "checker_approve_create_partycam";
	public static final String CHECKER_REJECT_CREATE_PARTYCAM = "checker_reject_create_partycam";
	public static final String ERROR_REJECT_PARTYCAM="error_reject_partycam";
	public static final String MAKER_PREPARE_PARTYCAM_CLOSE = "maker_prepare_partycam_close";
	public static final String MAKER_CLOSE_PARTYCAM_FILE = "maker_close_partycam_file";
	public static final String ERROR_CSVUPLOAD="error_csvupload";
	
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	
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
			
			objArray[0] = (ICommand) getNameCommandMap().get("PartyCamUploadCmd");
		} else if (event.equals(PARTYCAMUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PartyCamFileUploadCmd");
		} else if (event.equals(EVENT_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}else if (event.equals(DOWNLOAD_REPORT)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadReportCommand");
	
		}else if (event.equals(CHECKER_PROCESS_CREATE_PARTYCAM)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadPartyCamFileUploadCmd");
		}else if (event.equals(CHECKER_APPROVE_CREATE_PARTYCAM) ){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApprovePartyCamFileUploadCmd");
		}else if (event.equals(CHECKER_REJECT_CREATE_PARTYCAM)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectPartyCamFileCmd");
		}else if (event.equals(MAKER_PREPARE_PARTYCAM_CLOSE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadPartyCamRejectCmd");
		}else if (event.equals(MAKER_CLOSE_PARTYCAM_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerClosePartyCamFileCmd");
		}else if (event.equals(REJECTED_DELETE_READ)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadProcessPartyCamFile");
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
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if ("checker_reject_create_partycam".equals(errorEvent)) {
			errorEvent = ERROR_REJECT_PARTYCAM;
		}
		if ("partycamupload".equals(errorEvent)) {
			errorEvent = ERROR_CSVUPLOAD;
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
		if ((event != null) && (event.equals(CSVUPLOAD))) {
			forwardName = "csvupload";
		}else if ((event != null) && (event.equals(PARTYCAMUPLOAD) || (event.equals(EVENT_PAGINATION)))) {
			forwardName = "partycamupload";
		}else if (event.equals("downloadReport")){
			forwardName = "downloadReport";
		}else if (event.equals("checker_process_create_partycam")){
			forwardName = "checker_process_create_partycam";
		}else if (event.equals("checker_approve_create_partycam")){
			forwardName = "checker_approve_create_partycam";
		}else if (event.equals("checker_reject_create_partycam")){
			forwardName = "checker_reject_create_partycam";
		}else if (ERROR_REJECT_PARTYCAM.equals(event)){
			forwardName=ERROR_REJECT_PARTYCAM;
		}else if (event.equals("maker_prepare_partycam_close")){
			forwardName = "maker_prepare_partycam_close";
		}else if (event.equals("maker_close_partycam_file")){
			forwardName = "maker_close_partycam_file";
		}else if (ERROR_CSVUPLOAD.equals(event)){
			forwardName=ERROR_CSVUPLOAD;
		}else if ("rejected_delete_read".equals(event)){
			forwardName = "rejected_delete_read";
		}
		return forwardName;
	}

}