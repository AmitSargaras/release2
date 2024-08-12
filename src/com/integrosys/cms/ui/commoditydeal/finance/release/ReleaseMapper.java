/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/ReleaseMapper.java,v 1.4 2006/05/31 02:58:59 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBSettleWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/05/31 02:58:59 $ Tag: $Name: $
 */

public class ReleaseMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		ReleaseForm aForm = (ReleaseForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		ICommodityDeal dealObj = trxValue.getCommodityDeal();
		int index = Integer.parseInt((String) inputs.get("indexID"));
		IReceiptRelease[] releaseArr = trxValue.getStagingCommodityDeal().getReceiptReleases();

		OBReceiptRelease obToChange;
		if (index == -1) {
			obToChange = new OBReceiptRelease();
		}
		else {
			try {
				obToChange = (OBReceiptRelease) AccessorUtil.deepClone(releaseArr[index]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		String[] selectedWarehouse = aForm.getSelectedWarehouseRec();
		String[] receiptNo = aForm.getReceiptNo();
		String[] qtyToRelease = aForm.getQtyToReleased();
		List receiptNoList = new ArrayList();
		if (receiptNo != null) {
			receiptNoList = Arrays.asList(receiptNo);
		}

		if ((selectedWarehouse != null) && (dealObj != null)) {
			HashMap warehouseReceiptMap = new HashMap();
			ICommodityTitleDocument[] titleDocList = dealObj.getTitleDocsAll();
			if (dealObj.getIsAnyWRTitleDoc() && (titleDocList != null)) {
				// Modified by Pratheepa 0n 31/05/2006vfor CR129
				// boolean found = false;
				// for (int i = 0; !found && i < titleDocList.length; i++) {
				for (int i = 0; i < titleDocList.length; i++) {
					if ((titleDocList[i].getTitleDocType().getName()
							.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
							|| (titleDocList[i].getTitleDocType().getName()
									.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
						// found = true;
						IWarehouseReceipt[] receiptList = titleDocList[i].getWarehouseReceipts();
						if (receiptList != null) {
							for (int j = 0; j < receiptList.length; j++) {
								warehouseReceiptMap.put(String.valueOf(receiptList[j].getRefID()), receiptList[j]);
							}
						}
					}
				}
			}
			HashMap oldSettleReceiptMap = new HashMap();
			if (obToChange.getSettleWarehouseReceipts() != null) {
				ISettleWarehouseReceipt[] oldSettleReceipt = obToChange.getSettleWarehouseReceipts();
				for (int i = 0; i < oldSettleReceipt.length; i++) {
					if (oldSettleReceipt[i].getWarehouseReceipt() != null) {
						oldSettleReceiptMap.put(String.valueOf(oldSettleReceipt[i].getWarehouseReceipt().getRefID()),
								oldSettleReceipt[i]);
					}
				}
			}
			UOMWrapper uomUnit = null;
			if (dealObj.getContractQuantity() != null) {
				uomUnit = dealObj.getContractQuantity().getUnitofMeasure();
			}

			ISettleWarehouseReceipt[] newSettleReceipt = new ISettleWarehouseReceipt[selectedWarehouse.length];
			for (int i = 0; i < selectedWarehouse.length; i++) {
				DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<< HSHII: selected warehouse id: "
						+ selectedWarehouse[i]);
				ISettleWarehouseReceipt tempReceipt = (ISettleWarehouseReceipt) oldSettleReceiptMap
						.get(selectedWarehouse[i]);
				if (tempReceipt == null) {
					tempReceipt = new OBSettleWarehouseReceipt();
					tempReceipt.setWarehouseReceipt((IWarehouseReceipt) warehouseReceiptMap.get(selectedWarehouse[i]));
				}
				int receiptIndex = -1;
				BigDecimal releaseValue = new BigDecimal(0);
				if ((receiptIndex = receiptNoList.indexOf(selectedWarehouse[i])) >= 0) {
					DefaultLogger.debug(this, "<<<<<<<<<<< receiptIndex: " + receiptIndex + "\trelease qty: "
							+ qtyToRelease[receiptIndex]);
					releaseValue = UIUtil.mapStringToBigDecimal(qtyToRelease[receiptIndex]);
				}
				Quantity releaseQty = new Quantity(releaseValue, uomUnit);
				tempReceipt.setReleasedQty(releaseQty);
				newSettleReceipt[i] = tempReceipt;
			}
			obToChange.setSettleWarehouseReceipts(newSettleReceipt);
		}
		else {
			obToChange.setSettleWarehouseReceipts(null);
		}

		obToChange.setReleaseDate(compareDate(locale, obToChange.getReleaseDate(), aForm.getPartialReleaseDate()));
		Quantity totalReleaseQty = obToChange.getTotalReleasedQuantity();
		obToChange.setReleasedQty(totalReleaseQty);

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		ReleaseForm aForm = (ReleaseForm) cForm;

		HashMap releaseMap = (HashMap) obj;
		IReceiptRelease release = (IReceiptRelease) releaseMap.get("obj");
		Collection allReceipt = (Collection) releaseMap.get("allReceipt");
		Collection fullReleaseQtyList = (Collection) releaseMap.get("fullReleaseQtyList");

		Collection allWarehouseReceipt = new ArrayList();
		if (allReceipt != null) {
			Iterator itr = allReceipt.iterator();
			while (itr.hasNext()) {
				allWarehouseReceipt.add(itr.next());
			}
		}
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) inputs.get("commodityDealTrxValue");
		String from_event = (String) inputs.get("from_event");
		ICommodityDeal dealObj;
		if (from_event.equals(EVENT_READ)) {
			dealObj = trxValue.getCommodityDeal();
		}
		else {
			dealObj = trxValue.getStagingCommodityDeal();
			if (ReleaseAction.EVENT_PREPARE_UPDATE.equals(from_event)
					|| ReleaseAction.EVENT_PREPARE_ADD_DEAL.equals(from_event)
					|| ReleaseAction.EVENT_PROCESS_UPDATE.equals(from_event)) {
				int index = Integer.parseInt((String) inputs.get("indexID"));
				if (index > -1) {
					try {
						dealObj = (ICommodityDeal) AccessorUtil.deepClone(trxValue.getStagingCommodityDeal());
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					IReceiptRelease[] tempList = dealObj.getReceiptReleases();
					tempList[index] = release;
					dealObj.setReceiptReleases(tempList);
				}
				else {
					try {
						dealObj = (ICommodityDeal) AccessorUtil.deepClone(trxValue.getStagingCommodityDeal());
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					IReceiptRelease[] tempList = dealObj.getReceiptReleases();
					tempList = ReleaseUtil.addRelease(tempList, release);
					dealObj.setReceiptReleases(tempList);
				}
			}
		}

		if (dealObj.getActualQuantity() != null) {
			if (dealObj.getActualQuantity().getQuantity() != null) {
				try {
					aForm.setOriginalStock(UIUtil.formatNumber(dealObj.getActualQuantity().getQuantity(), 4, locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			if (dealObj.getActualQuantity().getUnitofMeasure() != null) {
				aForm.setActualDealUOM(dealObj.getActualQuantity().getUnitofMeasure().getLabel());
			}
		}

		Collection selectedReceipt = new ArrayList();
		ISettleWarehouseReceipt[] settleReceipt = release.getSettleWarehouseReceipts();
		String[] receiptNo = null;
		String[] actualQty = null;
		String[] qtyReleasedSoFar = null;
		String[] balanceQtyToBeRelease = null;
		String[] qtyToRelease = null;
		String[] balance = null;

		if (settleReceipt != null) {
			receiptNo = new String[settleReceipt.length];
			actualQty = new String[settleReceipt.length];
			qtyReleasedSoFar = new String[settleReceipt.length];
			balanceQtyToBeRelease = new String[settleReceipt.length];
			qtyToRelease = new String[settleReceipt.length];
			balance = new String[settleReceipt.length];
			BigDecimal[] totalRelSoFar = ReleaseUtil.getWRTotalQtyReleaseBySettle(dealObj.getReceiptReleases(), true,
					release);

			for (int i = 0; i < settleReceipt.length; i++) {
				if (settleReceipt[i].getWarehouseReceipt() != null) {
					selectedReceipt.add(String.valueOf(settleReceipt[i].getWarehouseReceipt().getRefID()));
					receiptNo[i] = String.valueOf(settleReceipt[i].getWarehouseReceipt().getRefID());
					BigDecimal actualQuantity = null;
					BigDecimal balanceQty = null;
					if (settleReceipt[i].getWarehouseReceipt().getQuantity() != null) {
						try {
							actualQuantity = settleReceipt[i].getWarehouseReceipt().getQuantity().getQuantity();
							actualQty[i] = UIUtil.formatNumber(actualQuantity, 4, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					try {
						qtyReleasedSoFar[i] = UIUtil.formatNumber(totalRelSoFar[i], 4, locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					if ((actualQuantity != null) && (totalRelSoFar[i] != null)) {
						BigDecimal balanceToRel = actualQuantity.subtract(totalRelSoFar[i]);
						balanceQty = balanceToRel;
						try {
							balanceQtyToBeRelease[i] = UIUtil.formatNumber(balanceToRel, 4, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					if ((settleReceipt[i].getReleasedQty() != null)
							&& (settleReceipt[i].getReleasedQty().getQuantity() != null)) {
						balanceQty = balanceQty.subtract(settleReceipt[i].getReleasedQty().getQuantity());
						try {
							qtyToRelease[i] = UIUtil.formatNumber(settleReceipt[i].getReleasedQty().getQuantity(), 4,
									locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
					if (balanceQty != null) {
						try {
							balance[i] = UIUtil.formatNumber(balanceQty, 4, locale);
						}
						catch (Exception e) {
							throw new MapperException(e.getMessage());
						}
					}
				}
			}
		}
		aForm.setReceiptNo(receiptNo);
		aForm.setActualQty(actualQty);
		aForm.setQtyReleasedSoFar(qtyReleasedSoFar);
		aForm.setBalanceQtyToBeReleased(balanceQtyToBeRelease);
		aForm.setQtyToReleased(qtyToRelease);
		aForm.setBalance(balance);

		Quantity totalQtyReleaseForDeal = dealObj.getTotalQuantityReleased();
		if (totalQtyReleaseForDeal != null) {
			try {
				aForm.setTotalQtyRelForDeal(UIUtil.formatNumber(totalQtyReleaseForDeal.getQuantity(), 4, locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}
		Quantity balanceQtyToRelease = dealObj.getBalanceDealQty();
		if (balanceQtyToRelease != null) {
			try {
				aForm.setTotalBalanceQtyToBeReleased(UIUtil.formatNumber(balanceQtyToRelease.getQuantity(), 4, locale));
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		aForm.setSelectedWarehouseRec((String[]) selectedReceipt.toArray(new String[0]));
		if (allWarehouseReceipt != null) {
			allWarehouseReceipt.removeAll(selectedReceipt);
			if (fullReleaseQtyList != null) {
				allWarehouseReceipt.removeAll(fullReleaseQtyList);
			}
			aForm.setWarehouseRec((String[]) allWarehouseReceipt.toArray(new String[0]));
		}

		aForm.setPartialReleaseDate(DateUtil.formatDate(locale, release.getReleaseDate()));
		if (release.getTotalReleasedQuantity() != null) {
			try {
				aForm.setQuantityRelease(UIUtil.formatNumber(release.getTotalReleasedQuantity().getQuantity(), 4,
						locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		else {
			aForm.setQuantityRelease("");
		}
		if (dealObj.getContractQuantity() != null) {
			aForm.setQuantityReleaseUOM(dealObj.getContractQuantity().getUnitofMeasure().getLabel());
		}
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, { "from_event", "java.lang.String", SERVICE_SCOPE }, });
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}
}
