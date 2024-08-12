package com.integrosys.cms.ui.bonddetailsupload;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

public class PrepareFileUploadCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "stillPending", Boolean.class.getName(), REQUEST_SCOPE }
			};	
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
		boolean stillPending = fileUploadJdbc.isBondDetailsUploadTrxPendingOrRejected();
		if(stillPending) {
			DefaultLogger.info(this, "Previous Uploaded File Is Pending For Authorization!!!");
		}
		resultMap.put("stillPending",stillPending);
		
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
	
}