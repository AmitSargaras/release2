package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.customer.CustomerNotMatchException;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.EaiLimitProfileMessageException;
import com.integrosys.cms.host.eai.limit.NoSuchChargeDetailException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitSecMapException;
import com.integrosys.cms.host.eai.limit.bus.ChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.IChargeDetailJdbc;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.bus.ISecurityJdbc;

public class LimitInternalKeyCheckingHandler extends AbstractCommonActualTrxHandler {

	private ILimitJdbc limitJdbc;

	private IChargeDetailJdbc chargeDetailJdbc;

	private ISecurityJdbc securityJdbcDao;

	public ISecurityJdbc getSecurityJdbcDao() {
		return securityJdbcDao;
	}

	public void setSecurityJdbcDao(ISecurityJdbc securityJdbcDao) {
		this.securityJdbcDao = securityJdbcDao;
	}

	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}

	public IChargeDetailJdbc getChargeDetailJdbc() {
		return chargeDetailJdbc;
	}

	public void setChargeDetailJdbc(IChargeDetailJdbc chargeDetailJdbc) {
		this.chargeDetailJdbc = chargeDetailJdbc;
	}

	protected Message checkValidCMSId(Message msg) {

		AAMessageBody msgBody = (AAMessageBody) msg.getMsgBody();

		/*
		 * If CMSLimitProfileId is greater than zero, assume as variation
		 * (renew) and continue validating the old cms ids (internal keys)
		 * otherwise skip the validation process
		 */
		if ((msgBody.getLimitProfile().getCMSLimitProfileId() != null)
				&& (msgBody.getLimitProfile().getCMSLimitProfileId().longValue() > 0)) {

			validateCMSLimitProfileId(msgBody.getLimitProfile());

			long cmsLimitProfileId = msgBody.getLimitProfile().getCMSLimitProfileId().longValue();

			if (msgBody.getLimits() != null) {
				validateCMSLimitId(msgBody.getLimits(), cmsLimitProfileId);
			}

			if (msgBody.getLimitsApprovedSecurityMap() != null) {
				validateCMSLimitSecurityMapId(msgBody.getLimitsApprovedSecurityMap(), cmsLimitProfileId);
			}

			if (msgBody.getChargeDetail() != null) {
				validateCMSSecurityLimitChargeId(msgBody.getChargeDetail(), cmsLimitProfileId);
			}

			updateRenewFlag(cmsLimitProfileId);
		}

		return msg;
	}

	private void validateCMSLimitProfileId(LimitProfile limitProfile) {
		long cmsLimitProfileId = limitProfile.getCMSLimitProfileId().longValue();
		Long cmsId = getLimitJdbc().getLimitProfileIdByCMSLimitProfileID(cmsLimitProfileId);

		if ((cmsId != null) && (cmsId.longValue() > 0)) {
			limitProfile.setLimitProfileId(cmsId.longValue());
			String cifNumber = getLimitJdbc().getCifNumberByCmsLimitProfileId(cmsId.longValue());
			if (!limitProfile.getCIFId().equals(cifNumber)) {
				throw new CustomerNotMatchException(cifNumber, limitProfile.getCIFId());
			}
		}
		else {
			throw new NoSuchLimitProfileException(limitProfile.getCIFId(), limitProfile.getSubProfileId(),
					cmsLimitProfileId);
		}
	}

	private void validateCMSLimitId(Vector limits, long limitProfileId) {

		if ((limits == null) || (limits.size() < 1)) {
			return;
		}

		for (Iterator iterator = limits.iterator(); iterator.hasNext();) {
			Limits limit = (Limits) iterator.next();
			long oldCMSLimitId;
			/*
			 * If the CMSLimitId is given, that limit is a existing limit
			 * otherwise new limit
			 */
			if (limit.getLimitGeneral().getCMSLimitId() > 0) {
				oldCMSLimitId = limit.getLimitGeneral().getCMSLimitId();
			}
			else {
				continue;
			}

			Long cmsLimitId = getLimitJdbc().getLmtIdByLmtProfileIdAndOldLmtId(limitProfileId, oldCMSLimitId);

			if ((cmsLimitId != null) && (cmsLimitId.longValue() > 0)) {
				limit.getLimitGeneral().setCmsId(cmsLimitId.longValue());
			}
			else {
				throw new NoSuchLimitException(limit.getLimitGeneral().getLOSAANumber(), limitProfileId, oldCMSLimitId);
			}

		}
	}

	private void validateCMSSecurityLimitChargeId(Vector limitCharges, long limitProfileId) {
		for (Iterator iterator = limitCharges.iterator(); iterator.hasNext();) {
			ChargeDetail chargeDetail = (ChargeDetail) iterator.next();

			// check cms charge detail key
			if (chargeDetail.getChargeDetailId() > 0) {
				Long cmsChargeDetailId = getChargeDetailJdbc().getCmsChargeDetailIdByOldCmsChargeDetailId(
						chargeDetail.getChargeDetailId());

				if ((cmsChargeDetailId == null) || (cmsChargeDetailId.longValue() <= 0)) {
					throw new NoSuchChargeDetailException(limitProfileId, chargeDetail.getChargeDetailId());
				}
			}

			// check cms security key
			if (chargeDetail.getCmsSecurityId() > 0) {
				Long cmsSecurityId = getSecurityJdbcDao().getCmsSecurityIdByOldCmsSecurityId(
						chargeDetail.getCmsSecurityId());

				if ((cmsSecurityId == null) || (cmsSecurityId.longValue() <= 0)) {
					throw new NoSuchSecurityException(chargeDetail.getCmsSecurityId());
				}
			}

			// check cms limit keys
			if ((chargeDetail.getCMSLimitId() != null) && (chargeDetail.getCMSLimitId().length > 0)) {
				for (int i = 0; i < chargeDetail.getCMSLimitId().length; i++) {
					Long oldCmsLimitId = chargeDetail.getCMSLimitId()[i];

					Long cmsLimitId = getLimitJdbc().getLmtIdByLmtProfileIdAndOldLmtId(limitProfileId,
							oldCmsLimitId.longValue());
					if ((cmsLimitId == null) || (cmsLimitId.longValue() <= 0)) {
						throw new NoSuchLimitException(limitProfileId, oldCmsLimitId.longValue());
					}
				}
			}
			if ((chargeDetail.getLimitId() != null) && (chargeDetail.getCMSLimitId() != null)
					&& (chargeDetail.getCMSLimitId().length > 0) && (chargeDetail.getLimitId().length > 0)) {
				crossCheckCMSLimitIdAndLOSLimitId(chargeDetail.getCMSLimitId(), chargeDetail.getLimitId());
			}

		}
	}

	private void validateCMSLimitSecurityMapId(Vector limitSecurityMaps, long limitProfileId) {
		for (Iterator iterator = limitSecurityMaps.iterator(); iterator.hasNext();) {
			LimitsApprovedSecurityMap limitSecurityMap = (LimitsApprovedSecurityMap) iterator.next();

			// check CMS Limit Security Map key
			if (limitSecurityMap.getLimitsApprovedSecurityMapId() > 0) {
				Long cmsLmtSecMapid = getLimitJdbc().getLmtSecMapIdByLmtProfileIdAndOldLmtSecMapId(limitProfileId,
						limitSecurityMap.getLimitsApprovedSecurityMapId());

				if ((cmsLmtSecMapid != null) && (cmsLmtSecMapid.longValue() > 0)) {
					limitSecurityMap.setCmsId(cmsLmtSecMapid.longValue());
				}
				else {
					throw new NoSuchLimitSecMapException(limitSecurityMap.getAANumber(), limitSecurityMap
							.getLimitsApprovedSecurityMapId());
				}
			}

			// check CMS Security key
			if (limitSecurityMap.getCmsSecurityId() > 0) {
				Long cmsSecurityId = getSecurityJdbcDao().getCmsSecurityIdByOldCmsSecurityId(
						limitSecurityMap.getCmsSecurityId());
				if ((cmsSecurityId == null) || (cmsSecurityId.longValue() <= 0)) {
					throw new NoSuchSecurityException(limitSecurityMap.getCmsSecurityId());
				}
			}

			// check CMS Limit key
			if (limitSecurityMap.getCmsLimitId() > 0) {
				Long cmsLimitId = getLimitJdbc().getLmtIdByLmtProfileIdAndOldLmtId(limitProfileId,
						limitSecurityMap.getCmsLimitId());
				if ((cmsLimitId == null) || (cmsLimitId.longValue() <= 0)) {
					throw new NoSuchLimitException(limitSecurityMap.getAANumber(), limitProfileId, limitSecurityMap
							.getCmsLimitId());
				}
			}

		}
	}

	private void crossCheckCMSLimitIdAndLOSLimitId(Long[] CMSLimitId, String[] LOSLimitId) {
		for (int i = 0; i < LOSLimitId.length; i++) {
			for (int j = 0; j < CMSLimitId.length; j++) {
				long cmsLimitId = (CMSLimitId[j] != null ? CMSLimitId[j].longValue() : 0l);
				if (LOSLimitId[i].equals(getLimitJdbc().getLOSLimitIdByCMSLimitId(cmsLimitId))) {
					throw new LosAndCmsLimitIdProvidedException(LOSLimitId[i], CMSLimitId[j]);
				}
			}
		}
	}

	private void updateRenewFlag(long limitProfileId) {
		getLimitJdbc().updateRenewInd(limitProfileId, ICMSConstant.YES);
	}

	public Message persistActualTrx(Message msg) {
		return checkValidCMSId(msg);
	}

	/**
	 * Exception class to indicate that, for charge detail, los limit id and cms
	 * limit id provided is actually the same one.
	 */
	private class LosAndCmsLimitIdProvidedException extends EaiLimitProfileMessageException {

		private static final long serialVersionUID = 3026778305822190155L;

		/**
		 * Constructor to provide LOS Limit Id and CMS Limit Id, to indicate
		 * both are the same one.
		 * @param losLimitId LOS Limit Id
		 * @param cmsLimitId CMS Limit Id
		 */
		public LosAndCmsLimitIdProvidedException(String losLimitId, Long cmsLimitId) {
			super("Either LOSLimitId or CMSLimitId should be provided for the same Limit. LOSLimtiId [" + losLimitId
					+ "] CMSLimit Id [" + cmsLimitId + "] is actually the same Limit.");
			setErrorCode("LOS_CMS_LIMIT");
		}
	}
}
