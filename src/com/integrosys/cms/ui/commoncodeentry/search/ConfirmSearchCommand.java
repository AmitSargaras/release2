package com.integrosys.cms.ui.commoncodeentry.search;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;

public class ConfirmSearchCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] {  
			{ "aCommonCodeEntryObj", "com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry", FORM_SCOPE },
			{"entryList", "java.util.List", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "entryList", "java.util.List", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();


		OBCommonCodeEntry criteria = (OBCommonCodeEntry) map.get("aCommonCodeEntryObj");
		ArrayList entryList = (ArrayList)map.get("entryList");
		entryList = getMatchedEntryList(entryList,criteria);
		resultMap.put("entryList", entryList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private ArrayList getMatchedEntryList(ArrayList entryList,
			OBCommonCodeEntry criteria) {
		ArrayList returnList = new ArrayList();
		return returnList;
	}

}
