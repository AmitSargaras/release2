/*
 * Created on Sep 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.commodity.secapportion;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteApportionmentCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "commSecObj", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		HashMap mapInMap = (HashMap) (map.get("commodityMainTrxValue"));
		String indexID = (String) (map.get("indexID"));
		ICollateralTrxValue[] trxValList = (ICollateralTrxValue[]) (mapInMap.get("trxValue"));
		ICollateralTrxValue itrxValue = trxValList[Integer.parseInt(indexID)];
		ICollateral iCol = (ICollateral) map.get("commSecObj");
		itrxValue.setStagingCollateral(iCol);

		result.put("commodityMainTrxValue", mapInMap);
		result.put("commSecObj", iCol);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
