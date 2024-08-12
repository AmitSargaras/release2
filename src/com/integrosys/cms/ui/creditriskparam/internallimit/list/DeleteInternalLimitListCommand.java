/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitParamComparator;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class DeleteInternalLimitListCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { { "theILParamFullList", "java.util.ArrayList",
				SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "theILParamFullList", "java.util.ArrayList",
				SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		List ilParamFullList = (List) map.get("theILParamFullList");
		List newList = new ArrayList();
		if (ilParamFullList != null && ilParamFullList.size() > 0) {
			for (int index = 0; index < ilParamFullList.size(); index++) {
				IInternalLimitParameter ilParam = (IInternalLimitParameter) ilParamFullList
						.get(index);
				if ("D".equals(ilParam.getStatus())) {
					continue;
				} else {
					newList.add(ilParam);
				}
			}
		}
		ilParamFullList = null;
		ilParamFullList = newList;
		Collections.sort(ilParamFullList, new InternalLimitParamComparator());
		resultMap.put("theILParamFullList", ilParamFullList);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
