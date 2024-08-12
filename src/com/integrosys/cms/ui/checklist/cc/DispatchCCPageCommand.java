package com.integrosys.cms.ui.checklist.cc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Mar 10, 2006 Time: 5:41:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchCCPageCommand extends AbstractCommand implements ICommonEventConstant {
	public DispatchCCPageCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "custCategory", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "custCategory", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		resultMap = (HashMap) map.clone();
		resultMap.put("frame", "true");

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
