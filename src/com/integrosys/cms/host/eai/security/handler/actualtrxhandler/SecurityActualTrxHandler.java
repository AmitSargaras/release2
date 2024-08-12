/*
 * Copyright 2008 Integro Technologies Pte Ltd
 * 
 * Smartlender Suite : Collateral Management System
 */
package com.integrosys.cms.host.eai.security.handler.actualtrxhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.SCISecurityHelper;
import com.integrosys.cms.host.eai.security.SecurityAlreadyExistsException;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecurityInstrument;
import com.integrosys.cms.host.eai.security.bus.SecuritySource;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.StageSecurityInstrument;
import com.integrosys.cms.host.eai.security.handler.IHandlerHelper;
import com.integrosys.cms.host.eai.security.handler.ILimitChargeHelper;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * This class will handle actual and staging business data for the approved
 * security.
 * 
 * @author Chong Jun Yong
 * @since 17.11.2006
 */

public class SecurityActualTrxHandler extends AbstractCommonActualTrxHandler {
	private String[] EXCLUDE_METHOD = new String[] { "getCMSSecurityId" };

	private String SECURITY_KEY = IEaiConstant.SECURITY_KEY;

	private IHandlerHelper securityHandlerHelper;

	private ILimitChargeHelper limitChargeHelper;

	private ISecurityDao securityDao;

	private Map variationProperties;

	protected SCISecurityHelper helper = SCISecurityHelper.getInstance();

	// protected LimitChargeHelper specialCaseHelper = new LimitChargeHelper();

	/**
	 * @return suppose to be instance of SecurityHandlerHelper.class
	 */
	public IHandlerHelper getSecurityHandlerHelper() {
		return securityHandlerHelper;
	}

	public void setSecurityHandlerHelper(IHandlerHelper securityHandlerHelper) {
		this.securityHandlerHelper = securityHandlerHelper;
	}

	public ILimitChargeHelper getLimitChargeHelper() {
		return limitChargeHelper;
	}

	public void setLimitChargeHelper(ILimitChargeHelper limitChargeHelper) {
		this.limitChargeHelper = limitChargeHelper;
	}

	public ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

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

	/**
	 * This method should persist the SCI user data + CMS generated IDs into the
	 * db.
	 * 
	 * @param msg SCI user data
	 * @return modified message returned for staging and transaction purpose
	 * @throws EAIMessageValidationException
	 */

	public Message persistActualTrx(Message msg) {

		logger.info("persistActualTrx for msg type: [" + msg.getMsgHeader().getMessageType() + "]");

		SecurityMessageBody secMsgBody = ((SecurityMessageBody) msg.getMsgBody());

		ApprovedSecurity apprSec = secMsgBody.getSecurityDetail();

		boolean isVariation = (apprSec.getCMSSecurityId() > 0);
		secMsgBody.setVariation(isVariation);

		if ((apprSec.getUpdateStatusIndicator() != null) && (apprSec.getUpdateStatusIndicator() == "I")) {
			if (apprSec.getOriginalCurrency() != null) {
				apprSec.setCurrency(apprSec.getOriginalCurrency());
			}
		}
		if (apprSec.getCMSSecurityId() <= 0) {
			apprSec.setSourceId(msg.getMsgHeader().getSource());
		}
		else {
			String sourceId = getSecurityDao().findSecuritySourceIdByCmsSecurityId(apprSec.getCMSSecurityId());
			apprSec.setSourceId(sourceId);
		}

		// getting the refEntryCode
		if (((apprSec.getSourceSecurityType().getStandardCodeNumber() != null) && (apprSec.getSourceSecurityType()
				.getStandardCodeValue() != null))) {
			String sourceSecurityTypeCode = apprSec.getSourceSecurityType().getStandardCodeNumber();
			String sourceSecurityTypeValue = apprSec.getSourceSecurityType().getStandardCodeValue();
			apprSec.setRefEntryCode(getRefEntryCode(sourceSecurityTypeCode, sourceSecurityTypeValue));
		}

		String securityType = apprSec.getSecurityType().getStandardCodeValue();

		if (isSecurityChanged(apprSec)) {
			getLimitChargeHelper().persistAll(secMsgBody, apprSec);

			// Set the common fields for actual & staging table together
			getSecurityHandlerHelper().setCommonFieldsForSecurity(secMsgBody, apprSec, securityType);

			// Set description for Security Type & SubType (actual & staging)
			getSecurityHandlerHelper().setStandardCodeDescription(apprSec);

			apprSec = storeSecurity(apprSec, secMsgBody.getValuationDetail(), isVariation);
		}
		else {
			// if 'Change Indicator' is 'N', need to retrieve the cmsSecurityId
			long cmsSecurityId = getCollateralIdBySecurityId(apprSec);
			apprSec.setCMSSecurityId(cmsSecurityId);
		}

		if (isSecurityChanged(apprSec) && !helper.isShareSecurity(apprSec)) {
			// Only persist when the 'Change Indicator' is 'Y'

			if ((ICMSConstant.SECURITY_TYPE_ASSET).equals(securityType)) {
				getSecurityHandlerHelper().persistAssetDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistChequeDetail(msg.getMsgHeader(), secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistInsurancePolicyDetail(msg.getMsgHeader(), secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_CASH).equals(securityType)) {
				getSecurityHandlerHelper().persistCashDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistCashDeposit(msg.getMsgHeader(), secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_INSURANCE).equals(securityType)) {
				getSecurityHandlerHelper().persistInsuranceDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistCreditDefaultSwapsDetail(msg.getMsgHeader(), secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_MARKETABLE).equals(securityType)) {
				getSecurityHandlerHelper().persistMarketableSecurity(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistPortFolioItems(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_DOCUMENT).equals(securityType)) {
				getSecurityHandlerHelper().persistDocumentDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_OTHERS).equals(securityType)) {

				getSecurityHandlerHelper().persistOthersDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistInsurancePolicyDetail(msg.getMsgHeader(), secMsgBody, apprSec);

			}
			else if ((ICMSConstant.SECURITY_TYPE_GUARANTEE).equals(securityType)) {
				getSecurityHandlerHelper().persistGuaranteeDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_PROPERTY).equals(securityType)) {

				getSecurityHandlerHelper().persistPropertyDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistValuationDetail(secMsgBody, apprSec);

				getSecurityHandlerHelper().persistInsurancePolicyDetail(msg.getMsgHeader(), secMsgBody, apprSec);
			}
			else if (ICMSConstant.SECURITY_TYPE_CLEAN.equals(securityType)) {
				// NO DETAIL INFO
			}
		}

		return msg;
	}

	/**
	 * Persist the SCI user data + staging CMS generated IDs into the db.
	 * 
	 * @param msg SCI user data
	 * @param trxValueMap a HashMap of ICollateralTrxValue objects with SCI
	 *        security id as its key
	 * @return return the staging message
	 * @throws Exception
	 */
	public Message persistStagingTrx(Message msg, Object trxValueMap) {
		SecurityMessageBody secMsgbody = ((SecurityMessageBody) msg.getMsgBody());
		ApprovedSecurity sec = secMsgbody.getSecurityDetail();
		StageApprovedSecurity stageSec = new StageApprovedSecurity();
		AccessorUtil.copyValue(sec, stageSec, EXCLUDE_METHOD);

		HashMap trxMap = new HashMap();
		if (trxValueMap != null) {
			trxMap = (HashMap) trxValueMap;
		}
		ICollateralTrxValue trxValue = (ICollateralTrxValue) trxMap.get(sec.getLOSSecurityId());
		String stageRefId = null;
		if (trxValue == null) {
			logger.warn("#persitStagingTrx cannot find collateral trx , for security id [" + sec.getLOSSecurityId()
					+ "]");
		}
		else {
			stageRefId = trxValue.getStagingReferenceID();
		}
		if ((stageRefId != null) && (stageRefId.length() != 0)) {
			stageSec.setCMSSecurityId(Long.parseLong(stageRefId));
		}

		if (isSecurityChanged(stageSec)) { // Only persist when the 'Change
			// Indicator' is 'Y'
			stageSec = (StageApprovedSecurity) storeSecurity(stageSec, null, secMsgbody.isVariation());
		}

		secMsgbody.setSecurityDetail(stageSec);

		if (isSecurityChanged(stageSec) && !helper.isShareSecurity(stageSec)) {
			// Only persist when the 'Change Indicator' is 'Y'

			String securityType = stageSec.getSecurityType().getStandardCodeValue();

			logger.debug("Store staging trx handler, security type [" + securityType + "] security id ["
					+ stageSec.getLOSSecurityId() + "]");

			if ((ICMSConstant.SECURITY_TYPE_ASSET).equals(securityType)) {
				getSecurityHandlerHelper().persistStageAssetDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageChequeDetail(msg.getMsgHeader(), secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageInsPolicyDetail(msg.getMsgHeader(), secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_CASH).equals(securityType)) {
				getSecurityHandlerHelper().persistStageCashDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageCashDeposit(msg.getMsgHeader(), secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_INSURANCE).equals(securityType)) {
				getSecurityHandlerHelper().persistStageInsuranceDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageCreditDefaultSwapsDetail(msg.getMsgHeader(), secMsgbody,
						stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_MARKETABLE).equals(securityType)) {
				getSecurityHandlerHelper().persistStageMarketableSecurity(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStagePortFolioItems(msg, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_DOCUMENT).equals(securityType)) {
				getSecurityHandlerHelper().persistStageDocumentDetial(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_OTHERS).equals(securityType)) {

				getSecurityHandlerHelper().persistStageOthersDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageInsPolicyDetail(msg.getMsgHeader(), secMsgbody, stageSec);

			}
			else if ((ICMSConstant.SECURITY_TYPE_GUARANTEE).equals(securityType)) {
				getSecurityHandlerHelper().persistStageGuaranteeDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);
			}
			else if ((ICMSConstant.SECURITY_TYPE_PROPERTY).equals(securityType)) {
				getSecurityHandlerHelper().persistStagePropertyDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageValuationDetail(secMsgbody, stageSec);

				getSecurityHandlerHelper().persistStageInsPolicyDetail(msg.getMsgHeader(), secMsgbody, stageSec);
			}
			else if (ICMSConstant.SECURITY_TYPE_CLEAN.equals(securityType)) {
				// NO DETAIL INFO
			}
			else {
				logger.warn("Wrong collateral subtype: [" + securityType + "] security id ["
						+ stageSec.getLOSSecurityId() + "]");
			}
		}

		return msg;
	}

	/**
	 * Helper method to store security in persistent storage. It will assign cms
	 * security id and subtype code in the approved security.
	 * 
	 * @param security approved security
	 * @return approved security with CMS security id and subtype code assigned
	 * @throws EAIMessageValidationException
	 */
	protected ApprovedSecurity storeSecurity(ApprovedSecurity security, Vector valuationList, boolean isVariation)
			throws EAIMessageValidationException {
		if (helper.isCreate(security)) {
			if (security instanceof StageApprovedSecurity) {
				// no checking for staging
			}
			else {
				// actual, check for existing security record
				long cmsSecurityId = getCollateralIdWithoutStatus(security);
				if (ICMSConstant.LONG_INVALID_VALUE != cmsSecurityId) {
					// Validation failed! same record found, reject message
					throw new SecurityAlreadyExistsException(security.getLOSSecurityId(), security.getSourceId());
				}
			}

			security = createSecurity(security, valuationList);

			if (security instanceof StageApprovedSecurity) {
				// no staging for security source
			}
			else {
				createShareSecurity(security);
			}

			createSecurityInstrument(security);

		}
		else if (helper.isUpdate(security)) {

			if (security instanceof StageApprovedSecurity) {
				security = updateSecurity(security, valuationList, isVariation);

			}
			else {
				long cmsSecurityId = security.getCMSSecurityId();
				if (security.getCMSSecurityId() < 1) {
					cmsSecurityId = getCollateralIdBySecurityId(security);
				}
				security.setCMSSecurityId(cmsSecurityId);
				if (helper.isShareSecurity(security)) {

					if (cmsSecurityId == ICMSConstant.LONG_INVALID_VALUE) {
						security.setCMSSecurityId(security.getSharedSecurityId());
						createShareSecurity(security);
					}
					else {
						updateShareSecurity(security);
					}
				}
				else {
					updateShareSecurity(security);
				}

				security = updateSecurity(security, valuationList, isVariation);

			}

			updateSecurityInstrument(security);

		}
		else if (helper.isDelete(security)) {
			security.setCMSStatus(ICMSConstant.STATE_PENDING_DELETE);
			if (security instanceof StageApprovedSecurity) {
				security = updateSecurity(security, valuationList, isVariation);
			}
			else {
				long cmsSecurityId = getCollateralIdBySecurityId(security);
				security.setCMSSecurityId(cmsSecurityId);
				deleteShareSecurity(security);
				security = updateSecurity(security, valuationList, isVariation);
			}
		}

		return security;
	}

	protected ApprovedSecurity updateSecurity(ApprovedSecurity security, Vector valuationList, boolean isVariation) {
		String messageSource = EAIMessageSynchronizationManager.getMessageSource();
		if (!helper.isShareSecurity(security)) {
			// check CMS_SECURITY table for security_id + source_id to allow
			// only owner of security can update security record
			checkSecurityOwner(security);

			// to retrieve CMV / FSV from LOS Valuation
			getSecurityHandlerHelper().retrieveCmvFsvfromValuation(security, valuationList);

			ApprovedSecurity tmpSec = (ApprovedSecurity) getSecurityDao().retrieve(
					new Long(security.getCMSSecurityId()), security.getClass());
			// long versionTime = VersionGenerator.getVersionNumber();
			// security.setCMSVersionTime(versionTime);
			Date createDate = tmpSec.getCreateDate();
			String colStatus = tmpSec.getCollateralStatus();

			if (isVariation) {
				List copyingProperties = (List) getVariationProperties().get(
						new VariationPropertiesKey(messageSource, ApprovedSecurity.class.getName()));
				getSecurityHandlerHelper().copyVariationProperties(security, tmpSec, copyingProperties);
			}
			else {
				List ignoredPropertiesList = new ArrayList();
				ignoredPropertiesList.addAll(Arrays.asList(new String[] { "chargeInfoDrawAmountUsageIndicator",
						"chargeInfoPledgeAmountUsageIndicator" }));

				String[] ignoredProperties = getSecurityHandlerHelper().excludeUnchangedCmvFsv(
						(security.getCmv() == null ? 0 : security.getCmv().doubleValue()));
				if (ignoredProperties != null && ignoredProperties.length > 0) {
					ignoredPropertiesList.addAll(Arrays.asList(ignoredProperties));
				}

				AccessorUtil.copyValue(security, tmpSec, (String[]) ignoredPropertiesList.toArray(new String[0]));
			}
			tmpSec.setCreateDate(createDate);
			tmpSec.setCollateralStatus(colStatus);
			getSecurityDao().update(tmpSec, security.getClass());

			try {
				security = (ApprovedSecurity) AccessorUtil.deepClone(tmpSec);
			}
			catch (Throwable t) {
				logger.error("error cloning security, security id [" + tmpSec.getLOSSecurityId() + "]", t);
				IllegalStateException isex = new IllegalStateException("error cloning security, security id ["
						+ tmpSec.getLOSSecurityId() + "] details [" + t.getMessage() + "]");
				isex.initCause(t);

				throw isex;
			}
		}

		return security;
	}

	protected ApprovedSecurity createSecurity(ApprovedSecurity security, Vector valuationList) {
		if (!helper.isShareSecurity(security)) {
			security.setCreateDate(new Date(System.currentTimeMillis()));
			security.setCollateralStatus("1");

			Long cmsSecurityId = (Long) getSecurityDao().store(security, security.getClass());
			security.setCMSSecurityId(cmsSecurityId.longValue());
		}
		else {
			// if it is share security, use shared security id as CMSSecurityId
			security.setCMSSecurityId(security.getSharedSecurityId());
		}
		return security;
	}

	protected long getCollateralIdBySecurityId(ApprovedSecurity apprSec) {
		long collaterlId = ICMSConstant.LONG_INVALID_VALUE;
		String securityType = apprSec.getSecurityType().getStandardCodeValue();

		Map parameters = new HashMap();
		parameters.put("sourceSecurityId", apprSec.getLOSSecurityId());
		parameters.put("sourceId", apprSec.getSourceId());
		parameters.put("status", "ACTIVE");

		if ((ICMSConstant.SECURITY_TYPE_MARKETABLE).equals(securityType)) {
			parameters.put("securitySubTypeId", apprSec.getSecuritySubType().getStandardCodeValue());
		}

		SecuritySource securtySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters,
				SecuritySource.class);

		if (securtySource != null) {
			collaterlId = securtySource.getCMSSecurityId();
		}

		return collaterlId;
	}

	/**
	 * Select cms_collateral_id By Security ID and Source ID only (included
	 * subtype ID if it is Marketable Security)
	 * @param apprSec
	 * @return
	 */
	protected long getCollateralIdWithoutStatus(ApprovedSecurity apprSec) {
		long collaterlId = ICMSConstant.LONG_INVALID_VALUE;
		String securityType = apprSec.getSecurityType().getStandardCodeValue();

		HashMap parameters = new HashMap();
		parameters.put("sourceSecurityId", apprSec.getLOSSecurityId());
		parameters.put("sourceId", apprSec.getSourceId());

		if ((ICMSConstant.SECURITY_TYPE_MARKETABLE).equals(securityType)) {
			parameters.put("securitySubTypeId", apprSec.getSecuritySubType().getStandardCodeValue());
		}

		SecuritySource securitySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters,
				SecuritySource.class);

		if (securitySource != null) {
			collaterlId = securitySource.getCMSSecurityId();
		}

		return collaterlId;
	}

	protected void createShareSecurity(ApprovedSecurity security) {
		// for shared security, need to validate shared security id doing create
		if (helper.isShareSecurity(security)) {
			checkExistingSharedSecId(security);
		}

		if (ICMSConstant.LONG_INVALID_VALUE == getCollateralIdWithoutStatus(security)) {
			// String seq = (new
			// SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_SHARE_SECURITY
			// , true);
			SecuritySource securitySource = new SecuritySource();
			// securitySource.setCMSSecuritySourceId(Long.parseLong(seq));
			securitySource.setCMSSecurityId(security.getCMSSecurityId());
			securitySource.setSourceSecurityId(security.getLOSSecurityId());
			securitySource.setSourceId(security.getSourceId());
			securitySource.setSecuritySubTypeId(security.getSecuritySubType().getStandardCodeValue());
			securitySource.setStatus(ICMSConstant.STATE_ACTIVE);

			getSecurityDao().store(securitySource, SecuritySource.class);
		}

	}

	protected void updateShareSecurity(ApprovedSecurity security) {
		// for shared security, need to validate shared security id before doing
		// update
		if (helper.isShareSecurity(security)) {
			checkExistingSharedSecId(security);
		}

		long shareCollateralId = security.getSharedSecurityId();
		SecuritySource ss = new SecuritySource();
		ss.setSourceSecurityId(security.getLOSSecurityId());
		ss.setCMSSecurityId(security.getCMSSecurityId());
		ss.setSourceId(security.getSourceId());

		long cmsid = findSecuritySourceByColIdAndSecId(ss);
		ss.setSourceSecurityId(security.getLOSSecurityId());
		ss.setCMSSecuritySourceId(cmsid);
		ss.setSourceId(security.getSourceId());

		if ((shareCollateralId != ICMSConstant.LONG_INVALID_VALUE) && (shareCollateralId != 0)) {
			ss.setCMSSecurityId(shareCollateralId);
		}
		ss.setSecuritySubTypeId(security.getSecuritySubType().getStandardCodeValue());
		SecuritySource tmpSecSource = (SecuritySource) getSecurityDao().retrieve(new Long(ss.getCMSSecuritySourceId()),
				ss.getClass());

		AccessorUtil.copyValue(ss, tmpSecSource);
		getSecurityDao().update(tmpSecSource, ss.getClass());

		// security = (ApprovedSecurity) AccessorUtil.deepClone (tmpSec);
	}

	protected void deleteShareSecurity(ApprovedSecurity security) {
		// for shared security, need to validate shared security id before doing
		// delete
		if (helper.isShareSecurity(security)) {
			checkExistingSharedSecId(security);
		}

		SecuritySource ss = new SecuritySource();
		ss.setSourceSecurityId(security.getLOSSecurityId());
		ss.setCMSSecurityId(security.getCMSSecurityId());
		ss.setSourceId(security.getSourceId());
		long cmsid = findSecuritySourceByColIdAndSecId(ss);
		ss.setSourceSecurityId(security.getLOSSecurityId());
		ss.setCMSSecuritySourceId(cmsid);
		ss.setSourceId(security.getSourceId());
		ss.setSecuritySubTypeId(security.getSecuritySubType().getStandardCodeValue());
		ss.setStatus(ICMSConstant.STATE_DELETED);

		SecuritySource tmpSecSource = (SecuritySource) getSecurityDao().retrieve(new Long(ss.getCMSSecuritySourceId()),
				ss.getClass());
		AccessorUtil.copyValue(ss, tmpSecSource);
		getSecurityDao().update(tmpSecSource, ss.getClass());

		// security = (ApprovedSecurity) AccessorUtil.deepClone (tmpSec);
	}

	private long findSecuritySourceByColIdAndSecId(SecuritySource security) {
		HashMap parameters = new HashMap();
		// parameters.put("sourceSecurityId", security.getSourceSecurityId());
		parameters.put("sourceId", security.getSourceId());
		parameters.put("CMSSecurityId", new Long(security.getCMSSecurityId()));

		SecuritySource securitySource = (SecuritySource) getSecurityDao().retrieveObjectByParameters(parameters,
				SecuritySource.class);

		if (securitySource == null) {
			throw new NoSuchSecurityException(security.getSourceSecurityId(), security.getSourceId());
		}

		return securitySource.getCMSSecuritySourceId();
	}

	protected void createSecurityInstrument(ApprovedSecurity security) {
		if (!helper.isShareSecurity(security)) {
			Vector securityInstrumentList = security.getSecurityInstrument();
			long cMSSecurityId = security.getCMSSecurityId();
			if (securityInstrumentList == null) {
				securityInstrumentList = new Vector();
			}

			if (security instanceof StageApprovedSecurity) {
				getSecurityHandlerHelper().persistStageSecurityInstrument(securityInstrumentList, cMSSecurityId);
			}
			else {
				getSecurityHandlerHelper().persistSecurityInstrument(securityInstrumentList, cMSSecurityId);
			}
		}
	}

	protected void updateSecurityInstrument(ApprovedSecurity security) {
		if (!helper.isShareSecurity(security)) {
			Vector securityInstrumentList = security.getSecurityInstrument();
			long cMSSecurityId = security.getCMSSecurityId();

			if (securityInstrumentList == null) {
				securityInstrumentList = new Vector();
			}

			HashMap map = new HashMap();
			map.put("CMSSecurityId", new Long(cMSSecurityId));

			if (security instanceof StageApprovedSecurity) {
				if (security.getUpdateStatusIndicator().equals("U")) {
					getSecurityHandlerHelper().removeAllSecurityInstrument(map, StageSecurityInstrument.class);
				}

				getSecurityHandlerHelper().persistStageSecurityInstrument(securityInstrumentList, cMSSecurityId);
			}
			else {
				if (security.getUpdateStatusIndicator().equals("U")) {
					getSecurityHandlerHelper().removeAllSecurityInstrument(map, SecurityInstrument.class);
				}

				getSecurityHandlerHelper().persistSecurityInstrument(securityInstrumentList, cMSSecurityId);
			}
		}
	}

	/**
	 * Used to validate Shared Security ID, if the security is a Shared Security
	 * 
	 * @param security
	 * @param cdb
	 */
	protected void checkExistingSharedSecId(ApprovedSecurity security) {
		HashMap parameters = new HashMap();
		parameters.put("referenceID", new Long(security.getSharedSecurityId()));
		parameters.put("transactionType", ICMSConstant.INSTANCE_COLLATERAL);

		CMSTransaction trx = (CMSTransaction) getSecurityDao().retrieveObjectByParameters(parameters,
				CMSTransaction.class);

		if ((trx == null) || (ICMSConstant.STATE_DELETED).equalsIgnoreCase(trx.getStatus())) {
			String err = "Security with Security Id : [" + security.getLOSSecurityId() + "] , Shared Security Id : ["
					+ "] is not valid. Please send with valid Shared Security Id.";

			logger.error(err);

			throw new IllegalStateException(err);
		}
	}

	/**
	 * To validate the owner of Security, by using unique combination key
	 * (Security ID + Source ID)
	 * 
	 * @param security
	 * @param cdb
	 */
	protected void checkSecurityOwner(ApprovedSecurity security) {
		ApprovedSecurity tmpSec = (ApprovedSecurity) getSecurityDao().retrieve(new Long(security.getCMSSecurityId()),
				security.getClass());

		if (tmpSec != null) {
			if ((tmpSec.getCMSSecurityId() == (security.getCMSSecurityId()))
					&& ((tmpSec.getSourceId() == null) || tmpSec.getSourceId().equals(security.getSourceId()))) {
				// validation past
			}
			else {
				String err = "Security with Security Id : [" + security.getLOSSecurityId() + "] and Source Id : ["
						+ security.getSourceId() + "] is not owner of the Security! No persistence allowed.";

				logger.error(err);

				throw new IllegalStateException(err);
			}
		}
	}

	/**
	 * Check if it is to delete the approved security.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isDeleteSecurity(ApprovedSecurity security) {
		return helper.isDelete(security);
	}

	/**
	 * Check if the security is changed.
	 * 
	 * @param security of type ApprovedSecurity
	 * @return boolean
	 */
	protected boolean isSecurityChanged(ApprovedSecurity security) {
		return helper.isSecurityChanged(security);
	}

	/**
	 * Return transaction key that is used by this actual business data.
	 * 
	 * @return security transaction key
	 */
	public String getTrxKey() {
		return SECURITY_KEY;
	}

	private String getRefEntryCode(String categoryCode, String sourceSecurityEntry) {
		HashMap parameters = new HashMap();
		parameters.put("categoryCode", categoryCode);
		parameters.put("entryCode", sourceSecurityEntry);
		Object result = getSecurityDao().retrieveObjectByParameters(parameters, OBCommonCodeEntry.class, "entryCode");
		OBCommonCodeEntry resultEntry = (OBCommonCodeEntry) result;
		return (resultEntry == null ? null : resultEntry.getRefEntryCode());
	}

}
