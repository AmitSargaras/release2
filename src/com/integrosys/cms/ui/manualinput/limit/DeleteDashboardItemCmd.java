package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

public class DeleteDashboardItemCmd extends AbstractCommand {

	@Override
	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
		List newLimitDashboardList=new ArrayList();
		
		result.put("newLimitDashboardList", newLimitDashboardList);
		DefaultLogger.debug(this, "newLimitDashboardList:"+newLimitDashboardList);
		//DefaultLogger.debug(this, "iLimitTrxValue:"+iLimitTrxValue);
		//DefaultLogger.debug(this, " iLimit:"+iLimit);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}
	
	@Override

	public String[][] getResultDescriptor() {
		return (new String[][] {{"newLimitDashboardList","java.util.List", SERVICE_SCOPE},
			
		});
}
	
	

}
