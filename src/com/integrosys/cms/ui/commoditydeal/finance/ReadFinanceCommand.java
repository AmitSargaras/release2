/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/ReadFinanceCommand.java,v 1.13 2004/09/08 02:45:16 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2004/09/08 02:45:16 $ Tag: $Name: $
 */

public class ReadFinanceCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "previous_event", "java.lang.String", SERVICE_SCOPE },
				{ "selectedWRList", "java.util.Collection", SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
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
				{ "financeObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
				{ "selectedWRList", "java.util.Collection", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		String from_event = (String) map.get("from_event");
		String previous_event = (String) map.get("previous_event");
		Collection selectedWRList = (ArrayList) map.get("selectedWRList");

		ICommodityDeal obj;
		if (from_event.equals(FinanceAction.EVENT_READ)
				&& ((previous_event == null) || !previous_event.equals(FinanceAction.EVENT_USER_PROCESS))) {
			obj = trxValue.getCommodityDeal();
		}
		else {
			obj = trxValue.getStagingCommodityDeal();
			if (FinanceAction.EVENT_PREPARE_UPDATE.equals(from_event)
					|| FinanceAction.EVENT_PROCESS_UPDATE.equals(from_event)
					|| FinanceAction.EVENT_PREPARE_ADD_DEAL.equals(from_event)) {
				Amount cmvAmt = obj.getCalculatedCMVAmt();
				obj.setCMV(cmvAmt);

				ICommodityCollateral col = (ICommodityCollateral) map.get("dealCollateral");
				ICollateralProxy proxy = CollateralProxyFactory.getProxy();
				if (col != null) {
					ICollateralParameter colParam = null;

					try {
						colParam = proxy.getCollateralParameter(col.getCollateralLocation(), col.getCollateralSubType()
								.getSubTypeCode());
					}
					catch (Exception e) {
						// ignore the exception when trying to get CRP.
					}

					double hedgingMargin = 0;
					if (obj.getHedgeContractID() > 0) {
						IHedgingContractInfo hedgingContract = CommodityDealUtil.getHedgingContractByID(col, obj
								.getHedgeContractID());
						if ((hedgingContract != null) && (hedgingContract.getMargin() > 0)) {
							hedgingMargin = hedgingContract.getMargin();
						}
					}
					double crp = 0;
					if (colParam != null) {
						crp = colParam.getThresholdPercent();
					}
					Amount fsvAmt = obj.getCalculatedFSVAmt(crp, hedgingMargin);
					obj.setFSV(fsvAmt);
				}
				trxValue.setStagingCommodityDeal(obj);
				result.put("commodityDealTrxValue", trxValue);
			}
		}

		if ((selectedWRList == null) || selectedWRList.isEmpty()) {
			selectedWRList = new ArrayList();
			if (obj.getReceiptReleases() != null) {
				IReceiptRelease[] releaseList = obj.getReceiptReleases();
				for (int i = 0; i < releaseList.length; i++) {
					if (releaseList[i].getSettleWarehouseReceipts() != null) {
						ISettleWarehouseReceipt[] settleWRList = releaseList[i].getSettleWarehouseReceipts();
						for (int j = 0; j < settleWRList.length; j++) {
							selectedWRList.add(String.valueOf(settleWRList[j].getWarehouseReceipt().getRefID()));
						}
					}
				}
			}
			result.put("selectedWRList", selectedWRList);
		}

		result.put("financeObj", obj);

		result.put("previous_event", previous_event);
		result.put("tab", CommodityDealConstant.TAB_PS_HEDGING);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
