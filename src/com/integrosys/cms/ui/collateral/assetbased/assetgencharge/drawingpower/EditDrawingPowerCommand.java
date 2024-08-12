/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/EditDrawingPowerCommand.java,v 1.2 2006/07/12 06:25:53 jychong Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/12 06:25:53 $ Tag: $Name: $
 */

public class EditDrawingPowerCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "limitMap", "java.util.HashMap", SERVICE_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "form.collateralObject", "java.util.HashMap", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "colLimitMap", "java.util.Collection", SERVICE_SCOPE }, });

	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICollateral iColObj = (ICollateral) map.get("form.collateralObject");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		HashMap limitMap = (HashMap) map.get("limitMap");
		HashMap colLimitMap = new HashMap();

		try {
			AssetGenChargeUtil.populateLimitDetails((IGeneralCharge) iColObj, limitMap, colLimitMap);
		}
		catch (Exception e) {
			throw new CommandProcessingException(e.getMessage());
		}
		Collection colLimitMapList = AssetGenChargeUtil.getSortCollateralLimitMap(colLimitMap);
		DefaultLogger.debug(this, "colLimitMapList: "
				+ (colLimitMapList == null ? "null" : String.valueOf(colLimitMapList.size())));

		BigDecimal totalApprovedLimit = AssetGenChargeUtil.getTotalApprovedLimit(limitMap, iColObj.getSCISecurityID(),
				iColObj.getCurrencyCode(), locale);
		AssetGenChargeUtil.validateLimitDetails(totalApprovedLimit, (IGeneralCharge) iColObj, exceptionMap);

		if (exceptionMap.isEmpty()) {
			itrxValue.setStagingCollateral(iColObj);
		}
		else {
			HashMap returnMap = new HashMap();
			returnMap.put("col", iColObj);
			returnMap.put("colLimitMap", colLimitMapList);
			returnMap.put("limitMap", limitMap);
			returnMap.put("totalApprovedLimit", totalApprovedLimit);

			result.put("form.collateralObject", returnMap);
			result.put("hasError", "true");
			result.put("colLimitMap", colLimitMapList);
			// exceptionMap.put(STOP_COMMAND_CHAIN, null);
			DefaultLogger.debug(this, "<<<<<<<<<<<<< exception map is not null");
		}

		result.put("serviceColObj", itrxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}

}
