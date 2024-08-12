package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: USER Date: Apr 9, 2007 Time: 3:44:21 PM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareBridgingLoanCommand extends AbstractCommand {

	public PrepareBridgingLoanCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "initEvent", "java.lang.String", REQUEST_SCOPE },
				{ "orig_country", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "initEvent", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE }, { "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE },
				{ "orig_country", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareBridgingLoanCommand doExecute()");

		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			resultMap.put("orig_country", limit.getOriginatingLocation().getCountryCode());

			String initEvent = (String) map.get("initEvent");
			if ((initEvent != null) && !initEvent.equals("")) {
				DefaultLogger.debug(this, "initEvent now:" + initEvent);
				resultMap.put("initEvent", initEvent);
				resultMap.put("tab", null); // clear service scope initial
			}
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