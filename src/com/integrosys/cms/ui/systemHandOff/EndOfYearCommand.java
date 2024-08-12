package com.integrosys.cms.ui.systemHandOff;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.eod.IEndOfDayBatchService;

public class EndOfYearCommand extends AbstractCommand implements ICommonEventConstant {
	
	IEndOfDayBatchService endOfDayBatchService;
	
	public IEndOfDayBatchService getEndOfDayBatchService() {
		return endOfDayBatchService;
	}

	public void setEndOfDayBatchService(IEndOfDayBatchService endOfDayBatchService) {
		this.endOfDayBatchService = endOfDayBatchService;
	}
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {		
		StringBuffer log = getEndOfDayBatchService().performEndOfYearActivities();
		HashMap resultMap = new HashMap();
		resultMap.put("log", log.toString());
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
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
