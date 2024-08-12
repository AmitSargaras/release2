package com.integrosys.cms.ui.stockdetailsupload;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

public class PrepareStockdetailsUploadCommand extends AbstractCommand implements ICommonEventConstant{

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		try {
			IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			boolean stillPending = fileUploadJdbc.isStockDetailsUploadTrxPendingOrRejected();
			resultMap.put("stillPending",stillPending);
			returnMap.put(COMMAND_RESULT_MAP, resultMap);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "Error occured in PrepareStockdetailsUploadCommand ",e);
			throw new CommandProcessingException("Error occured in Stock Detailss Upload", e);
		}
		
		return returnMap;
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "stillPending", Boolean.class.getName(), REQUEST_SCOPE }
		};	
	}

}
