package com.integrosys.cms.ui.systemHandOff;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.eod.IEndOfDayBatchService;
import com.integrosys.cms.batch.eod.IEndOfDaySyncMastersService;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;


public class EndOfDayCommand extends AbstractCommand implements ICommonEventConstant {
	
	IEndOfDayBatchService endOfDayBatchService;
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}

	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}
	
	IEndOfDaySyncMastersService endOfDaySyncMastersService;
	public IEndOfDaySyncMastersService getEndOfDaySyncMastersService() {
		return endOfDaySyncMastersService;
	}

	/**
	 * @param endOfDaySyncMastersService the endOfDaySyncMastersService to set
	 */
	public void setEndOfDaySyncMastersService(IEndOfDaySyncMastersService endOfDaySyncMastersService) {
		this.endOfDaySyncMastersService = endOfDaySyncMastersService;
	}

	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {
		String CALL_PERFORM_EOD; 
		CALL_PERFORM_EOD = PropertyManager.getValue("call.performEod");
		System.out.println("CALL_PERFORM_EOD====="+CALL_PERFORM_EOD);
		HashMap returnMap = new HashMap();
		if("true".equalsIgnoreCase(CALL_PERFORM_EOD)){
		StringBuffer log = getEndOfDayBatchService().performEOD();
		HashMap resultMap = new HashMap();
		resultMap.put("log", log.toString());
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		}else{
			System.out.println("performEODSyncClimsToCps called");
		getEndOfDaySyncMastersService().performEODSyncClimsToCps();
		}
		
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "log", "java.lang.String", REQUEST_SCOPE }
			}
	);}
}
