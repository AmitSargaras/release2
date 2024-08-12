package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class RefreshIncoDescCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "incoTerm", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "incoDescription", "java.lang.String", REQUEST_SCOPE },
				{ "incoTerm", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String errorCode = null;
		String flag = "false";
		String incoDescription = null;
		
			String incoTerm = (String) (map.get("incoTerm"));
			
			if(incoTerm.equalsIgnoreCase("")|| null==incoTerm){
				incoDescription ="";
			}
			
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			
					if(!AbstractCommonMapper.isEmptyOrNull(incoTerm)){
						incoDescription = lmtDao.getIncoDesc(incoTerm.trim());
					 }
		result.put("flag", flag);
		result.put("incoTerm", incoTerm);
		result.put("incoDescription", incoDescription);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
