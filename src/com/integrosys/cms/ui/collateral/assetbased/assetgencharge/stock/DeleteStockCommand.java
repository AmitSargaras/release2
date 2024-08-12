/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/DeleteStockCommand.java,v 1.5 2005/03/31 10:00:26 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/03/31 10:00:26 $ Tag: $Name: $
 */

public class DeleteStockCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "java.util.HashMap", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "stockSummaryList", "java.util.Collection", SERVICE_SCOPE }, });
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
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

		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");

		HashMap colMap = (HashMap) map.get("form.collateralObject");
		try {
			IGeneralCharge col = (IGeneralCharge) AccessorUtil.deepClone(colMap.get("col"));
			String[] deleteStocks = (String[]) colMap.get("deleteStocks");
			String[] deleteInsurances = (String[]) colMap.get("deleteInsurances");

			String event = (String) map.get("event");

			if (event.startsWith(CollateralConstant.STOCK)) {
				// if stock still link to at least one insurance, cannot be
				// deleted
				HashMap stockMap = (HashMap) col.getStocks();
				HashMap stockInsuranceMap = (HashMap) col.get_Stock_Insurance_Map();
				boolean hasException = false;
				if (deleteStocks != null) {
					for (int i = 0; i < deleteStocks.length; i++) {
						Collection insuranceList = (Collection) stockInsuranceMap.get(deleteStocks[i]);
						if ((insuranceList != null) && !insuranceList.isEmpty()) {
							exceptionMap.put("stockItemDelete" + deleteStocks[i], new ActionMessage(
									"collateral.stock.delete.stockitem"));
							hasException = true;
						}
						else {
							stockMap.remove(deleteStocks[i]);
							stockInsuranceMap.remove(deleteStocks[i]);
						}
					}
				}
				if (hasException) {
					DefaultLogger.debug(this, "<<<<<<<< has Exception for deletion of stock");
					exceptionMap.put("stockItemDelete", new ActionMessage("error.collateral.stock.deletestock"));
				}
				else {
					col.setStocks(stockMap);
					col.set_Stock_Insurance_Map(stockInsuranceMap);
				}
			}
			else {
				// delete insurance regardless the linkage with stock still
				// exist
				// if stock linkage still exist, delete the linkage too
				col = AssetGenChargeUtil.deleteInsuranceList(col, deleteInsurances, CollateralConstant.TAB_STOCK);
			}

			DefaultLogger.debug(this, "ExceptionMap size: " + exceptionMap.size());

			HashMap returnMap = new HashMap();
			Collection stockSummaryList = (Collection) map.get("stockSummaryList");
			if ((exceptionMap != null) && !exceptionMap.isEmpty()) {
				returnMap.put("hasException", "true");
				col = (IGeneralCharge) trxValue.getStagingCollateral();
			}
			else {
				trxValue.setStagingCollateral(col);
				stockSummaryList = AssetGenChargeUtil.formatStockList(col);
			}

			returnMap.put("col", col);
			returnMap.put("stockSummaryList", stockSummaryList);

			result.put("stockSummaryList", stockSummaryList);
			result.put("serviceColObj", trxValue);
			result.put("form.collateralObject", returnMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
