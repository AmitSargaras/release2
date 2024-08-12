package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;

/**
 * Created by IntelliJ IDEA. User: Tan Kien Leong Date: Apr 19, 2007 Time:
 * 12:01:12 PM To change this template use File | Settings | File Templates.
 */
public class SG_SaveAdvanceCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "obAdvanceForm", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance",
				FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE }, };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			DefaultLogger.debug(this, "in SaveAdvanceCommand");

			OBAdvance obAdvance = (OBAdvance) hashMap.get("obAdvance");

			resultMap.put("obAdvanceForm", obAdvance);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
