/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.liquidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.IRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ViewExpenseCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ViewExpenseCommand() {
		DefaultLogger.debug(this, "Inside View Expense Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
				// {"recoveryExpense",
				// "com.integrosys.cms.app.liquidation.bus.IRecoveryExpense",
				// FORM_SCOPE},
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "isToTrack", "java.lang.String", SERVICE_SCOPE },
				{ "isProcess", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "recoveryExpense", "com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense", FORM_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE },
				{ "stageExpense", "com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense", REQUEST_SCOPE },
				{ "actualExpense", "com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense", REQUEST_SCOPE }, });
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
		String strIndex = (String) map.get("index");
		int index = 0;
		try {
			index = Integer.parseInt(strIndex);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "index invalid");
		}

		boolean isToTrack = false;
		String toTrack = (String) map.get("isToTrack");
		if ((toTrack != null) && toTrack.equalsIgnoreCase("Yes")) {
			isToTrack = true;
		}

		boolean isProcess = false;
		String process = (String) map.get("isProcess");
		if ((process != null) && process.equalsIgnoreCase("Yes")) {
			isProcess = true;
		}

		OBRecoveryExpense expense = null;// (IRecoveryExpense)map.get(
		// "expenses");
		ILiquidationTrxValue liquidationTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");
		ILiquidation liquidation = (ILiquidation) map.get("InitialLiquidation");

		DefaultLogger.debug(this, liquidation);

		ArrayList expenses = null;

		if (isProcess) {

			IRecoveryExpense[] actualExpenseArray = null;
			IRecoveryExpense[] stageExpenseArray = null;

			if ((liquidationTrxVal.getLiquidation() != null)
					&& (liquidationTrxVal.getLiquidation().getRecoveryExpense() != null)) {
				actualExpenseArray = (OBRecoveryExpense[]) (liquidationTrxVal.getLiquidation().getRecoveryExpense())
						.toArray(new OBRecoveryExpense[0]);
			}

			if ((liquidationTrxVal.getStagingLiquidation() != null)
					&& (liquidationTrxVal.getStagingLiquidation().getRecoveryExpense() != null)) {
				stageExpenseArray = (OBRecoveryExpense[]) (liquidationTrxVal.getStagingLiquidation()
						.getRecoveryExpense()).toArray(new OBRecoveryExpense[0]);
			}

			List res = CompareOBUtil.compOBArray(stageExpenseArray, actualExpenseArray);
			CompareResult[] resultList = (CompareResult[]) res.toArray(new CompareResult[0]);

			// sort result
			Arrays.sort(resultList, new Comparator() {
				public int compare(Object o1, Object o2) {
					long long1 = ((OBRecoveryExpense) ((CompareResult) o1).getObj()).getRefID();
					long long2 = ((OBRecoveryExpense) ((CompareResult) o2).getObj()).getRefID();
					return (int) (long2 - long1);
				}
			});

			if (resultList.length > index) {
				expense = (OBRecoveryExpense) resultList[index].getObj();
			}

			if (expense != null) {

				IRecoveryExpense stageExpense = getRecoveryExpense(liquidationTrxVal.getStagingLiquidation(), expense
						.getRefID());
				IRecoveryExpense actualExpense = getRecoveryExpense(liquidationTrxVal.getLiquidation(), expense
						.getRefID());

				resultMap.put("actualExpense", actualExpense);
				resultMap.put("stageExpense", stageExpense);
			}

		}
		else {
			if (liquidation == null) {
				if (((liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (liquidationTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))) {
					expenses = (ArrayList) liquidationTrxVal.getLiquidation().getRecoveryExpense();
				}
				else {
					expenses = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecoveryExpense();
				}
			}
			else {
				expenses = (ArrayList) liquidation.getRecoveryExpense();
			}

			if (isToTrack) {
				expenses = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecoveryExpense();
			}

			if (expenses != null) {
				expense = (OBRecoveryExpense) expenses.get(index);
			}

		}

		resultMap.put("recoveryExpense", expense);
		resultMap.put("InitialLiquidation", liquidation);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Search the right actual/stage object from the given liquidation
	 * @param liquidation
	 * @param refID
	 * @return
	 */
	private IRecoveryExpense getRecoveryExpense(ILiquidation liquidation, long refID) {

		if (liquidation == null) {
			return null;
		}

		OBRecoveryExpense recoveryExpense = null;

		Collection expenseList = liquidation.getRecoveryExpense();

		if (expenseList != null) {
			for (Iterator riIterator = expenseList.iterator(); riIterator.hasNext();) {
				OBRecoveryExpense expense = (OBRecoveryExpense) riIterator.next();
				if (expense.getRefID() == refID) {
					recoveryExpense = expense;
				}
			}
		}

		return recoveryExpense;
	}
}
