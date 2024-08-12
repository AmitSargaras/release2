package com.integrosys.cms.ui.fileUpload;

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

public class FileUploadAction extends CommonAction implements IPin {
	private Map nameCommandMap;
	
	
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String SYSTEM_UPLOAD= "system_upload";
	public static final String LIST_SYSTEM_FILES= "list_system_files";
	
	
	/*public static final String LIST_UBS_FILES= "list_ubs_files";
	public static final String LIST_FINWARE_FILES= "list_finware_files";
	public static final String LIST_FINWARE_FD_FILES= "list_finware_fd_files";
	public static final String LIST_HONGKONG_FILES= "list_hongkong_files";
	public static final String LIST_BAHRAIN_FILES= "list_bahrain_files";	*/
	
	public static final String CHECKER_PROCESS_CREATE = "checker_process_create";
	
	public static final String CHECKER_APPROVE_CREATE_UBS = "checker_approve_create_ubs";
	public static final String CHECKER_APPROVE_CREATE_HONGKONG = "checker_approve_create_hongkong";
	public static final String CHECKER_APPROVE_CREATE_FINWARE = "checker_approve_create_finware";
	public static final String CHECKER_APPROVE_CREATE_BAHRAIN = "checker_approve_create_bahrain";
	
	public static final String CHECKER_REJECT_CREATE = "checker_reject_create";
	
	public static final String MAKER_UPLOAD_UBS_FILE = "maker_upload_ubs_file";
	public static final String MAKER_UPLOAD_HONGKONG_FILE = "maker_upload_hongkong_file";
	public static final String MAKER_UPLOAD_FINWARE_FILE = "maker_upload_finware_file";
	public static final String MAKER_UPLOAD_BAHRAIN_FILE = "maker_upload_bahrain_file";
	
	
	public static final String MAKER_PREPARE_UBS_CLOSE = "maker_prepare_ubs_close";
	public static final String MAKER_CLOSE_UBS_FILE = "maker_close_ubs_file";
	public static final String REJECTED_DELETE_READ = "rejected_delete_read";
	
	public static final String DOWNLOAD_REPORT = "downloadReport";
	
	//Added by Uma for FD Upload
	public static final String MAKER_UPLOAD_FD_FILE = "maker_upload_fd_file";
	public static final String CHECKER_APPROVE_CREATE_FD = "checker_approve_create_fd";
	public static final String ERROR_REJECT="error_reject";
	//public static final String MAKER_CLOSE_FD_FILE = "maker_close_fd_file";
	

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this,"In  FileUploadAction with event --" + event);
		ICommand objArray[] = null;
		if (event.equals(SYSTEM_UPLOAD)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListFileTypeCmd");
		//****************************************list commands***************************************	
		}else if (event.equals(LIST_SYSTEM_FILES)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListSystemFileCmd");
			/*}else if (event.equals(LIST_FINWARE_FILES)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListFinWareFileCmd");
		}else if (event.equals(LIST_BAHRAIN_FILES)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListBahrainFileCmd");
		}else if (event.equals(LIST_FINWARE_FD_FILES)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListFinwareFdFileCmd");
		}else if (event.equals(LIST_HONGKONG_FILES)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListHongKongFileCmd");*/
		//**********************************upload commands**************************************	
		}else if (event.equals(MAKER_UPLOAD_UBS_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UploadUbsFileCmd");
		}else if (event.equals(MAKER_UPLOAD_FINWARE_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UploadfinWareFileCmd");
		}else if (event.equals(MAKER_UPLOAD_HONGKONG_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UploadHongKongFileCmd");	
		}else if (event.equals(MAKER_UPLOAD_BAHRAIN_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UploadBahrainFileCmd");	
		//****************************maker checker activity commands*****************************		
		}else if (event.equals(CHECKER_PROCESS_CREATE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerReadFileUploadCmd");
		}else if (event.equals(CHECKER_APPROVE_CREATE_UBS) 
				|| event.equals(CHECKER_APPROVE_CREATE_FINWARE) 
						|| event.equals(CHECKER_APPROVE_CREATE_BAHRAIN)
								|| event.equals(CHECKER_APPROVE_CREATE_HONGKONG) || event.equals(CHECKER_APPROVE_CREATE_FD)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveFileUploadCmd");
		}else if (event.equals(CHECKER_REJECT_CREATE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectFileCmd");
		}else if (event.equals(MAKER_PREPARE_UBS_CLOSE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerReadRejectCmd");
		}else if (event.equals(MAKER_CLOSE_UBS_FILE)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseFileCmd");
		}else if (event.equals(REJECTED_DELETE_READ)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ReadProcessFile");
		}else if (event.equals(DOWNLOAD_REPORT)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("DownloadReportCommand");
	
		}
		//Added by Uma for FD Upload
		else if (MAKER_UPLOAD_FD_FILE.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UploadFdFileCmd");
	
		}
	
		return (objArray);
	}
	
public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		
		Page aPage = new Page();
		
			aPage.setPageReference(getReference(event));
			return aPage;	

	}

private String getReference(String event) {
	String forwardName = null;
	
	if (event.equals("system_upload")){
		forwardName = "system_list";
	}else if (event.equals("maker_upload_ubs_file")){
		forwardName = "ack_ubs_submit";
	}else if (event.equals("maker_upload_hongkong_file")){
		forwardName = "ack_hongkong_submit";
	}else if (event.equals("maker_upload_finware_file")){
		forwardName = "ack_finware_submit";
	}else if (event.equals("maker_upload_bahrain_file")){
		forwardName = "ack_bahrain_submit";
	}else if (event.equals("checker_process_create")){
		forwardName = "checker_process_create";
		
	/*}else if (event.equals("list_finware_files")){
		forwardName = "list_finware_files";
	}else if (event.equals("list_ubs_files")){
		forwardName = "list_ubs_files";
	}else if (event.equals("list_finware_fd_files")){
		forwardName = "list_finware_fd_files";
	}else if (event.equals("list_hongkong_files")){
		forwardName = "list_hongkong_files";
	}else if (event.equals("list_bahrain_files")){
		forwardName = "list_bahrain_files";*/
	}else if (event.equals("list_system_files")){
		forwardName = "list_system_files";	
		
		
		
	}else if (event.equals("maker_prepare_ubs_close")){
		forwardName = "maker_prepare_ubs_close";
	}else if (event.equals("rejected_delete_read")){
		forwardName = "rejected_delete_read";
	}else if (event.equals("checker_approve_create_ubs")){
		forwardName = "checker_approve_create_ubs";
	}else if (event.equals("checker_approve_create_finware")){
		forwardName = "checker_approve_create_finware";
	}else if (event.equals("checker_approve_create_hongkong")){
		forwardName = "checker_approve_create_hongkong";
	}else if (event.equals("checker_approve_create_bahrain")){
		forwardName = "checker_approve_create_bahrain";
	}else if (event.equals("checker_reject_create")){
		forwardName = "checker_reject_create";
	}else if (event.equals("maker_close_ubs_file")){
		forwardName = "maker_close_sys_file";
	}else if (event.equals("downloadReport")){
		forwardName = "downloadReport";
	}
	
	
	//For FD Upload
	else if (MAKER_UPLOAD_FD_FILE.equals(event)){
		forwardName = "ack_fd_submit";
	}
	else if ("checker_approve_create_fd".equals(event)){
		forwardName = "checker_approve_create_fd";
	}

	else if (ERROR_REJECT.equals(event)){
		forwardName=ERROR_REJECT;
	}
	return forwardName;
}


protected String getErrorEvent(String event) {
	
	String errorEvent = event;
	DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
	if ("checker_reject_create".equals(errorEvent)) {
		errorEvent = ERROR_REJECT;
	}
	return errorEvent;
}

}
