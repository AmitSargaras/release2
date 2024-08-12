/*
 * PreDealPrepareCommonCodeCommand.java
 *
 * Created on May 21, 2007, 10:54 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.ui.predeal;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * 
 * @author OEM
 */
public class PreDealPrepareCommonCodeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return super.getParameterDescriptor();
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_SOURCE_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_VALUES, "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Retreving common code data");

		result.put(PreDealConstants.PRE_DEAL_SOURCE_LABELS, CommonDataSingleton
				.getCodeCategoryLabels(PreDealConstants.PRE_DEAL_SOURCE));
		result.put(PreDealConstants.PRE_DEAL_SOURCE_VALUES, CommonDataSingleton
				.getCodeCategoryValues(PreDealConstants.PRE_DEAL_SOURCE));

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
