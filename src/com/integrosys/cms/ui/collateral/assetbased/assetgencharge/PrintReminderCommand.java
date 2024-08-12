/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/PrintReminderCommand.java,v 1.4 2006/02/22 01:01:43 jzhan Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/02/22 01:01:43 $ Tag: $Name: $
 */

public class PrintReminderCommand extends AbstractCommand {
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
		return (new String[][] {
				{ "drawingPowerCol", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge",
						REQUEST_SCOPE }, { "customerInfoList", "java.util.Collection", REQUEST_SCOPE } });
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
		IGeneralCharge col = (IGeneralCharge) itrxValue.getCollateral();

		// for existing case which does not have recoverable amount and does not
		// click on drawing power tab to set recoverable amount
		Collection stockSummaryList = AssetGenChargeUtil.formatStockList(col);
		col = AssetGenChargeUtil.setStockRecoverableAmt(stockSummaryList, col);

		result.put("drawingPowerCol", col);

		try {
			List securityBCAList = SecuritySubTypeUtil.getSecurityBCAList(col);
			ICustomerProxy proxy = CustomerProxyFactory.getProxy();
			HashMap customerList = (HashMap) proxy.getCustomerMailingDetails(securityBCAList);
			result.put("customerInfoList", customerList.values());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
