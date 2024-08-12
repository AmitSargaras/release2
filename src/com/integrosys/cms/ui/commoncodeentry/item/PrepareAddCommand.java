/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryAddCommand.java
 *
 * Created on February 2, 2007, 10:48 AM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;

public class PrepareAddCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "aCommonCodeEntryObj",
				"com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBCommonCodeEntry entry = new OBCommonCodeEntry();
		result.put("aCommonCodeEntryObj", entry);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
