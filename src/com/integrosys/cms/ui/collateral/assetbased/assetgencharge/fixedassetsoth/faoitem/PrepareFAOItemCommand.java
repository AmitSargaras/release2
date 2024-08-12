/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/PrepareFAOItemCommand.java,v 1.2 2005/04/01 09:41:11 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/01 09:41:11 $ Tag: $Name: $
 */

public class PrepareFAOItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "valuerName", "java.util.Collection", SERVICE_SCOPE },
				{ "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral col = (colTrxValue.getStagingCollateral() == null) ? colTrxValue.getCollateral() : colTrxValue
				.getStagingCollateral();

		DefaultLogger.debug(this, "collateral location: " + col.getCollateralLocation());
		result.put("valuerName", SecuritySubTypeUtil.getValuerListByCountry(col.getCollateralLocation()));

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		TimeFreqList freqList = TimeFreqList.getInstance();
		result.put("freqValue", freqList.getTimeFreqValue());
		result.put("freqID", freqList.getTimeFreqID());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
