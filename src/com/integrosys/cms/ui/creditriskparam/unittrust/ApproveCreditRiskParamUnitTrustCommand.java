/*
Copyright Integro Technologies Pte Ltd
$Header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * ApproveCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ApproveCreditRiskParamUnitTrustCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "creditRiskParamGroupTrxValue",
						"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) map
					.get("creditRiskParamGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			ICreditRiskParamGroup group = value.getCreditRiskParamGroup();
			ICreditRiskParamGroup stageGroup = value.getStagingCreditRiskParamGroup();
			OBCreditRiskParam[] actualEntries = null;
			OBCreditRiskParam[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}

			stageGroup.setFeedEntries(stageEntries);
			value.setStagingCreditRiskParamGroup(stageGroup);

			String remarks = (String) map.get(CreditRiskParamUnitTrustForm.MAPPER);
			value.setRemarks(remarks);

			ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();
			value = (ICreditRiskParamGroupTrxValue) proxy.checkerApproveCreditRiskParam(trxContext, value, value
					.getStagingCreditRiskParamGroup(), CreditRiskParamType.UNIT_TRUST);

			resultMap.put("request.ITrxValue", value);

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
