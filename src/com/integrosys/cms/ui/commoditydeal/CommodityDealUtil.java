/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealUtil.java,v 1.13 2006/09/15 12:42:54 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.LimitDetailsComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.main.bus.profile.BuyerComparator;
import com.integrosys.cms.app.commodity.main.bus.profile.IBuyer;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.commodity.main.bus.profile.OBProfile;
import com.integrosys.cms.app.commodity.main.bus.profile.SupplierComparator;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitUtils;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/09/15 12:42:54 $ Tag: $Name: $
 */
public class CommodityDealUtil {
	public static boolean isNewTitleDoc(ICommodityDeal actualDeal, ICommodityTitleDocument titleDocObj) {
		if (actualDeal == null) {
			return true;
		}
		if (actualDeal.getTitleDocsAll() == null) {
			return true;
		}

		return !hasActualTitleDocByRefID(actualDeal.getTitleDocsAll(), titleDocObj.getRefID());
	}

	private static boolean hasActualTitleDocByRefID(ICommodityTitleDocument[] docList, long refID) {
		if (docList != null) {
			for (int i = 0; i < docList.length; i++) {
				if (refID == docList[i].getRefID()) {
					return true;
				}
			}
		}
		return false;
	}

	public static HashMap getCustomerLimit(HashMap colLimitMap) {
		HashMap newColLimitMap = new HashMap();
		Collection colList = colLimitMap.keySet();
		Iterator itr = colList.iterator();
		while (itr.hasNext()) {
			ICollateral col = (ICollateral) itr.next();
			ArrayList limitList = (ArrayList) colLimitMap.get(col);
			HashMap limitMap = new HashMap();
			if (limitList != null) {
				for (int i = 0; i < limitList.size(); i++) {
					limitMap.put(generateStrLmtID(limitList.get(i)), limitList.get(i));
				}
			}
			ICollateralLimitMap[] tempColLimitMap = col.getCurrentCollateralLimits();
			Collection newList = new ArrayList();
			if (tempColLimitMap != null) {
				for (int i = 0; i < tempColLimitMap.length; i++) {
					if (limitMap.containsKey(CollateralHelper.getColLimitMapLimitID(tempColLimitMap[i]))) {
						newList.add(tempColLimitMap[i]);
					}
				}
			}
			col.setCollateralLimits((ICollateralLimitMap[]) newList.toArray(new ICollateralLimitMap[0]));
			newColLimitMap.put(col, limitList);
		}
		return newColLimitMap;
	}

	public static String generateStrLmtID(Object limitObj) {
		if (limitObj instanceof ILimit) {
			return ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER + String.valueOf(((ILimit) limitObj).getLimitID());
		}
		if (limitObj instanceof ICoBorrowerLimit) {
			return ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER
					+ String.valueOf(((ICoBorrowerLimit) limitObj).getLimitID());
		}
		return null;
	}

	public static HashMap getGeneralInfoDropDownList(HashMap commodityLimitMap, long securityID, String limitID,
			boolean isCollateralPool, boolean isGeneralInfo, long subLimitID, boolean isSubLimitNeeded) {
		Collection secID = new ArrayList();
		Collection secValue = new ArrayList();
		Collection limitIDLabels = new ArrayList();
		Collection limitIDValues = new ArrayList();
		Collection subLimitIDLabels = new ArrayList();
		Collection subLimitIDValues = new ArrayList();
		ISubLimit selectedSL = null;
		HashMap returnMap = new HashMap();
		HashMap limitTypeMap = CommonDataSingleton
				.getCodeCategoryValueLabelMap(SLTUIConstants.CONSTANT_COMMODITY_CATEGORY_CODE);

		if (commodityLimitMap != null) {
			Collection commodityList = commodityLimitMap.keySet();
			Iterator itr = commodityList.iterator();
			while (itr.hasNext()) {
				ICommodityCollateral col = (ICommodityCollateral) itr.next();
				if (!col.getStatus().equals(ICMSConstant.STATE_DELETED)
						|| ((securityID != ICMSConstant.LONG_INVALID_VALUE) && (securityID == col.getCollateralID()))) {
					secID.add(String.valueOf(col.getCollateralID()));
					// Cynthia: Added for CR-145
					String secSubType = col.getCollateralSubType().getSubTypeName();
					secSubType = (secSubType == null) ? "" : secSubType;
					secValue.add(String.valueOf(col.getSCISecurityID() + " - " + secSubType));
					if ((securityID != ICMSConstant.LONG_INVALID_VALUE) && (securityID == col.getCollateralID())) {
						ICollateralLimitMap[] colLimitMapList = col.getCollateralLimits();
						HashMap limitMap = new HashMap();
						Collection limitList = (Collection) commodityLimitMap.get(col);
						if (limitList != null) {
							Iterator itr1 = limitList.iterator();
							while (itr1.hasNext()) {
								Object limitObj = itr1.next();
								// ILimit limit = (ILimit)itr1.next();
								limitMap.put(generateStrLmtID(limitObj), limitObj);
							}
						}
						List selectedLimit = new ArrayList();
						if (colLimitMapList != null) {
							for (int i = 0; i < colLimitMapList.length; i++) {
								String strLimitID = CollateralHelper.getColLimitMapLimitID(colLimitMapList[i]);
								String limitStatus = "";

								if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(colLimitMapList[i]
										.getCustomerCategory())) {
									ILimit limit = (ILimit) limitMap.get(strLimitID);
									limitStatus = limit.getLimitStatus();
								}
								else if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(colLimitMapList[i]
										.getCustomerCategory())) {
									ICoBorrowerLimit colmt = (ICoBorrowerLimit) limitMap.get(strLimitID);
									limitStatus = colmt.getStatus();
								}
								if ((!isEmptyStr(limitID) && limitID.equals(strLimitID))
										|| (!(isCollateralPool ^ isGeneralInfo)
												&& !limitStatus.equals(ICMSConstant.STATE_DELETED)
												&& !colLimitMapList[i].getSCIStatus().equals(
														ICMSConstant.HOST_STATUS_DELETE) && colLimitMapList[i]
												.getIsCollateralPool())) {
									selectedLimit.add(colLimitMapList[i]);
								}
								else if ((!isEmptyStr(limitID) && limitID.equals(strLimitID))
										|| (!(!isCollateralPool ^ isGeneralInfo)
												&& !limitStatus.equals(ICMSConstant.STATE_DELETED)
												&& !colLimitMapList[i].getSCIStatus().equals(
														ICMSConstant.HOST_STATUS_DELETE) && colLimitMapList[i]
												.getIsSpecificTrx())) {
									selectedLimit.add(colLimitMapList[i]);
								}
							}
						}
						if (selectedLimit != null) {
							ICollateralLimitMap[] tmpColLimit = (ICollateralLimitMap[]) selectedLimit
									.toArray(new ICollateralLimitMap[0]);
							Arrays.sort(tmpColLimit, new LimitDetailsComparator());
							selectedLimit = Arrays.asList(tmpColLimit);
						}
						if (selectedLimit != null) {
							for (int i = 0; i < selectedLimit.size(); i++) {
								ICollateralLimitMap collateralLimitMap = (ICollateralLimitMap) selectedLimit.get(i);
								String strLimitID = CollateralHelper.getColLimitMapLimitID(collateralLimitMap);
								limitIDValues.add(strLimitID);
								String prodDesc = CommonDataSingleton.getCodeCategoryLabelByValue(
										CategoryCodeConstant.PRODUCT_DESCRIPTION, ((ICollateralLimitMap) selectedLimit
												.get(i)).getLimitType());
								if (prodDesc == null) {
									prodDesc = "";
								}
								limitIDLabels.add(String.valueOf(CollateralHelper
										.getDisplayColLimitMapLimitID(collateralLimitMap))
										+ " - " + prodDesc);
								if (isSubLimitNeeded && !isEmptyStr(limitID) && limitID.equals(strLimitID)) {
									ISubLimit[] slArray = collateralLimitMap.getSubLimit();
									DefaultLogger.debug(CommodityDealUtil.class.getName(), "Len of SubLimit Array : "
											+ (slArray == null ? 0 : slArray.length));
									if (slArray != null) {
										for (int index = 0; index < slArray.length; index++) {
											subLimitIDValues.add(String.valueOf(slArray[index].getSubLimitID()));
											ISubLimitType slt = (ISubLimitType) SubLimitUtils.getSLTMap().get(
													slArray[index].getSubLimitType());
											if (slt != null) {
												StringBuffer sltNameBuffer = new StringBuffer();
												sltNameBuffer.append(slt.getSubLimitType());
												sltNameBuffer.append("(");
												sltNameBuffer.append(limitTypeMap.get(String
														.valueOf(slt.getLimitType())));
												sltNameBuffer.append(")");
												subLimitIDLabels.add(sltNameBuffer);
											}
											if ((subLimitID != ICMSConstant.LONG_INVALID_VALUE)
													&& (subLimitID == slArray[index].getSubLimitID())) {
												selectedSL = slArray[index];
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		returnMap.put("secID", secID);
		returnMap.put("secValue", secValue);
		returnMap.put("limitIDValues", limitIDValues);
		returnMap.put("limitIDLabels", limitIDLabels);
		// add sub limit
		if (isSubLimitNeeded) {
			returnMap.put("subLimitIDValues", subLimitIDValues);
			returnMap.put("subLimitIDLabels", subLimitIDLabels);
			returnMap.put("selectedSublimit", selectedSL);
		}

		return returnMap;
	}

	public static HashMap getGeneralInfoDropDownList(HashMap commodityLimitMap, long securityID, String limitID,
			boolean isCollateralPool, boolean isGeneralInfo) {
		return getGeneralInfoDropDownList(commodityLimitMap, securityID, limitID, isCollateralPool, isGeneralInfo,
				ICMSConstant.LONG_INVALID_VALUE, false);
	}

	public static HashMap getProfileDropDownList(ICommodityCollateral dealCollateral, long profileID, String productType) {
		IApprovedCommodityType[] approved = dealCollateral.getApprovedCommodityTypes();
		boolean foundProfile = false;
		Collection productTypeID = new ArrayList();
		Collection productTypeValue = new ArrayList();
		Collection productSubTypeID = new ArrayList();
		Collection productSubTypeValue = new ArrayList();
		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		HashMap returnMap = new HashMap();

		if (approved != null) {
			for (int i = 0; i < approved.length; i++) {
				IProfile profile = approved[i].getProfile();
				if (profile != null) {
					if (profile.getProfileID() == profileID) {
						foundProfile = true;
					}
				}
				if (!productTypeID.contains(profile.getProductType())) {
					productTypeID.add(profile.getProductType());
					productTypeValue.add(categoryList.getCommProductItem(profile.getCategory(), profile
							.getProductType()));
				}
				if ((productType != null) && productType.equals(profile.getProductType())) {
					if (!productSubTypeID.contains(String.valueOf(profile.getProfileID()))) {
						productSubTypeID.add(String.valueOf(profile.getProfileID()));
						productSubTypeValue.add(profile.getProductSubType());
					}
				}
			}
		}
		returnMap.put("foundProfile", new Boolean(foundProfile));
		returnMap.put("productTypeID", productTypeID);
		returnMap.put("productTypeValue", productTypeValue);
		returnMap.put("productSubTypeID", productSubTypeID);
		returnMap.put("productSubTypeValue", productSubTypeValue);

		return returnMap;
	}

	public static HashMap getUOMDropDownListByProfile(long profileID) throws Exception {
		HashMap returnMap = new HashMap();
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			UOMWrapper[] uomList = proxy.getUnitofMeasure(profileID);
			Collection uomID = new ArrayList();
			Collection uomValue = new ArrayList();
			if (uomList != null) {
				for (int i = 0; i < uomList.length; i++) {
					uomID.add(uomList[i].getID());
					uomValue.add(uomList[i].getLabel());
				}
			}
			returnMap.put("uomID", uomID);
			returnMap.put("uomValue", uomValue);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return returnMap;
	}

	public static HashMap getUOMMap(long profileID) throws MapperException {
		HashMap uomMap = new HashMap();
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			UOMWrapper[] uomList = proxy.getUnitofMeasure(profileID);
			if (uomList != null) {
				for (int i = 0; i < uomList.length; i++) {
					uomMap.put(uomList[i].getID(), uomList[i]);
				}
			}
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}
		return uomMap;
	}

	public static IHedgingContractInfo getHedgingContractByID(ICommodityCollateral col, long hedgingContractID) {
		if (col != null) {
			IHedgingContractInfo[] contractList = col.getHedgingContractInfos();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getHedgingContractInfoID() == hedgingContractID) {
						return contractList[i];
					}
				}
			}
			contractList = col.getDeletedHedgeContractInfos();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getHedgingContractInfoID() == hedgingContractID) {
						return contractList[i];
					}
				}
			}
		}
		return null;
	}

	public static IContract getContractByID(ICommodityCollateral col, long contractID) {
		if (col != null) {
			IContract[] contractList = col.getContracts();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getContractID() == contractID) {
						return contractList[i];
					}
				}
			}
			contractList = col.getDeletedContracts();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getContractID() == contractID) {
						return contractList[i];
					}
				}
			}
		}
		return null;
	}

	public static Quantity getTotalWRQuantity(ICommodityDeal dealObj, ICommodityTitleDocument titleDoc) {
		if (!(dealObj.getIsAnyWRTitleDoc()) && (titleDoc == null)) {
			return null; // no warehouse receipt title doc
		}

		ICommodityTitleDocument[] titleDocList = dealObj.getTitleDocsAll();
		ICommodityTitleDocument[] titleDocLatestList = dealObj.getTitleDocsLatest();
		Quantity totalQty = null;

		boolean hasCheckWRNegotiable = false;
		boolean hasCheckWRNonNegotiable = false;

		if (titleDoc != null) {
			totalQty = addWarehouseReceiptsQuantity(totalQty, titleDoc);
			if (isWarehouseReceiptNegotiable(titleDoc.getTitleDocType().getName())) {
				hasCheckWRNegotiable = true;
			}
			else {
				hasCheckWRNonNegotiable = true;
			}
		}

		HashMap resultMap = getTitleWRQuantity(titleDocLatestList, totalQty, hasCheckWRNegotiable,
				hasCheckWRNonNegotiable);
		totalQty = (Quantity) resultMap.get("totalQty");
		hasCheckWRNegotiable = ((Boolean) resultMap.get("hasCheckWRNegotiable")).booleanValue();
		hasCheckWRNonNegotiable = ((Boolean) resultMap.get("hasCheckWRNonNegotiable")).booleanValue();

		if (!hasCheckWRNegotiable || !hasCheckWRNonNegotiable) {
			resultMap = getTitleWRQuantity(titleDocList, totalQty, hasCheckWRNegotiable, hasCheckWRNonNegotiable);
			totalQty = (Quantity) resultMap.get("totalQty");
			hasCheckWRNegotiable = ((Boolean) resultMap.get("hasCheckWRNegotiable")).booleanValue();
			hasCheckWRNonNegotiable = ((Boolean) resultMap.get("hasCheckWRNonNegotiable")).booleanValue();
		}

		return totalQty;
	}

	private static HashMap getTitleWRQuantity(ICommodityTitleDocument[] titleDocList, Quantity totalQty,
			boolean hasCheckWRNegotiable, boolean hasCheckWRNonNegotiable) {
		if (titleDocList != null) {
			DefaultLogger.debug(CommodityDealUtil.class.getName(), "titleDocList.length: " + titleDocList.length);
			for (int i = 0; i < titleDocList.length; i++) {
				DefaultLogger.debug(CommodityDealUtil.class.getName(), "titleDocTypeName: "
						+ titleDocList[i].getTitleDocType().getName());
				if ((titleDocList[i].getTitleDocType().getName()
						.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
						|| (titleDocList[i].getTitleDocType().getName()
								.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N))) {
					if (isWarehouseReceiptNegotiable(titleDocList[i].getTitleDocType().getName())
							&& !hasCheckWRNegotiable) {
						totalQty = addWarehouseReceiptsQuantity(totalQty, titleDocList[i]);
						hasCheckWRNegotiable = true;
					}
					else if (!isWarehouseReceiptNegotiable(titleDocList[i].getTitleDocType().getName())
							&& !hasCheckWRNonNegotiable) {
						totalQty = addWarehouseReceiptsQuantity(totalQty, titleDocList[i]);
						hasCheckWRNonNegotiable = true;
					}
				}
			}
		}
		HashMap returnMap = new HashMap();
		returnMap.put("totalQty", totalQty);
		returnMap.put("hasCheckWRNegotiable", new Boolean(hasCheckWRNegotiable));
		returnMap.put("hasCheckWRNonNegotiable", new Boolean(hasCheckWRNonNegotiable));

		return returnMap;
	}

	private static boolean isWarehouseReceiptNegotiable(String name) {
		if (name.equals(CommodityDealConstant.DOC_TYPE_WAREHOUSE_RECEIPT)) {
			return true;
		}
		return false;
	}

	private static Quantity addWarehouseReceiptsQuantity(Quantity totalQty, ICommodityTitleDocument titleDoc) {
		IWarehouseReceipt[] rcpts = titleDoc.getWarehouseReceipts();
		if (rcpts != null) {
			for (int j = 0; j < rcpts.length; j++) {
				Quantity qty = null;
				if (rcpts[j].getQuantity() != null) {
					qty = new Quantity(rcpts[j].getQuantity().getQuantity(), rcpts[j].getQuantity().getUnitofMeasure());
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
		}
		return totalQty;
	}

	public static IProfile sortBuyerSupplier(IProfile profile) {
		SupplierComparator s = new SupplierComparator();
		BuyerComparator b = new BuyerComparator();

		if (profile != null) {
			ISupplier[] supplier = profile.getSuppliers();
			if (supplier != null) {
				Arrays.sort(supplier, s);
			}
			IBuyer[] buyer = profile.getBuyers();
			if (buyer != null) {
				Arrays.sort(buyer, b);
			}
			((OBProfile) profile).setSuppliers(supplier);
			((OBProfile) profile).setBuyers(buyer);
		}
		return profile;
	}

	public static String generateLimitIDByDeal(ICommodityDeal dealObj) {
		if (dealObj == null) {
			return null;
		}
		if (dealObj.getCustomerCategory() == null) {
			return null;
		}
		if (dealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER)) {
			if (dealObj.getLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
				return ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER + String.valueOf(dealObj.getLimitID());
			}
		}
		if (dealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER)) {
			if (dealObj.getCoBorrowerLimitID() != ICMSConstant.LONG_INVALID_VALUE) {
				return ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER + String.valueOf(dealObj.getCoBorrowerLimitID());
			}
		}
		return null;
	}

	public static String getLimitLabelByLimitType(Object limit, ICollateral colObj) {
		String prodDesc = getProdDescByColLimitMap(colObj, generateStrLmtID(limit));
		if (prodDesc == null) {
			prodDesc = "";
		}

		if (limit instanceof ILimit) {
			return (((ILimit) limit).getLimitRef() + " - " + prodDesc);
		}
		if (limit instanceof ICoBorrowerLimit) {
			return (((ICoBorrowerLimit) limit).getLimitRef() + " - " + prodDesc);
		}
		return "";
	}

	private static String getProdDescByColLimitMap(ICollateral colObj, String limitID) {
		ICollateralLimitMap[] colLimitMaps = colObj.getCurrentCollateralLimits();

		if (colLimitMaps != null) {
			for (int i = 0; i < colLimitMaps.length; i++) {
				String strLimitID = CollateralHelper.getColLimitMapLimitID(colLimitMaps[i]);
				if (strLimitID.equals(limitID)) {
					return CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.PRODUCT_DESCRIPTION,
							colLimitMaps[i].getLimitType());
				}
			}
		}
		return null;
	}

	private static boolean isEmptyStr(String str) {
		if (str == null) {
			return true;
		}
		if (str.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
