package com.integrosys.cms.ui.mf.schemadetailsupload;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;

public class SchemaDetailsUploadAction extends CommonAction implements IPin{

	private Map<String,ICommand> nameCommandMap;

	public Map<String,ICommand> getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map<String,ICommand> nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String EVENT_PREPARE_UPLOAD = "prepare_upload";
	
	public static final String EVENT_UPLOAD = "upload";
	public static final String EVENT_UPLOAD_ERROR = "upload_error";
	
	public static final String EVENT_DOWNLOAD_REPORT = "download_report";
	
	public static final String EVENT_CHECKER_PREPARE_PROCESS = "checker_prepare_process";
	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";
	
	public static final String EVENT_CHECKER_REJECT = "checker_reject";
	public static final String EVENT_CHECKER_REJECT_ERROR = "checker_reject_error";
	
	public static final String EVENT_MAKER_CLOSE = "maker_close";
	
	public static final String EVENT_CHECKER_APPROVE = "checker_approve";
	
	protected ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if(EVENT_PREPARE_UPLOAD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("prepareFileUploadCommand");
		}else if(EVENT_UPLOAD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("fileUploadCommand");
		}else if(EVENT_DOWNLOAD_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("downloadReportCommand");
		}else if(EVENT_CHECKER_PREPARE_PROCESS.equals(event) || EVENT_MAKER_PREPARE_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("readFileUploadCommand");
		}else if(EVENT_CHECKER_REJECT.equals(event)) {
				objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("checkerRejectFileUploadCommand");
		}else if(EVENT_MAKER_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("makerCloseFileUploadCommand");
		}else if(EVENT_CHECKER_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("checkerApproveFileUploadCommand");
		}
		
		return objArray;
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		
		if(EVENT_UPLOAD.equals(event)) {
			errorEvent = EVENT_UPLOAD_ERROR;
		}else if(EVENT_CHECKER_REJECT.equals(event)) {
			errorEvent = EVENT_CHECKER_REJECT_ERROR;
		}
		
		return errorEvent;
	}

	protected IPage getNextPage(String event, HashMap inputs, HashMap exceptions) {
		Page aPage = new Page();
		
		aPage.setPageReference(event);
		
		return aPage;
	}

}
