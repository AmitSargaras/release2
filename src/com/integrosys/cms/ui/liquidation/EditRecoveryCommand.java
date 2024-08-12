/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.liquidation;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.liquidation.bus.OBRecovery;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class EditRecoveryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public EditRecoveryCommand() {
		DefaultLogger.debug(this, "Inside Edit Recovery Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
				{ "recovery", "com.integrosys.cms.app.liquidation.bus.OBRecovery", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "recovery", "com.integrosys.cms.app.liquidation.bus.OBRecovery", FORM_SCOPE }, });
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
		String strIndex = (String) map.get("index");
		int index = 0;
		try {
			index = Integer.parseInt(strIndex);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "index invalid");
		}
		OBRecovery recovery = (OBRecovery) map.get("recovery");
		OBLiquidation liquidation = (OBLiquidation) map.get("InitialLiquidation");

		boolean duplicate = false;
		if (liquidation.getRecovery() != null) {
			ArrayList recoveryList = (ArrayList) liquidation.getRecovery();
			IRecovery temp = null;
			for (int i = 0; i < recoveryList.size(); i++) {
				if (i != index) {
					temp = (IRecovery) recoveryList.get(i);

					if (temp.getRecoveryType().equals(recovery.getRecoveryType())) {
						exceptionMap.put("recoveryTypeError", new ActionMessage("error.liquidation.similarRecovery"));
						duplicate = true;
					}
				}
			}
		}

		if (!duplicate) {
			liquidation.updateRecovery(index, recovery);
		}
		resultMap.put("recovery", recovery);
		resultMap.put("InitialLiquidation", liquidation);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
