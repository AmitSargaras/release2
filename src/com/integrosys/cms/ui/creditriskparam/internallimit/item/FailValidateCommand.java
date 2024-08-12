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
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.creditriskparam.internallimit.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * 
 * @author Eric
 */
public class FailValidateCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { {
				"theIntrenalLimitItem",
				"com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter",
				FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { {
				"theIntrenalLimitItem",
				"com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter",
				FORM_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		result.put("theIntrenalLimitItem", map.get("theIntrenalLimitItem"));

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
