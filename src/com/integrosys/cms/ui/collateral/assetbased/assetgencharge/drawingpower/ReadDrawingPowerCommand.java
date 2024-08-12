/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/ReadDrawingPowerCommand.java,v 1.10 2006/07/12 06:25:53 jychong Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.LimitDetailsComparator;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/07/12 06:25:53 $ Tag: $Name: $
 */

public class ReadDrawingPowerCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "limitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "form.collateralObject", "java.util.HashMap", FORM_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE }, { "colLimitMap", "java.util.Collection", SERVICE_SCOPE },
				{ "limitMap", "java.util.HashMap", SERVICE_SCOPE }, });
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

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String from_event = (String) map.get("from_page");

		IGeneralCharge col = null;
		if (itrxValue != null) {
			if ((from_event != null) && from_event.equals(EVENT_READ)) {
				col = (IGeneralCharge) itrxValue.getCollateral();
			}
			else {
				col = (IGeneralCharge) itrxValue.getStagingCollateral();
				// if (from_event != null &&
				// (from_event.equals(DrawingPowerAction.EVENT_PREPARE_UPDATE)
				// ||
				// from_event.equals(DrawingPowerAction.EVENT_PROCESS_UPDATE)))
				// {
				// Collection stockSummaryList =
				// AssetGenChargeUtil.formatStockList(col);
				// col =
				// AssetGenChargeUtil.setStockRecoverableAmt(stockSummaryList,
				// col);
				// }
			}
		}

		// setting the stock recoverable amount
		Collection stockSummaryList = AssetGenChargeUtil.formatStockList(col);
		col = AssetGenChargeUtil.setStockRecoverableAmt(stockSummaryList, col);

		HashMap limitMap = (HashMap) map.get("limitMap"); // key is the limit
															// id, value is the
															// ILimit
		if (limitMap == null) {
			limitMap = new HashMap();
		}
		HashMap colLimitMap = new HashMap(); // key is the limit id, value is
												// the ICollateralLimitMap

		try {
			AssetGenChargeUtil.populateLimitDetails(col, limitMap, colLimitMap);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		DefaultLogger
				.debug(this, "colLimitMap: " + (colLimitMap == null ? "null" : String.valueOf(colLimitMap.size())));
		List colLimitMapList = new ArrayList();
		if ((from_event != null) && from_event.equals(EVENT_PROCESS)) {
			HashMap actualLimitMap = new HashMap();
			IGeneralCharge actualCol = (IGeneralCharge) itrxValue.getCollateral();
			try {
				AssetGenChargeUtil.populateLimitDetails(actualCol, limitMap, actualLimitMap);
			}
			catch (Exception e) {
				throw new CommandProcessingException(e.getMessage());
			}

			ICollateralLimitMap[] actualMapList = null;
			ICollateralLimitMap[] stageMapList = null;

			if (colLimitMap != null) {
				Collection values = colLimitMap.values();
				stageMapList = (ICollateralLimitMap[]) values.toArray(new ICollateralLimitMap[0]);
			}
			if (actualLimitMap != null) {
				Collection values = actualLimitMap.values();
				actualMapList = (ICollateralLimitMap[]) values.toArray(new ICollateralLimitMap[0]);
			}
			try {
				if ((colLimitMap != null) || (actualLimitMap != null)) {
					colLimitMapList = CompareOBUtil.compOBArray(stageMapList, actualMapList);
					Collections.sort(colLimitMapList, new LimitDetailsComparator());
				}
			}
			catch (Exception e) {
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			colLimitMapList = AssetGenChargeUtil.getSortCollateralLimitMap(colLimitMap);
		}

		BigDecimal totalApprovedLimit = AssetGenChargeUtil.getTotalApprovedLimit(limitMap, col.getSCISecurityID(), col
				.getCurrencyCode(), locale);
		DefaultLogger.debug(this, "colLimitMapList: "
				+ (colLimitMapList == null ? "null" : String.valueOf(colLimitMapList.size())));

		HashMap returnMap = new HashMap();
		returnMap.put("col", col);
		returnMap.put("colLimitMap", colLimitMapList);
		returnMap.put("limitMap", limitMap);
		returnMap.put("totalApprovedLimit", totalApprovedLimit);

		result.put("limitMap", limitMap);
		result.put("colLimitMap", colLimitMapList);
		result.put("form.collateralObject", returnMap);
		result.put("tab", CollateralConstant.TAB_DRAWING_POWER);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
