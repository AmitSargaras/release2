/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

public class UpdateLimitAccountCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "accountOB", "com.integrosys.cms.app.customer.bus.ICustomerSysXRef", FORM_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ILimitTrxValue trxValue = (ILimitTrxValue) map.get("service.limitTrxValue");
		ICustomerSysXRef account = (ICustomerSysXRef) map.get("accountOB");

		int index = Integer.parseInt((String) map.get("indexID"));
		ILimit limit = trxValue.getStagingLimit();
		limit.getLimitSysXRefs()[index].setCustomerSysXRef(account);

		trxValue.setStagingLimit(limit);
		result.put("service.limitTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}