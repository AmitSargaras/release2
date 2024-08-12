/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/LimitsAction.java,v 1.9 2006/09/27 06:09:07 hshii Exp $
 */

package com.integrosys.cms.ui.limit.limitaccount;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AddLimitAccountCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "accountOB", "com.integrosys.cms.app.customer.bus.ICustomerSysXRef", FORM_SCOPE },
				{ "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "service.limitTrxValue", "com.integrosys.cms.app.limit.trx.ILimitTrxValue",
				SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

		ILimitTrxValue trxValue = (ILimitTrxValue) map.get("service.limitTrxValue");
		ICustomerSysXRef account = (ICustomerSysXRef) map.get("accountOB");

		ILimit limit = trxValue.getStagingLimit();
		addLimitAccount(limit, account, limitProfile, customer);

		trxValue.setStagingLimit(limit);
		result.put("service.limitTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private static void addLimitAccount(ILimit limit, ICustomerSysXRef account, ILimitProfile limitProfile,
			ICMSCustomer customer) {
		ILimitSysXRef newLimitAccount = new OBLimitSysXRef();
		newLimitAccount.setXRefLimitRef(limit.getLimitRef());
		newLimitAccount.setXRefLegalRef(customer.getCMSLegalEntity().getLEReference());
		newLimitAccount.setCustomerSysXRef(account);

		// AT:Default to Active Status .
		newLimitAccount.setStatus("ACTIVE");

		ILimitSysXRef[] existingArray = limit.getLimitSysXRefs();

		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ILimitSysXRef[] newArray = new ILimitSysXRef[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = newLimitAccount;

		limit.setLimitSysXRefs(newArray);
	}
}