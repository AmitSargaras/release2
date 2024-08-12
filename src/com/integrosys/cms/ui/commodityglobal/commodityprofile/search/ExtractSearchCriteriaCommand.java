package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-9
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.list.
 *      PrepareSearchCommand.java
 */
public class ExtractSearchCriteriaCommand extends AbstractCommand implements ICMSUIConstant, CMDTProfConstants {
	public String[][] getParameterDescriptor() {
		return new String[][] { { AN_CMDT_PROF_SEARCH_OBJ, CN_CMDT_PROF_SEARCH_OBJ, FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { AN_CMDT_PROF_SEARCH_OBJ, CN_CMDT_PROF_SEARCH_OBJ, SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Extract search criteria & put to session.");
		resultMap.put(AN_CMDT_PROF_SEARCH_OBJ, map.get(AN_CMDT_PROF_SEARCH_OBJ));

		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
