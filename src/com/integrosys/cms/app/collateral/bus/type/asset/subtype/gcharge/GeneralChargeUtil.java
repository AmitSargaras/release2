/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/GeneralChargeUtil.java,v 1.13 2006/07/14 03:21:58 jychong Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Utility Class for Asset Based General Charge
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/07/14 03:21:58 $ Tag: $Name: $
 */
public abstract class GeneralChargeUtil {
	public static final int FAO_TYPE = 1;

	public static final int STOCK_TYPE = 2;

	public static String generateNewID(String prefix, char filler, int maxLength, int index) {
		StringBuffer buf = new StringBuffer();
		buf.append(prefix);
		String indexStr = Integer.toString(index);
		int indexLength = indexStr.length();
		while (buf.length() + indexLength < maxLength) {
			buf.append(filler);
		}
		buf.append(indexStr);
		return buf.toString();
	}

	/**
	 * Get amount representing forex error has occurred.
	 * 
	 * @return - boolean
	 */
	public static Amount getForexErrorAmount() {
		return new Amount(new BigDecimal(0), new CurrencyCode(ICMSConstant.CURRENCYCODE_INVALID_VALUE));
	}

	/**
	 * Check if the amount is a result of a forex error.
	 * 
	 * @param amt - Amount
	 * @return - boolean
	 */
	public static boolean isForexErrorAmount(Amount amt) {
		boolean isForexErrorAmt = ((amt != null) && (amt.getAmount() == 0) && amt.getCurrencyCode().equals(
				ICMSConstant.CURRENCYCODE_INVALID_VALUE));
		return isForexErrorAmt;
	}

	/**
	 * Convert an amount to a specified target currency.
	 * 
	 * @param amtToBeConverted - AMount
	 * @param convertToCCY - CurrencyCode
	 * @return Amount - converted amount in target currency.
	 * @return '0' amount with no currency if forex error occurs
	 */
	public static Amount convertAmount(Amount amtToBeConverted, CurrencyCode convertToCCY) {
		if ((amtToBeConverted == null) || (convertToCCY == null)) {
			return null;
		}
		Amount convertedAmt = null;
		try {
			convertedAmt = ForexHelper.getInstance().convert(amtToBeConverted, convertToCCY);
		}
		catch (Exception e) {
			convertedAmt = null;
		}
		if (convertedAmt == null) {
			DefaultLogger.info("GeneralChargeUtil.convertAmount", "Forex Error occurred while converting "
					+ amtToBeConverted + " to " + convertToCCY);
			convertedAmt = getForexErrorAmount();
		}
		return convertedAmt;
	}

	/**
	 * Add amt to total.
	 * 
	 * @param total - Amount
	 * @param amtToAdd - Amount
	 * @return Amount new total
	 */
	public static Amount add(Amount total, Amount amtToAdd) throws ChainedException {
		if (isForexErrorAmount(total) || isForexErrorAmount(amtToAdd)) {
			return getForexErrorAmount();
		}
		if (total == null) {
			return amtToAdd;
		}
		return (amtToAdd == null) ? total : total.add(amtToAdd);
	}

	/**
	 * Get the smaller of the 2 amount. This assumes the ccy of both amount to
	 * be the same.
	 * 
	 * @param a - Amount
	 * @param b - Amount
	 * @return Amount
	 */
	public static Amount min(Amount a, Amount b) {
		if ((a == null) || (b == null)) {
			return null;
		}
		if (isForexErrorAmount(a) || isForexErrorAmount(b)) {
			return getForexErrorAmount();
		}

		int compareResult = a.getAmountAsBigDecimal().compareTo(b.getAmountAsBigDecimal());
		return (compareResult <= 0) ? a : b;
	}

	/**
	 * Format stock summary based on specified IGeneralCharge.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @return List of OBStockSummary
	 */
	public static List formatStockList(IGeneralCharge genChrg) {
		if ((genChrg == null) || (genChrg.getStocks() == null)) {
			return null;
		}
		return formatList(genChrg, STOCK_TYPE);
	}

	/**
	 * Format stock summary list based on trxvalue and comparisonResults.
	 * 
	 * @param trxValue - ICollateralTrxValue
	 * @param compareResults - CompareResult[]
	 * @return List of OBStockSummary
	 */
	public static List formatStockList(ICollateralTrxValue trxValue, List compareResults) {
		if ((trxValue == null) || (compareResults == null) || (compareResults.size() == 0)) {
			return null;
		}
		return formatList(trxValue, compareResults);
	}

	/**
	 * Format stock summary based on specified IGeneralCharge.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @return List of OBStockSummary
	 */
	public static List formatFixedAssetOthersList(IGeneralCharge genChrg) {
		if ((genChrg == null) || (genChrg.getFixedAssetOthers() == null)) {
			return null;
		}
		return formatList(genChrg, FAO_TYPE);
	}

	/**
	 * Format FAO summary list based on trxvalue and comparisonResults.
	 * 
	 * @param trxValue - ICollateralTrxValue
	 * @param compareResults - CompareResult[]
	 * @return List of OBStockSummary
	 */
	public static List formatFixedAssetOthersList(ICollateralTrxValue trxValue, List compareResults) {
		if ((trxValue == null) || (compareResults == null) || (compareResults.size() == 0)) {
			return null;
		}
		return formatList(trxValue, compareResults);
	}

	/**
	 * Format summary list based on genChrg and subtype required.
	 * 
	 * @param genChrg
	 * @param type
	 * @return
	 */
	private static List formatList(IGeneralCharge genChrg, int type) {

		DefaultLogger.debug("GeneralChargeUtil.formatList(IGeneralCharge, int)", ">>>>>>>>>> start");

		if (genChrg == null) {
			return null;
		}

		CurrencyCode cmsSecurityCCY = new CurrencyCode(genChrg.getCurrencyCode());

		Map subTypeMap = getMap(genChrg, type);
		DefaultLogger.debug("GeneralChargeUtil.formatList(IGeneralCharge, int)", "Map: "
				+ (subTypeMap == null ? "null" : "not null :" + subTypeMap.size()));
		Set refIDsKeySet = subTypeMap.keySet();
		String[] refIDs = (String[]) refIDsKeySet.toArray(new String[refIDsKeySet.size()]);
		Arrays.sort(refIDs);

		DefaultLogger.debug("GeneralChargeUtil.formatList(IGeneralCharge, int)", "== cms security ccy : "
				+ cmsSecurityCCY);

		List summaryList = null;
		try {
			HashMap availableCoverageMap = new HashMap(refIDs.length);
			HashMap insrExpiryStatusMap = new HashMap(refIDs.length);
			IGeneralChargeSubType genChrgSubType = null;
			OBGeneralChargeSubTypeSummary summary = null;
			int idx = 0;
			summaryList = new ArrayList();
			while (idx < refIDs.length) {
				genChrgSubType = (IGeneralChargeSubType) subTypeMap.get(refIDs[idx++]);
				// skip if no valid genChrgSubType found
				if (genChrgSubType == null) {
					continue;
				}
				DefaultLogger.debug("GeneralChargeUtil.formatList(IGeneralCharge, int)", "refID :" + refIDs[idx - 1]
						+ " is not null");
				summary = getSummary(genChrgSubType, genChrg, cmsSecurityCCY, availableCoverageMap,
						insrExpiryStatusMap, null);
				summaryList.add(summary);
			}
		}
		catch (Exception e) {
			summaryList = null;
			DefaultLogger.error("GeneralChargeUtil.formatList", "Error encountered when formatting summary list", e);
		}
		return summaryList;
	}

	private static Map getMap(IGeneralCharge genChrg, int type) {
		if (FAO_TYPE == type) {
			return genChrg.getFixedAssetOthers();
		}
		else if (STOCK_TYPE == type) {
			return genChrg.getStocks();
		}
		return null;
	}

	/**
	 * Format summary list based on trxvalue and comparisonResults.
	 * 
	 * @param trxValue - ICollateralTrxValue
	 * @param comparisonResults - CompareResult[]
	 * @return List of OBStockSummary
	 */
	private static List formatList(ICollateralTrxValue trxValue, List comparisonResults) {
		if ((trxValue == null) || (comparisonResults == null) || (comparisonResults.size() == 0)) {
			return null;
		}

		List summaryList = new ArrayList();
		try {
			IGeneralCharge stage = (IGeneralCharge) trxValue.getStagingCollateral();
			IGeneralCharge actual = (IGeneralCharge) trxValue.getCollateral();
			CurrencyCode cmsSecurityCCY = new CurrencyCode(stage.getCurrencyCode());

			HashMap availableCoverageMap = new HashMap(comparisonResults.size());
			HashMap insrExpiryStatusMap = new HashMap(comparisonResults.size());
			OBGeneralChargeSubTypeSummary summary = null;
			IGeneralChargeSubType genChrgSubType = null;

			if (((CompareResult) comparisonResults.get(0)).getObj() instanceof IStock) {
				Collections.sort(comparisonResults, new StockComparator());
			}
			else {
				Collections.sort(comparisonResults, new FixedAssetOthersComparator());
			}

			CompareResult result = null;
			Iterator compareResultIterator = comparisonResults.iterator();
			while (compareResultIterator.hasNext()) {
				result = (CompareResult) compareResultIterator.next();
				genChrgSubType = (IGeneralChargeSubType) result.getObj();
				// skip if no valid genChrgSubType found
				if (genChrgSubType == null) {
					continue;
				}
				if (result.getKey().equals(CompareOBUtil.OB_DELETED)) {
					// instantiate summary with actual obj
					summary = getSummary(genChrgSubType, actual, cmsSecurityCCY, availableCoverageMap,
							insrExpiryStatusMap, result);
				}
				else {
					// instantiate summary with staging obj
					summary = getSummary(genChrgSubType, stage, cmsSecurityCCY, availableCoverageMap,
							insrExpiryStatusMap, result);
				}
				summaryList.add(summary);
			}
		}
		catch (Exception e) {
			summaryList = null;
			DefaultLogger.error("GeneralChargeUtil.formatList", "Error encountered when formatting summary list", e);
		}
		return summaryList;
	}

	/**
	 * Helper method to get OBGeneralChargeSubTypeSummary.
	 * 
	 * @param genChrgSubType
	 * @param genChrg
	 * @param cmsSecurityCCY
	 * @param availableCoverageMap
	 * @param insrExpiryStatusMap
	 * @param compareResult
	 * @return
	 * @throws Exception
	 */
	private static OBGeneralChargeSubTypeSummary getSummary(IGeneralChargeSubType genChrgSubType,
			IGeneralCharge genChrg, CurrencyCode cmsSecurityCCY, Map availableCoverageMap, Map insrExpiryStatusMap,
			CompareResult compareResult) throws Exception {
		if (genChrgSubType instanceof IStock) {
			return createStockSummary((IStock) genChrgSubType, genChrg, cmsSecurityCCY, availableCoverageMap,
					insrExpiryStatusMap, compareResult);
		}
		else if (genChrgSubType instanceof IFixedAssetOthers) {
			return createFAOSummary((IFixedAssetOthers) genChrgSubType, genChrg, cmsSecurityCCY, availableCoverageMap,
					insrExpiryStatusMap, compareResult);
		}
		return null;

	}

	/**
	 * Helper method to create new stock summary.
	 * 
	 * @param stock
	 * @param availableCoverageMap
	 * @return
	 */
	private static OBStockSummary createStockSummary(IStock stock, IGeneralCharge genChrg, CurrencyCode cmsSecurityCCY,
			Map availableCoverageMap, Map insrExpiryStatusMap, CompareResult compareResult) throws Exception {
		OBStockSummary stockSummary = new OBStockSummary();
		stockSummary = (OBStockSummary) createSummary(stockSummary, stock, genChrg, cmsSecurityCCY,
				availableCoverageMap, insrExpiryStatusMap, compareResult);
		stockSummary.setStockTypes(stock.getStockType());
		stockSummary.setCreditorAmount(convertAmount(stock.getCreditorAmt(), cmsSecurityCCY));

		Amount totalInsrCoverageAmt = getTotalInsrCoverageAmount(genChrg, stockSummary.getInsuranceSummary());
		stockSummary.setTotalInsrCoverageAmt(totalInsrCoverageAmt);

		// DefaultLogger.debug("GeneralChargeUtil.createStockSummary",
		// ">>>>> cms_collateral_id : " + genChrg.getCollateralID());
		// DefaultLogger.debug("GeneralChargeUtil.createStockSummary",
		// ">>>>> stock id : " + stockSummary.getID());
		// DefaultLogger.debug("GeneralChargeUtil.createStockSummary",
		// ">>>>> netValue : " + stockSummary.getNetValue());
		// DefaultLogger.debug("GeneralChargeUtil.createStockSummary",
		// ">>>>> totalInsrCoverageAmt : " + totalInsrCoverageAmt);

		stockSummary.setRecoverableAmount(min(stockSummary.getNetValue(), totalInsrCoverageAmt));

		// DefaultLogger.debug("GeneralChargeUtil.createStockSummary",
		// ">>>>> recoverableamt : " + stockSummary.getRecoverableAmount());

		return stockSummary;
	}

	/**
	 * Helper method to create new fao summary.
	 * 
	 * @param fao
	 * @param availableCoverageMap
	 * @return
	 */
	private static OBFixedAssetOthersSummary createFAOSummary(IFixedAssetOthers fao, IGeneralCharge genChrg,
			CurrencyCode cmsSecurityCCY, Map availableCoverageMap, Map insrExpiryStatusMap, CompareResult compareResult)
			throws Exception {
		OBFixedAssetOthersSummary faoSummary = new OBFixedAssetOthersSummary();
		faoSummary = (OBFixedAssetOthersSummary) createSummary(faoSummary, fao, genChrg, cmsSecurityCCY,
				availableCoverageMap, insrExpiryStatusMap, compareResult);
		faoSummary.setDescription(fao.getDescription());

		Amount totalInsrCoverageAmt = getTotalInsrCoverageAmount(genChrg, faoSummary.getInsuranceSummary());
		faoSummary.setTotalInsrCoverageAmt(totalInsrCoverageAmt);
		faoSummary.setRecoverableAmount(min(faoSummary.getNetValue(), totalInsrCoverageAmt));

		return faoSummary;
	}

	/**
	 * Helper method to create new general charge subtype summary.
	 * 
	 * @param genChrgSubType
	 * @param genChrg
	 * @param cmsSecurityCCY
	 * @param availableCoverageMap
	 * @param insrExpiryStatusMap
	 * @param compareResult
	 * @return
	 * @throws Exception
	 */
	private static OBGeneralChargeSubTypeSummary createSummary(OBGeneralChargeSubTypeSummary summary,
			IGeneralChargeSubType genChrgSubType, IGeneralCharge genChrg, CurrencyCode cmsSecurityCCY,
			Map availableCoverageMap, Map insrExpiryStatusMap, CompareResult compareResult) throws Exception {
		summary.setID(genChrgSubType.getID());
		summary.setCompareResultKey((compareResult == null) ? null : compareResult.getKey());
		summary.setAddress(genChrgSubType.getAddress());
		summary.setValuationDate(genChrgSubType.getValuationDate());
		summary.setValuerName(genChrgSubType.getValuerName());

		// gross value
		summary.setGrossValue(convertAmount(genChrgSubType.getGrossValue(), cmsSecurityCCY));

		// net value
		Amount convertedNetValue = convertAmount(genChrgSubType.getNetValue(), cmsSecurityCCY);
		summary.setNetValue(convertedNetValue);

		// insurance details
		int type = (genChrgSubType instanceof IStock) ? OBGeneralCharge.TYPE_STOCK
				: OBGeneralCharge.TYPE_FIXEDASSETOTHERS;
		OBInsuranceSummary[] insuranceSummaryList = getInsuranceSummary(genChrgSubType, genChrg, cmsSecurityCCY,
				availableCoverageMap, insrExpiryStatusMap, type);
		summary.setInsuranceSummary(insuranceSummaryList);

		return summary;
	}

	/**
	 * Helper method to get total recoverable value given a list of insurance
	 * summary.
	 * 
	 * @param genChrg - IGeneralCharge
	 * @param insuranceSummaryList - OBInsuranceSummary[]
	 * @return Amount
	 */
	private static Amount getTotalInsrCoverageAmount(IGeneralCharge genChrg, OBInsuranceSummary[] insuranceSummaryList) {
		Amount totalCoverageAmt = null;
		try {
			for (int i = 0; i < insuranceSummaryList.length; i++) {
				Amount coverageAmt = insuranceSummaryList[i].getCoverageAmount();
				totalCoverageAmt = GeneralChargeUtil.add(totalCoverageAmt, coverageAmt);
			}
		}
		catch (ChainedException ce) {
			ce.printStackTrace();
		}
		return (totalCoverageAmt == null) ? new Amount(0, genChrg.getCurrencyCode()) : totalCoverageAmt;
	}

	/**
	 * Helper method to get the list of insurance summary for a
	 * genChargeSubType.
	 * 
	 * @param genChargeSubType
	 * @param genChrg - IGeneralCharge
	 * @param cmsSecurityCCY - CurrencyCode
	 * @param availableCoverageMap - HashMap containing available remaining
	 *        insured amount. Key as insrID, value as Amoutn denoting remaining
	 *        amount.
	 * @param insrExpiryStatusMap - HashMap containing expiry status of
	 *        insurance. Key as insrID, value as Boolean denoting if the insr
	 *        has expired.
	 * @return
	 */
	public static OBInsuranceSummary[] getInsuranceSummary(IGeneralChargeSubType genChargeSubType,
			IGeneralCharge genChrg, CurrencyCode cmsSecurityCCY, Map availableCoverageMap, Map insrExpiryStatusMap,
			int type) {
		ArrayList insrSummaryList = null;
		String refID = genChargeSubType.getID();

		// DefaultLogger.debug("GeneralChargeUtil.getInsuranceSummary",
		// "genChargeSubType to get insurance summary for : " + refID);
		try {
			Map insrMap = genChrg.getInsurance();
			List insrList = genChrg.getInsuranceIDList(refID, type);

			if ((insrList != null) && (insrList.size() > 0)) {

				Collections.sort(insrList, new GenChargeMapEntryComparator());
				Iterator insrIterator = insrList.iterator();

				// convert stock gross amt to cms security ccy
				Amount convertedStockGrossValue = convertAmount(genChargeSubType.getGrossValue(), cmsSecurityCCY);
				if (convertedStockGrossValue == null) {
					convertedStockGrossValue = new Amount(new BigDecimal(0), cmsSecurityCCY);
				}

				// create container to hold the remaining stock gross value to
				// be covered
				HashMap grossValueToCoverMap = new HashMap(1);
				grossValueToCoverMap.put(refID, convertedStockGrossValue);

				// loop thru insr linked to this subtype
				insrSummaryList = new ArrayList();
				while (insrIterator.hasNext()) {
					IGenChargeMapEntry entry = (IGenChargeMapEntry) insrIterator.next();
					String insrID = (String) entry.getInsuranceID();
					IInsurancePolicy insr = (IInsurancePolicy) insrMap.get(insrID);
					OBInsuranceSummary insrSummary = getInsuranceSummary(insr, genChrg, cmsSecurityCCY,
							availableCoverageMap, insrExpiryStatusMap, refID, grossValueToCoverMap);
					if (insrSummary != null) {
						insrSummaryList.add(insrSummary);
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error("GeneralChargeUtil.getInsuranceSummary",
					"Exception occured when getting summary list for " + refID, e);
		}
		return (insrSummaryList == null) ? new OBInsuranceSummary[0] : (OBInsuranceSummary[]) insrSummaryList
				.toArray(new OBInsuranceSummary[insrSummaryList.size()]);
	}

	/**
	 * Helper method to get the insurance summary given a insurance linked to
	 * the subtype.
	 * 
	 * @param insr
	 * @param genChrg
	 * @param cmsSecurityCCY
	 * @param availableCoverageMap
	 * @param insrExpiryStatusMap
	 * @param subTypeRefID
	 * @param grossValueToCoverMap
	 * @return OBInsuranceSummary
	 */
	private static OBInsuranceSummary getInsuranceSummary(IInsurancePolicy insr, IGeneralCharge genChrg,
			CurrencyCode cmsSecurityCCY, Map availableCoverageMap, Map insrExpiryStatusMap, String subTypeRefID,
			Map grossValueToCoverMap) throws ChainedException {
		OBInsuranceSummary insrSummary = null;
		if (insr != null) {
			String insrID = insr.getRefID();
			insrSummary = new OBInsuranceSummary();
			insrSummary.setRefID(insrID);
			insrSummary.setPolicyNumber(insr.getPolicyNo());
			// DefaultLogger.debug("GeneralChargeUtil.getInsuranceSummary",
			// "insr policy: "+insr.getPolicyNo());

			// check if the insurance has expired
			boolean isInsrExpired = getExpiryStatus(insr, genChrg, insrExpiryStatusMap);
			// DefaultLogger.debug("GeneralChargeUtil.getInsuranceSummary()",
			// "insr id: " + insrID + " isInsrExpired: " + (new
			// Boolean(isInsrExpired)));
			insrSummary.setExpired(isInsrExpired);

			// calculate the insr coverage for the genChargeSubType
			if (!isInsrExpired) {

				// get insured amt
				Amount availableInsrCoverageAmt = null;
				if (!availableCoverageMap.containsKey(insrID)) {
					// the first time this insurance has been linked to a
					// genChargeSubType
					availableInsrCoverageAmt = convertAmount(insr.getInsuredAmount(), cmsSecurityCCY);
					insrSummary.setInsuredAmount(availableInsrCoverageAmt);
				}
				else {
					// this insurance has been linked to some other
					// genChargeSubType
					insrSummary.setInsuredAmount(null);
					availableInsrCoverageAmt = (Amount) availableCoverageMap.get(insrID);
				}

				Amount remainingGrossValueToCover = (Amount) grossValueToCoverMap.get(subTypeRefID);
				boolean isGrossValueForexError = isForexErrorAmount(remainingGrossValueToCover);
				boolean isInsrForexError = isForexErrorAmount(availableInsrCoverageAmt);
				Amount coverageAmt = null;
				if (isGrossValueForexError || isInsrForexError) {
					coverageAmt = getForexErrorAmount();
				}
				else {
					coverageAmt = min(availableInsrCoverageAmt, remainingGrossValueToCover);
					if (coverageAmt != null) {
						// calculate the reminaing stock gross value to be
						// covered by insr
						grossValueToCoverMap.put(subTypeRefID, remainingGrossValueToCover.subtract(coverageAmt));
						// calculate the insured amt remaining/available for the
						// other stocks
						availableInsrCoverageAmt = availableInsrCoverageAmt.subtract(coverageAmt);
					}
				}
				insrSummary.setCoverageAmount(coverageAmt);
				availableCoverageMap.put(insrID, availableInsrCoverageAmt);
				//DefaultLogger.debug("GeneralChargeUtil.getInsuranceSummary()",
				// "insrID: " + insrID
				// + " availableInsrCoverageAmt: " + availableInsrCoverageAmt);
			}
		}
		return insrSummary;
	}

	/**
	 * Helper method to get expiry status for an insurance from the expiry
	 * status map. If insurance not found in the map, check if insurance has
	 * expired and put result in map.
	 * 
	 * @param insr - IInsurancePolicy : cannot be null
	 * @param genChrg - IGeneralCharge : cannot be null
	 * @param insrExpiryStatusMap : cannot be null
	 * @return boolean - expiry status of an insurance
	 */
	private static boolean getExpiryStatus(IInsurancePolicy insr, IGeneralCharge genChrg, Map insrExpiryStatusMap) {
		boolean isInsrExpired = false;
		String insrID = insr.getRefID();
		if (!insrExpiryStatusMap.containsKey(insrID)) {
			// calculate grace period for this insr
			Date gracePeriodEndDate = genChrg.getInsuranceGracePeriodEndDate(insr);
			if (gracePeriodEndDate != null) {
				gracePeriodEndDate = DateUtil.initializeStartDate(gracePeriodEndDate);
				Date currentDate = DateUtil.clearTime(DateUtil.getDate());
				isInsrExpired = ((gracePeriodEndDate != null) && (currentDate.compareTo(gracePeriodEndDate) > 0));
			}
			else {
				// if no expiry date found, default insr as expired
				isInsrExpired = true;
			}

			insrExpiryStatusMap.put(insrID, new Boolean(isInsrExpired));
		}
		else {
			isInsrExpired = ((Boolean) insrExpiryStatusMap.get(insrID)).booleanValue();
		}
		return isInsrExpired;
	}

}