package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

public class BackToCollateralCommand extends AbstractCommand  implements ILineDetailConstants{

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		
		resultMap.put(SESSION_FAC_NAME_LIST, null);
		resultMap.put(SESSION_FAC_ID_LIST, null);
		resultMap.put(SESSION_FAC_LINE_NO_LIST, null);
		resultMap.put(SESSION_FAC_DETAIL_LIST, null);
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, List.class.getName(), SERVICE_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
}
