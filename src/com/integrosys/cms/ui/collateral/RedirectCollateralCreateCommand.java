/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/RedirectCollateralCommand.java,v 1.5 2006/07/19 11:11:27 wltan Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/07/19 11:11:27 $ Tag: $Name: $
 */
public class RedirectCollateralCreateCommand extends AbstractCommand {
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Gets the security sub-type ID for the
	 * required transaction to find out which action to forward to.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String trxIDStr = (String) map.get("trxID");
		String subtype = (String) map.get("subtype");
		String securityType = (String) map.get("selectedSecurityType");
		String securitySubType = (String) map.get("selectedSecuritySubType");
		resultMap.put("subtypeCode", subtype);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE }, 
								 { "subtype", "java.lang.String", REQUEST_SCOPE },
								 { "selectedSecurityType", "java.lang.String", REQUEST_SCOPE },
								 { "selectedSecuritySubType", "java.lang.String", REQUEST_SCOPE },
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "subtypeCode", "java.lang.String", REQUEST_SCOPE }, });
	}

}
