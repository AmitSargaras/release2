package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.LimitAlreadyExistsException;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.StagingLimits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Handler to process <tt>LimitGeneral</tt>, existing staging info will using
 * the transaction value object to process.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 * 
 */
public class LimitActualTrxHandler extends AbstractCommonActualTrxHandler {

	protected static String[] fixpk = new String[] { IEaiConstant.CMSID };

	private ILimitDao limitDao;

	private Map variationProperties;

	private LimitHelper helper;

	private String[] facilityStatusForLimitDeletion;

	public static final String MAIN_FACILITY_IND_Y = "Y";

	public static final String MAIN_FACILITY_IND_N = "N";

	public static final String LIMIT_TYPE_NO = "25";

	public static final String LIMIT_OUTER = "OUTER";

	public static final String LIMIT_INNER = "INNER";

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

	/**
	 * @return the helper
	 */
	public LimitHelper getHelper() {
		return helper;
	}

	/**
	 * @param helper the helper to set
	 */
	public void setHelper(LimitHelper helper) {
		this.helper = helper;
	}

	public void setFacilityStatusForLimitDeletion(String[] facilityStatusForLimitDeletion) {
		this.facilityStatusForLimitDeletion = facilityStatusForLimitDeletion;
	}

	public Message persistActualTrx(Message msg) {

		AAMessageBody aaMessage = ((AAMessageBody) msg.getMsgBody());

		LimitProfile limitsProfile = aaMessage.getLimitProfile();

		boolean variation = false;
		if ((limitsProfile.getCMSLimitProfileId() != null) && (limitsProfile.getCMSLimitProfileId().longValue() > 0)) {
			variation = true;
		}

		String source = msg.getMsgHeader().getSource();

		Vector limits = aaMessage.getLimits();

		if ((limits == null) || (limits.isEmpty())) {
			return msg;
		}

		Vector newLimits = new Vector();

		int limitSize = limits.size();

		for (int i = 0; i < limitSize; i++) {

			Limits limit = (Limits) limits.elementAt(i);

			long limitProfileId = limitsProfile.getLimitProfileId();

			LimitGeneral limitGeneral = limit.getLimitGeneral();

			limitGeneral.setSourceId(source);
			limitGeneral.setHostAANumber(limitsProfile.getHostAANumber());

			// setting account type to the limit table for product description
			// purpose
			String accountType = getLimitDao().getAccountTypeByLimitProductTypeAndFacilityType(
					limitGeneral.getProductType().getStandardCodeValue(),
					limitGeneral.getFacilityType().getStandardCodeValue());
			limitGeneral.setAccountType(accountType);

			limit = storeLimits(limitProfileId, variation, source, limit, Limits.class);

			// preparation for the cms outer limit id population
			if (StringUtils.equals(limit.getLimitGeneral().getLOSOuterLimitId(), limit.getLimitGeneral()
					.getLOSLimitId())) {
				limit.getLimitGeneral().setLOSOuterLimitId(null);
			}

			newLimits.add(limit);
		}
		aaMessage.setLimits(newLimits);

		populateOuterLimitId(newLimits);

		return msg;
	}

	/**
	 * Limits and LimitSysXRef needs to persist staging data.
	 */
	public Message persistStagingTrx(Message msg, Object trxValue) {

		AAMessageBody aaMessageBody = (AAMessageBody) msg.getMsgBody();
		String source = msg.getMsgHeader().getSource();

		LimitProfile limitProfile = aaMessageBody.getLimitProfile();

		boolean variation = false;
		if ((limitProfile.getCMSLimitProfileId() != null) && (limitProfile.getCMSLimitProfileId().longValue() > 0)) {
			variation = true;
		}

		Vector veclimit = aaMessageBody.getLimits();

		if (veclimit == null) {
			veclimit = new Vector();
		}

		HashMap trxValMap = new HashMap();
		if (trxValue != null) {
			trxValMap = (HashMap) trxValue;
		}

		for (int i = 0; i < veclimit.size(); i++) {
			StagingLimits stagingLimit = new StagingLimits();

			AccessorUtil.copyValue((Limits) veclimit.elementAt(i), stagingLimit);

			stagingLimit.getLimitGeneral().setHostAANumber(limitProfile.getHostAANumber());

			ILimitTrxValue lmtTrxVal = getLimitTrxValue(stagingLimit, trxValMap);

			if ((lmtTrxVal != null) && (lmtTrxVal.getStagingLimit() != null)) {
				stagingLimit.getLimitGeneral().setCmsId(lmtTrxVal.getStagingLimit().getLimitID());

			}

			long limitProfileId = limitProfile.getLimitProfileId();
			stagingLimit = (StagingLimits) storeLimits(limitProfileId, variation, source, stagingLimit,
					StagingLimits.class);

			veclimit.setElementAt(stagingLimit, i);
		}
		return msg;
	}

	protected Limits storeLimits(long limitProfileId, boolean variation, String source, Limits limit, Class profileClass) {

		if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {

			if (limit instanceof StagingLimits) {

				LimitGeneral stagLimitGeneral = limit.getLimitGeneral();

				stagLimitGeneral.setCmsLimitProfileId(limitProfileId);
				stagLimitGeneral.setStatus(IEaiConstant.ACTIVE_STATUS);

				StandardCode limitType = new StandardCode();

				limitType.setStandardCodeNumber(LIMIT_TYPE_NO);

				// if aatype is CC there is no facility master
				if (limit.getFacilityMaster() != null) {
					if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_Y) {
						limitType.setStandardCodeValue(LIMIT_OUTER);
					}
					else if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_N) {
						limitType.setStandardCodeValue(LIMIT_INNER);
					}
					stagLimitGeneral.setInterestRate(limit.getFacilityMaster().getInterestRate());

					if (ArrayUtils.contains(this.facilityStatusForLimitDeletion, limit.getFacilityMaster()
							.getFacilityStatusEntryCode())) {
						stagLimitGeneral.setStatus(ICMSConstant.STATE_DELETED);
					}
				}

				limit.getLimitGeneral().setLimitType(limitType);

				Long actualLmtGenId = (Long) getLimitDao().store(stagLimitGeneral, LimitGeneral.class,
						IEaiConstant.STAGE_LIMIT_GENERAL);

				stagLimitGeneral.setCmsId(actualLmtGenId.longValue());

			}
			else {
				/* checking if the same Limit id from the source has been used */
				Map parameters = new HashMap();

				// if the variation case use the cmsid provided
				if (limit.getLimitGeneral().getCmsId() > 0) {
					parameters.put("cmsId", new Long(limit.getLimitGeneral().getCmsId()));
				}
				else {
					parameters.put("LOSAANumber", limit.getLimitGeneral().getLOSAANumber());
					parameters.put("LOSLimitId", limit.getLimitGeneral().getLOSLimitId());
				}
				LimitGeneral limitGeneral = (LimitGeneral) getLimitDao().retrieveObjectByParameters(parameters,
						LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

				if ((limitGeneral != null) && (profileClass == Limits.class)) {
					if (limit.getLimitGeneral().getCmsId() > 0) {
						throw new LimitAlreadyExistsException(limitProfileId, limit.getLimitGeneral().getCmsId());
					}
					else {
						throw new LimitAlreadyExistsException(limit.getLimitGeneral().getCIFId(), limit
								.getLimitGeneral().getLOSAANumber(), limit.getLimitGeneral().getLOSLimitId());
					}
				}
				else {
					LimitGeneral actualLimitGeneral = limit.getLimitGeneral();

					actualLimitGeneral.setCmsLimitProfileId(limitProfileId);
					actualLimitGeneral.setStatus(IEaiConstant.ACTIVE_STATUS);
					actualLimitGeneral.setLimitId(actualLimitGeneral.getLOSLimitId());

					StandardCode limitType = new StandardCode();

					limitType.setStandardCodeNumber(LIMIT_TYPE_NO);

					if (limit.getFacilityMaster() != null) {
						if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_Y) {
							limitType.setStandardCodeValue(LIMIT_OUTER);
						}
						else if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_N) {
							limitType.setStandardCodeValue(LIMIT_INNER);
						}

						limit.getLimitGeneral().setInterestRate(limit.getFacilityMaster().getInterestRate());
						if (ArrayUtils.contains(this.facilityStatusForLimitDeletion, limit.getFacilityMaster()
								.getFacilityStatusEntryCode())) {
							limit.getLimitGeneral().setStatus(ICMSConstant.STATE_DELETED);
						}
					}

					limit.getLimitGeneral().setLimitType(limitType);

					Long actualLmtGenId = (Long) getLimitDao().store(actualLimitGeneral, LimitGeneral.class,
							IEaiConstant.ACTUAL_LIMIT_GENERAL);

					actualLimitGeneral.setCmsId(actualLmtGenId.longValue());
				}
			}
		}
		else if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
				&& ((limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (limit
						.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {

			Map parameters = new HashMap();

			if (limit instanceof StagingLimits) {

				// get the actual limit recently persisted
				parameters.put("LOSLimitId", limit.getLimitGeneral().getLOSLimitId());
				parameters.put("LOSAANumber", limit.getLimitGeneral().getLOSAANumber());

				LimitGeneral actualLimitGeneral = (LimitGeneral) getLimitDao().retrieveObjectByParameters(parameters,
						LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

				// get the stage limit
				parameters.clear();
				parameters.put("referenceID", new Long(actualLimitGeneral.getCmsId()));
				parameters.put("transactionType", ICMSConstant.INSTANCE_LIMIT);

				CMSTransaction limitWorkflow = (CMSTransaction) getLimitDao().retrieveObjectByParameters(parameters,
						CMSTransaction.class);

				long stageCmsLimitId = limitWorkflow.getStageReferenceID();

				LimitGeneral stageLimitGeneral = (LimitGeneral) getLimitDao().retrieve(new Long(stageCmsLimitId),
						IEaiConstant.STAGE_LIMIT_GENERAL);

				if (variation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, LimitGeneral.class.getName()));
					getHelper().copyVariationProperties(limit.getLimitGeneral(), stageLimitGeneral, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(limit.getLimitGeneral(), stageLimitGeneral, new String[] { "CmsId",
							"Status", "CmsLimitProfileId", "LimitId" });
				}

				if (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))) {
					stageLimitGeneral.setStatus(ICMSConstant.STATE_DELETED);
				}

				StandardCode limitType = new StandardCode();
				limitType.setStandardCodeNumber(LIMIT_TYPE_NO);

				if (limit.getFacilityMaster() != null) {
					stageLimitGeneral.setInterestRate(limit.getFacilityMaster().getInterestRate());
					if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_Y) {
						limitType.setStandardCodeValue(LIMIT_OUTER);
					}
					else if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_N) {
						limitType.setStandardCodeValue(LIMIT_INNER);
					}

					if (ArrayUtils.contains(this.facilityStatusForLimitDeletion, limit.getFacilityMaster()
							.getFacilityStatusEntryCode())) {
						stageLimitGeneral.setStatus(ICMSConstant.STATE_DELETED);
					}
				}
				limit.getLimitGeneral().setLimitType(limitType);

				getLimitDao().update(stageLimitGeneral, LimitGeneral.class, IEaiConstant.STAGE_LIMIT_GENERAL);

				limit.setLimitGeneral(stageLimitGeneral);

			}
			else {
				parameters.clear();

				// for the variation cases used the cmslimitid to retrieve the
				// existing record
				if (limit.getLimitGeneral().getCMSLimitId() > 0) {
					parameters.put("cmsId", new Long(limit.getLimitGeneral().getCMSLimitId()));
					// variation = true;
				}
				else {
					parameters.put("LOSLimitId", limit.getLimitGeneral().getLOSLimitId());
					parameters.put("LOSAANumber", limit.getLimitGeneral().getLOSAANumber());
				}

				LimitGeneral actualLimitGeneral = (LimitGeneral) getLimitDao().retrieveNonDeletedObjectByParameters(
						parameters, "updateStatusIndicator", LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

				if (variation) {
					List copyingProperties = (List) getVariationProperties().get(
							new VariationPropertiesKey(source, LimitGeneral.class.getName()));
					getHelper().copyVariationProperties(limit.getLimitGeneral(), actualLimitGeneral, copyingProperties);
				}
				else {
					AccessorUtil.copyValue(limit.getLimitGeneral(), actualLimitGeneral, new String[] { "CmsId",
							"Status", "CmsLimitProfileId", "LimitId" });
				}

				if (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))) {
					actualLimitGeneral.setStatus(ICMSConstant.STATE_DELETED);
				}

				StandardCode limitType = new StandardCode();

				limitType.setStandardCodeNumber(LIMIT_TYPE_NO);

				if (limit.getFacilityMaster() != null) {
					actualLimitGeneral.setInterestRate(limit.getFacilityMaster().getInterestRate());

					if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_Y) {
						limitType.setStandardCodeValue(LIMIT_OUTER);
					}
					else if (limit.getFacilityMaster().getMainFacilityInd() == MAIN_FACILITY_IND_N) {
						limitType.setStandardCodeValue(LIMIT_INNER);
					}

					if (ArrayUtils.contains(this.facilityStatusForLimitDeletion, limit.getFacilityMaster()
							.getFacilityStatusEntryCode())) {
						actualLimitGeneral.setStatus(ICMSConstant.STATE_DELETED);
					}
				}
				limit.getLimitGeneral().setLimitType(limitType);

				getLimitDao().update(actualLimitGeneral, LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

				limit.setLimitGeneral(actualLimitGeneral);
			}
		}
		else {
			Limits tmpLmt = new Limits();
			LimitGeneral tmpLmtGeneral = null;
			// FacilityMaster tmpFacMaster = null;

			if (limit instanceof StagingLimits) {
				Map parameters = new HashMap();
				parameters.put("transactionType", ICMSConstant.INSTANCE_LIMIT);
				parameters.put("referenceID", new Long(limit.getLimitGeneral().getCmsId()));
				// get the latest trx to get limit staging reference id

				CMSTransaction trx = (CMSTransaction) getLimitDao().retrieveObjectByParameters(parameters,
						CMSTransaction.class);

				if (trx != null) {
					logger.debug("Transaction is not null ");
					tmpLmtGeneral = (LimitGeneral) getLimitDao().retrieve(new Long(trx.getStageReferenceID()),
							IEaiConstant.STAGE_LIMIT_GENERAL);

					tmpLmt.setLimitGeneral(tmpLmtGeneral);
				}
			}
			else {
				Map parameters = new HashMap();
				parameters.put("cmsLimitProfileId", new Long(limitProfileId));
				parameters.put("LOSLimitId", limit.getLimitGeneral().getLOSLimitId());

				tmpLmtGeneral = (LimitGeneral) getLimitDao().retrieveNonDeletedObjectByParameters(parameters,
						"updateStatusIndicator", LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

				if (tmpLmtGeneral == null && limit.getLimitGeneral().getCMSLimitId() > 0) {
					tmpLmtGeneral = (LimitGeneral) getLimitDao().retrieve(
							new Long(limit.getLimitGeneral().getCMSLimitId()), IEaiConstant.ACTUAL_LIMIT_GENERAL);
				}

				if (tmpLmtGeneral == null) {
					throw new NoSuchLimitException(limit.getLimitGeneral().getCIFId(), limit.getLimitGeneral()
							.getLOSLimitId());
				}

				tmpLmt.setLimitGeneral(tmpLmtGeneral);
			}

			limit.getLimitGeneral().setCmsId(tmpLmt.getLimitGeneral().getCmsId());
		}

		return limit;
	}

	/**
	 * To populate CMS key of the outer limit into inner limit's outer limit cms
	 * key.
	 * 
	 * @param limits list of limit which already persisted.
	 */
	protected void populateOuterLimitId(Vector limits) {
		LimitActualHelper limitHelper = new LimitActualHelper();

		if ((limits == null) || (limits.size() == 0)) {
			return;
		}

		for (int i = 0; i < limits.size(); i++) {
			Limits limit = (Limits) limits.get(i);
			LimitGeneral limitGeneral = limit.getLimitGeneral();

			if (StringUtils.isNotBlank(limitGeneral.getLOSOuterLimitId())) {
				long cmsOuterLimitId = 0L;
				try {
					cmsOuterLimitId = limitHelper.getInternalLimitId(limits, limitGeneral.getLOSOuterLimitId());
				}
				catch (NoSuchLimitException e) {
					logger.warn("failed to find limit from the message itself, for limit id ["
							+ limitGeneral.getLOSOuterLimitId() + "] which is outer of limit, id ["
							+ limitGeneral.getLOSLimitId() + "], searching through the persistent storage");

					Map parameters = new HashMap();
					parameters.put("LOSLimitId", limitGeneral.getLOSOuterLimitId());

					LimitGeneral outerlimitGeneral = (LimitGeneral) getLimitDao().retrieveNonDeletedObjectByParameters(
							parameters, "updateStatusIndicator", LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);

					if (outerlimitGeneral == null) {
						throw new NoSuchLimitException(limitGeneral.getCIFId(), limitGeneral.getSubProfileId(),
								limitGeneral.getCmsLimitProfileId(), limitGeneral.getLOSOuterLimitId());
					}

					cmsOuterLimitId = outerlimitGeneral.getCmsId();
				}

				limitGeneral.setCMSOuterLimitId(new Long(cmsOuterLimitId));
				getLimitDao().update(limitGeneral, LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);
			}
		}
	}

	/**
	 * Get limit transaction value in a list given the limit.
	 * 
	 * @param lmts of type Limits
	 * @param col a list of ILimitTrxValue objects
	 * @return ILimitTrxValue
	 */
	private ILimitTrxValue getLimitTrxValue(Limits lmts, HashMap col) {
		String key = constructLimitTrxValueKey(lmts.getLimitGeneral().getCIFId(), lmts.getLimitGeneral()
				.getSubProfileId(), lmts.getLimitGeneral().getCmsLimitProfileId(), lmts.getLimitGeneral()
				.getLOSLimitId());
		Iterator i = col.keySet().iterator();
		while (i.hasNext()) {
			String tmpkey = (String) i.next();
			if (key.equals(tmpkey)) {
				return (ILimitTrxValue) col.get(tmpkey);
			}
		}
		return null;
	}

	/**
	 * to identify a Limit in a hashmap , the key is a combination of leid ,
	 * subprofileid, LimitprofileId and the limitid. This method will return the
	 * key .
	 */
	private String constructLimitTrxValueKey(String leid, long subprofileid, long limitprofileid, String limitid) {
		return IEaiConstant.LIMIT_KEY + IEaiConstant.DELIMITER + leid + IEaiConstant.DELIMITER + subprofileid
				+ IEaiConstant.DELIMITER + limitprofileid + IEaiConstant.DELIMITER + limitid;
	}

	public String getTrxKey() {
		return IEaiConstant.LIMIT_KEY;
	}

}
