/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MaintainShareCounterUpdateRejectedCommand
 *
 * Created on 5:44:21 PM
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

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 22, 2007 Time: 5:44:21 PM
 */
public class MaintainShareCounterUpdateRejectedCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "com.integrosys.cms.app.creditriskparam.OBShareCounter",
						FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();
		OBTrxContext ctx = (OBTrxContext) hashMap.get("theOBTrxContext");
		OBShareCounter obTrx = (OBShareCounter) hashMap.get(ShareCounterConstants.SHARE_COUNTER_FORM);
		OBCreditRiskParamGroupTrxValue trxValue = (OBCreditRiskParamGroupTrxValue) hashMap
				.get(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE);
		OBCreditRiskParamGroup stagingData = (OBCreditRiskParamGroup) trxValue.getStagingCreditRiskParamGroup();

		try {
			stagingData = MaintainShareCounterUtil.mergeStagingWithOb(stagingData, obTrx);

			DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());

			proxy.makerUpdateRejectedCreditRiskParam(ctx, trxValue, stagingData, CreditRiskParamType.SHARE_COUNTER);

			result.put("request.ITrxValue", trxValue);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
