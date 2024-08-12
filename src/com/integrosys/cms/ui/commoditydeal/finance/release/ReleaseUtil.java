/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/ReleaseUtil.java,v 1.3 2006/02/20 07:06:12 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettleWarehouseReceipt;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/02/20 07:06:12 $ Tag: $Name: $
 */
public class ReleaseUtil {
	private static Quantity getTotalQuantityReleasedForDeal(IReceiptRelease[] releases) {
		try {
			if ((releases == null) || (releases.length == 0)) {
				return null;
			}

			Quantity totalQty = null;
			for (int i = 0; i < releases.length; i++) {
				Quantity qty = null;
				if (releases[i].getTotalReleasedQuantity() != null) {
					qty = new Quantity(releases[i].getTotalReleasedQuantity().getQuantity(), releases[i]
							.getTotalReleasedQuantity().getUnitofMeasure());
				}

				if (totalQty == null) {
					totalQty = qty;
				}
				else {
					if (qty != null) {
						totalQty = new Quantity(totalQty.getQuantity().add(qty.getQuantity()), totalQty
								.getUnitofMeasure());
					}
				}
			}
			return totalQty;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total quantity released! " + e.toString());
		}
	}

	public static boolean isExceedActualQty(IReceiptRelease[] releases, Quantity actualQty) {
		Quantity totalSettleQty = getTotalQuantityReleasedForDeal(releases);
		if (((actualQty == null) || (actualQty.getQuantity() == null)) && (totalSettleQty != null)
				&& (totalSettleQty.getQuantity() != null)) {
			return true;
		}
		if ((actualQty != null) && (actualQty.getQuantity() != null) && (totalSettleQty != null)
				&& (totalSettleQty.getQuantity() != null)) {
			if (totalSettleQty.getQuantity().compareTo(actualQty.getQuantity()) > 0) {
				return true;
			}
		}
		return false;
	}

	// Method modified by Pratheepa on 16.01.2006 while fixing R1.5 CR129.Added
	// check for DOC_TYPE_WAREHOUSE_RECEIPT_N also.
	public static HashMap getWarehouseReceiptMap(ICommodityDeal dealObj) {
		HashMap warehouseReceiptMap = new HashMap();
		ReleaseUtil ru = new ReleaseUtil();
		if (dealObj != null) {
			ICommodityTitleDocument[] titleDocList = dealObj.getTitleDocsAll();
			DefaultLogger.debug(ru, "commDeal" + titleDocList.length);
			if (dealObj.getIsAnyWRTitleDoc() && (titleDocList != null)) {
				boolean found = false;
				// for (int i = 0; !found && i < titleDocList.length; i++) {
				for (int i = 0; i < titleDocList.length; i++) {
					if ((titleDocList[i].getTitleDocType().getName()
							.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
							|| (titleDocList[i].getTitleDocType().getName()
									.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
						found = true;
						IWarehouseReceipt[] receiptList = titleDocList[i].getWarehouseReceipts();
						if (receiptList != null) {
							for (int j = 0; j < receiptList.length; j++) {
								warehouseReceiptMap.put(String.valueOf(receiptList[j].getRefID()), receiptList[j]
										.getOrigReceiptNo());
							}
						}
					}
				}
			}
		}
		return warehouseReceiptMap;
	}

	public static BigDecimal[] getWRTotalQtyReleaseBySettle(IReceiptRelease[] releaseList, boolean isExclude,
			IReceiptRelease releaseObj) {
		BigDecimal[] qtyReleaseList = null;
		if ((releaseObj != null) && (releaseObj.getSettleWarehouseReceipts() != null)) {
			ISettleWarehouseReceipt[] settleWRList = releaseObj.getSettleWarehouseReceipts();
			qtyReleaseList = new BigDecimal[settleWRList.length];
			ArrayList receiptList = new ArrayList();
			for (int i = 0; i < settleWRList.length; i++) {
				receiptList.add(String.valueOf(settleWRList[i].getWarehouseReceipt().getRefID()));
				qtyReleaseList[i] = new BigDecimal(0);
			}
			if (releaseList != null) {
				for (int i = 0; i < releaseList.length; i++) {
					if (releaseList[i].getSettleWarehouseReceipts() != null) {
						ISettleWarehouseReceipt[] tempList = releaseList[i].getSettleWarehouseReceipts();
						for (int j = 0; j < tempList.length; j++) {
							int index = -1;
							if ((index = receiptList.indexOf(String.valueOf(tempList[j].getWarehouseReceipt()
									.getRefID()))) >= 0) {
								BigDecimal bd = qtyReleaseList[index];
								if ((tempList[j].getReleasedQty() != null)
										&& (tempList[j].getReleasedQty().getQuantity() != null)) {
									bd = bd.add(tempList[j].getReleasedQty().getQuantity());
									qtyReleaseList[index] = bd;
								}
							}
						}
					}
				}
			}
			if (isExclude) {
				for (int i = 0; i < settleWRList.length; i++) {
					if ((qtyReleaseList[i] != null) && (settleWRList[i].getReleasedQty() != null)
							&& (settleWRList[i].getReleasedQty().getQuantity() != null)) {
						qtyReleaseList[i] = qtyReleaseList[i].subtract(settleWRList[i].getReleasedQty().getQuantity());
					}
				}
			}
		}
		return qtyReleaseList;
	}

	public static Collection getFullReleasedQtyWarehouseReceipt(ICommodityDeal dealObj) {
		Collection fullReleasedQtyList = new ArrayList();
		HashMap releaseMap = new HashMap();
		if (dealObj != null) {
			if (dealObj.getReceiptReleases() != null) {
				IReceiptRelease[] releaseList = dealObj.getReceiptReleases();
				for (int i = 0; i < releaseList.length; i++) {
					ISettleWarehouseReceipt[] settleWRList = releaseList[i].getSettleWarehouseReceipts();
					if (settleWRList != null) {
						for (int j = 0; j < settleWRList.length; j++) {
							BigDecimal[] bdArr = (BigDecimal[]) releaseMap.get(String.valueOf(settleWRList[j]
									.getWarehouseReceipt().getRefID()));
							if (bdArr == null) {
								bdArr = new BigDecimal[2];
								if ((settleWRList[j].getWarehouseReceipt() != null)
										&& (settleWRList[j].getWarehouseReceipt().getQuantity() != null)) {
									bdArr[0] = settleWRList[j].getWarehouseReceipt().getQuantity().getQuantity();
								}
								if (settleWRList[j].getReleasedQty() != null) {
									bdArr[1] = settleWRList[j].getReleasedQty().getQuantity();
								}
							}
							else {
								BigDecimal temp = bdArr[1];
								if (temp == null) {
									if (settleWRList[j].getReleasedQty() != null) {
										temp = settleWRList[j].getReleasedQty().getQuantity();
									}
								}
								else {
									if ((settleWRList[j].getReleasedQty() != null)
											&& (settleWRList[j].getReleasedQty().getQuantity() != null)) {
										temp = temp.add(settleWRList[j].getReleasedQty().getQuantity());
									}
								}
								bdArr[1] = temp;
							}
							releaseMap.put(String.valueOf(settleWRList[j].getWarehouseReceipt().getRefID()), bdArr);
						}
					}
				}
				Collection keySet = releaseMap.keySet();
				Iterator itr = keySet.iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					BigDecimal[] bdArr = (BigDecimal[]) releaseMap.get(key);
					if ((bdArr != null) && (bdArr[0] != null) && (bdArr[1] != null)
							&& (bdArr[0].compareTo(bdArr[1]) <= 0)) {
						fullReleasedQtyList.add(key);
					}
				}
			}
		}
		return fullReleasedQtyList;
	}

	public static IReceiptRelease[] addRelease(IReceiptRelease[] existingArray, IReceiptRelease obj) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IReceiptRelease[] newArray = new IReceiptRelease[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = obj;

		return newArray;
	}

	public static BigDecimal getQtyReleasedByWarehouseReceipt(ICommodityDeal dealObj, ISettleWarehouseReceipt receipt,
			boolean isExclude) {
		BigDecimal bd = new BigDecimal(0);
		if (receipt != null) {
			long warehouseRef = receipt.getWarehouseReceipt().getRefID();

			if (dealObj != null) {
				IReceiptRelease[] receiptList = dealObj.getReceiptReleases();
				if (receiptList != null) {
					for (int i = 0; i < receiptList.length; i++) {
						ISettleWarehouseReceipt[] settleWRList = receiptList[i].getSettleWarehouseReceipts();
						if (settleWRList != null) {
							for (int j = 0; j < settleWRList.length; j++) {
								if (settleWRList[j].getWarehouseReceipt().getRefID() == warehouseRef) {
									if ((settleWRList[j].getReleasedQty() != null)
											&& (settleWRList[j].getReleasedQty().getQuantity() != null)) {
										bd = bd.add(settleWRList[j].getReleasedQty().getQuantity());
									}
								}
							}
						}
					}
				}
			}
			if (isExclude) {
				if ((receipt.getReleasedQty() != null) && (receipt.getReleasedQty().getQuantity() != null)) {
					bd = bd.subtract(receipt.getReleasedQty().getQuantity());
				}
			}
		}
		return bd;
	}
}
