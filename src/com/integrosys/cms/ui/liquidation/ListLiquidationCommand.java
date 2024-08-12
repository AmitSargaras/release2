/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationListCommand.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.liquidation.proxy.ILiquidationProxy;
import com.integrosys.cms.app.liquidation.proxy.LiquidationProxyFactory;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for maker to read the value Description:
 * command that let the maker to read the value that want to be modify
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class ListLiquidationCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.OBLiquidation", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "securityType", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralLoc", "java.lang.String", REQUEST_SCOPE },
				{ "sess.collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "flag1", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "currency.map", "java.util.HashMap", SERVICE_SCOPE },
				// {"InitialLiquidation",
				// "com.integrosys.cms.app.liquidation.bus.OBLiquidation",
				// FORM_SCOPE}
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.OBLiquidation", SERVICE_SCOPE },
				{ "collateralLoc", "java.lang.String", SERVICE_SCOPE },
				{ "sess.collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE }, });
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
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		String flag = (String) map.get("flag");
		String flag1 = (String) map.get("flag1");

		// result.put("flag", flag);
		if (flag1 != null) {
			resultMap.put("flag1", flag1);
		}
		else {
			resultMap.put("flag1", flag);
		}

		// Getting collateralID from request scope of session
		// Putting the collateralID into session
		String collateralID = (String) map.get("collateralID");
		if ((collateralID == null) || collateralID.trim().equals("")) {
			collateralID = (String) map.get("sess.collateralID");
		}
		long lCollateralID = 0;

		try {
			lCollateralID = Long.parseLong(collateralID);
		}
		catch (Exception e) {
		}

		if (collateralID != null) {
			resultMap.put("sess.collateralID", collateralID);
		}

		String collateralLoc = (String) map.get("collateralLoc");
		map.put("collateralLoc", collateralLoc);
		OBLiquidation obLiquidation = (OBLiquidation) map.get("InitialLiquidation");

		String event = (String) map.get("event");
		DefaultLogger.debug(this, "Inside doExecute() event= " + event);

		try {

			ILiquidationProxy proxy = LiquidationProxyFactory.getProxy();

			if (lCollateralID != 0) {
				Collection nplInfo = proxy.getNPLInfo(lCollateralID);
				resultMap.put("nplInfo", nplInfo);
			}

			// proxy.checkLiquidationTrx();
			// If Liquidation is in session, get from session.
			if (obLiquidation == null) {
				if (!(event.equals(LiquidationAction.EV_MKR_EDIT_LIQ_CONFIRM) || event.equals("liquidation_list_error"))) {

					ILiquidationTrxValue liquidationTrxVal = proxy.getLiquidationTrxValueByTrxRefID(trxContext,
							collateralID);

					if (LiquidationAction.EVENT_VIEW.equals(event) || LiquidationAction.EV_CHECKER_VIEW.equals(event)) {
						resultMap.put("LiquidationTrxValue", liquidationTrxVal);
						resultMap.put("InitialLiquidation", liquidationTrxVal.getLiquidation());

					}
					else {
						// if current status is other than ACTIVE & REJECTED,
						// then show workInProgress.
						// i.e. allow edit only if status is either ACTIVE or
						// REJECTED
						if (!((liquidationTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (liquidationTrxVal
								.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
							resultMap.put("wip", "wip");
							resultMap.put("InitialLiquidation", liquidationTrxVal.getStagingLiquidation());

						}
						else {
							resultMap.put("LiquidationTrxValue", liquidationTrxVal);
						}

						resultMap.put("InitialLiquidation", liquidationTrxVal.getLiquidation());
					}
				}
			}
			resultMap.put("currency.map", MaintainLiquidationHelper.buildCurrencyMap());

		}
		catch (LiquidationException e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to retrieve npl info and liquidation trx value for collateral id [" + collateralID + "]");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
