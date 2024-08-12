/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PaginateCreditRiskParamShareCounterCommand
 *
 * Created on 12:00:40 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 26, 2007 Time: 12:00:40 PM
 */
public class PaginateCreditRiskParamShareCounterCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "com.integrosys.cms.app.creditriskparam.OBShareCounter",
						FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBTrxContext ctx = (OBTrxContext) hashMap.get("theOBTrxContext");
		OBShareCounter form = (OBShareCounter) hashMap.get(ShareCounterConstants.SHARE_COUNTER_FORM);
		Integer offset = (Integer) hashMap.get("offset");

		try {
			if (offset == null) {
				offset = new Integer(0);
			}

			offset = new Integer(offset.intValue() + 1);

			result.put("offset", offset);
			result.put("length", new Integer(10));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}