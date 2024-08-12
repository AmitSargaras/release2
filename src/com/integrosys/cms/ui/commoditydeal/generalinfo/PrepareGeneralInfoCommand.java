/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/PrepareGeneralInfoCommand.java,v 1.17 2006/09/26 03:18:45 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2006/09/26 03:18:45 $ Tag: $Name: $
 */

public class PrepareGeneralInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "securityID", "java.lang.String", REQUEST_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "subLimitID", "java.lang.String", REQUEST_SCOPE },
				{ "contractNo", "java.lang.String", REQUEST_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "checkDealAmtAgainstCMVFlag", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "contractNoID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "contractNoValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitIDValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitIDLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "subLimitIDValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "subLimitIDLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "selectedSublimit", "com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit",
						ICommonEventConstant.REQUEST_SCOPE },
				{ "checkDealAmtAgainstCMVFlag", "java.lang.String", REQUEST_SCOPE },
				{ "checkDealAmtAgainstCMVMsg", "java.lang.String", REQUEST_SCOPE },
				{ "currentEventNum", "java.lang.String", REQUEST_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE }, });
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

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		// Prepare drop down list value for commodity main page
		String strSecID = (String) map.get("securityID");
		String strLimitID = (String) map.get("limitID");
		String strSubLimitID = (String) map.get("subLimitID");
		String strContractID = (String) map.get("contractNo");
		long securityID = ICMSConstant.LONG_INVALID_VALUE;

		long subLimitID = ICMSConstant.LONG_INVALID_VALUE;
		long contractID = ICMSConstant.LONG_INVALID_VALUE;
		if ((strSecID != null) && (strSecID.length() > 0)) {
			securityID = Long.parseLong(strSecID);
		}
		if ((strSubLimitID != null) && (strSubLimitID.length() > 0)) {
			subLimitID = Long.parseLong(strSubLimitID);
		}
		if ((strContractID != null) && (strContractID.length() > 0)) {
			contractID = Long.parseLong(strContractID);
		}

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getStagingCommodityDeal();
		boolean isCollateralPool = dealObj.getDealTypeCode().equals(ICMSConstant.DEAL_TYPE_COLLATERAL_POOL);
		HashMap commodityLimitMap = (HashMap) map.get("commodityLimitMap");
		Collection contractNoID = new ArrayList();
		Collection contractNoValue = new ArrayList();

		HashMap generalInfoMap = CommodityDealUtil.getGeneralInfoDropDownList(commodityLimitMap, securityID,
				strLimitID, isCollateralPool, true, subLimitID, true);

		Collection secID = (Collection) generalInfoMap.get("secID");
		Collection secValue = (Collection) generalInfoMap.get("secValue");
		Collection limitIDValues = (Collection) generalInfoMap.get("limitIDValues");
		Collection limitIDLabels = (Collection) generalInfoMap.get("limitIDLabels");

		ICommodityCollateral dealCollateral = getDealCollateral(map, securityID);
		if (dealCollateral != null) {
			IContract[] contractList = dealCollateral.getContracts();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					contractNoID.add(String.valueOf(contractList[i].getContractID()));
					contractNoValue.add(contractList[i].getMainContractNumber());
				}
			}
			if ((contractID != ICMSConstant.LONG_INVALID_VALUE) && !contractNoID.contains(String.valueOf(contractID))) {
				contractList = dealCollateral.getDeletedContracts();
				if (contractList != null) {
					boolean found = false;
					for (int i = 0; !found && (i < contractList.length); i++) {
						if (contractList[i].getContractID() == contractID) {
							found = true;
							contractNoID.add(String.valueOf(contractList[i].getContractID()));
							contractNoValue.add(contractList[i].getMainContractNumber());
						}
					}
				}
			}
		}

		String checkDealAmtAgainstCMVFlag = (String) map.get("checkDealAmtAgainstCMVFlag");
		if ((checkDealAmtAgainstCMVFlag == null) || checkDealAmtAgainstCMVFlag.equals("")) {
			result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_AGAINST_CMV_REQUIRED);
			result.put("checkDealAmtAgainstCMVMsg", null);
		}

		result.put("dealCollateral", dealCollateral);

		result.put("secID", secID);
		result.put("secValue", secValue);

		result.put("contractNoID", contractNoID);
		result.put("contractNoValue", contractNoValue);

		result.put("limitIDValues", limitIDValues);
		result.put("limitIDLabels", limitIDLabels);

		// add sub limit
		result.put("subLimitIDValues", (Collection) generalInfoMap.get("subLimitIDValues"));
		result.put("subLimitIDLabels", (Collection) generalInfoMap.get("subLimitIDLabels"));
		result.put("selectedSublimit", (ISubLimit) generalInfoMap.get("selectedSublimit"));

		// for checking deal amount against cmv amount

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private ICommodityCollateral getDealCollateral(HashMap map, long securityID) throws CommandProcessingException {
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");

		if (securityID != ICMSConstant.LONG_INVALID_VALUE) {
			if (dealCollateral == null) {
				try {
					dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy().getCollateral(securityID,
							true);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
			else if (dealCollateral.getCollateralID() != securityID) {
				try {
					dealCollateral = (ICommodityCollateral) CollateralProxyFactory.getProxy().getCollateral(securityID,
							true);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}
		return dealCollateral;
	}
}
