package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 25, 2007 Time: 1:16:14 AM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareProjectScheduleCommand extends AbstractCommand {

	public PrepareProjectScheduleCommand() {
		DefaultLogger.debug("\n----------------------------->", "Entering PrepareProjectScheduleCommand()");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareProjectScheduleCommand doExecute()");

		try {
			IBridgingLoan objBridgingLoan = (IBridgingLoan) map.get("objBridgingLoan");
			DefaultLogger.debug(this, "objBridgingLoan: " + objBridgingLoan);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
