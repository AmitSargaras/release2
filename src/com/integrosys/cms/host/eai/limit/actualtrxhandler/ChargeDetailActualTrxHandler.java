package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchChargeDetailException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitSecMapException;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.IChargeDetailJdbc;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitChargeMap;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.StagingChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.StagingLimitChargeMap;
import com.integrosys.cms.host.eai.limit.bus.StagingLimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Handler to process charge detail, limit charge map from the AA Message.
 * 
 * @author Chong Jun Yong
 * 
 */
public class ChargeDetailActualTrxHandler extends AbstractCommonActualTrxHandler {

	/** value for prior charge type is N/A */
	private static final String PRIOR_CHARGE_TYPE_NOT_AVAILABLE = "O";

	/** charge rank for prior charge not required */
	private static final int CHARGE_RANK_PRIOR_CHARGE_NOT_REQUIRED = 1;

	private ILimitDao limitDao;

	private IChargeDetailJdbc chargeDetailJdbcDao;

	private boolean isRepublish;

	private Map variationProperties;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public void setChargeDetailJdbcDao(IChargeDetailJdbc chargeDetailJdbcDao) {
		this.chargeDetailJdbcDao = chargeDetailJdbcDao;
	}

	public void setRepublish(boolean isRepublish) {
		this.isRepublish = isRepublish;
	}

	public void setVariationProperties(Map variationProperties) {
		this.variationProperties = variationProperties;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessageBody.getLimitProfile();

		String messageSource = EAIMessageSynchronizationManager.getMessageSource();

		// variation case when the CMS Limit Profile Id in the Limit Profile is
		// a valid one, ie, greater than 0, and can be found in the system.
		boolean variation = limitProfile.getCMSLimitProfileId() != null
				&& limitProfile.getCMSLimitProfileId().longValue() > 0;

		Vector chargeDetails = aaMessageBody.getChargeDetail();
		LimitActualHelper helper = new LimitActualHelper();
		long limitProfileId = limitProfile.getLimitProfileId();

		if (chargeDetails == null) {
			chargeDetails = new Vector();
		}

		if (this.isRepublish) {
			removeChargeDetailsNotInMessage(aaMessageBody);
		}

		for (Iterator iter = chargeDetails.iterator(); iter.hasNext();) {
			ChargeDetail charge = (ChargeDetail) iter.next();

			defaultPriorChargeIfRankMetCriteria(charge);

			long cmsSecurityId = 0l;
			if (charge.getCmsSecurityId() > 0) {
				cmsSecurityId = charge.getCmsSecurityId();
			}
			else {
				cmsSecurityId = helper.getInternalCollateralID(charge.getSecurityId(), aaMessageBody
						.getLimitsApprovedSecurityMap());

				if (cmsSecurityId == ICMSConstant.LONG_INVALID_VALUE || cmsSecurityId == 0) {
					cmsSecurityId = this.chargeDetailJdbcDao.getCmsSecurityIdByLosSecurityId(charge.getSecurityId(),
							EAIMessageSynchronizationManager.getMessageSource());
				}
			}

			String currency = this.chargeDetailJdbcDao.getSecurityCurrencyCodeByCmsSecurityId(cmsSecurityId);
			if (currency == null) {
				throw new NoSuchSecurityException(cmsSecurityId);
			}

			Map parameters = new WeakHashMap();
			parameters.put("transactionType", ICMSConstant.INSTANCE_COLLATERAL);
			parameters.put("referenceID", new Long(cmsSecurityId));
			CMSTransaction trx = (CMSTransaction) this.limitDao.retrieveObjectByParameters(parameters,
					CMSTransaction.class);

			charge.setCmsSecurityId(cmsSecurityId);
			charge.setChargeCurrency(currency);
			charge.setPriorChargeCurrency(currency);

			if (charge.getChargeDetailId() > 0) {
				ChargeDetail storedChargeDetail = (ChargeDetail) this.limitDao.retrieve(new Long(charge
						.getChargeDetailId()), ChargeDetail.class);

				if (storedChargeDetail == null) {
					throw new NoSuchChargeDetailException(limitProfileId, charge.getChargeDetailId());
				}

				if (variation) {
					LimitHelper limitHelper = new LimitHelper();
					List chargeDetailVariationProperties = (List) this.variationProperties
							.get(new VariationPropertiesKey(messageSource, ChargeDetail.class.getName()));
					limitHelper.copyVariationProperties(charge, storedChargeDetail, chargeDetailVariationProperties);
				}
				else {
					AccessorUtil.copyValue(charge, storedChargeDetail, new String[] { "ChargeDetailId", "CmsRefId",
							"Status" });
				}
				this.limitDao.update(storedChargeDetail, ChargeDetail.class);

				// prepare CMS Limit Ids for the charge detail
				List cmsLimitIdList = (charge.getCMSLimitId() != null) ? new ArrayList(Arrays.asList(charge
						.getCMSLimitId())) : new ArrayList();
				String[] losLimitIds = (charge.getLimitId() != null) ? charge.getLimitId() : new String[0];
				for (int i = 0; i < losLimitIds.length; i++) {
					cmsLimitIdList.add(new Long(helper.getInternalLimitId(aaMessageBody.getLimits(), losLimitIds[i])));
				}

				// prepare the CMS Limit ids which to be created based on the
				// existing limit charge map
				Long[] cmsLimitIds = (Long[]) cmsLimitIdList.toArray(new Long[0]);
				List limitChargeMapList = this.limitDao.retrieveLimitChargeMapByChargeDetailId(charge
						.getChargeDetailId(), true);
				for (Iterator itr = limitChargeMapList.iterator(); itr.hasNext();) {
					LimitChargeMap limitChargeMap = (LimitChargeMap) itr.next();
					cmsLimitIds = (Long[]) ArrayUtils.removeElement(cmsLimitIds, new Long(limitChargeMap.getLimitID()));
				}

				// store stage charge detail, based on the collateral id, ref id
				// and charge detail id
				long stageChargeDetaildId = this.chargeDetailJdbcDao
						.retrieveStageChargeDetailIdByActualChargeDetailId(charge.getChargeDetailId());
				StagingChargeDetail storedStagingChargeDetail = (StagingChargeDetail) this.limitDao.retrieve(new Long(
						stageChargeDetaildId), StagingChargeDetail.class);
				AccessorUtil.copyValue(storedChargeDetail, storedStagingChargeDetail, new String[] { "ChargeDetailId",
						"CmsRefId", "CmsCollateralId" });
				this.limitDao.update(storedStagingChargeDetail, StagingChargeDetail.class);

				// loop through the cms limit ids list, to create limit - charge
				// map, for both actual and staging
				for (int i = 0; i < cmsLimitIds.length; i++) {
					LimitChargeMap limitChargeMap = new LimitChargeMap();

					limitChargeMap.setLimitID(cmsLimitIds[i].longValue());
					limitChargeMap.setCollateralID(cmsSecurityId);

					long chargeId = 01;
					// first look the chargeid from xml
					chargeId = helper.getChargeIdByCmsLimitIdAndCmsSecurityId(cmsSecurityId,
							cmsLimitIds[i].longValue(), aaMessageBody.getLimitsApprovedSecurityMap());

					// if not exist look into the db to get the chargeid
					if (chargeId < 1) {
						chargeId = this.chargeDetailJdbcDao
								.retrieveChargeIdByCmsLimitIdAndCmsSecurityIdAndCmsLimitProfileId(cmsLimitIds[i]
										.longValue(), cmsSecurityId, limitProfileId, true);
					}

					limitChargeMap.setChargeID(chargeId);
					limitChargeMap.setChargeDetailID(charge.getChargeDetailId());
					limitChargeMap.setLimitProfileId(limitProfileId);

					this.limitDao.store(limitChargeMap, LimitChargeMap.class);

					if (trx != null) {
						StagingLimitChargeMap stageLimitChargeMap = new StagingLimitChargeMap();
						AccessorUtil.copyValue(limitChargeMap, stageLimitChargeMap);
						stageLimitChargeMap.setCollateralID(trx.getStageReferenceID());
						stageLimitChargeMap.setChargeDetailID(stageChargeDetaildId);

						long stagingChargeId = 0l;

						parameters.clear();
						parameters.put("cmsLimitId", cmsLimitIds[i]);
						parameters.put("cmsSecurityId", new Long(trx.getStageReferenceID()));
						parameters.put("limitProfileId", new Long(limitProfileId));

						StagingLimitsApprovedSecurityMap tempLmtAppSecMap = (StagingLimitsApprovedSecurityMap) this.limitDao
								.retrieveObjectByParameters(parameters, StagingLimitsApprovedSecurityMap.class);
						if (tempLmtAppSecMap != null) {
							stagingChargeId = tempLmtAppSecMap.getCmsId();
						}
						else {
							throw new NoSuchLimitSecMapException(cmsLimitIds[i].longValue(), trx.getStageReferenceID(),
									limitProfileId, false);
						}

						stageLimitChargeMap.setChargeID(stagingChargeId);

						this.limitDao.store(stageLimitChargeMap, StagingLimitChargeMap.class);
					}
				}
			}
			else {
				charge.setStatus(ICMSConstant.STATE_ACTIVE);

				Long chargeDetailId = (Long) this.limitDao.store(charge, ChargeDetail.class);
				charge.setChargeDetailId(chargeDetailId.longValue());
				charge.setCmsRefId(chargeDetailId.longValue());
				this.limitDao.update(charge, ChargeDetail.class);

				Long stageChargeID = null;
				if (trx != null) {
					StagingChargeDetail staging = new StagingChargeDetail();
					AccessorUtil.copyValue(charge, staging);

					staging.setCmsRefId(chargeDetailId.longValue());
					staging.setCmsSecurityId(trx.getStageReferenceID());

					stageChargeID = (Long) this.limitDao.store(staging, StagingChargeDetail.class);
				}

				Vector limitsForCharge = null;
				String[] limitIds = null;
				if (variation) {
					// get existing limits
					limitsForCharge = getStoredLimits(aaMessageBody.getLimitProfile().getCMSLimitProfileId()
							.longValue());
					// get the new LOS limit ids from xml
					List losLimitIdList = new ArrayList();
					if (charge.getLimitId() != null && charge.getLimitId().length > 0) {
						losLimitIdList.addAll(Arrays.asList(charge.getLimitId()));
					}
					// get the existing LOS limit ids from db
					if (charge.getCMSLimitId() != null && charge.getCMSLimitId().length > 0) {
						List oldLOSLimitIds = prepareLOSLimitIds(charge.getLimitId(), charge.getCMSLimitId());
						losLimitIdList.addAll(oldLOSLimitIds);
					}

					limitIds = (String[]) losLimitIdList.toArray(new String[] {});
				}
				else {
					limitsForCharge = aaMessageBody.getLimits();
					limitIds = charge.getLimitId();
				}

				if (limitIds != null) {
					for (int i = 0; i < limitIds.length; i++) {
						String strLimitID = limitIds[i].trim();
						LimitChargeMap limitChargeMap = new LimitChargeMap();
						Limits tmpLmt = null;
						tmpLmt = helper.getInternalLimit(limitsForCharge, strLimitID);

						limitChargeMap.setLimitID(tmpLmt.getLimitGeneral().getCmsId());
						limitChargeMap.setCollateralID(cmsSecurityId);
						limitChargeMap.setChargeID(helper.getChargeID(charge.getSecurityId(), strLimitID, tmpLmt
								.getLimitGeneral().getCIFId(), aaMessageBody.getLimitsApprovedSecurityMap()));
						limitChargeMap.setChargeDetailID(chargeDetailId.longValue());
						limitChargeMap.setLimitProfileId(limitProfileId);

						this.limitDao.store(limitChargeMap, LimitChargeMap.class);

						StagingLimitChargeMap stageLimitChargeMap;

						if (trx != null) {
							stageLimitChargeMap = new StagingLimitChargeMap();
							AccessorUtil.copyValue(limitChargeMap, stageLimitChargeMap);
							stageLimitChargeMap.setCollateralID(trx.getStageReferenceID());
							stageLimitChargeMap.setChargeDetailID(stageChargeID.longValue());

							stageLimitChargeMap.setChargeID(helper.getChargeID(charge.getSecurityId(), strLimitID,
									tmpLmt.getLimitGeneral().getCIFId(), getStageLimitSecurityMap(limitProfile, trx
											.getStageReferenceID())));

							this.limitDao.store(stageLimitChargeMap, StagingLimitChargeMap.class);
						}
					}
				}
			}
		}

		return msg;
	}

	/**
	 * <p>
	 * To default prior charge to certain value, for prior charge amount (null)
	 * and prior charge type (to N/A).
	 * <p>
	 * The <i>chargeRank</i> supplied will be used to check against
	 * {@link #CHARGE_RANK_PRIOR_CHARGE_NOT_REQUIRED}
	 * @param charge the charge object
	 */
	protected void defaultPriorChargeIfRankMetCriteria(ChargeDetail charge) {
		if (charge.getSecurityRanking() != null
				&& charge.getSecurityRanking().intValue() == CHARGE_RANK_PRIOR_CHARGE_NOT_REQUIRED) {
			charge.setPriorChargeAmount(null);
			charge.setPriorChargeCurrency(null);
			charge.setPriorChargeType(PRIOR_CHARGE_TYPE_NOT_AVAILABLE);
		}
	}

	protected void removeChargeDetailsNotInMessage(AAMessageBody aaMessage) {

		LimitProfile limitProfile = aaMessage.getLimitProfile();

		/*
		 * list of charge detail ids, can be used to check against whether to
		 * delete the charge detail and it's map
		 */
		List chargeDetailIdList = new ArrayList();
		Vector chargeDetails = aaMessage.getChargeDetail();
		if (chargeDetails != null) {
			for (Iterator itr = chargeDetails.iterator(); itr.hasNext();) {
				ChargeDetail charge = (ChargeDetail) itr.next();
				if (charge.getChargeDetailId() > 0) {
					chargeDetailIdList.add(new Long(charge.getChargeDetailId()));
				}
			}
		}

		/*
		 * list of stage charge detail ids, can be used to check against whether
		 * to delete the staging charge detail and it's map
		 */
		Collection stageChargeDetailIdList = this.chargeDetailJdbcDao
				.retrieveStageChargeDetailIdListByActualChargeDetailIdList(chargeDetailIdList);

		long limitProfileIdDeleteRef = limitProfile.getLimitProfileId();

		Map parameters = new WeakHashMap();
		parameters.put("limitProfileId", new Long(limitProfileIdDeleteRef));

		List storedStageLimitChargeMaps = this.limitDao.retrieveObjectsListByParameters(parameters,
				StagingLimitChargeMap.class);
		for (Iterator stgLmtChgMpIter = storedStageLimitChargeMaps.iterator(); stgLmtChgMpIter.hasNext();) {
			StagingLimitChargeMap storedStageLimitChargeMap = (StagingLimitChargeMap) stgLmtChgMpIter.next();

			if (!stageChargeDetailIdList.contains(new Long(storedStageLimitChargeMap.getChargeDetailID()))) {
				storedStageLimitChargeMap.setStatus(ICMSConstant.STATE_DELETED);
				this.limitDao.update(storedStageLimitChargeMap, StagingLimitChargeMap.class);
			}
		}

		LimitChargeMap[] oldLimitChargeMap = this.chargeDetailJdbcDao
				.getLimitChargeMapByLimitProfileId(limitProfileIdDeleteRef);

		for (int i = 0; i < oldLimitChargeMap.length; i++) {

			LimitChargeMap chargeMap = oldLimitChargeMap[i];

			if (!chargeDetailIdList.contains(new Long(chargeMap.getChargeDetailID()))) {
				chargeMap.setStatus(ICMSConstant.STATE_DELETED);
				this.limitDao.update(chargeMap, LimitChargeMap.class);
			}

			ChargeDetail chargeDetail = (ChargeDetail) this.limitDao.retrieve(new Long(chargeMap.getChargeDetailID()),
					ChargeDetail.class);

			if (chargeDetail == null) {
				continue;
			}

			parameters.clear();
			parameters.put("cmsRefId", new Long(chargeDetail.getChargeDetailId()));

			List stagingChargeDetailList = this.limitDao.retrieveObjectListByParameters(parameters,
					StagingChargeDetail.class);
			for (Iterator itr = stagingChargeDetailList.iterator(); itr.hasNext();) {
				StagingChargeDetail stageChargeDetail = (StagingChargeDetail) itr.next();

				if (!stageChargeDetailIdList.contains(new Long(stageChargeDetail.getChargeDetailId()))) {
					stageChargeDetail.setStatus(ICMSConstant.STATE_DELETED);
					this.limitDao.update(stageChargeDetail, StagingChargeDetail.class);
				}
			}

			if (!chargeDetailIdList.contains(new Long(chargeDetail.getChargeDetailId()))) {
				chargeDetail.setStatus(ICMSConstant.STATE_DELETED);
				this.limitDao.update(chargeDetail, ChargeDetail.class);
			}
		}
	}

	private Vector getStageLimitSecurityMap(LimitProfile limitProfile, long stageCmsSecurityId) {
		Map parameters = new WeakHashMap();
		parameters.put("limitProfileId", new Long(limitProfile.getLimitProfileId()));
		parameters.put("cmsSecurityId", new Long(stageCmsSecurityId));

		List resultList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters, "updateStatusIndicator",
				StagingLimitsApprovedSecurityMap.class);

		if (resultList != null) {
			return new Vector(resultList);
		}
		else {
			throw new NoSuchLimitSecMapException(limitProfile.getLOSAANumber(), stageCmsSecurityId, false);
		}
	}

	private Vector getStoredLimits(long limitProfileId) {
		Map parameters = new WeakHashMap();
		parameters.clear();
		parameters.put("cmsLimitProfileId", new Long(limitProfileId));

		List limitList = this.limitDao.retrieveNonDeletedObjectsListByParameters(parameters, "updateStatusIndicator",
				LimitGeneral.class);

		Vector storedLimits = new Vector();
		for (Iterator iterator = limitList.iterator(); iterator.hasNext();) {
			LimitGeneral general = (LimitGeneral) iterator.next();
			Limits limit = new Limits();
			limit.setLimitGeneral(general);
			storedLimits.addElement(limit);

		}
		return storedLimits;
	}

	/* For the variation case */
	private List prepareLOSLimitIds(String[] LOSLimitIds, Long[] CMSLimitIds) {
		List existingLOSLimitIds = new ArrayList();
		for (int i = 0; i < CMSLimitIds.length; i++) {
			Long CMSID = CMSLimitIds[i];
			Map parameters = new WeakHashMap();
			parameters.put("cmsId", CMSID);
			LimitGeneral limit = (LimitGeneral) this.limitDao
					.retrieveObjectByParameters(parameters, LimitGeneral.class);
			existingLOSLimitIds.add(limit.getLOSLimitId());
		}
		return existingLOSLimitIds;

	}

}
