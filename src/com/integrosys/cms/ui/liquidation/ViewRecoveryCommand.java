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
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.OBRecovery;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */
public class ViewRecoveryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ViewRecoveryCommand() {
		DefaultLogger.debug(this, "Inside View Recovery Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
				// {"recovery",
				// "com.integrosys.cms.app.liquidation.bus.IRecovery",
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
				{ "recoveryType", "java.lang.String", SERVICE_SCOPE },
				{ "recovery", "com.integrosys.cms.app.liquidation.bus.OBRecovery", FORM_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE },
				{ "stageRecovery", "com.integrosys.cms.app.liquidation.bus.OBRecovery", REQUEST_SCOPE },
				{ "actualRecovery", "com.integrosys.cms.app.liquidation.bus.OBRecovery", REQUEST_SCOPE }, });
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
			DefaultLogger.debug(this, "index invalid [" + strIndex + "]");
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

		OBRecovery recovery = null;// (IRecovery)map.get("recovery");
		ILiquidationTrxValue liquidationTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");
		ILiquidation liquidation = (ILiquidation) map.get("InitialLiquidation");

		ArrayList recoverys = null;

		if (isProcess) {

			IRecovery[] actualRecoveryArray = null;
			IRecovery[] stageRecoveryArray = null;

			if ((liquidationTrxVal.getLiquidation() != null)
					&& (liquidationTrxVal.getLiquidation().getRecovery() != null)) {
				actualRecoveryArray = (OBRecovery[]) (liquidationTrxVal.getLiquidation().getRecovery())
						.toArray(new OBRecovery[0]);
			}

			if ((liquidationTrxVal.getStagingLiquidation() != null)
					&& (liquidationTrxVal.getStagingLiquidation().getRecoveryExpense() != null)) {
				stageRecoveryArray = (OBRecovery[]) (liquidationTrxVal.getStagingLiquidation().getRecovery())
						.toArray(new OBRecovery[0]);
			}

			List res = CompareOBUtil.compOBArray(stageRecoveryArray, actualRecoveryArray);
			CompareResult[] resultList = (CompareResult[]) res.toArray(new CompareResult[0]);

			// sort result
			Arrays.sort(resultList, new Comparator() {
				public int compare(Object o1, Object o2) {
					long long1 = ((OBRecovery) ((CompareResult) o1).getObj()).getRefID();
					long long2 = ((OBRecovery) ((CompareResult) o2).getObj()).getRefID();
					return (int) (long2 - long1);
				}
			});

			if (resultList.length > index) {
				recovery = (OBRecovery) resultList[index].getObj();
			}

			if (recovery != null) {

				IRecovery stageRecovery = getRecovery(liquidationTrxVal.getStagingLiquidation(), recovery.getRefID());
				IRecovery actualRecovery = getRecovery(liquidationTrxVal.getLiquidation(), recovery.getRefID());

				resultMap.put("actualRecovery", actualRecovery);
				resultMap.put("stageRecovery", stageRecovery);
			}

		}
		else {

			if (liquidation == null) {
				if (((liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (liquidationTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))) {
					recoverys = (ArrayList) liquidationTrxVal.getLiquidation().getRecovery();
				}
				else {
					recoverys = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecovery();
				}
			}
			else {
				recoverys = (ArrayList) liquidation.getRecovery();
			}

			if (isToTrack) {
				recoverys = (ArrayList) liquidationTrxVal.getStagingLiquidation().getRecovery();
			}

			if (recoverys != null) {
				recovery = (OBRecovery) recoverys.get(index);
			}
		}

		if (recovery != null) {
			resultMap.put("recoveryType", recovery.getRecoveryType());
		}

		resultMap.put("recovery", recovery);
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
	private IRecovery getRecovery(ILiquidation liquidation, long refID) {

		if (liquidation == null) {
			return null;
		}

		OBRecovery recovery = null;

		Collection recoveryList = liquidation.getRecovery();

		if (recoveryList != null) {
			for (Iterator riIterator = recoveryList.iterator(); riIterator.hasNext();) {
				OBRecovery obRecovery = (OBRecovery) riIterator.next();
				if (obRecovery.getRefID() == refID) {
					recovery = obRecovery;
				}
			}
		}

		return recovery;
	}
}
