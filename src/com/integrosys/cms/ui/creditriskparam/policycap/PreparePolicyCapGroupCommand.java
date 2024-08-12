/**
 * 
 */
package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.StockExchangeList;

/**
 * Purpose: Policy Cap Group Command Class which prepare transaction object
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 31/AUG/2007 $ Tag: $Name: $
 */
public class PreparePolicyCapGroupCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "stockExchangeLabels", "java.util.List", REQUEST_SCOPE },
				{ "stockExchangeValues", "java.util.List", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			DefaultLogger.debug(this, "policycapmakerpreparecommand");
			StockExchangeList.fillStockExchange2Map(resultMap);
			DefaultLogger.debug(this, "after getPolicyCapProxyManager()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
