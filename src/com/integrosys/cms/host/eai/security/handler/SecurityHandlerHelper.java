package com.integrosys.cms.host.eai.security.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.CollateralParameter;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.StageSecurityValuation;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecuritySubTypeHelper;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.StageAssetSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.StageCashSecurity;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.document.StageDocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.StageGuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.StageInsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.PortfolioItem;
import com.integrosys.cms.host.eai.security.bus.marketable.StageMarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.StagePortfolioItem;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;
import com.integrosys.cms.host.eai.security.bus.others.StageOthersSecurity;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;
import com.integrosys.cms.host.eai.security.bus.property.StagePropertySecurity;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Security Actual Trx Handler Helper.
 * 
 * @author zhaijian
 * @author Chong Jun Yong
 * @since 08-Apr-2007
 */
public class SecurityHandlerHelper extends AbstractCommonSecurityHandlerHelper {

	private String[] EXCLUDE_METHOD = new String[] { "getCMSSecurityId" };

	private Map variationProperties;

	/**
	 * @return the variationProperties
	 */
	public Map getVariationProperties() {
		return variationProperties;
	}

	/**
	 * @param variationProperties the variationProperties to set
	 */
	public void setVariationProperties(Map variationProperties) {
		this.variationProperties = variationProperties;
	}

	public boolean isCreate(String indicator) {
		if (String.valueOf(IEaiConstant.CREATEINDICATOR).equals(indicator)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isUpdate(String indicator) {
		if (String.valueOf(IEaiConstant.UPDATEINDICATOR).equals(indicator)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDelete(String indicator) {
		if (String.valueOf(IEaiConstant.DELETEINDICATOR).equals(indicator)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * store actual valuation detail
	 * 
	 * @param secMsgBody
	 * @param apprSec
	 * @param cdb
	 */
	public void persistValuationDetail(SecurityMessageBody secMsgBody, ApprovedSecurity apprSec) {
		persistValuationDetail(secMsgBody, apprSec, SecurityValuation.class);
	}

	/**
	 * store staging valuation detail
	 * 
	 * @param secMsgBody
	 * @param stageSec
	 * 
	 */
	public void persistStageValuationDetail(SecurityMessageBody secMsgBody, StageApprovedSecurity stageSec) {
		persistValuationDetail(secMsgBody, stageSec, StageSecurityValuation.class);
	}

	protected void persistValuationDetail(SecurityMessageBody secMsgBody, ApprovedSecurity apprSec,
			Class valuationDetailClass) {
		String source = EAIMessageSynchronizationManager.getMessageSource();
		Vector valuationDetailList;
		valuationDetailList = secMsgBody.getValuationDetail();

		boolean isVariation = secMsgBody.isVariation();

		if (valuationDetailList == null) {
			valuationDetailList = new Vector();
		}

		CollateralParameter collateralParameter = getSecurityDao()
				.findCollateralParameterBySubTypeAndCountryCode(apprSec.getSecuritySubType().getStandardCodeValue(),
						apprSec.getSecurityLocation().getLocationCountry());

		for (int i = 0; i < valuationDetailList.size(); i++) {
			SecurityValuation actSecurityValuation = (SecurityValuation) valuationDetailList.elementAt(i);

			if (isChanged(actSecurityValuation.getChangeIndicator())) {
				SecurityValuation storingSecurityValuation = (valuationDetailClass == StageSecurityValuation.class) ? new StageSecurityValuation()
						: new SecurityValuation();

				AccessorUtil.copyValue(actSecurityValuation, storingSecurityValuation);
				storingSecurityValuation.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_S);
				storingSecurityValuation.setCMSSecurityId(apprSec.getCMSSecurityId());
				storingSecurityValuation.setReceivedDate(new Date());

				if (isUpdate(String.valueOf(actSecurityValuation.getUpdateStatusIndicator()))) {
					Map parameters = new HashMap();
					parameters.put("LOSValuationId", storingSecurityValuation.getLOSValuationId());

					SecurityValuation existingValuation = (SecurityValuation) getSecurityDao()
							.retrieveObjectByParameters(parameters, valuationDetailClass);

					if (existingValuation != null) {

						if (isVariation) {
							List copyingProperties = (List) getVariationProperties().get(
									new VariationPropertiesKey(source, SecurityValuation.class.getName()));
							copyVariationProperties(storingSecurityValuation, existingValuation, copyingProperties);
						}
						else {
							AccessorUtil.copyValue(storingSecurityValuation, existingValuation,
									new String[] { "getValuationId" });
						}

						getSecurityDao().update(existingValuation, valuationDetailClass);
						storingSecurityValuation = existingValuation;
					}
				}
				else if (isCreate(String.valueOf(actSecurityValuation.getUpdateStatusIndicator()))) {
					String messageSource = EAIMessageSynchronizationManager.getMessageSource();
					storingSecurityValuation.setSourceId(messageSource);
					if (collateralParameter != null) {
						storingSecurityValuation.setRevaluationFreq(collateralParameter.getValuationFrequency());
						storingSecurityValuation
								.setRevaluationFreqUnit(collateralParameter.getValuationFrequencyUnit());
					}

					Long key = (Long) getSecurityDao().store(storingSecurityValuation, valuationDetailClass);
					storingSecurityValuation.setValuationId(key.longValue());
				}
				else if (isDelete(String.valueOf(actSecurityValuation.getUpdateStatusIndicator()))) {
					Map parameters = new HashMap();
					parameters.put("LOSValuationId", actSecurityValuation.getLOSValuationId());

					SecurityValuation existingValuation = (SecurityValuation) getSecurityDao()
							.retrieveObjectByParameters(parameters, valuationDetailClass);

					if (existingValuation != null) {
						getSecurityDao().remove(existingValuation, valuationDetailClass);
					}
				}

				valuationDetailList.setElementAt(storingSecurityValuation, i);
			}
		}
	}

	public void persistMarketableSecurity(SecurityMessageBody secMsgBody, ApprovedSecurity accSec) {
		MarketableSecurity obMarketableSecurity = secMsgBody.getMarketableSecDetail();
		boolean isVariation = secMsgBody.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (isCreate(accSec.getUpdateStatusIndicator())) {
			obMarketableSecurity.setCMSSecurityId(accSec.getCMSSecurityId());
			getSecurityDao().store(obMarketableSecurity, MarketableSecurity.class);
		}
		if (isUpdate(accSec.getUpdateStatusIndicator())) {
			Map parameters = new HashMap();
			parameters.put("CMSSecurityId", new Long(accSec.getCMSSecurityId()));

			MarketableSecurity existingMarketableSec = (MarketableSecurity) getSecurityDao()
					.retrieveObjectByParameters(parameters, MarketableSecurity.class);

			if (isVariation) {
				List copyingProperties = (List) getVariationProperties().get(
						new VariationPropertiesKey(source, MarketableSecurity.class.getName()));
				copyVariationProperties(obMarketableSecurity, existingMarketableSec, copyingProperties);
			}
			else {
				AccessorUtil.copyValue(obMarketableSecurity, existingMarketableSec, EXCLUDE_METHOD);
			}
			getSecurityDao().update(existingMarketableSec, MarketableSecurity.class);
		}

	}

	public void persistStageMarketableSecurity(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		MarketableSecurity actMS = s1.getMarketableSecDetail();
		StageMarketableSecurity stgMS = new StageMarketableSecurity();
		AccessorUtil.copyValue(actMS, stgMS);

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (isCreate(stageSec.getUpdateStatusIndicator())) {
			stgMS.setCMSSecurityId(stageSec.getCMSSecurityId());
			getSecurityDao().store(stgMS, StageMarketableSecurity.class);
		}
		if (isUpdate(stageSec.getUpdateStatusIndicator())) {
			Map parameters = new HashMap();
			parameters.put("CMSSecurityId", new Long(stageSec.getCMSSecurityId()));

			MarketableSecurity existingMarketableSec = (MarketableSecurity) getSecurityDao()
					.retrieveObjectByParameters(parameters, MarketableSecurity.class);
			if (existingMarketableSec != null) {
				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, MarketableSecurity.class.getName()));
					copyVariationProperties(actMS, existingMarketableSec, copyingProperties);
				}
				else {
					existingMarketableSec.setCappedPrice(actMS.getCappedPrice());
					existingMarketableSec.setStockCounterCode(actMS.getStockCounterCode());
					existingMarketableSec.setInterestRate(actMS.getInterestRate());
				}
				getSecurityDao().update(existingMarketableSec, MarketableSecurity.class);
			}
		}
	}

	public void persistPortFolioItems(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		Vector vecPortFolio = s1.getPortfolioItems();
		String[] EXCLUDE_METHOD = new String[] { "getItemId" };

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		for (int i = 0; i < vecPortFolio.size(); i++) {
			PortfolioItem pi = (PortfolioItem) vecPortFolio.get(i);
			pi.setCurrencyCode(apprSec.getCurrency());
			pi.setCollateralId(apprSec.getCMSSecurityId());
			pi.setUnitCurrencyCode(apprSec.getCurrency());
			pi.setLegalEnforceDate(apprSec.getLegalEnforcebilityDate());
			if (isUpdate(pi.getUpdateStatusIndicator())) {
				PortfolioItem portfolioItem = null;
				if (pi.getCMSPortfolioItemId() > 0) {
					portfolioItem = (PortfolioItem) getSecurityDao().retrieve(new Long(pi.getCMSPortfolioItemId()),
							PortfolioItem.class);
				}
				else {
					portfolioItem = getExistingPortFilioItems(apprSec.getCMSSecurityId(), pi.getStockCode(),
							PortfolioItem.class);
				}

				if (portfolioItem != null) {
					pi.setShareId(portfolioItem.getShareId());

					if (isVariation) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(source, PortfolioItem.class.getName()));
						copyVariationProperties(pi, portfolioItem, copyingProperties);
					}
					else {
						AccessorUtil.copyValue(pi, portfolioItem, EXCLUDE_METHOD);
					}

					getSecurityDao().update(portfolioItem, PortfolioItem.class);
				}
			}
			else if (isCreate(pi.getUpdateStatusIndicator())) {
				getSecurityDao().store(pi, PortfolioItem.class);
			}
		}

	}

	public void persistStagePortFolioItems(Message msg, StageApprovedSecurity stageSec) {
		SecurityMessageBody smb = (SecurityMessageBody) msg.getMsgBody();

		boolean isVariation = smb.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		String[] EXCLUDE_METHOD = new String[] { "getItemId" };
		Vector vecPortFolio = smb.getPortfolioItems();
		for (int i = 0; i < vecPortFolio.size(); i++) {
			PortfolioItem pi = (PortfolioItem) vecPortFolio.get(i);
			StagePortfolioItem stgPF = new StagePortfolioItem();
			AccessorUtil.copyValue(pi, stgPF);
			stgPF.setCollateralId(stageSec.getCMSSecurityId());
			if (isUpdate(pi.getUpdateStatusIndicator())) {
				StagePortfolioItem portfolioItem = null;
				if (pi.getCMSPortfolioItemId() > 0) {
					Map portfolioParameters = new HashMap();
					portfolioParameters.put("shareId", pi.getShareId());
					portfolioParameters.put("collateralId", new Long(stageSec.getCMSSecurityId()));
					portfolioItem = (StagePortfolioItem) getSecurityDao().retrieveObjectByParameters(
							portfolioParameters, StagePortfolioItem.class);
				}
				else {
					portfolioItem = (StagePortfolioItem) getExistingPortFilioItems(stageSec.getCMSSecurityId(), pi
							.getStockCode(), StagePortfolioItem.class);
				}
				if (portfolioItem != null) {

					if (isVariation) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(source, PortfolioItem.class.getName()));
						copyVariationProperties(pi, portfolioItem, copyingProperties);
					}
					else {
						AccessorUtil.copyValue(pi, portfolioItem, EXCLUDE_METHOD);
					}
					getSecurityDao().update(portfolioItem, StagePortfolioItem.class);
				}
			}
			else if (isCreate(pi.getUpdateStatusIndicator())) {
				getSecurityDao().store(stgPF, StagePortfolioItem.class);
			}
		}
	}

	public void persistAssetDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		AssetSecurity obAsset = s1.getAssetDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obAsset != null) {

			if (isCreate(obAsset.getUpdateStatusIndicator())) {
				obAsset.setCMSSecurityId(apprSec.getCMSSecurityId());

				AssetSecuritySubTypeHelper.createSubTypeInstanceIfEmpty(obAsset, apprSec.getSecuritySubType());

				if (obAsset.getVesselDetail() != null) {
					if ((obAsset.getVesselDetail().getVesselState() != null)
							&& (obAsset.getSpecificChargeDetail() != null)) {
						obAsset.getSpecificChargeDetail().setGoodStatus(obAsset.getVesselDetail().getVesselState());
					}
				}

				if (obAsset.getSpecificChargeDetail() != null) {
					if (obAsset.getVehicleDetail() != null) {
						obAsset.getVehicleDetail().setDownPaymentCash(
								obAsset.getSpecificChargeDetail().getDownPaymentCash());
						obAsset.getVehicleDetail().setDownPaymentTradeIn(
								obAsset.getSpecificChargeDetail().getDownPaymentTradeIn());
					}
					else if (obAsset.getPlantEquipDetail() != null) {
						obAsset.getPlantEquipDetail().setDownPaymentCash(
								obAsset.getSpecificChargeDetail().getDownPaymentCash());
						obAsset.getPlantEquipDetail().setDownPaymentTradeIn(
								obAsset.getSpecificChargeDetail().getDownPaymentTradeIn());
					}
				}

				getSecurityDao().store(obAsset, AssetSecurity.class);
			}
			else if (isUpdate(obAsset.getUpdateStatusIndicator())) {
				AssetSecurity tempAsset = (AssetSecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obAsset.getClass());

				if (obAsset.getVesselDetail() != null) {
					if ((obAsset.getVesselDetail().getVesselState() != null)
							&& (obAsset.getSpecificChargeDetail() != null)) {
						obAsset.getSpecificChargeDetail().setGoodStatus(obAsset.getVesselDetail().getVesselState());
					}
				}

				if (obAsset.getSpecificChargeDetail() != null) {
					if (obAsset.getVehicleDetail() != null) {
						obAsset.getVehicleDetail().setDownPaymentCash(
								obAsset.getSpecificChargeDetail().getDownPaymentCash());
						obAsset.getVehicleDetail().setDownPaymentTradeIn(
								obAsset.getSpecificChargeDetail().getDownPaymentTradeIn());
					}
					else if (obAsset.getPlantEquipDetail() != null) {
						obAsset.getPlantEquipDetail().setDownPaymentCash(
								obAsset.getSpecificChargeDetail().getDownPaymentCash());
						obAsset.getPlantEquipDetail().setDownPaymentTradeIn(
								obAsset.getSpecificChargeDetail().getDownPaymentTradeIn());
					}
				}

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, AssetSecurity.class.getName()));
					copyVariationProperties(obAsset, tempAsset, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obAsset, tempAsset, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempAsset, AssetSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageAssetDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		AssetSecurity act = s1.getAssetDetail();
		StageAssetSecurity stg = new StageAssetSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				if (stg.getVesselDetail() != null) {
					if ((stg.getVesselDetail().getVesselState() != null) && (stg.getSpecificChargeDetail() != null)) {
						stg.getSpecificChargeDetail().setGoodStatus(stg.getVesselDetail().getVesselState());
					}
				}
				getSecurityDao().store(stg, StageAssetSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				AssetSecurity tempAsset = (AssetSecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), stg.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, AssetSecurity.class.getName()));
					copyVariationProperties(act, tempAsset, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempAsset, EXCLUDE_METHOD);
				}

				if (tempAsset.getVesselDetail() != null) {
					if ((tempAsset.getVesselDetail().getVesselState() != null)
							&& (tempAsset.getSpecificChargeDetail() != null)) {
						tempAsset.getSpecificChargeDetail().setGoodStatus(tempAsset.getVesselDetail().getVesselState());
					}
				}
				getSecurityDao().update(tempAsset, stg.getClass());
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistCashDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		CashSecurity obCash = s1.getCashDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obCash != null) {
			if (isCreate(obCash.getUpdateStatusIndicator())) {
				obCash.setCMSSecurityId(apprSec.getCMSSecurityId());
				getSecurityDao().store(obCash, CashSecurity.class);
			}
			else if (isUpdate(obCash.getUpdateStatusIndicator())) {
				CashSecurity tempCash = (CashSecurity) getSecurityDao().retrieve(new Long(apprSec.getCMSSecurityId()),
						obCash.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, CashSecurity.class.getName()));
					copyVariationProperties(obCash, tempCash, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obCash, tempCash, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempCash, CashSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageCashDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		CashSecurity act = s1.getCashDetail();
		StageCashSecurity stg = new StageCashSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageCashSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				CashSecurity tempCash = (CashSecurity) getSecurityDao().retrieve(new Long(stageSec.getCMSSecurityId()),
						stg.getClass());
				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, CashSecurity.class.getName()));
					copyVariationProperties(act, tempCash, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempCash, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempCash, StageCashSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistInsuranceDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		InsuranceSecurity obInsurance = s1.getInsuranceDetail();
		String currency = apprSec.getCurrency();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (currency != null) {
			obInsurance.setInsuredAmountCurrency(currency);
		}
		if (obInsurance != null) {
			if (isCreate(obInsurance.getUpdateStatusIndicator())) {
				obInsurance.setCMSSecurityId(apprSec.getCMSSecurityId());
				getSecurityDao().store(obInsurance, InsuranceSecurity.class);
			}
			else if (isUpdate(obInsurance.getUpdateStatusIndicator())) {

				InsuranceSecurity tempInsurance = (InsuranceSecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obInsurance.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, InsuranceSecurity.class.getName()));
					copyVariationProperties(obInsurance, tempInsurance, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obInsurance, tempInsurance, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempInsurance, InsuranceSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageInsuranceDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		InsuranceSecurity act = s1.getInsuranceDetail();
		StageInsuranceSecurity stg = new StageInsuranceSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageInsuranceSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				InsuranceSecurity tempInsurance = (InsuranceSecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), stg.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, InsuranceSecurity.class.getName()));
					copyVariationProperties(act, tempInsurance, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempInsurance, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempInsurance, StageInsuranceSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistDocumentDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		DocumentSecurity obDocument = s1.getDocumentationDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obDocument == null) {
			obDocument = new DocumentSecurity();
			obDocument.setMinimumAmtCcy(apprSec.getCurrency());
			obDocument.setMaximumAmtCcy(apprSec.getCurrency());
			obDocument.setDocumentAmtCcy(apprSec.getCurrency());
			obDocument.setCurrency(apprSec.getCurrency());
			if (isCreate(apprSec.getUpdateStatusIndicator())) {
				// need to insert record into Document table
				obDocument.setCMSSecurityId(apprSec.getCMSSecurityId());
				getSecurityDao().store(obDocument, DocumentSecurity.class);
			}
			else {
				// update / delete, nothing to do
			}
		}
		else {
			obDocument.setMinimumAmtCcy(apprSec.getCurrency());
			obDocument.setMaximumAmtCcy(apprSec.getCurrency());
			obDocument.setDocumentAmtCcy(apprSec.getCurrency());

			if (isCreate(obDocument.getUpdateStatusIndicator())) {
				obDocument.setCMSSecurityId(apprSec.getCMSSecurityId());
				getSecurityDao().store(obDocument, DocumentSecurity.class);
			}
			else if (isUpdate(obDocument.getUpdateStatusIndicator())) {
				DocumentSecurity tempDocument = (DocumentSecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obDocument.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, DocumentSecurity.class.getName()));
					copyVariationProperties(obDocument, tempDocument, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obDocument, tempDocument, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempDocument, DocumentSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageDocumentDetial(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		DocumentSecurity act = s1.getDocumentationDetail();
		StageDocumentSecurity stg = new StageDocumentSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act == null) {
			act = new DocumentSecurity();
			act.setMinimumAmtCcy(stageSec.getCurrency());
			act.setMaximumAmtCcy(stageSec.getCurrency());
			act.setDocumentAmtCcy(stageSec.getCurrency());

			if (isCreate(stageSec.getUpdateStatusIndicator())) {
				// need to insert record into Document table
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageDocumentSecurity.class);
			}
			else {
				// update / delete, nothing to do
			}
		}
		else {
			act.setMinimumAmtCcy(stageSec.getCurrency());
			act.setMaximumAmtCcy(stageSec.getCurrency());
			act.setDocumentAmtCcy(stageSec.getCurrency());

			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageDocumentSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				DocumentSecurity tempDocument = (DocumentSecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), stg.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, DocumentSecurity.class.getName()));
					copyVariationProperties(act, tempDocument, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempDocument, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempDocument, StageDocumentSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistGuaranteeDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		GuaranteeSecurity obGuarantee = s1.getGuaranteeDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obGuarantee != null) {
			if (isCreate(obGuarantee.getUpdateStatusIndicator())) {
				obGuarantee.setCMSSecurityId(apprSec.getCMSSecurityId());
				obGuarantee.setCurrency(apprSec.getCurrency());
				getSecurityDao().store(obGuarantee, GuaranteeSecurity.class);
			}
			else if (isUpdate(obGuarantee.getUpdateStatusIndicator())) {
				GuaranteeSecurity tempGuarantee = (GuaranteeSecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obGuarantee.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, GuaranteeSecurity.class.getName()));
					copyVariationProperties(obGuarantee, tempGuarantee, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obGuarantee, tempGuarantee, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempGuarantee, obGuarantee.getClass());
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageGuaranteeDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		GuaranteeSecurity act = s1.getGuaranteeDetail();
		StageGuaranteeSecurity stg = new StageGuaranteeSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageGuaranteeSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				GuaranteeSecurity tempGuarantee = (GuaranteeSecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), stg.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, GuaranteeSecurity.class.getName()));
					copyVariationProperties(act, tempGuarantee, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempGuarantee, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempGuarantee, StageGuaranteeSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistPropertyDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		PropertySecurity obProperty = s1.getPropertyDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obProperty != null) {
			if (obProperty.getTitleNumber() != null) {
				obProperty.setTitleNumber(obProperty.getTitleNumber().toUpperCase());
			}

			if ((obProperty.getQuitRentInd() == null) || (obProperty.getQuitRentInd().length() <= 0)) {
				obProperty.setQuitRentInd("N");
			}

			if (isCreate(obProperty.getUpdateStatusIndicator())) {
				obProperty.setCMSSecurityId(apprSec.getCMSSecurityId());
				if (obProperty.getCompletionStage() != null) {
					if (obProperty.getCompletionStage().getStandardCodeValue().equals("")) {
						obProperty.setCompletionStage(null);
					}
				}
				getSecurityDao().store(obProperty, PropertySecurity.class);
			}
			else if (isUpdate(obProperty.getUpdateStatusIndicator())) {
				PropertySecurity tempProperty = (PropertySecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obProperty.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, PropertySecurity.class.getName()));
					copyVariationProperties(obProperty, tempProperty, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obProperty, tempProperty, EXCLUDE_METHOD);
				}

				if (tempProperty.getCompletionStage() != null) {
					if (tempProperty.getCompletionStage().getStandardCodeValue().equals("")) {
						tempProperty.setCompletionStage(null);
					}
				}
				getSecurityDao().update(tempProperty, PropertySecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStagePropertyDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		PropertySecurity act = s1.getPropertyDetail();
		StagePropertySecurity stg = new StagePropertySecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				if (stg.getCompletionStage() != null) {
					if (stg.getCompletionStage().getStandardCodeValue().equals("")) {
						stg.setCompletionStage(null);
					}
				}
				getSecurityDao().store(stg, StagePropertySecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				PropertySecurity tempProperty = (PropertySecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), StagePropertySecurity.class);

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, PropertySecurity.class.getName()));
					copyVariationProperties(act, tempProperty, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempProperty, EXCLUDE_METHOD);
				}
				if (tempProperty.getCompletionStage() != null) {
					if (tempProperty.getCompletionStage().getStandardCodeValue().equals("")) {
						tempProperty.setCompletionStage(null);
					}
				}
				getSecurityDao().update(tempProperty, StagePropertySecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistOthersDetail(SecurityMessageBody s1, ApprovedSecurity apprSec) {
		OthersSecurity obOthers = s1.getOtherDetail();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (obOthers != null) {
			if (isCreate(obOthers.getUpdateStatusIndicator())) {
				obOthers.setCMSSecurityId(apprSec.getCMSSecurityId());
				getSecurityDao().store(obOthers, OthersSecurity.class);
			}
			else if (isUpdate(obOthers.getUpdateStatusIndicator())) {
				OthersSecurity tempOthers = (OthersSecurity) getSecurityDao().retrieve(
						new Long(apprSec.getCMSSecurityId()), obOthers.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, OthersSecurity.class.getName()));
					copyVariationProperties(obOthers, tempOthers, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(obOthers, tempOthers, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempOthers, OthersSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public void persistStageOthersDetail(SecurityMessageBody s1, StageApprovedSecurity stageSec) {
		OthersSecurity act = s1.getOtherDetail();
		StageOthersSecurity stg = new StageOthersSecurity();

		boolean isVariation = s1.isVariation();
		String source = EAIMessageSynchronizationManager.getMessageSource();

		if (act != null) {
			if (isCreate(act.getUpdateStatusIndicator())) {
				AccessorUtil.copyValue(act, stg);
				stg.setCMSSecurityId(stageSec.getCMSSecurityId());
				getSecurityDao().store(stg, StageOthersSecurity.class);
			}
			else if (isUpdate(act.getUpdateStatusIndicator())) {
				OthersSecurity tempOthers = (OthersSecurity) getSecurityDao().retrieve(
						new Long(stageSec.getCMSSecurityId()), stg.getClass());

				if (isVariation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, OthersSecurity.class.getName()));
					copyVariationProperties(act, tempOthers, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(act, tempOthers, EXCLUDE_METHOD);
				}

				getSecurityDao().update(tempOthers, StageOthersSecurity.class);
			}
			else {
				// delete,nothing to do
			}
		}
	}

	public ApprovedSecurity getApprovedSecurity(ApprovedSecurity appSec, String oldSecurityId, String sourceId)
			throws NoSuchSecurityException {
		if (appSec.getLOSSecurityId().equalsIgnoreCase(oldSecurityId)
				&& (appSec.getSourceId().equalsIgnoreCase(sourceId))) {
			return appSec;
		}
		throw new NoSuchSecurityException(oldSecurityId, sourceId);

	}

	private PortfolioItem getExistingPortFilioItems(long cmsSecurityID, String stockCode, Class portfolioClass) {
		Map parameters = new HashMap();
		parameters.put("collateralId", new Long(cmsSecurityID));
		parameters.put("stockCode", stockCode);
		PortfolioItem portfolioItem = (PortfolioItem) getSecurityDao().retrieveObjectByParameters(parameters,
				portfolioClass);
		return portfolioItem;
	}

}
