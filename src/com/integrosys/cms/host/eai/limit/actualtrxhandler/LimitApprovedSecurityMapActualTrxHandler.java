package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.LimitSecMapAlreadyExistsException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.bus.StagingLimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecuritySource;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * <p>
 * Handler to process limit security maps, both CMS Limit and Security keys are
 * important to be processed. If there is none, LOS Limit and Security keys will
 * be used to retrieve CMS internal keys from CMS persistent storage.
 * <p>
 * <b>Note: </b> Limit security maps info will be required for charge detail
 * (processed by <tt>ChargeDetailActualTrxHandler</tt>), such as CMS Security
 * Id, Limit Security Map charge id. So make sure this handler processing the
 * records correctly before look into charge detail handler.
 * <p>
 * If there is no limit security maps found in the AA Message body, this handler
 * will be skipped.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitApprovedSecurityMapActualTrxHandler extends AbstractCommonActualTrxHandler {

	private final static LimitHelper helper = new LimitHelper();

	protected ILimitDao limitDao;

	private ISecurityDao securityDao;

	private Map variationProperties;

	private String[] sourceIdsRequiredDefaultDrawPercentage;

	private Double defaultDrawPercentage = new Double(100d);

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public void setVariationProperties(Map variationProperties) {
		this.variationProperties = variationProperties;
	}

	public void setSourceIdsRequiredDefaultDrawPercentage(String[] sourceIdsRequiredDefaultDrawPercentage) {
		this.sourceIdsRequiredDefaultDrawPercentage = sourceIdsRequiredDefaultDrawPercentage;
	}

	public void setDefaultDrawPercentage(Double defaultDrawPercentage) {
		this.defaultDrawPercentage = defaultDrawPercentage;
	}

	public Message persistActualTrx(Message msg) {

		AAMessageBody msgBody = (AAMessageBody) msg.getMsgBody();

		if (msgBody.getLimitsApprovedSecurityMap() == null || msgBody.getLimitsApprovedSecurityMap().isEmpty()) {
			logger.info("There is no limit security map to be processed for this message, header info ["
					+ msg.getMsgHeader() + "], skip to process.");
			return msg;
		}

		Vector limitsApprovedSecurityMap = msgBody.getLimitsApprovedSecurityMap();

		LimitProfile limitsProfile = msgBody.getLimitProfile();
		boolean variation = false;
		if ((limitsProfile.getCMSLimitProfileId() != null) && (limitsProfile.getCMSLimitProfileId().longValue() > 0)) {
			variation = true;
		}

		limitsApprovedSecurityMap = storeLimitsSecurityMaps(msgBody, limitsApprovedSecurityMap, variation, msg
				.getMsgHeader().getSource());

		msgBody.setLimitsApprovedSecurityMap(limitsApprovedSecurityMap);
		return msg;

	}

	/**
	 * THis method will check whether the updatestatusIndicator is set to 'D' in
	 * the preprocess method . If not, method will check whether a create should
	 * be perform or a update. Method will try to load the record . If it does
	 * not exist in the database, method do a create If method is able to
	 * retrieve the record , it will be process as a update.
	 */
	protected Vector storeLimitsSecurityMaps(AAMessageBody aaMessage, Vector limitSecuritySecMaps, boolean variation,
			String sourceId) {
		if ((limitSecuritySecMaps == null) || limitSecuritySecMaps.isEmpty()) {
			limitSecuritySecMaps = new Vector();
		}

		long limitProfileId = aaMessage.getLimitProfile().getLimitProfileId();

		for (int i = limitSecuritySecMaps.size() - 1; i >= 0; i--) {

			LimitsApprovedSecurityMap limitSecMap = (LimitsApprovedSecurityMap) limitSecuritySecMaps.elementAt(i);

			if (limitSecMap.getSecurityId() != null) {
				limitSecMap.setSecurityId(limitSecMap.getSecurityId().toUpperCase());
			}

			if (limitSecMap.getCmsLimitId() <= 0) {
				LimitGeneral limitGeneral = (LimitGeneral) this.limitDao.retrieveLimitByLOSLimitId(limitSecMap
						.getLimitId());

				if (limitGeneral != null) {
					limitSecMap.setCmsLimitId(limitGeneral.getCmsId());
				}
				else {
					throw new NoSuchLimitException(limitSecMap.getCIFId(), limitSecMap.getSubProfileId().longValue(),
							limitProfileId, limitSecMap.getLimitId());
				}
			}

			if (limitSecMap.getCmsSecurityId() <= 0) {
				List securitySourceList = this.securityDao.retrieveSharedSecurityListBySourceSecurityId(limitSecMap
						.getSecurityId(), sourceId);

				if ((securitySourceList != null) && (securitySourceList.size() >= 1)) {
					if (securitySourceList.size() == 1) {
						SecuritySource securitySource = (SecuritySource) securitySourceList.get(0);

						limitSecMap = storeLimitSecurityMap(securitySource.getCMSSecurityId(), limitSecMap,
								limitProfileId, variation, sourceId);
						limitSecuritySecMaps.setElementAt(limitSecMap, i);
					}
					else {
						logger.debug("More than one security records found for source sec id: ["
								+ limitSecMap.getSecurityId() + "]");

						boolean found = false;

						for (Iterator iter = securitySourceList.iterator(); iter.hasNext();) {
							SecuritySource securitySource = (SecuritySource) iter.next();
							LimitsApprovedSecurityMap tmpLimitSecMap = new LimitsApprovedSecurityMap();
							AccessorUtil.copyValue(limitSecMap, tmpLimitSecMap);
							limitSecMap = storeLimitSecurityMap(securitySource.getCMSSecurityId(), tmpLimitSecMap,
									limitProfileId, variation, sourceId);

							if ((securitySource.getSecuritySubTypeId() != null)
									&& ICMSConstant.COLTYPE_MAR_MAIN_IDX_LOCAL.equalsIgnoreCase(securitySource
											.getSecuritySubTypeId().trim())) {
								limitSecuritySecMaps.setElementAt(limitSecMap, i);

								found = true;
							}
						}

						if (!found) {
							throw new NoSuchSecurityException(limitSecMap.getSecurityId(), sourceId);
						}
					}
				}
				else {
					throw new NoSuchSecurityException(limitSecMap.getSecurityId(), sourceId);
				}
			}
			else {
				LimitsApprovedSecurityMap tmpsref = new LimitsApprovedSecurityMap();
				AccessorUtil.copyValue(limitSecMap, tmpsref);
				limitSecMap = storeLimitSecurityMap(limitSecMap.getCmsSecurityId(), tmpsref, limitProfileId, variation,
						sourceId);
				limitSecuritySecMaps.setElementAt(limitSecMap, i);
			}
		}

		return limitSecuritySecMaps;
	}

	protected LimitsApprovedSecurityMap storeLimitSecurityMap(long cmsSecurityID,
			LimitsApprovedSecurityMap limitSecMap, long limitProfileId, boolean variation, String source) {

		limitSecMap.setCmsSecurityId(cmsSecurityID);
		limitSecMap.setLimitProfileId(limitProfileId);
		limitSecMap.setSource(source);

		// to update cms_stage_limit_security_map table also
		// get the latest trx to get security staging reference id
		Map parameters = new WeakHashMap();
		parameters.put("transactionType", ICMSConstant.INSTANCE_COLLATERAL);
		parameters.put("referenceID", new Long(limitSecMap.getCmsSecurityId()));
		CMSTransaction trx = (CMSTransaction) this.limitDao
				.retrieveObjectByParameters(parameters, CMSTransaction.class);

		StagingLimitsApprovedSecurityMap stageMap = null;

		if (trx != null) {
			stageMap = new StagingLimitsApprovedSecurityMap();
			AccessorUtil.copyValue(limitSecMap, stageMap);
			stageMap.setCmsSecurityId(trx.getStageReferenceID());
		}

		if ((limitSecMap.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& (limitSecMap.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {

			if (ArrayUtils.contains(this.sourceIdsRequiredDefaultDrawPercentage, source)) {
				limitSecMap.setPercentDraw(this.defaultDrawPercentage);
			}

			LimitsApprovedSecurityMap existingMap = (LimitsApprovedSecurityMap) this.limitDao
					.retrieveLimitApprovedSecurityMapByCmsLimitIdAndCmsCollateralId(limitSecMap.getCmsLimitId(),
							limitSecMap.getCmsSecurityId(), true);
			if (existingMap != null) {
				throw new LimitSecMapAlreadyExistsException(limitSecMap.getLimitId(), limitSecMap.getSecurityId());
			}

			Long chargeId = (Long) this.limitDao.store(limitSecMap, limitSecMap.getClass());

			// to set sys gen id, the key between actual and staging
			limitSecMap.setCmsId(chargeId.longValue());
			limitSecMap.setCmsRefId(chargeId);

			this.limitDao.update(limitSecMap, limitSecMap.getClass());

			// Stores staging map
			if (stageMap != null) {
				stageMap.setCmsLimitId(limitSecMap.getCmsLimitId());
				stageMap.setSource(source);
				stageMap.setCmsRefId(chargeId);
				this.limitDao.store(stageMap, stageMap.getClass());
			}
			else {
				logger.warn("StagingLimitsApprovedSecurityMap is missing, will not have staging, for cms limit id ["
						+ limitSecMap.getCmsLimitId() + "], cms security id [" + cmsSecurityID + "]");
			}
		}
		else if ((limitSecMap.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& ((limitSecMap.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR)) || (limitSecMap
						.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR)))))) {
			parameters.clear();

			if (limitSecMap.getCmsId() > 0) {
				parameters.put("cmsId", new Long(limitSecMap.getCmsId()));
			}
			else if (limitSecMap.getLimitsApprovedSecurityMapId() > 0) {
				parameters.put("cmsId", new Long(limitSecMap.getLimitsApprovedSecurityMapId()));
			}
			else {
				parameters.put("limitId", limitSecMap.getLimitId());
				parameters.put("securityId", limitSecMap.getSecurityId());
				parameters.put("CIFId", limitSecMap.getCIFId());
			}

			List resultList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters,
					"updateStatusIndicator", LimitsApprovedSecurityMap.class);

			for (Iterator itr = resultList.iterator(); itr.hasNext();) {
				LimitsApprovedSecurityMap storedLimitSecMap = (LimitsApprovedSecurityMap) itr.next();

				if (variation) {
					List copyingProperties = (List) this.variationProperties.get(new VariationPropertiesKey(source,
							LimitsApprovedSecurityMap.class.getName()));
					storedLimitSecMap = (LimitsApprovedSecurityMap) helper.copyVariationProperties(limitSecMap,
							storedLimitSecMap, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(limitSecMap, storedLimitSecMap, new String[] { IEaiConstant.CMSID,
							"CmsLimitId", "DrawPercentage", "CmsRefId" });
				}
				storedLimitSecMap.setSource(source);

				if (storedLimitSecMap.getPercentDraw() != null && storedLimitSecMap.getPercentDraw().doubleValue() > 0) {
					logger.info("There is draw percentage input into system for AA [" + limitSecMap.getAANumber()
							+ "], limit [" + storedLimitSecMap.getLimitId() + "], collateral ["
							+ storedLimitSecMap.getSecurityId() + "], value is [" + storedLimitSecMap.getPercentDraw()
							+ "], skipped.");
				}
				else {
					if (storedLimitSecMap.getAmtDraw() == null || storedLimitSecMap.getAmtDraw().doubleValue() == 0) {
						if (ArrayUtils.contains(this.sourceIdsRequiredDefaultDrawPercentage, source)) {
							limitSecMap.setPercentDraw(this.defaultDrawPercentage);
						}
					}
				}

				this.limitDao.update(storedLimitSecMap, storedLimitSecMap.getClass());

				limitSecMap.setCmsId(storedLimitSecMap.getCmsId());

			}

			if (stageMap != null) {
				parameters.clear();
				parameters.put("cmsLimitId", new Long(limitSecMap.getCmsLimitId()));
				parameters.put("cmsSecurityId", new Long(stageMap.getCmsSecurityId()));
				parameters.put("CIFId", limitSecMap.getCIFId());

				List stageLimitSecMapResultList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters,
						"updateStatusIndicator", StagingLimitsApprovedSecurityMap.class);

				for (Iterator itr = stageLimitSecMapResultList.iterator(); itr.hasNext();) {
					StagingLimitsApprovedSecurityMap tmpStage = (StagingLimitsApprovedSecurityMap) itr.next();

					if (variation) {
						List copyingProperties = (List) this.variationProperties.get(new VariationPropertiesKey(source,
								LimitsApprovedSecurityMap.class.getName()));
						helper.copyVariationProperties(stageMap, tmpStage, copyingProperties);
					}
					else {
						AccessorUtil.copyValue(stageMap, tmpStage, new String[] { IEaiConstant.CMSID, "CmsRefId" });
					}
					tmpStage.setSource(source);
					this.limitDao.update(tmpStage, tmpStage.getClass());
				}
			}
		}
		else {
			parameters.clear();
			parameters.put("limitId", limitSecMap.getLimitId());
			parameters.put("securityId", limitSecMap.getSecurityId());
			parameters.put("AANumber", limitSecMap.getAANumber());
			parameters.put("CIFId", limitSecMap.getCIFId());

			List resultList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters,
					"updateStatusIndicator", LimitsApprovedSecurityMap.class);

			for (Iterator itr = resultList.iterator(); itr.hasNext();) {
				LimitsApprovedSecurityMap storedLimitSecMap = (LimitsApprovedSecurityMap) itr.next();

				if (variation) {
					List copyingProperties = (List) this.variationProperties.get(new VariationPropertiesKey(source,
							LimitsApprovedSecurityMap.class.getName()));
					helper.copyVariationProperties(storedLimitSecMap, limitSecMap, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(storedLimitSecMap, limitSecMap);
				}
			}
		}

		return limitSecMap;
	}
}
