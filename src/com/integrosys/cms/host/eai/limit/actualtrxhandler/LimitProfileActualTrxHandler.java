package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.customer.bus.SubProfile;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.CifMismatchException;
import com.integrosys.cms.host.eai.limit.LimitProfileAlreadyExistsException;
import com.integrosys.cms.host.eai.limit.NoSuchCustomerForLimitProfileException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.StagingLimitProfile;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Handler to process LimitProfile
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class LimitProfileActualTrxHandler extends AbstractCommonActualTrxHandler {

	protected static String[] LIMIT_PROFILE_PK = new String[] { IEaiConstant.LIMITPROFILEID };

	private ILimitDao limitDao;

	private Map variationProperties;

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
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

	public Message persistActualTrx(Message msg) {

		AAMessageBody aaMessage = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessage.getLimitProfile();

		String source = msg.getMsgHeader().getSource();
		boolean variation = false;

		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			variation = true;
		}

		limitProfile.setAASource(source);

		limitProfile = storeLimitProfile(limitProfile, variation, source);

		aaMessage.setLimitProfile(limitProfile);

		return msg;
	}

	/**
	 * LimitProfile has a Staging table. Look at storeStagingLimitProfile method
	 * for storing of staging LimitProfile.
	 */
	public Message persistStagingTrx(Message msg, Object trxValues) {
		AAMessageBody aaMessage = (AAMessageBody) msg.getMsgBody();
		LimitProfile limitProfile = aaMessage.getLimitProfile();

		String source = msg.getMsgHeader().getSource();
		boolean variation = limitProfile.getCMSLimitProfileId() != null
				&& limitProfile.getCMSLimitProfileId().longValue() > 0;

		StagingLimitProfile stagingLimitProfile = new StagingLimitProfile();

		AccessorUtil.copyValue(limitProfile, stagingLimitProfile);

		Map trxValMap = new HashMap();
		if (trxValues != null) {
			trxValMap = (HashMap) trxValues;
		}

		ILimitProfileTrxValue trxVal = getLimitProfileTrxValue(stagingLimitProfile, trxValMap);

		if ((trxVal != null) && (trxVal.getStagingLimitProfile() != null)) {
			stagingLimitProfile.setLimitProfileId(trxVal.getStagingLimitProfile().getLimitProfileID());
		}

		stagingLimitProfile = (StagingLimitProfile) storeStagingLimitProfile(stagingLimitProfile, stagingLimitProfile
				.getClass(), variation, source);

		aaMessage.setLimitProfile(stagingLimitProfile);
		msg.setMsgBody(aaMessage);

		return msg;
	}

	/**
	 * the following will store the LimitProfile base on the ChangeIndicator and
	 * UPdateIndicator. condition 1 : if changeIndicator is 'Y' and
	 * updateIndicator is 'C'. The system will do create . condition 2 : if
	 * changeIndicator is 'Y' and updateIndicator is 'U' or 'D' . The System
	 * will do update condition 3 : If changeIndicator is 'N' . Just load the
	 * CMSID(table's primary key). Main Profile cms details needs to be loaded
	 * because other component rely on it.
	 * 
	 */
	protected LimitProfile storeLimitProfile(LimitProfile limitProfile, boolean variation, String source) {

		LimitHelper helper = new LimitHelper();

		String cifId = limitProfile.getCIFId();

		prepareBorrowerRecords(limitProfile);

		MainProfile mp = limitProfile.getMainProfile();
		limitProfile.setCmsSubProfileId(mp.getSubProfilePrimaryKey());

		updateSubProfileBorrowerIndicator(cifId, limitProfile);

		Map parameters = new HashMap();
		if (limitProfile.getLimitProfileId() > 0) {
			parameters.put("limitProfileId", new Long(limitProfile.getLimitProfileId()));
		}
		else {
			if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
				parameters.put("limitProfileId", limitProfile.getCMSLimitProfileId());
			}
			else {
				parameters.put("LOSAANumber", limitProfile.getLOSAANumber());
			}

			parameters.put("AASource", limitProfile.getAASource());
		}
		LimitProfile storedLimitProfile = (LimitProfile) getLimitDao().retrieveNonDeletedObjectByParameters(parameters,
				"updateStatusIndicator", LimitProfile.class);

		if ((limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {

			if (storedLimitProfile != null) {
				throw new LimitProfileAlreadyExistsException(cifId, limitProfile.getHostAANumber(), limitProfile
						.getLOSAANumber());
			}
			else {
				Long cmslimitProfileId = (Long) getLimitDao().store(limitProfile, LimitProfile.class);
				limitProfile.setLimitProfileId(cmslimitProfileId.longValue());

			}
		}
		else if ((limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& ((limitProfile.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (limitProfile
						.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {

			if (storedLimitProfile == null) {
				throw new NoSuchLimitProfileException(cifId, limitProfile.getHostAANumber(), limitProfile
						.getLOSAANumber());
			}

			if (!storedLimitProfile.getCIFId().equals(limitProfile.getCIFId())) {
				throw new CifMismatchException(storedLimitProfile.getLOSAANumber(), storedLimitProfile.getAASource(),
						limitProfile.getCIFId(), storedLimitProfile.getCIFId());
			}

			if (variation) {
				List copyingProperties = (List) getVariationProperties().get(
						new VariationPropertiesKey(source, LimitProfile.class.getName()));
				helper.copyVariationProperties(limitProfile, storedLimitProfile, copyingProperties);
			}
			else {
				AccessorUtil.copyValue(limitProfile, storedLimitProfile, LIMIT_PROFILE_PK);
			}

			getLimitDao().update(storedLimitProfile, LimitProfile.class);

			limitProfile.setLimitProfileId(storedLimitProfile.getLimitProfileId());
		}
		else {
			logger.info("no persistence required for AA [" + limitProfile.getLOSAANumber()
					+ "], but need to retrieve the key");

			if (storedLimitProfile != null) {
				logger.debug("Limit Profile found ! Setting limit profile id.");
				limitProfile.setLimitProfileId(storedLimitProfile.getLimitProfileId());
			}
			else {
				throw new NoSuchLimitProfileException(cifId, limitProfile.getHostAANumber(), limitProfile
						.getLOSAANumber());
			}
		}

		return limitProfile;
	}

	/**
	 * Helper method to get ILimitProfileTrxValue given the limit profile and a
	 * list of trx values.
	 * 
	 * @param lp of type LimitProfile
	 * @param keyTrxValueMap a HashMap of ILimitProfileTrxValue with key leid,
	 *        subprofileid, and limit profile id
	 * @return ILimitProfileTrxValue
	 */
	private ILimitProfileTrxValue getLimitProfileTrxValue(LimitProfile lp, Map keyTrxValueMap) {
		String key = constructLimitProfileKey(lp.getCIFId(), lp.getSubProfileId(), lp.getLimitProfileId());

		return (ILimitProfileTrxValue) keyTrxValueMap.get(key);
	}

	protected String constructLimitProfileKey(String leid, long subprofileid, long limitprofileid) {
		return IEaiConstant.LIMITPROFILE_KEY + IEaiConstant.DELIMITER + leid + IEaiConstant.DELIMITER + subprofileid
				+ IEaiConstant.DELIMITER + limitprofileid;
	}

	protected LimitProfile storeStagingLimitProfile(LimitProfile limitProfile, Class profileClass, boolean variation,
			String source) {
		LimitHelper helper = new LimitHelper();

		// boolean variation = (limitProfile.getOldLOSAANumber() != null);

		if ((limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {

			Long stageKey = (Long) getLimitDao().store(limitProfile, profileClass);
			limitProfile.setLimitProfileId(stageKey.longValue());
		}
		else if ((limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& ((limitProfile.getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (limitProfile
						.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {

			LimitProfile tmplp = (LimitProfile) getLimitDao().retrieve(new Long(limitProfile.getLimitProfileId()),
					profileClass);

			if (variation) {
				List copyingProperties = (List) getVariationProperties().get(
						new VariationPropertiesKey(source, LimitProfile.class.getName()));
				helper.copyVariationProperties(limitProfile, tmplp, copyingProperties);
			}
			else {
				AccessorUtil.copyValue(limitProfile, tmplp, LIMIT_PROFILE_PK);
			}
			getLimitDao().update(tmplp, profileClass);

			limitProfile.setLimitProfileId(tmplp.getLimitProfileId());
		}

		logger.debug("***Finished Storing Stage Limit Profile ***");
		return limitProfile;
	}

	public String getTrxKey() {
		return IEaiConstant.LIMITPROFILE_KEY;
	}

	/**
	 * To sync AA with the customer for nonBorrowerIndicator. If AA is created,
	 * customer is now a borrower, thus indicator is N. If AA is removed,
	 * customer is not a borrower anymore (if no any AA for that CIF), thus
	 * indicator is Y.
	 * 
	 * @param cdb
	 * @param cifId
	 * @param changeIndicator
	 * @param updateStatusIndicator
	 * @throws Exception
	 */
	protected void updateSubProfileBorrowerIndicator(String cifId, LimitProfile limitProfile) {
		if (limitProfile.getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR))) {

			MainProfile mp = limitProfile.getMainProfile();

			// If MainProfile Not Available , return .
			if (mp == null) {
				return;
			}

			SubProfile subProfile = (SubProfile) getLimitDao().retrieve(new Long(mp.getSubProfilePrimaryKey()),
					SubProfile.class);

			if (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR))) {
				subProfile.setNonBorrowerIndicator(ICMSConstant.FALSE_VALUE.charAt(0));
			}
			else if (limitProfile.getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))) {

				Map parameters = new WeakHashMap();
				parameters.put("LOSAANumber", limitProfile.getLOSAANumber());
				parameters.put("AASource", limitProfile.getAASource());

				Object object = getLimitDao().retrieveNonDeletedObjectByParameters(parameters, "updateStatusIndicator",
						LimitProfile.class);

				if (object != null) {
					subProfile.setNonBorrowerIndicator(ICMSConstant.FALSE_VALUE.charAt(0));
					logger.debug("Customer CIF id [" + subProfile.getCifId() + "] is still tied to at least one AA.");
				}
				else {
					subProfile.setNonBorrowerIndicator(ICMSConstant.TRUE_VALUE.charAt(0));
					logger.debug("Customer, CIF id [" + subProfile.getCifId() + "] is no longer tied to any AA.");
				}
			}

			getLimitDao().update(subProfile, SubProfile.class);
		}

	}

	protected void prepareBorrowerRecords(LimitProfile limitProfile) {
		Map parameters = new HashMap();
		parameters.put("CIFId", limitProfile.getCIFId());
		parameters.put("source", limitProfile.getCIFSource());
		MainProfile mp = (MainProfile) getLimitDao().retrieveObjectByParameters(parameters, MainProfile.class);
		if (mp == null) {
			throw new NoSuchCustomerForLimitProfileException(limitProfile.getCIFId(), limitProfile.getCIFSource(),
					limitProfile.getHostAANumber(), limitProfile.getLOSAANumber());
		}

		limitProfile.setMainProfile(mp);

		parameters.clear();
		parameters.put("cifId", mp.getCIFId());
		parameters.put("cmsMainProfileId", new Long(mp.getCmsId()));
		SubProfile sbp = (SubProfile) getLimitDao().retrieveObjectByParameters(parameters, SubProfile.class);
		if (sbp == null) {
			throw new NoSuchCustomerForLimitProfileException(limitProfile.getCIFId(), limitProfile.getCIFSource(),
					limitProfile.getHostAANumber(), limitProfile.getLOSAANumber());
		}

		limitProfile.getMainProfile().setSubProfilePrimaryKey(sbp.getCmsId());
	}

}
