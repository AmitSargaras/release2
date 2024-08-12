/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */

public class PrepareEditInternalLimitListCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theILParamFullList", "java.util.ArrayList", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "theILParamList", "java.util.ArrayList",
				FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		List ilParamList = (List) map.get("theILParamFullList");
		Integer offset = (Integer) map.get("offset");
		resultMap
				.put("theILParamList", getCurrentPageList(ilParamList, offset));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private List getCurrentPageList(List allEntryList, Integer offset) {
		List aPageList = new ArrayList();
		if (allEntryList == null || allEntryList.size() == 0) {
			return aPageList;
		}
		int startIdx = offset.intValue();
		DefaultLogger.debug(this, "Offet: " + startIdx);
		int pageLength = 10;
		int size = allEntryList.size();
		int endIdx = Math.min(size, startIdx + pageLength);
		for (int index = startIdx; index < endIdx; index++) {
			aPageList.add(allEntryList.get(index));
		}
		return aPageList;
	}
}
