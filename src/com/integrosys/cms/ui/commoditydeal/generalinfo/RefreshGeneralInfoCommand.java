/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/RefreshGeneralInfoCommand.java,v 1.6 2005/10/12 12:35:02 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/12 12:35:02 $ Tag: $Name: $
 */

public class RefreshGeneralInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, { "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "generalInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE }, });
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
				{ "generalInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, { "checkDealAmtAgainstCMVFlag", "java.lang.String", REQUEST_SCOPE },
				{ "checkDealAmtAgainstCMVMsg", "java.lang.String", REQUEST_SCOPE }, });
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

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		// ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		ICommodityDeal dealObj = (ICommodityDeal) map.get("generalInfoObj");
		String securityID = (String) map.get("securityID");
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		if ((securityID != null) && (securityID.length() > 0)) {
			if (dealCollateral == null) {
				try {
					dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy().getCollateral(
							Long.parseLong(securityID), true);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
			else {
				if (dealCollateral.getCollateralID() != Long.parseLong(securityID)) {
					try {
						dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy().getCollateral(
								Long.parseLong(securityID), true);
					}
					catch (Exception e) {
						e.printStackTrace();
						throw new CommandProcessingException(e.getMessage());
					}
				}
			}
		}

		result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_AGAINST_CMV_REQUIRED);
		result.put("checkDealAmtAgainstCMVMsg", null);

		result.put("dealCollateral", dealCollateral);
		result.put("generalInfoObj", dealObj);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
