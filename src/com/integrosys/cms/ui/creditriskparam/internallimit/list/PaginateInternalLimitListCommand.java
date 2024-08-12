/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class PaginateInternalLimitListCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },

				{ "current_offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "ERROR_ENTRY_MAP", "java.util.HashMap", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			HashMap errorMap = (HashMap) map.get("ERROR_ENTRY_MAP");
			if (errorMap == null || errorMap.size() == 0) {
				Integer offset = (Integer) map
						.get(CommonCodeEntryConstant.OFFSET);
				DefaultLogger.debug(this, "befor paging: " + offset.intValue());
				offset = new Integer((String) map.get("current_offset"));
				DefaultLogger.debug(this, "offset after paging: "
						+ offset.intValue());
				resultMap.put("offset", offset);
			}
		} catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
