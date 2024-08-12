package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

public class StockDetailsUploadAction  extends CommonAction{
	
	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}
	
	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	public static final String EVENT_PREPARE_EXCELUPLOAD = "prepare_excelupload";
	public static final String EVENT_EXCELUPLOAD = "excelupload";
	public static final String EVENT_ERROR_EXCELUPLOAD = "error_excelupload";
	public static final String EVENT_DOWNLOAD_REPORT = "download_report";
	public static final String EVENT_CHECKER_PREPARE_PROCESS = "checker_prepare_process";
	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";
	public static final String EVENT_CHECKER_REJECT = "checker_reject";
	public static final String EVENT_CHECKER_REJECT_ERROR = "checker_reject_error";
	public static final String EVENT_MAKER_CLOSE = "maker_close";
	public static final String EVENT_CHECKER_APPROVE = "checker_approve";
	
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_PREPARE_EXCELUPLOAD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("prepareStockdetailsUploadCommand");
		}
		else if(EVENT_EXCELUPLOAD.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("stockdetailsUploadCommand");
		}
		else if(EVENT_DOWNLOAD_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("downloadReportCommand");
		}
		else if(EVENT_CHECKER_PREPARE_PROCESS.equals(event) || EVENT_MAKER_PREPARE_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("readStockdetailsUploadCommand");
		}
		else if(EVENT_CHECKER_REJECT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("checkerRejectStockdetailsUploadCommand");
		}
		else if(EVENT_MAKER_CLOSE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("makerCloseStockdetailsUploadCommand");
		}
		else if(EVENT_CHECKER_APPROVE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("checkerApproveStockdetailsUploadCommand");
		}
		return objArray;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(event);
		return aPage;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if(EVENT_EXCELUPLOAD.equals(event)) {
			errorEvent = EVENT_ERROR_EXCELUPLOAD;
		}
		else if(EVENT_CHECKER_REJECT.equals(event)) {
			errorEvent = EVENT_CHECKER_REJECT_ERROR;
		}
		return errorEvent;
	}
	
	

}
