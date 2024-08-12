package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

public class ReturnDepositCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "depositObj", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {  
				{ "form.depositObject", "java.lang.Object", FORM_SCOPE },				
		});
	}		
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
		result.put("form.depositObject", map.get("depositObj"));

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}			
}
