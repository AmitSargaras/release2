package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.support.FacilityCodeBasedCollateralUpdateMetaInfo;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.bus.StagingLimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.SecuritySource;
import com.integrosys.cms.host.eai.security.bus.StageApprovedSecurity;

/**
 * <p>
 * Handler to do update on the collateral based on the facility codes of it's
 * linked facility/limit.
 * <p>
 * Currently doing update of source id of collateral, both in collateral table
 * and source table if it's linked facility facility code matched, and also the
 * security subtype/type matched.
 * <p>
 * <b>Note:</b> Matched subtype will take precedent of matched type. Meaning, if
 * the collateral subtype matched the criteria, matched collateral type will be
 * ignored.
 * <p>
 * <b>Important:</b> This handler has to be run after
 * <tt>LimitsApprovedSecurityMap</tt> has been processed. Else it wont update
 * the info correctly.
 * @author Chong Jun Yong
 * 
 */
public class FacilityCodeBasedCollateralUpdateHandler extends AbstractCommonActualTrxHandler {

	private FacilityCodeBasedCollateralUpdateMetaInfo[] metaInfos;

	private ILimitDao limitDao;

	public void setMetaInfos(FacilityCodeBasedCollateralUpdateMetaInfo[] metaInfos) {
		this.metaInfos = metaInfos;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaBody = (AAMessageBody) msg.getMsgBody();
		Vector limits = aaBody.getLimits();

		if (limits == null || limits.isEmpty()) {
			return msg;
		}

		for (int i = 0; i < this.metaInfos.length; i++) {
			FacilityCodeBasedCollateralUpdateMetaInfo metainfo = this.metaInfos[i];

			for (Iterator itr = limits.iterator(); itr.hasNext();) {
				Limits aLimit = (Limits) itr.next();
				if (ArrayUtils.contains(metainfo.getApplicableFacilityCodes(), aLimit.getLimitGeneral()
						.getFacilityType().getStandardCodeValue())) {
					List limitSecurityMapList = this.limitDao
							.retrieveListOfActualLimitsApprovedSecurityMapByCmsLimitIdAndSourceIds(aLimit
									.getLimitGeneral().getCmsId(), null);
					for (Iterator itrSecMap = limitSecurityMapList.iterator(); itrSecMap.hasNext();) {
						LimitsApprovedSecurityMap limitSecMap = (LimitsApprovedSecurityMap) itrSecMap.next();
						Long cmsSecurityId = new Long(limitSecMap.getCmsSecurityId());

						// first round, check actual collateral
						ApprovedSecurity security = (ApprovedSecurity) this.limitDao.retrieve(cmsSecurityId,
								ApprovedSecurity.class);
						if (metainfo.getCollateralSourceId().equals(security.getSourceId())) {
							continue;
						}
						else if (ArrayUtils.contains(metainfo.getCollateralSubTypes(), security.getSecuritySubType()
								.getStandardCodeValue())) {
							logger.info("Update collateral source to [" + metainfo.getCollateralSourceId()
									+ "] for collateral, key [" + security.getCMSSecurityId() + "], subtype matched!");
							security.setSourceId(metainfo.getCollateralSourceId());
							limitSecMap.setSource(metainfo.getCollateralSourceId());
						}
						else if (ArrayUtils.contains(metainfo.getCollateralTypes(), security.getSecurityType()
								.getStandardCodeValue())) {
							logger.info("Update collateral source to [" + metainfo.getCollateralSourceId()
									+ "] for collateral, key [" + security.getCMSSecurityId() + "], type matched!");
							security.setSourceId(metainfo.getCollateralSourceId());
							limitSecMap.setSource(metainfo.getCollateralSourceId());
						}
						else {
							continue;
						}

						// second round, security source
						Map parameters = new WeakHashMap();
						parameters.put("CMSSecurityId", cmsSecurityId);

						SecuritySource securitySource = (SecuritySource) this.limitDao.retrieveObjectByParameters(
								parameters, SecuritySource.class);
						if (securitySource != null) {
							securitySource.setSourceId(metainfo.getCollateralSourceId());
							securitySource.setLastUpdateDate(new Date());
							this.limitDao.update(securitySource, SecuritySource.class);
						}

						// third round, staging collateral, and limit sec map
						// based on transaction
						parameters.clear();
						parameters.put("referenceID", cmsSecurityId);
						parameters.put("transactionType", ICMSConstant.INSTANCE_COLLATERAL);

						CMSTransaction collateralTransaction = (CMSTransaction) this.limitDao
								.retrieveObjectByParameters(parameters, CMSTransaction.class);
						if (collateralTransaction != null) {
							Long stagingCollateralId = new Long(collateralTransaction.getStageReferenceID());
							StageApprovedSecurity stageSecurity = (StageApprovedSecurity) this.limitDao.retrieve(
									stagingCollateralId, StageApprovedSecurity.class);
							stageSecurity.setSourceId(metainfo.getCollateralSourceId());
							this.limitDao.update(stageSecurity, StageApprovedSecurity.class);

							StagingLimitsApprovedSecurityMap stageSecMap = (StagingLimitsApprovedSecurityMap) this.limitDao
									.retrieveLimitApprovedSecurityMapByCmsLimitIdAndCmsCollateralId(limitSecMap
											.getCmsLimitId(), stagingCollateralId.longValue(), false);
							stageSecMap.setSource(metainfo.getCollateralSourceId());
							this.limitDao.update(stageSecMap, StagingLimitsApprovedSecurityMap.class);
						}

						// last, update itself
						this.limitDao.update(security, ApprovedSecurity.class);
						this.limitDao.update(limitSecMap, LimitsApprovedSecurityMap.class);
					}
				}
			}
		}

		return msg;
	}
}
