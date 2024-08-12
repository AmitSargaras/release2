package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Jun 10, 2007 Time: 10:38:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareDisbursementDetailCommand extends AbstractCommand {

	public PrepareDisbursementDetailCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "purpose", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "subPurposeLabels", "java.util.List", REQUEST_SCOPE },
				{ "subPurposeValues", "java.util.List", REQUEST_SCOPE },
				{ "disburseModeLabels", "java.util.List", REQUEST_SCOPE },
				{ "disburseModeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareDisbursementDetailCommand doExecute()");

		try {
			String purpose = (String) map.get("purpose");
			DefaultLogger.debug(this, "purpose: " + purpose);

			CommonCodeList subPurposeList = CommonCodeList.getInstance(null, null, ICMSUIConstant.DISBURSE_SUBPURPOSE,
					true, purpose);
			resultMap.put("subPurposeLabels", subPurposeList.getCommonCodeLabels());
			resultMap.put("subPurposeValues", subPurposeList.getCommonCodeValues());

			CommonCodeList disburseModeList = CommonCodeList.getInstance(null, ICMSUIConstant.DISBURSE_MODE, true);
			resultMap.put("disburseModeLabels", disburseModeList.getCommonCodeLabels());
			resultMap.put("disburseModeValues", disburseModeList.getCommonCodeValues());

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());
			resultMap.put("currencyValues", currencyList.getCountryValues());

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}