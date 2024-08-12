/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITradingAgreement;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.bus.OBTradingAgreement;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;

/**
 * Describe this class. Purpose: for Maker to save the value into OB
 * Description: command that let the maker to save the value that being edited
 * to the OB
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class TradingAgreementSaveDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "InitialTradingAgreement", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "tradingAgreementVal", "com.integrosys.cms.app.limit.bus.OBTradingAgreement", SERVICE_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String trxId = (String) map.get("TrxId");
			String event = (String) (map.get("event"));
			String preEvent = (String) map.get("preEvent");
			String ind = (String) map.get("indexChange");
			int indexChange = Integer.parseInt(ind) + 1;
			ILimitProfileTrxValue lmtProfileTrxValue = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
			ILimitProfile curLimit = null;
			if (lmtProfileTrxValue == null) {
				lmtProfileTrxValue = new OBLimitProfileTrxValue();
				curLimit = new OBLimitProfile();
			}
			else {
				curLimit = lmtProfileTrxValue.getStagingLimitProfile();
			}

			if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)
					|| "maker_update_agreement_confirm".equals(event)
					|| "maker_update_editreject_confirm".equals(event)) {
				ITradingAgreement tradingAgreement = (ITradingAgreement) (map.get("InitialTradingAgreement"));
				ITradingAgreement link = new OBTradingAgreement();
				link = tradingAgreement;
				ITradingAgreement refArr = curLimit.getTradingAgreement();
				if (refArr == null) {
					refArr = new OBTradingAgreement();
					refArr = link;
					curLimit.setTradingAgreement(refArr);
					lmtProfileTrxValue.setStagingLimitProfile(curLimit);
				}
				else {
					ITradingAgreement newArr = new OBTradingAgreement();
					newArr = link;
					curLimit.setTradingAgreement(newArr);
					lmtProfileTrxValue.setStagingLimitProfile(curLimit);
				}

				result.put("limitProfileTrxVal", lmtProfileTrxValue);
				result.put("tradingAgreementVal", null);
				result.put("preEvent", preEvent);
				result.put("indexChange", String.valueOf(indexChange));
				result.put("TrxId", trxId);
			}
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
