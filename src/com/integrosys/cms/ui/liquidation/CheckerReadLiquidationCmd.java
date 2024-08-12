/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationCheckerReadCmd.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for checker to read the transaction
 * Description: command that let the checker to read the transaction that being
 * make by the maker
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class CheckerReadLiquidationCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE }, });
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
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE },
				{ "nplInfo", "java.util.Collection", SERVICE_SCOPE },
				{ "currency.map", "java.util.HashMap", SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "isToTrack", "java.lang.String", SERVICE_SCOPE },
				{ "isMakerCloseLiq", "java.lang.String", SERVICE_SCOPE },
				{ "isProcess", "java.lang.String", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Liquidation
	 * is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String trxId = (String) map.get("TrxId");
		if (trxId != null) {
			trxId = trxId.trim();
		}
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		// get from session if available
		ILiquidationTrxValue liquidationTrxVal = (OBLiquidationTrxValue) map.get("LiquidationTrxValue");

		String collateralID = (String) map.get("collateralID");
		long lCollateralID = 0;
		try {
			lCollateralID = Long.parseLong(collateralID);
		}
		catch (Exception e) {
		}

		String collateralLoc = (String) map.get("collateralLoc");
		map.put("collateralLoc", collateralLoc);

		String event = (String) map.get("event");
		if (event.equals("to_track")) {
			resultMap.put("isToTrack", "Yes");
		}
		else {
			resultMap.put("isToTrack", "No");
		}

		if (event.equals(LiquidationAction.EV_MKR_CLOSE_LIQ)) {
			resultMap.put("isMakerCloseLiq", "Yes");
		}
		else {
			resultMap.put("isMakerCloseLiq", "No");
		}

		if (event.equals(LiquidationAction.EV_CHKR_EDIT_LIQ)) {
			resultMap.put("isProcess", "Yes");
		}
		else {
			resultMap.put("isProcess", "No");
		}

		try {
			ILiquidationProxy proxy = LiquidationProxyFactory.getProxy();

			// Collection nplInfo = proxy.getNPLInfo(lCollateralID);
			// resultMap.put("nplInfo",nplInfo);

			if (liquidationTrxVal == null) {
				liquidationTrxVal = proxy.getLiquidationTrxValueByTrxID(trxContext, trxId);
			}
			resultMap.put("LiquidationTrxValue", liquidationTrxVal);

			if (liquidationTrxVal.getStagingLiquidation() != null) {
				ILiquidation liquidation = liquidationTrxVal.getStagingLiquidation();
				lCollateralID = liquidation.getCollateralID();
				Collection nplInfo = proxy.getNPLInfo(lCollateralID);
				resultMap.put("nplInfo", nplInfo);
			}

			resultMap.put("currency.map", MaintainLiquidationHelper.buildCurrencyMap());

		}
		catch (LiquidationException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to retrieve npl info");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
