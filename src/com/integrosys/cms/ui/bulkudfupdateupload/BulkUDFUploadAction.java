package com.integrosys.cms.ui.bulkudfupdateupload;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

public class BulkUDFUploadAction  extends CommonAction{
	
	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}
	
	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	public static final String CSVUPLOAD = "csvupload";
	public static final String BULKUDFUPLOAD ="bulkudfupload";
	public static final String EVENT_PAGINATION = "paginate";
	public static final String DOWNLOAD_REPORT = "downloadReport";
	public static final String CHECKER_PROCESS_CREATE_BULKUDF = "checker_process_create_bulkudf";
	public static final String CHECKER_APPROVE_CREATE_BULKUDF = "checker_approve_create_bulkudf";
	public static final String CHECKER_REJECT_CREATE_BULKUDF = "checker_reject_create_bulkudf";
	public static final String ERROR_REJECT_BULKUDF="error_reject_bulkudf";
	public static final String MAKER_PREPARE_BULKUDF_CLOSE = "maker_prepare_bulkudf_close";
	public static final String MAKER_CLOSE_BULKUDF_FILE = "maker_close_bulkudf_file";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	public static final String ERROR_CSVUPLOAD="error_csvupload";

	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "In Action with event --" + event);
		System.out.println("SSSSSSSSSSSSSSSSSSSSS in Action calsssssssssssssss"+event);
		ICommand objArray[] = null;
	if (event.equals(CSVUPLOAD)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("BulkUDFUploadCmd");
	} else if(event.equals(BULKUDFUPLOAD)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("BulkUDFFileUploadCmd");	
	} else if (event.equals(EVENT_PAGINATION)) {
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("PaginationCmd");
	}else if (event.equals(DOWNLOAD_REPORT)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("DownloadReportCommand");
	}else if (event.equals(CHECKER_PROCESS_CREATE_BULKUDF)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadBulkUDFFileUploadCmd");
	}else if (event.equals(CHECKER_APPROVE_CREATE_BULKUDF) ){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveBulkUDFFileUploadCmd");
	}else if (event.equals(CHECKER_REJECT_CREATE_BULKUDF)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectBulkUDFFileCmd");
	}else if (event.equals(MAKER_PREPARE_BULKUDF_CLOSE)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("MakerReadBulkUDFRejectCmd");
	}else if (event.equals(MAKER_CLOSE_BULKUDF_FILE)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseBulkUDFFileCmd");
	}else if (event.equals(REJECTED_DELETE_READ)){
		objArray = new ICommand[1];
		objArray[0] = (ICommand) getNameCommandMap().get("ReadProcessBulkUDFFile");
	}
		return (objArray);
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;

		return result;
	}

	public IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;

	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if ("checker_reject_create_bulkudf".equals(errorEvent)) {
			errorEvent = ERROR_REJECT_BULKUDF;
		}
		if ("bulkudfupload".equals(errorEvent)) {
			errorEvent = ERROR_CSVUPLOAD;
		}
		return errorEvent;
	}

	
	private String getReference(String event) {
		String forwardName = null;
		if ((event != null) && (event.equals(CSVUPLOAD))) {
			forwardName = "csvupload";
	}else if ((event != null) && (event.equals(BULKUDFUPLOAD) || (event.equals(EVENT_PAGINATION)))) {
		forwardName = "bulkudfupload";
	}else if (event.equals("downloadReport")){
		forwardName = "downloadReport";
	}else if (event.equals("checker_process_create_bulkudf")){
		forwardName = "checker_process_create_bulkudf";
	}else if (event.equals("checker_approve_create_bulkudf")){
		forwardName = "checker_approve_create_bulkudf";
	}else if (event.equals("checker_reject_create_bulkudf")){
		forwardName = "checker_reject_create_bulkudf";
	}else if (ERROR_REJECT_BULKUDF.equals(event)){
		forwardName=ERROR_REJECT_BULKUDF;
	}else if (event.equals("maker_prepare_bulkudf_close")){
		forwardName = "maker_prepare_bulkudf_close";
	}else if (event.equals("maker_close_bulkudf_file")){
		forwardName = "maker_close_bulkudf_file";
	}else if (ERROR_CSVUPLOAD.equals(event)){
		forwardName=ERROR_CSVUPLOAD;
	}else if ("rejected_delete_read".equals(event)){
		forwardName = "rejected_delete_read";
	}

		return forwardName;
	}

}
