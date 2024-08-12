/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/SetCommodityDealData.java,v 1.12 2006/01/16 07:18:04 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseDetails;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.ISalesDetails;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/01/16 07:18:04 $ Tag: $Name: $
 */
public class SetCommodityDealData {
	public static ICommodityDeal setCommodityDealCcy(ICommodityDeal dealObj, String currencyCode) {

		ISettlement[] settlementList = dealObj.getSettlements();
		if (settlementList != null) {
			for (int i = 0; i < settlementList.length; i++) {
				ISettlement tmpSettle = settlementList[i];
				Amount amt = tmpSettle.getPaymentAmt();
				amt.setCurrencyCode(currencyCode);
				tmpSettle.setPaymentAmt(amt);
				settlementList[i] = tmpSettle;
			}
		}
		dealObj.setSettlements(settlementList);

		return dealObj;
	}

	// Method modified by Pratheepa on 16/01/2006 to include check for
	// DOC_TYPE_WAREHOUSE_RECEIPT_N also.
	public static ICommodityDeal setCommodityUOM(ICommodityDeal dealObj, UOMWrapper marketUOM, UOMWrapper contractUOM) {
		if (marketUOM != null) {
			if (dealObj.getIsAnyWRTitleDoc()) {
				boolean foundWR = false;
				ICommodityTitleDocument[] titleDocList = dealObj.getTitleDocsLatest();
				if (titleDocList != null) {
					for (int i = 0; !foundWR && (i < titleDocList.length); i++) {
						if ((titleDocList[i].getTitleDocType().getName()
								.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
								|| (titleDocList[i].getTitleDocType().getName()
										.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
							foundWR = true;
							IWarehouseReceipt[] receiptList = titleDocList[i].getWarehouseReceipts();
							if (receiptList != null) {
								for (int j = 0; j < receiptList.length; j++) {
									Quantity qty = receiptList[j].getQuantity();
									if (qty != null) {
										qty = new Quantity(qty.getQuantity(), contractUOM);
										receiptList[j].setQuantity(qty);
									}
								}
							}
							titleDocList[i].setWarehouseReceipts(receiptList);
						}
					}
				}
				if (foundWR) {
					dealObj.setTitleDocsLatest(titleDocList);
				}
				else {
					titleDocList = dealObj.getTitleDocsHistory();
					if (titleDocList != null) {
						for (int i = 0; !foundWR && (i < titleDocList.length); i++) {
							if ((titleDocList[i].getTitleDocType().getName()
									.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
									|| (titleDocList[i].getTitleDocType().getName()
											.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
								foundWR = true;
								IWarehouseReceipt[] receiptList = titleDocList[i].getWarehouseReceipts();
								if (receiptList != null) {
									for (int j = 0; j < receiptList.length; j++) {
										Quantity qty = receiptList[j].getQuantity();
										if (qty != null) {
											qty = new Quantity(qty.getQuantity(), contractUOM);
											receiptList[j].setQuantity(qty);
										}
									}
								}
								titleDocList[i].setWarehouseReceipts(receiptList);
							}
						}
					}
					dealObj.setTitleDocsHistory(titleDocList);
				}
			}

			IFinancingDoc[] finDocList = dealObj.getFinancingDocs();
			if (finDocList != null) {
				for (int i = 0; i < finDocList.length; i++) {
					IFinancingDoc tmpFinDoc = finDocList[i];
					if (tmpFinDoc.getOrderQuantity() != null) {
						Quantity tmpQty = tmpFinDoc.getOrderQuantity();
						tmpFinDoc.setOrderQuantity(new Quantity(tmpQty.getQuantity(), contractUOM));
					}
					finDocList[i] = tmpFinDoc;
				}
			}

			IReceiptRelease[] releaseList = dealObj.getReceiptReleases();
			if (releaseList != null) {
				for (int i = 0; i < releaseList.length; i++) {
					IReceiptRelease tmpSettle = releaseList[i];
					if (tmpSettle.getReleasedQty() != null) {
						Quantity tmpQty = tmpSettle.getReleasedQty();
						tmpSettle.setReleasedQty(new Quantity(tmpQty.getQuantity(), contractUOM));
					}
					ISettleWarehouseReceipt[] settleWRList = tmpSettle.getSettleWarehouseReceipts();
					if (settleWRList != null) {
						for (int j = 0; j < settleWRList.length; j++) {
							ISettleWarehouseReceipt tempWR = settleWRList[j];
							Quantity tmpQty = tempWR.getReleasedQty();
							if (tmpQty != null) {
								tempWR.setReleasedQty(new Quantity(tmpQty.getQuantity(), contractUOM));
							}
							settleWRList[j] = tempWR;
						}
					}
					tmpSettle.setSettleWarehouseReceipts(settleWRList);
					releaseList[i] = tmpSettle;
				}
			}
			dealObj.setReceiptReleases(releaseList);

			IPurchaseAndSalesDetails purchaseSale = dealObj.getPurchaseAndSalesDetails();
			if (purchaseSale != null) {
				ISalesDetails sales = purchaseSale.getSalesDetails();
				if ((sales != null) && (sales.getQuantity() != null)) {
					Quantity tmpQty = sales.getQuantity();
					sales.setQuantity(new Quantity(tmpQty.getQuantity(), contractUOM));
				}
				purchaseSale.setSalesDetails(sales);
				IPurchaseDetails purchase = purchaseSale.getPurchaseDetails();
				if ((purchase != null) && (purchase.getQuantity() != null)) {
					Quantity tmpQty = purchase.getQuantity();
					purchase.setQuantity(new Quantity(tmpQty.getQuantity(), contractUOM));
				}
				purchaseSale.setPurchaseDetails(purchase);
			}
			dealObj.setPurchaseAndSalesDetails(purchaseSale);
		}
		if (contractUOM != null) {
			if (dealObj.getHedgeQuantity() != null) {
				Quantity tmpQty = dealObj.getHedgeQuantity();
				dealObj.setHedgeQuantity(new Quantity(tmpQty.getQuantity(), contractUOM));
			}
		}
		return dealObj;
	}

	public static ICommodityDeal setProfileInfo(ICommodityDeal dealObj, IProfile profile) {
		if ((profile != null)
				&& (dealObj.getContractPriceType() != null)
				&& (dealObj.getContractPriceType().equals(PriceType.EOD_PRICE) || dealObj.getContractPriceType()
						.equals(PriceType.FLOATING_FUTURES_PRICE))) {
			dealObj.setActualPrice(profile.getUnitPrice());
			dealObj.setActualMarketPriceDate(profile.getUnitPriceDate());
			dealObj.setContractRIC(profile.getReuterSymbol());
		}
		return dealObj;
	}
}
