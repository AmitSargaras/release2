/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/stockitem/RefreshStockItemCommand.java,v 1.2 2005/03/24 03:36:00 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/24 03:36:00 $ Tag: $Name: $
 */
public class RefreshStockItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "stockItemObj", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock", FORM_SCOPE },
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
		return (new String[][] { { "stockItemObj", "java.util.HashMap", FORM_SCOPE }, });
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

		IStock stock = (IStock) map.get("stockItemObj");
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iCol = (IGeneralCharge) trxValue.getStagingCollateral();

		HashMap returnMap = new HashMap();
		returnMap.put("obj", stock);
		returnMap.put("ccy", iCol.getCurrencyCode());
		returnMap.put("securityLocation", iCol.getCollateralLocation());

		result.put("stockItemObj", returnMap);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
