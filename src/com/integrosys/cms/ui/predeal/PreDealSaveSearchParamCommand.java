/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealSaveSearchParamCommand
 *
 * Created on 10:23:13 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 5, 2007 Time: 10:23:13 AM
 */
public class PreDealSaveSearchParamCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.ISIN_CODE, "java.lang.String", SERVICE_SCOPE },
				{ PreDealConstants.COUNTER_NAME, "java.lang.String", SERVICE_SCOPE },
				{ PreDealConstants.RIC, "java.lang.String", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.ISIN_CODE, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.COUNTER_NAME, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.RIC, "java.lang.String", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String counterName = (String) hashMap.get(PreDealConstants.COUNTER_NAME);
		String isinCode = (String) hashMap.get(PreDealConstants.ISIN_CODE);
		String ric = (String) hashMap.get(PreDealConstants.RIC);

		DefaultLogger.debug(this, "Counter name : " + counterName);
		DefaultLogger.debug(this, "Isin code : " + isinCode);
		DefaultLogger.debug(this, "RIC : " + ric);

		result.put(PreDealConstants.COUNTER_NAME, counterName);
		result.put(PreDealConstants.ISIN_CODE, isinCode);
		result.put(PreDealConstants.RIC, ric);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
