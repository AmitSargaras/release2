/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Manual Input Customer Prepare Search Command.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class PrepareManualInputCustomerCommand extends AbstractCommand {
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();

		// get the LE listing
		LEIDTypeList list = LEIDTypeList.getInstance();
		result.put("LEIDTypeID", list.getLEIDTypeID());
		result.put("LEIDTypeValue", list.getLEIDTypeValue());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);

		return temp;
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "LEIDTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEIDTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE } });
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {};
	}

}
