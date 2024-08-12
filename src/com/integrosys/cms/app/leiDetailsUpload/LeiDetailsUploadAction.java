package com.integrosys.cms.app.leiDetailsUpload;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * @author $Author: Mukesh Mohapatra Action For CERSAI LeiDetailsUpload Upload
 */
public class LeiDetailsUploadAction extends CommonAction{

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public static final String EXCELUPLOAD = "excelupload";
	public static final String LEIDETAILSUPLOAD ="leidetailsupload";
	public static final String EVENT_PAGINATION = "paginate";
	public static final String DOWNLOAD_REPORT = "downloadReport";
	public static final String CHECKER_PROCESS_CREATE_LEIDETAILS = "checker_process_create_leidetails";
	public static final String CHECKER_APPROVE_CREATE_LEIDETAILS = "checker_approve_create_leidetails";
	public static final String CHECKER_REJECT_CREATE_LEIDETAILS = "checker_reject_create_leidetails";
	public static final String ERROR_REJECT_LEIDETAILS = "error_reject_leidetails";
	public static final String MAKER_PREPARE_LEIDETAILS_CLOSE = "maker_prepare_leidetails_close";
	public static final String MAKER_CLOSE_LEIDETAILS_FILE = "maker_close_leidetails_file";
	public static final String ERROR_EXCELUPLOAD="error_excelupload";
	
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
		if (event.equals(EXCELUPLOAD)) {
			objArray = new ICommand[1];
			
			objArray[0] = (ICommand) getNameCommandMap().get("LeiDetailsUploadCmd");
		} else if (event.equals(LEIDETAILSUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("LeiDetailsFileUploadCmd");
		} else if (event.equals(EVENT_PAGINATION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
		}else if (event.equals(DOWNLOAD_REPORT)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadReportCommand");
	
		}else if (event.equals(CHECKER_PROCESS_CREATE_LEIDETAILS)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadLeiDetailsFileUploadCmd");
		}else if (event.equals(CHECKER_APPROVE_CREATE_LEIDETAILS) ){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveLeiDetailsFileUploadCmd");
		}else if (event.equals(CHECKER_REJECT_CREATE_LEIDETAILS)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectLeiDetailsFileCmd");
		}else if (event.equals(MAKER_PREPARE_LEIDETAILS_CLOSE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadLeiDetailsRejectCmd");
		}else if (event.equals(MAKER_CLOSE_LEIDETAILS_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseLeiDetailsFileCmd");
		}else if (event.equals(REJECTED_DELETE_READ)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadProcessLeiDetailsFile");
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
		if ("checker_reject_create_leidetails".equals(errorEvent)) {
			errorEvent = ERROR_REJECT_LEIDETAILS;
		}
		if ("leidetailsupload".equals(errorEvent)) {
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
		}else if ((event != null) && (event.equals(LEIDETAILSUPLOAD) || (event.equals(EVENT_PAGINATION)))) {
			forwardName = "leidetailsupload";
		}else if (event.equals("downloadReport")){
			forwardName = "downloadReport";
		}else if (event.equals(CHECKER_PROCESS_CREATE_LEIDETAILS)){
			forwardName = "checker_process_create_leidetails";
		}else if (event.equals(CHECKER_APPROVE_CREATE_LEIDETAILS)){
			forwardName = "checker_approve_create_leidetails";
		}else if (event.equals(CHECKER_REJECT_CREATE_LEIDETAILS)){
			forwardName = "checker_reject_create_leidetails";
		}else if (ERROR_REJECT_LEIDETAILS.equals(event)){
			forwardName=ERROR_REJECT_LEIDETAILS;
		}else if (event.equals("maker_prepare_leidetails_close")){
			forwardName = "maker_prepare_leidetails_close";
		}else if (event.equals("maker_close_leidetails_file")){
			forwardName = "maker_close_leidetails_file";
		}else if (ERROR_EXCELUPLOAD.equals(event)){
			forwardName=ERROR_EXCELUPLOAD;
		}else if ("rejected_delete_read".equals(event)){
			forwardName = "rejected_delete_read";
		}
		return forwardName;
	}
}