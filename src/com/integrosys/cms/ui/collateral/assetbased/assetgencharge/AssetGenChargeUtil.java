/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/AssetGenChargeUtil.java,v 1.61 2006/09/01 11:35:45 nkumar Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.LimitDetailsComparator;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GeneralChargeUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGenChargeMapEntry;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBFixedAssetOthersSummary;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBInsuranceSummary;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBStockSummary;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.61 $
 * @since $Date: 2006/09/01 11:35:45 $ Tag: $Name: $
 */

public class AssetGenChargeUtil {

	public static String FOREX_ERROR = "Forex Error";

	public static int RAW_MATERIAL_TYPE = 3;

	public static int FINISHED_GOODS_TYPE = 4;

	public static int WIP_TYPE = 5;

	public static int GOODS_IN_TRANSIT_TYPE = 6;

	public static int STORES_SPARES_TYPE = 7;

	public static int OTHER_MERCHANDISE_TYPE = 8;

	public AssetGenChargeUtil() {
	};

	/**
	 * Get insurance info object by the key given
	 * @param iCol type IGeneralCharge
	 * @param key type String, the reference ID of insurance info object
	 * @return IInsuranceInfo
	 */
	public static IInsurancePolicy getInsuranceInfo(IGeneralCharge iCol, String key) {
		IInsurancePolicy insurance = null;

		if ((key != null) && !"-1".equals(key)) {
			HashMap insuranceMap = (HashMap) iCol.getInsurance();
			return (IInsurancePolicy) insuranceMap.get(key);
		}
		else if ((key != null) && "-1".equals(key)) {
			insurance = new OBInsurancePolicy();
			String generatedInsID = iCol.generateNewID(OBGeneralCharge.TYPE_INSURANCE);
			insurance.setRefID(generatedInsID);
		}
		return insurance;
	}

	/**
	 * Helper method to generate insurance info hashMap as object to mapper It
	 * is used by Stock / FAO
	 * @param insurance insurance policy object
	 * @param iCol general charge collateral object
	 * 
	 * @return HashMap key: ccy, value: CMS security currency key: links, value:
	 *         String[] links between objID that links to the insurance by
	 *         insurance category key: obj, value: IInsuranceInfo insurance
	 *         object
	 */
	public static HashMap generateInsuranceMap(IInsurancePolicy insurance, IGeneralCharge iCol) {
		HashMap insuranceMap = new HashMap();

		insuranceMap.put("ccy", iCol.getCurrencyCode());

		if (insurance != null) {
			Collection linkage = new ArrayList();

			HashMap insuranceObjMap = new HashMap();
			if (insurance.getCategory().equals(IInsurancePolicy.STOCK)) {
				insuranceObjMap = (HashMap) iCol.get_Insurance_Stock_Map();
			}
			else {
				insuranceObjMap = (HashMap) iCol.get_Insurance_FixedAssetOthers_Map();
			}
			linkage = (Collection) insuranceObjMap.get(insurance.getRefID());
			if ((linkage != null) && !linkage.isEmpty()) {
				Iterator itr = linkage.iterator();
				String[] objIDList = new String[linkage.size()];
				int count = 0;
				while (itr.hasNext()) {
					IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
					objIDList[count++] = entry.getEntryValueID();
				}
				insuranceMap.put("links", objIDList);
			}
			else {
				insuranceMap.put("links", null);
			}

			insuranceMap.put("obj", insurance);
		}
		else {
			insuranceMap.put("links", null);
			insuranceMap.put("obj", null);
		}

		return insuranceMap;
	}

	public static String getCMSAmount(Amount amt, CurrencyCode ccy, Locale locale) {
		String returnAmt = null;
		ForexHelper fr = new ForexHelper();
		if ((amt != null) && (amt.getCurrencyCode() != null) && (ccy != null)) {
			try {
				double cmsValue = fr.convertAmount(amt, ccy);
				returnAmt = MapperUtil.mapDoubleToString(cmsValue, 0, locale);
			}
			catch (Exception e) {
				returnAmt = FOREX_ERROR;
			}
		}
		return returnAmt;
	}

	public static BigDecimal getCMSAmountAsBigDecimal(Amount amt, CurrencyCode ccy, Locale locale) {
		BigDecimal returnAmt = null;
		ForexHelper fr = new ForexHelper();
		if ((amt != null) && (amt.getCurrencyCode() != null) && (ccy != null)) {
			try {
				// double cmsValue = fr.convertAmount(amt, ccy);
				// returnAmt = new BigDecimal(cmsValue);
				Amount cmsValue = fr.convert(amt, ccy);
				returnAmt = cmsValue.getAmountAsBigDecimal();
			}
			catch (Exception e) {
				returnAmt = null;
			}
		}
		return returnAmt;
	}

	public static double setMarginStrToDouble(String marginStr) {
		return SecuritySubTypeUtil.setMarginStrToDouble(marginStr);
	}

	public static String setMarginDoubleToStr(double margin, Locale locale) {
		return SecuritySubTypeUtil.setMarginDoubleToStr(margin, locale);
	}

	public static IGeneralCharge deleteInsuranceList(IGeneralCharge col, String[] deleteInsurance, String tab) {

		HashMap insuranceMap = (HashMap) col.getInsurance();
		HashMap objInsuranceMap = getObjInsuranceMap(col, tab);
		HashMap insuranceObjMap = getInsuranceObjMap(col, tab);

		if (deleteInsurance != null) {
			for (int i = 0; i < deleteInsurance.length; i++) {
				Collection objList = (Collection) insuranceObjMap.get(deleteInsurance[i]);
				if (objList != null) {
					Iterator itr = objList.iterator();
					while (itr.hasNext()) {
						IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
						ArrayList insuranceList = (ArrayList) objInsuranceMap.get(entry.getEntryValueID());
						// int index = insuranceList.indexOf(entry);
						int index = -1;
						boolean found = false;
						for (Iterator insItr = insuranceList.iterator(); insItr.hasNext();) {
							IGenChargeMapEntry insEntry = (IGenChargeMapEntry) insItr.next();
							index++;
							if (insEntry.getInsuranceID().equals(deleteInsurance[i])) {
								found = true;
								break;
							}
						}
						if (found) {
							insuranceList.remove(index);
							objInsuranceMap.put(entry.getEntryValueID(), insuranceList);
						}
					}
				}
				insuranceMap.remove(deleteInsurance[i]);
				insuranceObjMap.remove(deleteInsurance[i]);
			}
		}

		col.setInsurance(insuranceMap);
		if (insuranceMap.isEmpty()) {
			col.setInsurancePolicies(null);
		}

		if (tab.equals(CollateralConstant.TAB_STOCK)) {
			col.set_Stock_Insurance_Map(objInsuranceMap);
			col.set_Insurance_Stock_Map(insuranceObjMap);
		}
		else {
			col.set_FixedAssetOthers_Insurance_Map(objInsuranceMap);
			col.set_Insurance_FixedAssetOthers_Map(insuranceObjMap);
		}
		return col;
	}

	/**
	 * To calculate the total amount value of stock summary page
	 */
	public static HashMap getStockSummaryTotal(Collection stockSummaryList) {
		BigDecimal totalGross = new BigDecimal(0);
		BigDecimal totalGrossCreditor = new BigDecimal(0);
		BigDecimal totalNetValue = new BigDecimal(0);
		BigDecimal totalInsuredAmt = new BigDecimal(0);
		BigDecimal totalEffectiveAmt = new BigDecimal(0);
		BigDecimal totalRecoverableAmt = new BigDecimal(0);

		if (stockSummaryList != null) {
			Iterator itr = stockSummaryList.iterator();
			while (itr.hasNext()) {
				OBStockSummary summary = (OBStockSummary) itr.next();

				if (isForexErrorAmount(summary.getGrossValue())) {
					totalGross = null;
				}
				else {
					if ((summary.getGrossValue() != null) && (totalGross != null)) {
						totalGross = totalGross.add(summary.getGrossValue().getAmountAsBigDecimal());
					}
				}

				if (isForexErrorAmount(summary.getGrossValueLessCreditorAmt())) {
					totalGrossCreditor = null;
				}
				else {
					if ((summary.getGrossValueLessCreditorAmt() != null) && (totalGrossCreditor != null)) {
						totalGrossCreditor = totalGrossCreditor.add(summary.getGrossValueLessCreditorAmt()
								.getAmountAsBigDecimal());
					}
				}

				if (isForexErrorAmount(summary.getNetValue())) {
					totalNetValue = null;
				}
				else {
					if ((summary.getNetValue() != null) && (totalNetValue != null)) {
						totalNetValue = totalNetValue.add(summary.getNetValue().getAmountAsBigDecimal());
					}
				}

				if (summary.getInsuranceSummary() != null) {
					OBInsuranceSummary[] insuranceSummary = summary.getInsuranceSummary();
					for (int i = 0; i < insuranceSummary.length; i++) {
						if (isForexErrorAmount(insuranceSummary[i].getInsuredAmount())) {
							totalInsuredAmt = null;
						}
						else {
							if ((insuranceSummary[i].getInsuredAmount() != null) && (totalInsuredAmt != null)) {
								totalInsuredAmt = totalInsuredAmt.add(insuranceSummary[i].getInsuredAmount()
										.getAmountAsBigDecimal());
							}
						}

						if (isForexErrorAmount(insuranceSummary[i].getCoverageAmount())) {
							totalEffectiveAmt = null;
						}
						else {
							if ((insuranceSummary[i].getCoverageAmount() != null) && (totalEffectiveAmt != null)) {
								totalEffectiveAmt = totalEffectiveAmt.add(insuranceSummary[i].getCoverageAmount()
										.getAmountAsBigDecimal());
							}
						}
					}
				}
				if (isForexErrorAmount(summary.getRecoverableAmount())) {
					totalRecoverableAmt = null;
				}
				else {
					if ((summary.getRecoverableAmount() != null) && (totalRecoverableAmt != null)) {
						totalRecoverableAmt = totalRecoverableAmt.add(summary.getRecoverableAmount()
								.getAmountAsBigDecimal());
					}
				}
			}
		}

		HashMap returnMap = new HashMap();
		returnMap.put("totalGross", totalGross);
		returnMap.put("totalGrossCreditor", totalGrossCreditor);
		returnMap.put("totalNetValue", totalNetValue);
		returnMap.put("totalInsuredAmt", totalInsuredAmt);
		returnMap.put("totalEffectiveAmt", totalEffectiveAmt);
		returnMap.put("totalRecoverableAmt", totalRecoverableAmt);

		return returnMap;
	}

	/**
	 * To calculate the total amount value of fao summary page
	 */
	public static HashMap getFAOSummaryTotal(Collection faoSummaryList) {
		BigDecimal totalGross = new BigDecimal(0);
		BigDecimal totalNetValue = new BigDecimal(0);
		BigDecimal totalInsuredAmt = new BigDecimal(0);
		BigDecimal totalEffectiveAmt = new BigDecimal(0);
		BigDecimal totalRecoverableAmt = new BigDecimal(0);

		if (faoSummaryList != null) {
			Iterator itr = faoSummaryList.iterator();
			while (itr.hasNext()) {
				OBFixedAssetOthersSummary summary = (OBFixedAssetOthersSummary) itr.next();

				if (isForexErrorAmount(summary.getGrossValue())) {
					totalGross = null;
				}
				else {
					if ((summary.getGrossValue() != null) && (totalGross != null)) {
						totalGross = totalGross.add(summary.getGrossValue().getAmountAsBigDecimal());
					}
				}

				if (isForexErrorAmount(summary.getNetValue())) {
					totalNetValue = null;
				}
				else {
					if ((summary.getNetValue() != null) && (totalNetValue != null)) {
						totalNetValue = totalNetValue.add(summary.getNetValue().getAmountAsBigDecimal());
					}
				}

				if (summary.getInsuranceSummary() != null) {
					OBInsuranceSummary[] insuranceSummary = summary.getInsuranceSummary();
					for (int i = 0; i < insuranceSummary.length; i++) {
						if (isForexErrorAmount(insuranceSummary[i].getInsuredAmount())) {
							totalInsuredAmt = null;
						}
						else {
							if ((insuranceSummary[i].getInsuredAmount() != null) && (totalInsuredAmt != null)) {
								totalInsuredAmt = totalInsuredAmt.add(insuranceSummary[i].getInsuredAmount()
										.getAmountAsBigDecimal());
							}
						}

						if (isForexErrorAmount(insuranceSummary[i].getCoverageAmount())) {
							totalEffectiveAmt = null;
						}
						else {
							if ((insuranceSummary[i].getCoverageAmount() != null) && (totalEffectiveAmt != null)) {
								totalEffectiveAmt = totalEffectiveAmt.add(insuranceSummary[i].getCoverageAmount()
										.getAmountAsBigDecimal());
							}
						}
					}
				}
				if (isForexErrorAmount(summary.getRecoverableAmount())) {
					totalRecoverableAmt = null;
				}
				else {
					if ((summary.getRecoverableAmount() != null) && (totalRecoverableAmt != null)) {
						totalRecoverableAmt = totalRecoverableAmt.add(summary.getRecoverableAmount()
								.getAmountAsBigDecimal());
					}
				}
			}
		}
		HashMap returnMap = new HashMap();
		returnMap.put("totalGross", totalGross);
		returnMap.put("totalNetValue", totalNetValue);
		returnMap.put("totalInsuredAmt", totalInsuredAmt);
		returnMap.put("totalEffectiveAmt", totalEffectiveAmt);
		returnMap.put("totalRecoverableAmt", totalRecoverableAmt);

		return returnMap;
	}

	/**
	 * Get calculate average margin for Stock/FAO average margin = fsvAmt /
	 * cmvAmt
	 * 
	 * @param cmvAmt valuation CMV amount
	 * @param fsvAmt valuation FSV amount
	 * @param locale type Locale
	 * @return String margin value convert to string with 0 decimal places
	 */
	public static String getSummaryAverageMargin(Amount cmvAmt, Amount fsvAmt, Locale locale) {
		double returnVal = ICMSConstant.DOUBLE_INVALID_VALUE;
		if ((cmvAmt != null) && (fsvAmt != null)) {
			if (cmvAmt.getAmount() > 0) {
				// returnVal =
				// fsvAmt.divide(cmvAmt.getAmountAsBigDecimal()).getAmountAsDouble
				// ();
				BigDecimal bd1 = fsvAmt.getAmountAsBigDecimal();
				BigDecimal bd2 = cmvAmt.getAmountAsBigDecimal();
				BigDecimal bd3 = bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_EVEN);
				returnVal = bd3.doubleValue();
			}
		}

		return setMarginDoubleToStr(returnVal, locale);
	}

	/**
	 * To calculate the total amount value of insurance summary page (stock or
	 * FAO page)
	 */
	public static HashMap getInsuranceSummaryTotal(IGeneralCharge col, String category, Locale locale) {
		HashMap returnMap = new HashMap();
		Date currentDate = DateUtil.clearTime(DateUtil.getDate());
		CurrencyCode ccy = new CurrencyCode(col.getCurrencyCode());

		BigDecimal totalInsuredAmt = new BigDecimal(0);
		BigDecimal totalInsuredCoverAmt = new BigDecimal(0);

		HashMap insuranceMap = (HashMap) col.getInsurance();
		if (insuranceMap != null) {
			Collection keyList = insuranceMap.keySet();
			Iterator itr = keyList.iterator();
			while (itr.hasNext()) {
				IInsurancePolicy insurance = (IInsurancePolicy) insuranceMap.get(itr.next());
				if (insurance.getCategory().equals(category)) {
					if (insurance.getInsuredAmount() != null) {
						boolean isExpired = false;
						BigDecimal cmsAmt = getCMSAmountAsBigDecimal(insurance.getInsuredAmount(), ccy, locale);
						int freqNum = (category.equals(IInsurancePolicy.STOCK)) ? col.getStockInsrGracePeriod() : col
								.getFaoInsrGracePeriod();
						int freqCode = UIUtil.getFreqCode((category.equals(IInsurancePolicy.STOCK)) ? col
								.getStockInsrGracePeriodFreq() : col.getFaoInsrGracePeriodFreq());
						Date dueDate = UIUtil.calculateDate(freqNum, freqCode, insurance.getExpiryDate());
						dueDate = DateUtil.clearTime(dueDate);
						if ((dueDate != null) && dueDate.before(currentDate)) {
							isExpired = true;
						}
						if (cmsAmt != null) {
							if (totalInsuredAmt != null) {
								totalInsuredAmt = totalInsuredAmt.add(cmsAmt);
							}
							if (!isExpired && (totalInsuredCoverAmt != null)) {
								totalInsuredCoverAmt = totalInsuredCoverAmt.add(cmsAmt);
							}
						}
						else {
							totalInsuredAmt = null;
							if (!isExpired) {
								totalInsuredCoverAmt = null;
							}
						}
					}
				}
			}
		}

		returnMap.put("totalInsuredAmt", totalInsuredAmt);
		returnMap.put("totalInsuredCoverAmt", totalInsuredCoverAmt);

		return returnMap;
	}

	public static OBInsuranceObjLinkage[] compareInsuranceObjLinkage(String insuranceRef, String tab,
			ICollateralTrxValue trxValue) {
		Collection returnList = new ArrayList();

		IGeneralCharge actual = (IGeneralCharge) trxValue.getCollateral();
		IGeneralCharge staging = (IGeneralCharge) trxValue.getStagingCollateral();

		HashMap actualMap = getInsuranceObjMap(actual, tab);
		HashMap stageMap = getInsuranceObjMap(staging, tab);

		Collection actualList = new ArrayList();
		Collection stageList = new ArrayList();

		if (actualMap != null) {
			actualList = (Collection) actualMap.get(insuranceRef);
		}
		if (stageMap != null) {
			stageList = (Collection) stageMap.get(insuranceRef);
		}

		if ((actualList == null) && (stageList != null)) {
			returnList = getStageListNew(stageList);
		}
		if ((stageList == null) && (actualList != null)) {
			returnList = getActualListDeleted(actualList);
		}
		if ((stageList != null) && (actualList != null)) {
			returnList = getActualStageDiffList(actualList, stageList);
		}

		OBInsuranceObjLinkage[] linkageList = null;
		if ((returnList != null) && (returnList.size() > 0)) {
			linkageList = (OBInsuranceObjLinkage[]) returnList.toArray(new OBInsuranceObjLinkage[0]);
			Arrays.sort(linkageList, new Comparator() {
				public int compare(Object o1, Object o2) {
					String id1 = ((OBInsuranceObjLinkage) o1).getObjID();
					String id2 = ((OBInsuranceObjLinkage) o2).getObjID();
					return id1.compareTo(id2);
				}
			});
		}

		return linkageList;
	}

	private static Collection getStageListNew(Collection objList) {
		Collection returnList = new ArrayList();
		if (objList != null) {
			Iterator itr = objList.iterator();
			while (itr.hasNext()) {
				IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
				OBInsuranceObjLinkage temp = new OBInsuranceObjLinkage();
				temp.setObjID(entry.getEntryValueID());
				temp.setStatus(CompareOBUtil.OB_ADDED);
				returnList.add(temp);
			}
		}
		return returnList;
	}

	private static Collection getActualListDeleted(Collection objList) {
		Collection returnList = new ArrayList();
		if (objList != null) {
			Iterator itr = objList.iterator();
			while (itr.hasNext()) {
				IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
				OBInsuranceObjLinkage temp = new OBInsuranceObjLinkage();
				temp.setObjID(entry.getEntryValueID());
				temp.setStatus(CompareOBUtil.OB_DELETED);
				returnList.add(temp);
			}
		}
		return returnList;
	}

	private static Collection getActualStageDiffList(Collection actualList, Collection stageList) {
		Iterator stageItr = stageList.iterator();

		Collection returnList = new ArrayList();
		Collection actualInStageList = new ArrayList();
		while (stageItr.hasNext()) {
			IGenChargeMapEntry entry = (IGenChargeMapEntry) stageItr.next();
			boolean found = false;
			Iterator actualItr = actualList.iterator();
			while (actualItr.hasNext()) {
				IGenChargeMapEntry actEntry = (IGenChargeMapEntry) actualItr.next();
				if (actEntry.getEntryValueID().equals(entry.getEntryValueID())) {
					found = true;
					actualInStageList.add(actEntry);
					break;
				}
			}
			OBInsuranceObjLinkage temp = new OBInsuranceObjLinkage();
			temp.setObjID(entry.getEntryValueID());
			if (!found) {
				temp.setStatus(CompareOBUtil.OB_ADDED);
			}
			else {
				temp.setStatus(CompareOBUtil.OB_UNMODIFIED);
			}
			returnList.add(temp);
		}
		actualList.removeAll(actualInStageList);
		if (actualList != null) {
			Collection deletedList = getActualListDeleted(actualList);
			returnList.addAll(deletedList);
		}

		return returnList;
	}

	public static String getInsuranceObjLinkageStatus(String insuranceRef, IGeneralCharge actualCol,
			IGeneralCharge stageCol, String tab) {
		String status = CompareOBUtil.OB_UNMODIFIED;

		HashMap actualInsObjMap = getInsuranceObjMap(actualCol, tab);
		HashMap stageInsObjMap = getInsuranceObjMap(stageCol, tab);

		ArrayList actEntryList = new ArrayList();
		ArrayList stageEntryList = new ArrayList();

		if (actualInsObjMap != null) {
			actEntryList = (ArrayList) actualInsObjMap.get(insuranceRef);
		}
		if (stageInsObjMap != null) {
			stageEntryList = (ArrayList) stageInsObjMap.get(insuranceRef);
		}

		if (((actEntryList == null) || (actEntryList.size() == 0))
				&& ((stageEntryList != null) && (stageEntryList.size() > 0))) {
			return CompareOBUtil.OB_MODIFIED;
		}

		if (((stageEntryList == null) || (stageEntryList.size() == 0))
				&& ((actEntryList != null) && (actEntryList.size() > 0))) {
			return CompareOBUtil.OB_MODIFIED;
		}

		if ((actEntryList != null) && (actEntryList.size() > 0) && (stageEntryList != null)
				&& (stageEntryList.size() > 0)) {
			if (actEntryList.size() != stageEntryList.size()) {
				return CompareOBUtil.OB_MODIFIED;
			}
			return getInsuranceLinkageStatus(actEntryList, stageEntryList);
		}
		return status;
	}

	private static String getInsuranceLinkageStatus(ArrayList actEntryList, ArrayList stageEntryList) {
		Iterator itr = actEntryList.iterator();
		while (itr.hasNext()) {
			IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
			Iterator stageItr = stageEntryList.iterator();
			boolean found = false;
			while (stageItr.hasNext()) {
				IGenChargeMapEntry stageEntry = (IGenChargeMapEntry) stageItr.next();
				if (entry.getEntryValueID().equals(stageEntry.getEntryValueID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				return CompareOBUtil.OB_MODIFIED;
			}
		}
		return CompareOBUtil.OB_UNMODIFIED;
	}

	public static IInsurancePolicy[] getInsuranceListByCategory(HashMap insuranceMap, String category) {
		ArrayList insList = new ArrayList();
		if (insuranceMap != null) {
			Collection insuranceList = insuranceMap.values();
			if (insuranceList != null) {
				Iterator itr = insuranceList.iterator();
				while (itr.hasNext()) {
					IInsurancePolicy insurance = (IInsurancePolicy) itr.next();
					if (insurance.getCategory().equals(category)) {
						insList.add(insurance);
					}
				}
			}
		}
		return (IInsurancePolicy[]) insList.toArray(new IInsurancePolicy[0]);
	}

	/**
	 * Helper method to populate Limit details list for drawing power tab Filter
	 * out the limit-security linkage is deleted
	 */
	public static void populateLimitDetails(IGeneralCharge col, HashMap limitMap, HashMap colLimitMap) throws Exception {
		ILimitProxy proxy = LimitProxyFactory.getProxy();

		if (col != null) {
			ICollateralLimitMap[] limitMapList = col.getCurrentCollateralLimits();
			if (limitMapList != null) {
				for (int i = 0; i < limitMapList.length; i++) {
					String limitMapStatus = limitMapList[i].getSCIStatus();
					// if (limitMapStatus == null ||
					// !ICMSConstant.HOST_STATUS_DELETE.equals(limitMapStatus))
					// {
					if (limitMapList[i].getCustomerCategory().equals("MB")) {
						String strLimitID = String.valueOf(limitMapList[i].getLimitID());
						if (!colLimitMap.containsKey(strLimitID)) {
							colLimitMap.put(strLimitID, limitMapList[i]);
						}

						if (!limitMap.containsKey(strLimitID)) {
							ILimit limit = proxy.getLimit(limitMapList[i].getLimitID());

							limitMap.put(strLimitID, limit);
						}
					}
					else {
						ICoBorrowerLimit coLimit = proxy.getCoBorrowerLimit(limitMapList[i].getCoBorrowerLimitID());
						String strLimitID = String.valueOf(coLimit.getOuterLimitID());
						if (!colLimitMap.containsKey(strLimitID)) {
							colLimitMap.put(strLimitID, limitMapList[i]);
						}

						if (!limitMap.containsKey(strLimitID)) {
							ILimit limit = proxy.getLimit(coLimit.getOuterLimitID());
							limitMap.put(strLimitID, limit);
						}
					}

					// }

				}
			}
		}
	}

	public static BigDecimal getTotalApprovedLimit(HashMap limitMap, String SCIColId, String cmsCcy, Locale locale) {
		BigDecimal totalApprovedLimit = new BigDecimal(0);
		BigDecimal bd;
		if (limitMap != null) {
			Collection key = limitMap.keySet();
			Iterator itr = key.iterator();
			while (itr.hasNext()) {

				ILimit limit = (ILimit) limitMap.get((String) itr.next());
				ICollateralAllocation[] nonDeletedLimitColMap = (limit.getNonDeletedCollateralAllocations() != null) ? limit
						.getNonDeletedCollateralAllocations()
						: new OBCollateralAllocation[0];
				boolean isFoundInMap = false;

				for (int i = 0; (i < nonDeletedLimitColMap.length) && !isFoundInMap; i++) {
					String mapSCIColId = nonDeletedLimitColMap[i].getCollateral().getSCISecurityID();
					if (SCIColId.equals(mapSCIColId)) {
						isFoundInMap = true;
					}
				}
				if (!isFoundInMap) {
					continue;
				}

				if ((limit.getOuterLimitID() == ICMSConstant.LONG_INVALID_VALUE) || (limit.getOuterLimitID() == 0)) {

					bd = getCMSAmountAsBigDecimal(limit.getApprovedLimitAmount(), new CurrencyCode(cmsCcy), locale);
					DefaultLogger.debug("AssetGenChargeUtil.validateLimitDetails", "limit.getApprovedLimitAmount(): "
							+ limit.getApprovedLimitAmount() + "\tconverted bd: " + bd);
					if (bd != null) {
						totalApprovedLimit = totalApprovedLimit.add(bd);
					}
					else {
						totalApprovedLimit = null;
					}
				}
			}
		}
		return totalApprovedLimit;
	}

	public static void validateLimitDetails(BigDecimal totalApprovedLimit, IGeneralCharge col, HashMap exceptionMap) {
		DefaultLogger.debug("AssetGenChargeUtil.validateLimitDetails", "totalApprovedLimit: " + totalApprovedLimit);
		Amount totalAppliedLimit = ((OBGeneralCharge) col).getTotalAppliedLimitAmount();
		DefaultLogger.debug("AssetGenChargeUtil.validateLimitDetails", "totalAppliedLimit: " + totalAppliedLimit);
		Amount totalReleasedLimit = ((OBGeneralCharge) col).getTotalReleasedLimitAmount();
		DefaultLogger.debug("AssetGenChargeUtil.validateLimitDetails", "totalReleasedLimit: " + totalReleasedLimit);
		if ((totalApprovedLimit != null) && !isForexErrorAmount(totalAppliedLimit) && (totalAppliedLimit != null)) {
			if (totalApprovedLimit.compareTo(totalAppliedLimit.getAmountAsBigDecimal()) < 0) {
				exceptionMap.put("totalAppliedLimitErr", new ActionMessage("error.collateral.totalappliedlimit.more"));
			}
		}
		if (!isForexErrorAmount(totalAppliedLimit) && (totalAppliedLimit != null)
				&& !isForexErrorAmount(totalReleasedLimit) && (totalReleasedLimit != null)) {
			if (totalAppliedLimit.getAmountAsBigDecimal().compareTo(totalReleasedLimit.getAmountAsBigDecimal()) < 0) {
				exceptionMap
						.put("totalReleasedLimitErr", new ActionMessage("error.collateral.totalreleasedlimit.more"));
			}
		}
	}

	public static List getDistinctLimitList(IGeneralCharge col) {
		return SecuritySubTypeUtil.getDistinctLimitList(col);
	}

	public static HashMap getTotalStockTypeValue(HashMap stockMap, String cmsCcy, Locale locale, int stockType) {
		HashMap returnMap = new HashMap();
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalMargin = new BigDecimal(0);
		BigDecimal netTotal = new BigDecimal(0);
		CurrencyCode ccy = new CurrencyCode(cmsCcy);
		if (stockMap != null) {
			Collection keyset = stockMap.keySet();
			if (keyset != null) {
				Iterator itr = keyset.iterator();
				while ((total != null) && itr.hasNext()) {
					IStock stock = (IStock) stockMap.get(itr.next());

					Amount amt = getStockTypeAmount(stock, stockType);
					if (amt != null) {
						BigDecimal bd = getCMSAmountAsBigDecimal(amt, ccy, locale);
						if (bd != null) {
							total = total.add(bd);
							double margin = getStockTypeMargin(stock, stockType);
							netTotal = netTotal.add(bd.multiply(new BigDecimal(margin)));
						}
						else {
							total = null;
							netTotal = null;
						}
					}
				}
			}
			if ((total != null) && (netTotal != null) & (netTotal.doubleValue() != 0)) {
				totalMargin = netTotal.divide(total, 2, BigDecimal.ROUND_HALF_EVEN);
			}
		}
		try {
			returnMap.put("value", total);
			returnMap.put("margin", totalMargin);
		}
		catch (Exception e) {
			DefaultLogger.debug("AssetGenChargeUtil.getTotalStockTypeValue", "exception throw for format big decimal");
		}
		return returnMap;
	}

	// if bd is null, it is a forex error BigDecimal after amount conversion.
	public static String displayBigDecimalToStrWForex(BigDecimal bd, int decimalPlaces, Locale locale) throws Exception {
		if (bd == null) {
			return FOREX_ERROR;
		}
		return UIUtil.formatNumber(bd, decimalPlaces, locale);
	}

	private static Amount getStockTypeAmount(IStock stock, int stock_type) {
		if (stock_type == RAW_MATERIAL_TYPE) {
			return stock.getRawMaterialAmt();
		}
		if (stock_type == FINISHED_GOODS_TYPE) {
			return stock.getFinishGoodsAmt();
		}
		if (stock_type == WIP_TYPE) {
			return stock.getWorkProgressAmt();
		}
		if (stock_type == GOODS_IN_TRANSIT_TYPE) {
			return stock.getGoodsTransitAmt();
		}
		if (stock_type == STORES_SPARES_TYPE) {
			return stock.getStoresSparesAmt();
		}
		if (stock_type == OTHER_MERCHANDISE_TYPE) {
			return stock.getOtherMerchandiseAmt();
		}
		return null;
	}

	private static double getStockTypeMargin(IStock stock, int stock_type) {
		if (stock_type == RAW_MATERIAL_TYPE) {
			return stock.getRawMaterialMargin();
		}
		if (stock_type == FINISHED_GOODS_TYPE) {
			return stock.getFinishGoodsMargin();
		}
		if (stock_type == WIP_TYPE) {
			return stock.getWorkProgressMargin();
		}
		if (stock_type == GOODS_IN_TRANSIT_TYPE) {
			return stock.getGoodsTransitMargin();
		}
		if (stock_type == STORES_SPARES_TYPE) {
			return stock.getStoresSparesMargin();
		}
		if (stock_type == OTHER_MERCHANDISE_TYPE) {
			return stock.getOtherMerchandiseMargin();
		}
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	public static List getSortCollateralLimitMap(HashMap colLimitMap) {
		List colLimitMapList = new ArrayList();
		if (colLimitMap != null) {
			Collection keySet = colLimitMap.keySet();
			Iterator itr = keySet.iterator();
			while (itr.hasNext()) {
				colLimitMapList.add(colLimitMap.get(itr.next()));
			}
			Collections.sort(colLimitMapList, new LimitDetailsComparator());
		}
		return colLimitMapList;
	}

	public static boolean hasSameInsurancePolicy(HashMap insuranceMap, String strKey) {
		if ((insuranceMap == null) || insuranceMap.isEmpty()) {
			return false;
		}
		IInsurancePolicy insurance = (IInsurancePolicy) insuranceMap.get(strKey);
		Collection keySet = insuranceMap.keySet();
		Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			if (!key.equals(strKey)) {
				IInsurancePolicy tempObj = (IInsurancePolicy) insuranceMap.get(key);
				if (insurance.getCategory().equals(tempObj.getCategory())) {
					if (insurance.getPolicyNo().equals(tempObj.getPolicyNo())
							&& insurance.getInsurerName().equals(tempObj.getInsurerName())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String removeBracket(String strAmt) {
		if (strAmt == null) {
			return null;
		}

		if (strAmt.trim().equals("")) {
			return "";
		}

		strAmt = strAmt.trim();
		int openBracket = strAmt.indexOf("(");
		int closeBracket = strAmt.indexOf(")");
		if (openBracket < 0) {
			return strAmt;
		}

		strAmt = strAmt.substring(openBracket + 1, closeBracket).trim();
		return strAmt;
	}

	/*
	 * public static IGeneralCharge setInsuranceCoverageAmt (Collection
	 * objSummaryList, IGeneralCharge col, String tab) { if (objSummaryList !=
	 * null && !objSummaryList.isEmpty()) { Iterator itr =
	 * objSummaryList.iterator(); HashMap insuranceObjMap =
	 * getInsuranceObjMap(col, tab); HashMap objInsuranceMap =
	 * getObjInsuranceMap(col, tab); while (itr.hasNext()) {
	 * OBGeneralChargeSubTypeSummary summary =
	 * (OBGeneralChargeSubTypeSummary)itr.next(); ArrayList insuranceList =
	 * (ArrayList)objInsuranceMap.get(summary.getID()); OBInsuranceSummary[]
	 * insSummaryList = summary.getInsuranceSummary(); if (insuranceList !=
	 * null) { int count = 0; Iterator insItr = insuranceList.iterator(); while
	 * (insItr.hasNext()) { IGenChargeMapEntry entry =
	 * (IGenChargeMapEntry)insItr.next(); for (int i = 0; i <
	 * insSummaryList.length; i++) { if
	 * (entry.getInsuranceID().equals(insSummaryList[i].getRefID()) &&
	 * !isForexErrorAmount(insSummaryList[i].getCoverageAmount())) {
	 * entry.setInsrCoverageAmount(insSummaryList[i].getCoverageAmount());
	 * setInsuranceObjCoverage (insuranceObjMap, entry); break; } }
	 * insuranceList.set(count, entry); count++; } }
	 * objInsuranceMap.put(summary.getID(), insuranceList); }
	 * setInsuranceObjMap(col, insuranceObjMap, tab); setObjInsuranceMap(col,
	 * objInsuranceMap, tab); }
	 * 
	 * return col; }
	 */
	private static void setInsuranceObjCoverage(HashMap insuranceObjMap, IGenChargeMapEntry entry) {
		ArrayList objList = (ArrayList) insuranceObjMap.get(entry.getInsuranceID());
		if (objList != null) {
			Iterator itr = objList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				IGenChargeMapEntry tmpObj = (IGenChargeMapEntry) itr.next();
				if (tmpObj.getEntryValueID().equals(entry.getEntryValueID())) {
					objList.set(count, entry);
					break;
				}
				count++;
			}
		}
		insuranceObjMap.put(entry.getInsuranceID(), objList);
	}

	/**
	 * Helper method to get Insuranc Info ID with Stock/FAO Map determine by
	 * Screen tab
	 */
	private static HashMap getInsuranceObjMap(IGeneralCharge col, String tab) {
		HashMap returnMap = new HashMap();

		if (tab.equals(CollateralConstant.TAB_STOCK)) {
			returnMap = (HashMap) col.get_Insurance_Stock_Map();
		}
		else {
			returnMap = (HashMap) col.get_Insurance_FixedAssetOthers_Map();
		}

		return returnMap;
	}

	private static void setInsuranceObjMap(IGeneralCharge col, HashMap map, String tab) {
		if (tab.equals(CollateralConstant.TAB_STOCK)) {
			col.set_Insurance_Stock_Map(map);
		}
		else {
			col.set_Insurance_FixedAssetOthers_Map(map);
		}
	}

	/**
	 * Helper method to get Stock/FAO ID with insurance Map determine by UI
	 * Screen tab
	 */
	private static HashMap getObjInsuranceMap(IGeneralCharge col, String tab) {
		HashMap returnMap = new HashMap();

		if (tab.equals(CollateralConstant.TAB_STOCK)) {
			returnMap = (HashMap) col.get_Stock_Insurance_Map();
		}
		else {
			returnMap = (HashMap) col.get_FixedAssetOthers_Insurance_Map();
		}

		return returnMap;
	}

	/**
	 * Helper method to set stock/FAO map into IGeneralCharge by screen tab
	 * 
	 * @param col - IGeneralCharge
	 * @param map - Hashmap of object-insurance-map (object either stock or fao)
	 * @param tab - String, the current position of the UI screen tab
	 */
	private static void setObjInsuranceMap(IGeneralCharge col, HashMap map, String tab) {
		if (tab.equals(CollateralConstant.TAB_STOCK)) {
			col.set_Stock_Insurance_Map(map);
		}
		else {
			col.set_FixedAssetOthers_Insurance_Map(map);
		}
	}

	/**
	 * Helper method to convert amount to string Handle amount conversion of
	 * currency having forex error
	 * 
	 * @param amt - Amount
	 * @param locale - Locale
	 * 
	 *        return String of converted amount in String
	 */
	public static String convertAmtToStringWForex(Amount amt, Locale locale) {
		if (isForexErrorAmount(amt)) {
			return FOREX_ERROR;
		}
		if ((amt != null) && (amt.getCurrencyCodeAsObject() != null)) {
			try {
				return UIUtil.formatAmount(amt, 0, locale, false);
			}
			catch (Exception e) {
				return "-";
			}
		}
		return "-";
	}

	/**
	 * Get the 1st stock valuation date as print ad hoc report date
	 * 
	 * @param col - IGeneralCharge
	 * @param locale - Locale
	 * 
	 *        return String
	 */
	public static String get1stStockValDate(IGeneralCharge col, Locale locale) {
		HashMap stockMap = (HashMap) col.getStocks();
		if (stockMap != null) {
			TreeSet stockIDList = new TreeSet(stockMap.keySet());
			if (!stockIDList.isEmpty()) {
				IStock stock = (IStock) stockMap.get((String) stockIDList.first());
				if (stock.getValuationDate() != null) {
					return DateUtil.formatDate(locale, stock.getValuationDate());
				}
			}
		}
		return "-";
	}

	/**
	 * Helper method to set the stock recoverable amount from stock summary list
	 * object
	 * @param stockSummaryList - Collection OBStockSummary
	 * @param col - IGeneralCharge
	 */
	public static IGeneralCharge setStockRecoverableAmt(Collection stockSummaryList, IGeneralCharge col) {
		HashMap stockMap = (HashMap) col.getStocks();
		if (stockSummaryList != null) {
			Iterator itr = stockSummaryList.iterator();
			while (itr.hasNext()) {
				OBStockSummary summary = (OBStockSummary) itr.next();
				IStock stock = (IStock) stockMap.get(summary.getID());
				if (isForexErrorAmount(summary.getRecoverableAmount())) {
					stock.setRecoverableAmount(null);
				}
				else {
					stock.setRecoverableAmount(summary.getRecoverableAmount());
				}
				stockMap.put(stock.getID(), stock);
			}
			col.setStocks(stockMap);
		}
		return col;
	}

	/**
	 * Format stock summary based on specified IGeneralCharge.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @return List of OBStockSummary
	 */
	public static List formatStockList(IGeneralCharge genChrg) {
		return GeneralChargeUtil.formatStockList(genChrg);
	}

	/**
	 * Format stock summary list based on trxvalue and comparisonResults.
	 * 
	 * @param trxValue - ICollateralTrxValue
	 * @param compareResults - CompareResult[]
	 * @return List of OBStockSummary
	 */
	public static List formatStockList(ICollateralTrxValue trxValue, List compareResults) {
		return GeneralChargeUtil.formatStockList(trxValue, compareResults);
	}

	/**
	 * Format stock summary based on specified IGeneralCharge.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @return List of OBStockSummary
	 */
	public static List formatFixedAssetOthersList(IGeneralCharge genChrg) {
		return GeneralChargeUtil.formatFixedAssetOthersList(genChrg);
	}

	/**
	 * Format FAO summary list based on trxvalue and comparisonResults.
	 * 
	 * @param trxValue - ICollateralTrxValue
	 * @param compareResults - CompareResult[]
	 * @return List of OBStockSummary
	 */
	public static List formatFixedAssetOthersList(ICollateralTrxValue trxValue, List compareResults) {
		return GeneralChargeUtil.formatFixedAssetOthersList(trxValue, compareResults);
	}

	/**
	 * Get amount representing forex error has occurred.
	 * 
	 * @return - boolean
	 */
	public static Amount getForexErrorAmount() {
		return GeneralChargeUtil.getForexErrorAmount();
	}

	/**
	 * Check if the amount is a result of a forex error.
	 * 
	 * @param amt - Amount
	 * @return - boolean
	 */
	public static boolean isForexErrorAmount(Amount amt) {
		return GeneralChargeUtil.isForexErrorAmount(amt);
	}

	public static boolean isAmountNegativeValue(Amount amt) {
		if ((amt != null) && (amt.getAmountAsBigDecimal() != null) && (amt.getAmountAsBigDecimal().signum() < 0)) {
			return true;
		}
		return false;
	}
}