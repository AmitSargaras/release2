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
import com.integrosys.cms.app.liquidation.bus.IRecoveryIncome;
import com.integrosys.cms.app.liquidation.bus.OBRecovery;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ViewRecoveryIncomeCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ViewRecoveryIncomeCommand() {
		DefaultLogger.debug(this, "Inside View Income Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
				// {"recoveryIncome",
				// "com.integrosys.cms.app.liquidation.bus.IRecoveryIncome",
				// FORM_SCOPE},
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "oindex", "java.lang.String", REQUEST_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE }, { "isProcess", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "recoveryIncome", "com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome", FORM_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE },
				{ "actualRecoveryIncome", "com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome", REQUEST_SCOPE },
				{ "stageRecoveryIncome", "com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome", REQUEST_SCOPE }, });
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

		String strIndex = (String) map.get("oindex");
		int index = 0;
		try {
			index = Integer.parseInt(strIndex);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "index invalid [" + strIndex + "], ignored");
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

		OBRecoveryIncome income = null;
		ILiquidationTrxValue liquidationTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");
		ILiquidation liquidation = (ILiquidation) map.get("InitialLiquidation");

		ArrayList recovery = null;
		ArrayList incomes = null;

		if (isProcess) {

			IRecoveryIncome[] actualRecoveryIncomeArray = null;
			IRecoveryIncome[] stageRecoveryIncomeArray = null;

			if ((liquidationTrxVal.getLiquidation() != null)
					&& (liquidationTrxVal.getLiquidation().getRecovery() != null)) {
				recovery = (ArrayList) liquidationTrxVal.getLiquidation().getRecovery();
				for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
					OBRecovery ob = (OBRecovery) iterator.next();
					if (ob.getRecoveryType().equals(recoveryType)) {
						actualRecoveryIncomeArray = (OBRecoveryIncome[]) ob.getRecoveryIncome().toArray(
								new OBRecoveryIncome[0]);
					}
				}
			}

			if ((liquidationTrxVal.getStagingLiquidation() != null)
					&& (liquidationTrxVal.getStagingLiquidation().getRecovery() != null)) {
				recovery = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecovery();
				for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
					OBRecovery ob = (OBRecovery) iterator.next();
					if (ob.getRecoveryType().equals(recoveryType)) {
						stageRecoveryIncomeArray = (OBRecoveryIncome[]) ob.getRecoveryIncome().toArray(
								new OBRecoveryIncome[0]);
					}
				}
			}

			List res = CompareOBUtil.compOBArray(stageRecoveryIncomeArray, actualRecoveryIncomeArray);
			CompareResult[] resultList = (CompareResult[]) res.toArray(new CompareResult[0]);

			// sort result
			Arrays.sort(resultList, new Comparator() {
				public int compare(Object o1, Object o2) {
					long long1 = ((OBRecoveryIncome) ((CompareResult) o1).getObj()).getRefID();
					long long2 = ((OBRecoveryIncome) ((CompareResult) o2).getObj()).getRefID();
					return (int) (long2 - long1);
				}
			});

			if (resultList.length > index) {
				income = (OBRecoveryIncome) resultList[index].getObj();
			}

			if (income != null) {
				IRecoveryIncome stageRecoveryIncome = getRecoveryIncome(liquidationTrxVal.getStagingLiquidation(),
						income.getRefID(), recoveryType, index);
				IRecoveryIncome actualRecoveryIncome = getRecoveryIncome(liquidationTrxVal.getLiquidation(), income
						.getRefID(), recoveryType, index);

				resultMap.put("actualRecoveryIncome", actualRecoveryIncome);
				resultMap.put("stageRecoveryIncome", stageRecoveryIncome);
			}
		}
		else {

			if (liquidation == null) {
				if (((liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (liquidationTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))) {
					recovery = (ArrayList) liquidationTrxVal.getLiquidation().getRecovery();
				}
				else {
					recovery = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecovery();
				}
			}
			else {
				recovery = (ArrayList) liquidation.getRecovery();
			}

			if (isToTrack) {
				recovery = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecovery();
			}

			if (recovery != null) {
				for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
					OBRecovery ob = (OBRecovery) iterator.next();
					if (ob.getRecoveryType().equals(recoveryType)) {
						incomes = (ArrayList) ob.getRecoveryIncome();
					}
				}
			}

			if (incomes != null) {
				income = (OBRecoveryIncome) incomes.get(index);
			}
		}

		resultMap.put("recoveryIncome", income);
		resultMap.put("InitialLiquidation", liquidation);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Get the corresponding recoveryIncome from the given actual/stage
	 * liquidation
	 * @param liquidation
	 * @param refID
	 * @param recoveryType
	 * @param index
	 * @return
	 */
	private IRecoveryIncome getRecoveryIncome(ILiquidation liquidation, long refID, String recoveryType, int index) {

		if (liquidation == null) {
			return null;
		}

		OBRecoveryIncome recoveryIncome = null;

		Collection recovery = liquidation.getRecovery();
		ArrayList temp = null;

		if (recovery != null) {
			for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
				OBRecovery ob = (OBRecovery) iterator.next();
				if (ob.getRecoveryType().equals(recoveryType)) {
					temp = (ArrayList) ob.getRecoveryIncome();
				}
			}
		}

		if (temp != null) {
			for (Iterator riIterator = temp.iterator(); riIterator.hasNext();) {
				OBRecoveryIncome income = (OBRecoveryIncome) riIterator.next();
				if (income.getRefID() == refID) {
					recoveryIncome = income;
				}
			}
		}

		return recoveryIncome;
	}
}
