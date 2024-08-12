/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.IRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class AddExpenseCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddExpenseCommand() {
		DefaultLogger.debug(this, "Inside Add Expense Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.ILiquidation", SERVICE_SCOPE },
				{ "recoveryExpense", "com.integrosys.cms.app.liquidation.bus.IRecoveryExpense", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.ILiquidation", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		map.put("theOBTrxContext", trxContext);

		IRecoveryExpense recoveryExpense = (IRecoveryExpense) map.get("recoveryExpense");
		ILiquidation liquidation = (ILiquidation) map.get("InitialLiquidation");

		if (liquidation == null) {
			liquidation = new OBLiquidation();
		}

		boolean duplicate = false;
		if (liquidation.getRecoveryExpense() != null) {
			Collection expenseList = liquidation.getRecoveryExpense();
			IRecoveryExpense temp;
			for (Iterator reIterator = expenseList.iterator(); reIterator.hasNext();) {
				temp = (IRecoveryExpense) reIterator.next();

				if (temp.getExpenseType().equals(recoveryExpense.getExpenseType())) {
					exceptionMap.put("expenseTypeError", new ActionMessage("error.liquidation.similarExpense"));
					duplicate = true;
				}
			}
		}
		if (!duplicate) {
			liquidation.addExpenseItem(recoveryExpense);
		}

		resultMap.put("InitialLiquidation", liquidation);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
