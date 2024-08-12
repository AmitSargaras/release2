/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/AddExchangeRateListCommand.java,v 1.3 2003/08/22 13:21:41 btchng Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.dimension;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class PrepareEditCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { {
				"theDimension",
				"com.integrosys.cms.app.creditriskparam.bus.internallimit.IDimension",
				FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IInternalLimitParameter ilParam = new OBInternalLimitParameter();
		resultMap.put("theDimension", ilParam);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
