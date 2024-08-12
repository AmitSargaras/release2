/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/stockitem/ReadStockItemCommand.java,v 1.3 2005/03/30 09:54:21 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStock;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/03/30 09:54:21 $ Tag: $Name: $
 */

public class ReadStockItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
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
		return (new String[][] {
				{ "stockItemObj", "java.util.HashMap", FORM_SCOPE },
				{ "stageStockItem", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock",
						REQUEST_SCOPE },
				{ "actualStockItem", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock",
						REQUEST_SCOPE }, });
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

		String strIndex = (String) map.get("indexID");

		String from_page = (String) map.get("from_page");

		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iCol = null;

		if ((from_page != null) && from_page.equals(EVENT_READ)) {
			iCol = (IGeneralCharge) trxValue.getCollateral();
		}
		else {
			iCol = (IGeneralCharge) trxValue.getStagingCollateral();
		}

		IStock stock = null;
		if (strIndex.equals("-1")) {
			stock = new OBStock();
			String generatedID = iCol.generateNewID(OBGeneralCharge.TYPE_STOCK);
			DefaultLogger.debug(this, "<<<<<<<<<<<<<<< New Generated Stock ID: " + generatedID);
			stock.setStockID(generatedID);
		}
		else {
			HashMap stockMap = (HashMap) iCol.getStocks();
			stock = (IStock) stockMap.get(strIndex);
		}

		if (from_page.equals(EVENT_PROCESS)) {
			IStock actualStock = null;
			HashMap actualStkMap = (HashMap) ((IGeneralCharge) trxValue.getCollateral()).getStocks();
			if (actualStkMap != null) {
				actualStock = (IStock) actualStkMap.get(strIndex);
			}
			result.put("actualStockItem", actualStock);
			result.put("stageStockItem", stock);
			if (stock == null) {
				stock = actualStock;
			}
		}

		HashMap returnMap = new HashMap();
		returnMap.put("obj", stock);
		returnMap.put("ccy", iCol.getCurrencyCode());
		returnMap.put("securityLocation", iCol.getCollateralLocation());

		result.put("stockItemObj", returnMap);
		result.put("from_page", from_page);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
