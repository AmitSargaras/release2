/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/ReadStockCommand.java,v 1.9 2005/05/25 03:08:13 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/05/25 03:08:13 $ Tag: $Name: $
 */

public class ReadStockCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
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
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "stockSummaryList", "java.util.Collection", SERVICE_SCOPE }, });
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

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		HashMap returnMap = new HashMap();
		IGeneralCharge iCol = null;
		String from_event = (String) map.get("from_page");
		if (itrxValue != null) {
			if ((from_event != null) && from_event.equals(EVENT_READ)) {
				iCol = (IGeneralCharge) itrxValue.getCollateral();
			}
			else {
				iCol = (IGeneralCharge) itrxValue.getStagingCollateral();
			}
		}

		Collection stockSummaryList = new ArrayList();
		if ((from_event != null) && from_event.equals(EVENT_PROCESS)) {
			IGeneralCharge actualCol = (IGeneralCharge) itrxValue.getCollateral();
			IStock[] actualStockList = null;
			if ((actualCol.getStocks() != null) && (actualCol.getStocks().size() > 0)) {
				actualStockList = (IStock[]) actualCol.getStocks().values().toArray(new IStock[0]);
			}
			DefaultLogger.debug(this, "<<<<<<< actual stock list length: "
					+ (actualStockList != null ? String.valueOf(actualStockList.length) : "null"));
			IStock[] stageStockList = null;
			if ((iCol.getStocks() != null) && (iCol.getStocks().size() > 0)) {
				stageStockList = (IStock[]) iCol.getStocks().values().toArray(new IStock[0]);
			}
			DefaultLogger.debug(this, "<<<<<<< staging stock list length: "
					+ (stageStockList != null ? String.valueOf(stageStockList.length) : "null"));
			try {
				if ((actualStockList != null) || (stageStockList != null)) {
					List compareResultList = CompareOBUtil.compOBArray(stageStockList, actualStockList);
					stockSummaryList = AssetGenChargeUtil.formatStockList(itrxValue, compareResultList);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			DefaultLogger.debug(this, "<<<<<<<<< stockMap size: "
					+ (iCol.getStocks() != null ? String.valueOf(iCol.getStocks().size()) : "null"));
			stockSummaryList = AssetGenChargeUtil.formatStockList(iCol);
			DefaultLogger.debug(this, "<<<<<<<<<<<<<< stock summary list : "
					+ (stockSummaryList != null ? String.valueOf(stockSummaryList.size()) : "null"));
			// iCol =
			// AssetGenChargeUtil.setStockRecoverableAmt(stockSummaryList,
			// iCol);
			/*
			 * remove the setting of insurance coverage amount to mapping object
			 * if (from_event != null &&
			 * (from_event.equals(StockAction.EVENT_PREPARE_UPDATE) ||
			 * from_event.equals(StockAction.EVENT_PROCESS_UPDATE))) { iCol =
			 * AssetGenChargeUtil.setInsuranceCoverageAmt(stockSummaryList,
			 * iCol, CollateralConstant.TAB_STOCK); }
			 */
		}
		returnMap.put("stockSummaryList", stockSummaryList);
		returnMap.put("col", iCol);

		result.put("form.collateralObject", returnMap);
		result.put("stockSummaryList", stockSummaryList);
		result.put("tab", CollateralConstant.TAB_STOCK);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
