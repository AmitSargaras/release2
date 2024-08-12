/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class DeletePaymentCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "obPaymentForm", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance",
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
			DefaultLogger.debug(this, "in CreateAdvanceCommand");

			OBAdvance obAdvance = (OBAdvance) hashMap.get("obPaymentForm");

			resultMap.put("obAdvance", obAdvance);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

}