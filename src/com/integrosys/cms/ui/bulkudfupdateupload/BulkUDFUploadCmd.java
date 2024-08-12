package com.integrosys.cms.ui.bulkudfupdateupload;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

public class BulkUDFUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public BulkUDFUploadCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
        return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE },
        	
        }
        );
    }
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{"event", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
			};	
	}
	
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			String strError = "";
			String fileType = "";
			String fileUploadPending ="";
			String fileCheckSum ="";
			
			IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			boolean preUpload = false ;
			
			resultMap.put("fileUploadPending",fileUploadPending);
			resultMap.put("fileCheckSum",fileCheckSum);	
			resultMap.put("fileType", fileType);
	    	resultMap.put("errorEveList", strError);
			
			if(fileUploadJdbc.getBulkUDFUploadCount()>0) {
				preUpload=true;
				DefaultLogger.info(this, "Previous Uploaded File Is Pending For Authorization!!!");
			}
			resultMap.put("event", map.get("event"));
			resultMap.put("preUpload",String.valueOf(preUpload));	
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			
			return returnMap;
			
			
	    }

}
