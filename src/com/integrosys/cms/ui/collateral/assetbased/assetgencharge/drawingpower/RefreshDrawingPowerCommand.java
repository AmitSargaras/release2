/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/RefreshDrawingPowerCommand.java,v 1.6 2006/07/12 06:25:53 jychong Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * Description
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/07/12 06:25:53 $ Tag: $Name: $
 */

public class RefreshDrawingPowerCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
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
				{ "colLimitMap", "java.util.Collection", SERVICE_SCOPE }, });
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
		IGeneralCharge col = (IGeneralCharge) map.get("form.collateralObject");
		HashMap limitMap = (HashMap) map.get("limitMap");
		HashMap colLimitMap = new HashMap();

		try {
			AssetGenChargeUtil.populateLimitDetails(col, limitMap, colLimitMap);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		Collection colLimitMapList = AssetGenChargeUtil.getSortCollateralLimitMap(colLimitMap);
		// DefaultLogger.debug(this, "colLimitMapList: "+(colLimitMapList ==
		// null?"null":String.valueOf(colLimitMapList.size())));

		BigDecimal totalApprovedLimit = AssetGenChargeUtil.getTotalApprovedLimit(limitMap, col.getSCISecurityID(), col
				.getCurrencyCode(), locale);
		AssetGenChargeUtil.validateLimitDetails(totalApprovedLimit, col, exceptionMap);

		HashMap returnMap = new HashMap();
		returnMap.put("col", col);
		returnMap.put("colLimitMap", colLimitMapList);
		returnMap.put("limitMap", limitMap);
		returnMap.put("totalApprovedLimit", totalApprovedLimit);

		result.put("form.collateralObject", returnMap);
		result.put("colLimitMap", colLimitMapList);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
