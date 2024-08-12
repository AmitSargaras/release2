/*
 * MaintainShareCounterPrepareCommonCodeCommand.java
 *
 * Created on May 1, 2007, 5:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * 
 * @author OEM
 */
public class MaintainShareCounterPrepareCommonCodeCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { ShareCounterConstants.BOARD_TYPE_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.BOARD_TYPE_VALUES, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.SHARE_STATUS_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.SHARE_STATUS_VALUES, "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		result.put(ShareCounterConstants.BOARD_TYPE_LABELS, CommonDataSingleton
				.getCodeCategoryLabels(ShareCounterConstants.BOARD_TYPE));
		result.put(ShareCounterConstants.BOARD_TYPE_VALUES, CommonDataSingleton
				.getCodeCategoryValues(ShareCounterConstants.BOARD_TYPE));

		result.put(ShareCounterConstants.SHARE_STATUS_LABELS, CommonDataSingleton
				.getCodeCategoryLabels(ShareCounterConstants.SHARE_STATUS));
		result.put(ShareCounterConstants.SHARE_STATUS_VALUES, CommonDataSingleton
				.getCodeCategoryValues(ShareCounterConstants.SHARE_STATUS));

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}