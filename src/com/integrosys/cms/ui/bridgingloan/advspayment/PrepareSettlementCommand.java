package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 11:11:26 AM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareSettlementCommand extends AbstractCommand {

	public PrepareSettlementCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering PrepareSettlementCommand()");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareSettlementCommand doExecute()");

		try {
			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());
			resultMap.put("currencyValues", currencyList.getCountryValues());

			DefaultLogger.debug("\n>>>>>>>>>>> Result Map: ", Integer.toString(resultMap.size()));

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
