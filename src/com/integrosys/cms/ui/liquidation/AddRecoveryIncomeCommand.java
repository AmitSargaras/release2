/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.liquidation;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.IRecoveryIncome;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class AddRecoveryIncomeCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddRecoveryIncomeCommand() {
		DefaultLogger.debug(this, "Inside Add Income Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.ILiquidation", SERVICE_SCOPE },
				{ "recoveryType", "java.lang.String", SERVICE_SCOPE },
				{ "recoveryIncome", "com.integrosys.cms.app.liquidation.bus.IRecoveryIncome", FORM_SCOPE },
		// {"theOBTrxContext",
		// "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
		});
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
				// {"theOBTrxContext",
				// "com.integrosys.cms.app.transaction.OBTrxContext",
				// FORM_SCOPE},
				{ "recoveryIncome", "com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome", FORM_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");

		String recoveryType = (String) map.get("recoveryType");

		IRecoveryIncome recoveryIncome = (IRecoveryIncome) map.get("recoveryIncome");
		// Liquidation from session
		ILiquidation liquidation = (ILiquidation) map.get("InitialLiquidation");

		if (liquidation == null) {
			liquidation = new OBLiquidation();
		}
		liquidation.addIncomeItem(recoveryIncome, recoveryType);
		resultMap.put("InitialLiquidation", liquidation);
		resultMap.put("recoveryIncome", recoveryIncome);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
