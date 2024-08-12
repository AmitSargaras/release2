/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/PortItemMapper.java,v 1.35 2005/10/10 07:45:05 priya Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.OBMarketableEquity;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.StockExchangeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: priya $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2005/10/10 07:45:05 $ Tag: $Name: $
 */

public class PortItemMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		PortItemForm aForm = (PortItemForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this + " - mapFormToOB", "Locale is: " + locale);
		IMarketableCollateral iMarket = (IMarketableCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());

		IMarketableEquity[] iMarketEquity = iMarket.getEquityList();
		DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");
		int index = -1;
		if(inputs.get("indexID")!= null)
		{
		 index = Integer.parseInt((String) inputs.get("indexID"));
		}
		IMarketableEquity obPortItem = null;
		if (index == -1) {
			obPortItem = new OBMarketableEquity();
		}
		else {
			obPortItem = iMarketEquity[index];
			// obPortItem = getItem(iMarketEquity, index);
		}

		Date stageDate;
		try {

			obPortItem.setItemCurrencyCode(iMarket.getCurrencyCode());

			DefaultLogger.debug(this, "Equity type: ---------------------" + aForm.getEquityType());

			if (iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_BONDS_FOREIGN)
					|| iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL)) {
				obPortItem.setEquityType(aForm.getBondType());
				obPortItem.setStockExchange(aForm.getBondMarket());
				obPortItem.setStockExchangeCountry(aForm.getCountryBondMarket());
			}
			else {
				if (iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL))
					obPortItem.setEquityType(ICMSConstant.EQUITY_TYPE_STOCK);
				else
					obPortItem.setEquityType(aForm.getEquityType());
				obPortItem.setStockExchange(aForm.getStockExchange());
				obPortItem.setStockExchangeCountry(aForm.getCountryStockExchange());
			}

			obPortItem.setCertificateNo(aForm.getCertNo());
			obPortItem.setAgentName(aForm.getNomineeName());

			if (!isEmptyOrNull(aForm.getDateConfirmNomine())) {
				stageDate = CollateralMapper.compareDate(locale, obPortItem.getAgentConfirmDate(), aForm
						.getDateConfirmNomine());
				obPortItem.setAgentConfirmDate(stageDate);
			}
			else {
				obPortItem.setAgentConfirmDate(null);
			}

			obPortItem.setRegisteredName(aForm.getRegisteredName());

			if (isEmptyOrNull(aForm.getNoOfUnit())) {
				obPortItem.setNoOfUnits(0);
			}else {
			//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
				obPortItem.setNoOfUnits(Double.parseDouble(aForm.getNoOfUnit().trim()));
			//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.	
			}

			/*
			 * if (isEmptyOrNull(aForm.getMinimalFSV())) {
			 * obPortItem.setMinimalFSV(null); } else {
			 * obPortItem.setMinimalFSV(CurrencyManager.convertToAmount(locale,
			 * obPortItem.getItemCurrencyCode(), aForm.getMinimalFSV())); }
			 */

			if (isEmptyOrNull(aForm.getNominalValue())) {
				obPortItem.setNominalValue(null);
			}
			else {
				obPortItem.setNominalValue(CurrencyManager.convertToAmount(locale, iMarket.getSCICurrencyCode(),
						aForm.getNominalValue()));
			}
			if (!isEmptyOrNull(aForm.getSecBlackListed())) {
				obPortItem.setIsCollateralBlacklisted(Boolean.valueOf(aForm.getSecBlackListed()).booleanValue());
			}

			if (!isEmptyOrNull(aForm.getLocalStockExchange())) {
				obPortItem.setIsLocalStockExchange(Boolean.valueOf(aForm.getLocalStockExchange()).booleanValue());
			}

			obPortItem.setIssuerName(aForm.getIssuerName());
			obPortItem.setIssuerIdType(aForm.getIsserIdentType());
			// obPortItem.setLocalStockExchange(aForm.getLocalStockExchange());
			obPortItem.setNameOfIndex(aForm.getIndexName());

			if (!isEmptyOrNull(aForm.getIsGuaranteeByGovt())) {
				obPortItem.setIsGuaranteeByGovt(Boolean.valueOf(aForm.getIsGuaranteeByGovt()).booleanValue());
			}
			if (!isEmptyOrNull(aForm.getIsSecurityReferred())) {
				obPortItem.setIsCollateralBlacklisted(Boolean.valueOf(aForm.getIsSecurityReferred()).booleanValue());
			}

			obPortItem.setGovernmentName(aForm.getGovtName());
			if (aForm.getBaselComplaintUT().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
				obPortItem.setBaselCompliant("");
			}
			else {
				obPortItem.setBaselCompliant(aForm.getBaselComplaintUT());
			}
			obPortItem.setIsExchangeCtrlObtained(aForm.getXchangeCtrlObtained());
			obPortItem.setLeadManager(aForm.getLeadMgr());
			obPortItem.setSettlementOrganisation(aForm.getSettleOrg());

			if (!isEmptyOrNull(aForm.getBondIssueDate())) {
				stageDate = CollateralMapper.compareDate(locale, obPortItem.getBondIssueDate(), aForm
						.getBondIssueDate());
				obPortItem.setBondIssueDate(stageDate);
			}
			else {
				obPortItem.setBondIssueDate(null);
			}
			if (!isEmptyOrNull(aForm.getBondMatDate())) {
				stageDate = CollateralMapper.compareDate(locale, obPortItem.getBondMaturityDate(), aForm
						.getBondMatDate());
				obPortItem.setBondMaturityDate(stageDate);
			}
			else {
				obPortItem.setBondMaturityDate(null);
			}

			obPortItem.setRIC(aForm.getRic());

			obPortItem.setIsinCode(aForm.getIsinCode());
			obPortItem.setStockCode(aForm.getStockCode());
			//obPortItem.setRecognizeExchange(aForm.getRecognizeExchange());

			if (!isEmptyOrNull(aForm.getSecMaturityDate())) {
				stageDate = CollateralMapper.compareDate(locale, obPortItem.getCollateralMaturityDate(), aForm
						.getSecMaturityDate());
				obPortItem.setCollateralMaturityDate(stageDate);
			}
			else {
				obPortItem.setCollateralMaturityDate(null);
			}

			obPortItem.setCollateralCustodianType(aForm.getSecCustodianType());
			if (!isEmptyOrNull(aForm.getSecCustodianType())) {
				if (aForm.getSecCustodianType().equals(ICMSConstant.INTERNAL_COL_CUSTODIAN)) {
					obPortItem.setCollateralCustodian(aForm.getSecCustodianInt());
				}
				else if (aForm.getSecCustodianType().equals(ICMSConstant.EXTERNAL_COL_CUSTODIAN)) {
					obPortItem.setCollateralCustodian(aForm.getSecCustodianExt());
				}
			}

			if (iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL)
					|| iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL)) {

				if (!isEmptyOrNull(aForm.getUnitPriceCcyCode())) {
					
					obPortItem.setUnitPriceCcyCode(aForm.getUnitPriceCcyCode());
					if (isEmptyOrNull(aForm.getUnitPrice())) {
						obPortItem.setUnitPrice(CurrencyManager
								.convertToAmount(locale, obPortItem
										.getUnitPriceCcyCode(), "0"));
					} else {
						obPortItem.setUnitPrice(CurrencyManager
								.convertToAmount(locale, obPortItem
										.getUnitPriceCcyCode(), aForm
										.getUnitPrice()));
					}
				}
			}
			
			if(iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL) || iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_OTHERLISTED_LOCAL)){
					
				obPortItem.setUnitPriceCcyCode(iMarket.getSCICurrencyCode());
				if (isEmptyOrNull(aForm.getUnitPrice())) {
					obPortItem.setValuationUnitPrice(CurrencyManager
							.convertToAmount(locale, obPortItem
									.getUnitPriceCcyCode(), "0"));
				} else {
					obPortItem.setValuationUnitPrice(CurrencyManager
						.convertToAmount(locale, obPortItem.getUnitPriceCcyCode(), 
						aForm.getUnitPrice()));
				}	
			}
			
			obPortItem.setClientCode(aForm.getClientCode());
			obPortItem.setCdsNumber(aForm.getCdsNumber());
			obPortItem.setExercisePrice(CurrencyManager.convertToAmount(locale, obPortItem.getItemCurrencyCode(), aForm
					.getExercisePrice()));
			//obPortItem.setBrokerName(aForm.getBrokerName());
			obPortItem.setExchangeCtrlDate(DateUtil.convertDate(locale, aForm.getXchangeCtrlDate()));
			obPortItem.setIsExchangeCtrlObtained(aForm.getXchangeCtrlObtained());
			obPortItem.setBondRating(aForm.getBondRating());
			if (StringUtils.isNotBlank(aForm.getLeDate())) {
				obPortItem.setLeDate(DateUtil.convertDate(locale, aForm.getLeDate()));
			}
			else {
				obPortItem.setLeDate(null);
			}
			
			obPortItem.setBrokerName(aForm.getSchemeName());
			obPortItem.setRecognizeExchange(aForm.getSchemeType());
			//DefaultLogger.debug(this, "Port item is: " + obPortItem);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return obPortItem;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		PortItemForm aForm = (PortItemForm) cForm;
		IMarketableEquity iMarketEquity = (IMarketableEquity) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this + " - mapOBToForm", "Locale is: " + locale);
		IMarketableCollateral iMarket = (IMarketableCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());

		try {
			if (iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_NONLISTED_LOCAL))
				aForm.setEquityType(ICMSConstant.EQUITY_TYPE_STOCK);
			else
				aForm.setEquityType(iMarketEquity.getEquityType());
			aForm.setCertNo(iMarketEquity.getCertificateNo());
			aForm.setNomineeName(iMarketEquity.getAgentName());
			aForm.setDateConfirmNomine(DateUtil.formatDate(locale, iMarketEquity.getAgentConfirmDate()));
			aForm.setRegisteredName(iMarketEquity.getRegisteredName());
			if(ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL.equals(iMarket.getCollateralSubType().getSubTypeCode()) 
					&& "Mutual Fund".equals(iMarket.getCollateralSubType().getSubTypeName()) && "Marketable Securities".equals(iMarket.getCollateralSubType().getTypeName())){
				DecimalFormat df = new DecimalFormat("##.####");
				 String noOfUnitsStr=df.format(iMarketEquity.getNoOfUnits());
				//aForm.setNoOfUnit(String.valueOf(iMarketEquity.getNoOfUnits()));
				 aForm.setNoOfUnit(String.valueOf(noOfUnitsStr));
			}else{
				aForm.setNoOfUnit(String.valueOf(Double.valueOf(iMarketEquity.getNoOfUnits()).longValue()));
				}
			/*
			 * if (iMarketEquity.getCMV() != null &&
			 * iMarketEquity.getCMV().getCurrencyCode() != null) { if
			 * (iMarketEquity.getNoOfUnits() != 0) { double unitPrice =
			 * iMarketEquity.getCMV().getAmount() /
			 * iMarketEquity.getNoOfUnits();
			 * //aForm.setUnitPrice(CurrencyManager.convertToString(locale, new
			 * Amount(unitPrice, iMarketEquity.getCMV().getCurrencyCode()))); if
			 * (unitPrice > 0) {
			 * aForm.setUnitPrice(MapperUtil.mapDoubleToString(unitPrice, 2,
			 * locale)); } } }
			 */
			aForm.setUnitPriceCcyCode(iMarketEquity.getUnitPriceCcyCode());
			if (AbstractCommonMapper.isEmptyOrNull(aForm.getUnitPriceCcyCode())) {
				aForm.setUnitPriceCcyCode(IGlobalConstant.DEFAULT_CURRENCY);
				iMarketEquity.setUnitPriceCcyCode(IGlobalConstant.DEFAULT_CURRENCY);
			}

			if (iMarketEquity.getUnitPrice() != null)
				iMarketEquity.getUnitPrice().setCurrencyCode(iMarketEquity.getUnitPriceCcyCode());
			if ((iMarketEquity.getUnitPrice() != null) && (iMarketEquity.getUnitPrice().getCurrencyCode() != null) && iMarketEquity.getValuationUnitPrice()!=null) {
				//aForm.setUnitPrice(CurrencyManager.convertToString(locale, iMarketEquity.getUnitPrice()));
				//aForm.setUnitPrice(UIUtil.formatBigDecimalToStr(iMarketEquity.getUnitPrice().getAmountAsBigDecimal()));
				aForm.setUnitPrice(UIUtil.formatWithCommaAndDecimal(UIUtil.formatBigDecimalToStr(iMarketEquity.getValuationUnitPrice().getAmountAsBigDecimal())));
			}

			if(iMarket.getCollateralSubType().getSubTypeCode().equals(ICMSConstant.COLTYPE_MAR_BONDS_LOCAL)){
				
				aForm.setUnitPrice(UIUtil.formatWithCommaAndDecimal(UIUtil.formatBigDecimalToStr(iMarketEquity.getValuationUnitPrice().getAmountAsBigDecimal())));	
			}

			
			
			/*
			 * if (iMarketEquity.getMinimalFSV() != null &&
			 * iMarketEquity.getMinimalFSV().getCurrencyCode() != null) { if
			 * (iMarketEquity.getMinimalFSV().getAmount() >= 0) {
			 * aForm.setMinimalFSV(CurrencyManager.convertToString(locale,
			 * iMarketEquity.getMinimalFSV())); } }
			 */
			
			/*commented by Sachin for iMarketEquity.getNominalValue().getCurrencyCode() != null fail condition */
			/*if ((iMarketEquity.getNominalValue() != null)
					&& (iMarketEquity.getNominalValue().getCurrencyCode() != null)) {
				if (iMarketEquity.getNominalValue().getAmount() >= 0) {
					aForm.setNominalValue(CurrencyManager.convertToString(locale, iMarketEquity.getNominalValue()));
				}
				String nominalStr = aForm.getNominalValue();
				int dotPosition = nominalStr.indexOf('.');
				nominalStr = nominalStr.substring(0, dotPosition);
				aForm.setNominalValue(nominalStr);
			}*/
			
			if (iMarketEquity.getNominalValue() != null
					 ) {
				if (iMarketEquity.getNominalValue().getAmount() >= 0) {
					aForm.setNominalValue (Double.toString(iMarketEquity.getNominalValue().getAmount()));
				}
				String nominalStr = aForm.getNominalValue();
				int dotPosition = nominalStr.indexOf('.');
				nominalStr = nominalStr.substring(0, dotPosition);
				aForm.setNominalValue(UIUtil.formatWithCommaAndDecimal(nominalStr));  //Phase 3 CR:comma separated
			}
			aForm.setSecBlackListed(String.valueOf(iMarketEquity.getIsCollateralBlacklisted()));
			aForm.setLocalStockExchange(String.valueOf(iMarketEquity.getIsLocalStockExchange()));

			aForm.setIssuerName(iMarketEquity.getIssuerName());
			aForm.setIsserIdentType(iMarketEquity.getIssuerIdType());
			aForm.setStockExchange(iMarketEquity.getStockExchange());
			aForm.setCountryStockExchange(iMarketEquity.getStockExchangeCountry());

			boolean isStockExchangeSameCountry = false;
			StockExchangeList stockExchangeList = StockExchangeList.getInstance();
			if ((iMarketEquity.getStockExchange() != null) && (iMarketEquity.getStockExchangeCountry() != null)) {
				if (iMarketEquity.getStockExchangeCountry().equals(iMarket.getCollateralLocation())) {
					isStockExchangeSameCountry = true;
				}
			}
			/*
			 * if (isStockExchangeSameCountry) {
			 * aForm.setLocalStockExchange(ICMSConstant.TRUE_VALUE); } else {
			 * aForm.setLocalStockExchange(ICMSConstant.FALSE_VALUE); }
			 */

			aForm.setIndexName(iMarketEquity.getNameOfIndex());
			aForm.setIsGuaranteeByGovt(String.valueOf(iMarketEquity.getIsGuaranteeByGovt()));
			aForm.setIsSecurityReferred(String.valueOf(iMarketEquity.getIsCollateralBlacklisted()));
			aForm.setGovtName(iMarketEquity.getGovernmentName());
			if ((iMarketEquity.getBaselCompliant() == null) || iMarketEquity.getBaselCompliant().equals("")) {
				aForm.setBaselComplaintUT(ICMSConstant.NOT_AVAILABLE_VALUE);
			}
			else {
				aForm.setBaselComplaintUT(iMarketEquity.getBaselCompliant());
			}
			aForm.setXchangeCtrlObtained(iMarketEquity.getIsExchangeCtrlObtained());
			aForm.setXchangeCtrlDate(DateUtil.formatDate(locale, iMarketEquity.getExchangeCtrlDate()));
			aForm.setLeadMgr(iMarketEquity.getLeadManager());
			aForm.setCountryBondMarket(iMarketEquity.getStockExchangeCountry());
			aForm.setSettleOrg(iMarketEquity.getSettlementOrganisation());
			aForm.setBondIssueDate(DateUtil.formatDate(locale, iMarketEquity.getBondIssueDate()));
			aForm.setBondMatDate(DateUtil.formatDate(locale, iMarketEquity.getBondMaturityDate()));
			aForm.setBondMarket(iMarketEquity.getStockExchange());
			aForm.setBondType(iMarketEquity.getEquityType());
			aForm.setRic(iMarketEquity.getRIC());
			aForm.setSecMaturityDate(DateUtil.formatDate(locale, iMarketEquity.getCollateralMaturityDate()));

			if (AbstractCommonMapper.isEmptyOrNull(iMarketEquity.getCollateralCustodianType())) {
				aForm.setSecCustodianType(ICMSConstant.EXTERNAL_COL_CUSTODIAN);
				aForm.setSecCustodianExt(iMarketEquity.getCollateralCustodian());
			}
			else {
				aForm.setSecCustodianType(iMarketEquity.getCollateralCustodianType());
				if (iMarketEquity.getCollateralCustodianType().equals(ICMSConstant.INTERNAL_COL_CUSTODIAN)) {
					aForm.setSecCustodianInt(iMarketEquity.getCollateralCustodian());
				}
				else {
					aForm.setSecCustodianExt(iMarketEquity.getCollateralCustodian());
				}
			}
			aForm.setBondRating(iMarketEquity.getBondRating());
			aForm.setIsinCode(iMarketEquity.getIsinCode());
			aForm.setStockCode(iMarketEquity.getStockCode());
			aForm.setRecognizeExchange(iMarketEquity.getRecognizeExchange());

			aForm.setClientCode(iMarketEquity.getClientCode());
			aForm.setCdsNumber(iMarketEquity.getCdsNumber());
		//	aForm.setExercisePrice(CurrencyManager.convertToString(locale, iMarketEquity.getExercisePrice()));
			aForm.setExercisePrice(UIUtil.formatBigDecimalToStr(iMarketEquity.getExercisePrice().getAmountAsBigDecimal()));

			aForm.setBrokerName(iMarketEquity.getBrokerName());
			aForm.setLeDate(DateUtil.formatDate(locale, iMarketEquity.getLeDate()));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "error is :" + e.toString());
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/*
	 * private IMarketableEquity getItem(IMarketableEquity temp[],long itemRef){
	 * IMarketableEquity item = null; if(temp == null){ return item; } for(int
	 * i=0;i<temp.length;i++){ if(temp[i].getRefID()==itemRef){ item = temp[i];
	 * }else{ continue; } } return item; }
	 */
}
