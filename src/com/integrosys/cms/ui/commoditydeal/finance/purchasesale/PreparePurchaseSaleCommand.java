/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/PreparePurchaseSaleCommand.java,v 1.4 2004/08/12 09:59:54 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.ISalesDetails;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/12 09:59:54 $ Tag: $Name: $
 */

public class PreparePurchaseSaleCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "supplierID", "java.util.Collection", REQUEST_SCOPE },
				{ "supplierValue", "java.util.Collection", REQUEST_SCOPE },
				{ "buyerID", "java.util.Collection", REQUEST_SCOPE },
				{ "buyerValue", "java.util.Collection", REQUEST_SCOPE }, });
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

		ArrayList supplierID = new ArrayList();
		ArrayList supplierValue = new ArrayList();
		ArrayList buyerID = new ArrayList();
		ArrayList buyerValue = new ArrayList();
		IProfile profile = (IProfile) map.get("profileService");
		if (profile != null) {
			ISupplier[] supplier = profile.getSuppliers();
			if (supplier != null) {
				for (int j = 0; j < supplier.length; j++) {
					supplierID.add(String.valueOf(supplier[j].getSupplierID()));
					supplierValue.add(supplier[j].getName());
				}
			}
			IBuyer[] buyer = profile.getBuyers();
			if (buyer != null) {
				for (int j = 0; j < buyer.length; j++) {
					buyerID.add(String.valueOf(buyer[j].getBuyerID()));
					buyerValue.add(buyer[j].getName());
				}
			}
		}

		String from_event = (String) map.get("from_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		ICommodityDeal dealObj;
		if (from_event.equals(PurchaseSaleAction.EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}
		else {
			dealObj = trxValue.getStagingCommodityDeal();
		}

		if (dealObj != null) {
			if (dealObj.getPurchaseAndSalesDetails() != null) {
				IPurchaseDetails purchase = dealObj.getPurchaseAndSalesDetails().getPurchaseDetails();
				ISalesDetails sales = dealObj.getPurchaseAndSalesDetails().getSalesDetails();
				if ((purchase != null) && (purchase.getSupplier() != null)) {
					if (supplierValue.contains(purchase.getSupplier().getName())) {
						int index = supplierValue.indexOf(purchase.getSupplier().getName());
						supplierValue.remove(index);
						supplierID.remove(index);
					}
					if (!supplierID.contains(String.valueOf(purchase.getSupplier().getSupplierID()))) {
						supplierID.add(String.valueOf(purchase.getSupplier().getSupplierID()));
						supplierValue.add(purchase.getSupplier().getName());
					}
				}
				if ((sales != null) && (sales.getBuyer() != null)) {
					if (buyerValue.contains(sales.getBuyer().getName())) {
						int index = buyerValue.indexOf(sales.getBuyer().getName());
						buyerValue.remove(index);
						buyerID.remove(index);
					}

					if (!buyerID.contains(String.valueOf(sales.getBuyer().getBuyerID()))) {
						buyerID.add(String.valueOf(sales.getBuyer().getBuyerID()));
						buyerValue.add(sales.getBuyer().getName());
					}
				}
			}
		}
		supplierID.add(ICMSConstant.NOT_AVAILABLE_VALUE);
		supplierValue.add("Others");
		buyerID.add(ICMSConstant.NOT_AVAILABLE_VALUE);
		buyerValue.add("Others");
		result.put("supplierID", supplierID);
		result.put("supplierValue", supplierValue);
		result.put("buyerID", buyerID);
		result.put("buyerValue", buyerValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
