/*
Copyright Integro Technologies Pte Ltd
$Header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;

/**
 * ListViewCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ListViewCreditRiskParamUnitTrustCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ CreditRiskParamUnitTrustForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = ((Integer) map.get(CreditRiskParamUnitTrustForm.MAPPER)).intValue();

			// Session-scoped trx value.
			ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) map
					.get("creditRiskParamGroupTrxValue");
			ICreditRiskParamGroup group = value.getStagingCreditRiskParamGroup();
			OBCreditRiskParam[] entriesArr = group.getFeedEntries();

			targetOffset = CreditRiskParamUnitTrustMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("offset", new Integer(targetOffset));

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
