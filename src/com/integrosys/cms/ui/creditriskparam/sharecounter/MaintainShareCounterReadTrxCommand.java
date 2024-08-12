/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * MaintainShareCounterReadTrxCommand
 *
 * Created on 2:40:06 PM
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
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 22, 2007 Time: 2:40:06 PM
 */
public class MaintainShareCounterReadTrxCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ ShareCounterForm.mapperName,
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "java.lang.Object", FORM_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.Integer", REQUEST_SCOPE },
				{ ShareCounterConstants.LENGTH, "java.lang.Integer", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String trxId = (String) hashMap.get("TrxId");
		String event = (String) hashMap.get("event");
		OBTrxContext ctx = (OBTrxContext) hashMap.get("theOBTrxContext");
		OBCreditRiskParamGroupTrxValue trxValue = new OBCreditRiskParamGroupTrxValue();

		ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();

		String offsetStr = (String) hashMap.get(ShareCounterConstants.OFFSET);
		Integer offset;

		try {
			offset = new Integer(offsetStr);
		}
		catch (Exception ex) {
			offset = ShareCounterConstants.INITIAL_OFFSET;
		}

		// ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR.equals ( event
		// )

		try {
			if ((trxId == null) || trxId.equals("")) {
				ICreditRiskParamGroupTrxValue inputValue = (ICreditRiskParamGroupTrxValue) hashMap
						.get(ShareCounterForm.mapperName);
				ICreditRiskParamGroup group = inputValue.getCreditRiskParamGroup();
				trxValue.setReferenceID(Long.toString(group.getCreditRiskParamGroupID()));
				trxValue = (OBCreditRiskParamGroupTrxValue) proxy.makerReadCreditRiskParam(ctx, trxValue, null,
						CreditRiskParamType.SHARE_COUNTER);
			}
			else {
				trxValue.setTransactionID(trxId);
				trxValue = (OBCreditRiskParamGroupTrxValue) proxy.checkerViewCreditRiskParam(ctx, trxValue, null,
						CreditRiskParamType.SHARE_COUNTER);

				DefaultLogger.debug(this, "trxValue.getReferenceID () : " + trxValue.getReferenceID());
			}

			result.put(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new CommandValidationException(e.getMessage());
		}

		result.put("offset", offset);
		result.put("length", ShareCounterConstants.FIXED_LENGTH);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
