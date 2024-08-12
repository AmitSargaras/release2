//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.valuation.ValuationException;
import com.integrosys.cms.app.collateral.bus.valuation.support.GoldFeedProfileSingleton;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedEntry;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * AssetSpecGoldMapperHelper
 *
 * Describe this class. Purpose: AssetSpecGoldMapperHelper Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class AssetSpecGoldMapperHelper {

	private final static Logger logger = LoggerFactory.getLogger(AssetSpecGoldMapperHelper.class);

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		AssetSpecGoldForm aForm = (AssetSpecGoldForm) cForm;
		ISpecificChargeGold iObj = (ISpecificChargeGold) obj;
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
		Date stageDate;
		try {
			if (aForm.getPurchasePrice().equals("") && (iObj.getPurchasePrice() != null)
					&& (iObj.getPurchasePrice().getAmount() > 0)) {
				iObj.setPurchasePrice(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPurchasePrice())) {
				iObj.setPurchasePrice(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getPurchasePrice()));
			}
			if(aForm.getMonitorProcess()!=null && !aForm.getMonitorProcess().equals("")){
				iObj.setMonitorProcess(aForm.getMonitorProcess());
			}
			if(aForm.getMonitorFrequency()!=null && !aForm.getMonitorFrequency().equals("")){
				iObj.setMonitorFrequency(aForm.getMonitorFrequency());
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePurchase())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getPurchaseDate(), aForm.getDatePurchase());
				iObj.setPurchaseDate(stageDate);
			}
			else {
				iObj.setPurchaseDate(null);
			}

			iObj.setAssetType(aForm.getAssetType());
			iObj.setEnvRiskyStatus(aForm.getSecEnvRisky());
			iObj.setEnvRiskyRemarks(aForm.getRemarkEnvRisk());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateSecurityEnv())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getEnvRiskyDate(), aForm.getDateSecurityEnv());
				iObj.setEnvRiskyDate(stageDate);
			}
			else {
				iObj.setEnvRiskyDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getIsPhysInsp())) {
				iObj.setIsPhysicalInspection(Boolean.valueOf(aForm.getIsPhysInsp()).booleanValue());
			}

			/*if (!AbstractCommonMapper.isEmptyOrNull(aForm.getPhysInspFreq())&& "true".equalsIgnoreCase(aForm.getIsPhysInsp())) {
				iObj.setPhysicalInspectionFreq(Integer.parseInt(aForm.getPhysInspFreq().trim()));
			}
			else {
				iObj.setPhysicalInspectionFreq(ICMSConstant.INT_INVALID_VALUE);
			}*/
			
			//Added by Pramod Katkar for New Filed CR on 5-08-2013
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
			iObj.setPhysicalInspectionFreqUnit(aForm.getPhysInspFreqUOM());
			}else{
				iObj.setPhysicalInspectionFreqUnit("");
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDatePhyInspec())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getLastPhysicalInspectDate(), aForm
						.getDatePhyInspec());
				iObj.setLastPhysicalInspectDate(stageDate);
				}
			}
			else {
				iObj.setLastPhysicalInspectDate(null);
			}
			if( "true".equalsIgnoreCase(aForm.getIsPhysInsp())){
				if ( !AbstractCommonMapper.isEmptyOrNull(aForm.getNextPhysInspDate())) {
					stageDate = CollateralMapper.compareDate(locale, iObj.getNextPhysicalInspectDate(), aForm
							.getNextPhysInspDate());
					iObj.setNextPhysicalInspectDate(stageDate);
				}
			}
			else {
				iObj.setNextPhysicalInspectDate(null);
			}
			
			//End by Pramod Katkar
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getNominalValue())) {
				iObj.setNominalValue(null);
			}
			else {
				iObj.setNominalValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getNominalValue()));
			}

			boolean goldAmountChange = false;
			if (!aForm.getGoldGrade().equals(iObj.getGoldGrade())
					|| (!AbstractCommonMapper.isEmptyOrNull(aForm.getGoldWeight()) && Double.parseDouble(aForm.getGoldWeight()) != iObj.getGoldWeight())) {
				goldAmountChange = true;
			}

			// new added - for new fields
			iObj.setGoldGrade(aForm.getGoldGrade());
			iObj.setPurchaseReceiptNo(aForm.getPurchaseReceiptNo());

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getGoldUnitPrice().trim())
					|| AbstractCommonMapper.isEmptyOrNull(aForm.getGoldUnitPriceCurrency().trim())) {
				iObj.setGoldUnitPrice(null);
			}
			else {
				iObj.setGoldUnitPrice(CurrencyManager.convertToAmount(locale, aForm.getGoldUnitPriceCurrency().trim(), aForm
						.getGoldUnitPrice()));
			}

			iObj.setGoldUnitPriceUOM(aForm.getGoldUnitPriceUOM());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getCertExpiryDate())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getCertExpiryDate(), aForm.getCertExpiryDate());
				iObj.setCertExpiryDate(stageDate);
			}
			else {
				iObj.setCertExpiryDate(null);
			}

			if (aForm.getGoldWeight().equals("") && (iObj.getGoldWeight() != ICMSConstant.DOUBLE_INVALID_VALUE)
					&& (iObj.getGoldWeight() > 0)) {
				iObj.setGoldWeight(ICMSConstant.DOUBLE_INVALID_VALUE);
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGoldWeight())) {
				iObj.setGoldWeight(Double.parseDouble(aForm.getGoldWeight()));
			}

			iObj.setGoldUOM(aForm.getGoldUOM());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAuctionDate())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getAuctionDate(), aForm.getAuctionDate());
				iObj.setAuctionDate(stageDate);
			}
			else {
				iObj.setAuctionDate(null);
			}

			if (aForm.getAuctionPrice().equals("") && (iObj.getAuctionPrice() != null)
					&& (iObj.getAuctionPrice().getAmount() > 0)) {
				iObj.setAuctionPrice(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getAuctionPrice())) {
				iObj.setAuctionPrice(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getAuctionPrice()));
			}

			iObj.setAuctioneer(aForm.getAuctioneer());

			if (aForm.getSalesProceed().equals("") && (iObj.getSalesProceed() != null)
					&& (iObj.getSalesProceed().getAmount() > 0)) {
				iObj.setSalesProceed(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getSalesProceed())) {
				iObj.setSalesProceed(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getSalesProceed()));
			}

			iObj.setRemarks(aForm.getDescription());

			if (goldAmountChange) {
				try {
					CollateralValuator.getInstance().setCollateralCMVFSV(iObj);
				}
				catch (CollateralException ex) {
					logger.warn("failed to valuation gold collateral [" + iObj + "]", ex);
				}
				catch (ValuationException ex) {
					logger.warn("failed to valuation gold collateral [" + iObj + "]", ex);
				}
			}

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to object for gold collateral [" + iObj + "]", ex);
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AssetSpecGoldForm aForm = (AssetSpecGoldForm) cForm;
		ISpecificChargeGold iObj = (ISpecificChargeGold) obj;
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);

		Amount amt;
		try {
			aForm.setAssetType(iObj.getAssetType());
			if ((iObj.getPurchasePrice() != null) && (iObj.getPurchasePrice().getCurrencyCode() != null)) {
				if (iObj.getPurchasePrice().getAmount() > 0) {
					aForm.setPurchasePrice(CurrencyManager.convertToString(locale, iObj.getPurchasePrice()));
				}
			}
			if(iObj.getMonitorProcess()!=null && !iObj.getMonitorProcess().equals("")){
				aForm.setMonitorProcess(iObj.getMonitorProcess());
			}
			if(iObj.getMonitorFrequency()!=null && !iObj.getMonitorFrequency().equals("")){
				aForm.setMonitorFrequency(iObj.getMonitorFrequency());
			}
			aForm.setDatePurchase(DateUtil.formatDate(locale, iObj.getPurchaseDate()));
			if (iObj.getEnvRiskyStatus() != null) {
				aForm.setSecEnvRisky(iObj.getEnvRiskyStatus().trim());
			}
			aForm.setRemarkEnvRisk(iObj.getEnvRiskyRemarks());
			aForm.setDateSecurityEnv(DateUtil.formatDate(locale, iObj.getEnvRiskyDate()));
			aForm.setIsPhysInsp(String.valueOf(iObj.getIsPhysicalInspection()));
			if (iObj.getPhysicalInspectionFreq() >= 0) {
				aForm.setPhysInspFreq(String.valueOf(iObj.getPhysicalInspectionFreq()));
			}
			if (iObj.getPhysicalInspectionFreqUnit() != null) {
				aForm.setPhysInspFreqUOM(iObj.getPhysicalInspectionFreqUnit().trim());
			}
			if(iObj.getLastPhysicalInspectDate()!=null){
			aForm.setDatePhyInspec(DateUtil.formatDate(locale, iObj.getLastPhysicalInspectDate()));
			}
			if(iObj.getNextPhysicalInspectDate()!=null){
			aForm.setNextPhysInspDate(DateUtil.formatDate(locale, iObj.getNextPhysicalInspectDate()));
			}
			amt = iObj.getNominalValue();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, amt));
			}

			// add for new fields
			if (iObj.getGoldGrade() != null) {
				aForm.setGoldGrade(iObj.getGoldGrade().trim());
			}
			if (iObj.getPurchaseReceiptNo() != null) {
				aForm.setPurchaseReceiptNo(iObj.getPurchaseReceiptNo().trim());
			}

			if ((iObj.getGoldUnitPrice() != null) && (iObj.getGoldUnitPrice().getCurrencyCode() != null)) {
				if (iObj.getGoldUnitPrice().getAmount() > 0) {
					aForm.setGoldUnitPrice(CurrencyManager.convertToString(locale, iObj.getGoldUnitPrice()));
				}
				aForm.setGoldUnitPriceCurrency(iObj.getGoldUnitPrice().getCurrencyCode());
			}

			amt = getFeedGoldUnitPrice(iObj.getGoldGrade());
			if (amt != null && (amt.getCurrencyCode() != null)) {
				if (amt.getAmount() > 0) {
					aForm.setFeedGoldUnitPrice(UIUtil.formatAmount(amt, 2, locale, true));
				}
			}
			aForm.setCertExpiryDate(DateUtil.formatDate(locale, iObj.getCertExpiryDate()));

			if ((iObj.getGoldWeight() != ICMSConstant.DOUBLE_INVALID_VALUE && iObj.getGoldWeight() > 0)) {
				aForm.setGoldWeight(String.valueOf(iObj.getGoldWeight()));
			}
			if (iObj.getGoldUOM() != null) {
				aForm.setGoldUOM(iObj.getGoldUOM().trim());
			}
			aForm.setAuctionDate(DateUtil.formatDate(locale, iObj.getAuctionDate()));

			if ((iObj.getAuctionPrice() != null) && (iObj.getAuctionPrice().getCurrencyCode() != null)) {
				if (iObj.getAuctionPrice().getAmount() > 0) {
					aForm.setAuctionPrice(CurrencyManager.convertToString(locale, iObj.getAuctionPrice()));
				}
			}
			if (iObj.getAuctioneer() != null) {
				aForm.setAuctioneer(iObj.getAuctioneer().trim());
			}
			if ((iObj.getSalesProceed() != null) && (iObj.getSalesProceed().getCurrencyCode() != null)) {
				if (iObj.getSalesProceed().getAmount() > 0) {
					aForm.setSalesProceed(CurrencyManager.convertToString(locale, iObj.getSalesProceed()));
				}
			}
			/*
			 * if (iObj.getDepreciateRate() != null &&
			 * iObj.getDepreciateRate().getCurrencyCode() != null) { if
			 * (iObj.getDepreciateRate().getAmount() > 0) {
			 * aForm.setDepreciateRate(CurrencyManager.convertToString(locale,
			 * iObj.getDepreciateRate())); } }
			 */
			if (iObj.getGoldUnitPriceUOM() != null) {
				aForm.setGoldUnitPriceUOM(iObj.getGoldUnitPriceUOM().trim());
			}

			aForm.setDescription(iObj.getRemarks());

		}
		catch (Exception ex) {
			throw new MapperException("failed to map from object to form for gold collateral [" + iObj + "]", ex);
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {

		return ((ISpecificChargeGold) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

	private static Amount getFeedGoldUnitPrice(String goldGrade) {
		GoldFeedProfileSingleton singleton = (GoldFeedProfileSingleton) BeanHouse.get("goldFeedProfileSingleton");
		OBGoldFeedEntry goldFeed = (OBGoldFeedEntry) singleton.getProfile().get(goldGrade);
		if (goldFeed != null) {
			return new Amount(goldFeed.getUnitPrice(), new CurrencyCode(goldFeed.getCurrencyCode()));
		}
		return null;
	}
}
