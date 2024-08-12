package com.integrosys.cms.ui.systemHandOff;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.OBBaselUploadFile;
import com.integrosys.cms.batch.eod.IEndOfDayBatchService;

//Created By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD 

public class DownloadFcUbsFileCmd extends AbstractCommand implements ICommonEventConstant {
	
	IEndOfDayBatchService endOfDayBatchService;
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}

	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {
		String uploadStatus = null;
		
		Object status= getEndOfDayBatchService().downloadFcubsFile();
		if (status instanceof String){
			uploadStatus = (String)status;
		}
		else
			uploadStatus = "fail";
		
		HashMap resultMap = new HashMap();
		resultMap.put("uploadStatus", uploadStatus);
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] 
		{
			{ "uploadStatus", "java.lang.String", REQUEST_SCOPE }
			
		}
	);}
}
