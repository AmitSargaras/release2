package com.integrosys.cms.host.eai.limit.inquiry.limitprofile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.AbstractInquiryResponseMessageHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchLimitProfileException;
import com.integrosys.cms.host.eai.limit.bus.ChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.FacilityIncremental;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.FacilityMessage;
import com.integrosys.cms.host.eai.limit.bus.FacilityMultiTierFinancing;
import com.integrosys.cms.host.eai.limit.bus.FacilityOfficer;
import com.integrosys.cms.host.eai.limit.bus.FacilityReduction;
import com.integrosys.cms.host.eai.limit.bus.FacilityRelationship;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.JointBorrower;
import com.integrosys.cms.host.eai.limit.bus.LimitChargeMap;
import com.integrosys.cms.host.eai.limit.bus.LimitCreditGrade;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.limit.bus.LimitsSysXRefMap;
import com.integrosys.cms.host.eai.limit.bus.SystemXReference;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Inquiry message handler for AA/Credit Application, based on the LOS AA Number
 * will search through CMS system and return the result.
 * 
 * @author Chong Jun Yong
 * @author Bao Hong Man
 * 
 */
public class CreditApplicationInquiryMessageHandler extends AbstractInquiryResponseMessageHandler {

	private static final String RESPONSE_MESSAGE_ID = "CA003R";

	private ILimitDao limitDao;

	private com.integrosys.cms.app.limit.bus.ILimitDAO limitJdbcDao;

	private String[] limitsApprovedSecurityMapApplicableSourceIds;

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public com.integrosys.cms.app.limit.bus.ILimitDAO getLimitJdbcDao() {
		return limitJdbcDao;
	}

	public void setLimitJdbcDao(com.integrosys.cms.app.limit.bus.ILimitDAO limitJdbcDao) {
		this.limitJdbcDao = limitJdbcDao;
	}

	/**
	 * <p>
	 * The sources ids of limit security linkages that required sent back to the
	 * client.
	 * <p>
	 * Default source ids would be <i>CMS</i>, <i>SIBS</i> (host), and also the
	 * source that sent in the inquiry. Normally the LOS system.
	 * @param limitsApprovedSecurityMapApplicableSourceIds the source ids of
	 *        limit security linkages
	 */
	public void setLimitsApprovedSecurityMapApplicableSourceIds(String[] limitsApprovedSecurityMapApplicableSourceIds) {
		this.limitsApprovedSecurityMapApplicableSourceIds = limitsApprovedSecurityMapApplicableSourceIds;
	}

	protected EAIMessage doProcessInquiryMessage(EAIMessage eaiMessage) {
		CreditApplicationInquiryMessageBody msgBody = (CreditApplicationInquiryMessageBody) eaiMessage.getMsgBody();
		CreditApplicationCriteria criteria = msgBody.getCreditApplicationCriteria();

		AAMessageBody responseMsgBody = new AAMessageBody();

		// limit profile
		LimitProfile limitProfile = getLimitDao().searchLimitProfileByLosAANumberAndSource(criteria.getLosAANumber(),
				criteria.getApplicationSource());

		if (limitProfile == null) {
			throw new NoSuchLimitProfileException(criteria.getLosAANumber(), criteria.getApplicationSource());
		}

		responseMsgBody.setLimitProfile(limitProfile);

		// limits
		Collection limitList = retrieveLimits(limitProfile);
		if (limitList != null && !limitList.isEmpty()) {
			responseMsgBody.setLimits(new Vector(limitList));
		}

		Collection accounts = retrieveAccounts(limitList);
		if (accounts != null && !accounts.isEmpty()) {
			responseMsgBody.setLimitsSystemXReferenceMap(new Vector(accounts));
		}

		// credit grade
		Collection creditGradeList = retrieveCreditGrades(limitProfile);
		if (creditGradeList != null && !creditGradeList.isEmpty()) {
			responseMsgBody.setCreditGrade(new Vector(creditGradeList));
		}

		// joint borrower
		Collection jointBorrowerList = retrieveJointBorrowers(limitProfile);
		if (jointBorrowerList != null && !jointBorrowerList.isEmpty()) {
			responseMsgBody.setJointBorrower(new Vector(jointBorrowerList));
		}

		// limit security map
		Collection limitSecurityMapList = retrieveLimitSecurityMaps(limitList);
		if (limitSecurityMapList != null && !limitSecurityMapList.isEmpty()) {
			responseMsgBody.setLimitsApprovedSecurityMap(new Vector(limitSecurityMapList));
		}

		// security limit charge
		Collection securityLimitChargeList = retrieveSecurityLimitCharges(limitSecurityMapList);
		if (securityLimitChargeList != null && !securityLimitChargeList.isEmpty()) {
			responseMsgBody.setChargeDetail(new Vector(securityLimitChargeList));
		}

		return prepareResponseMessage(responseMsgBody, eaiMessage.getMsgHeader());
	}

	private EAIMessage prepareResponseMessage(AAMessageBody responseMsgBody, EAIHeader msgHeader) {
		EAIHeader responseHeader = new EAIHeader();
		responseHeader.setMessageId(RESPONSE_MESSAGE_ID);
		responseHeader.setMessageRefNum(msgHeader.getMessageRefNum());
		responseHeader.setMessageType(msgHeader.getMessageType());
		responseHeader.setPublishType(IEAIHeaderConstant.PUB_TYPE_NORMAL);
		responseHeader.setSource(IEAIHeaderConstant.MESSAGE_SOURCE_CMS);
		responseHeader.setPublishDate(MessageDate.getInstance().getString(new Date()));

		return new EAIMessage(responseHeader, responseMsgBody);
	}

	protected Collection retrieveLimits(LimitProfile limitProfile) {
		Long cmsLimitProfileId = new Long(limitProfile.getLimitProfileId());

		Collection limitsList = new ArrayList();

		Collection limitGeneralList = getLimitDao().retrieveLimitsOnlyByCmsLimitProfileId(cmsLimitProfileId);
		for (Iterator itr = limitGeneralList.iterator(); itr.hasNext();) {
			LimitGeneral limitGeneral = (LimitGeneral) itr.next();
			if (ICMSConstant.STATE_DELETED.equals(limitGeneral.getStatus())) {
				continue;
			}

			Limits limits = new Limits();
			limits.setLimitGeneral(limitGeneral);

			Map parameters = new HashMap();
			parameters.put("limitID", new Long(limitGeneral.getCmsId()));

			FacilityMaster facilityMaster = (FacilityMaster) getLimitDao().retrieveObjectByParameters(parameters,
					FacilityMaster.class, IEaiConstant.ACTUAL_FACILITY_MASTER);
			if (facilityMaster != null) {
				Map accInfoMap = getLimitJdbcDao().getAccountInfoByLimitId(facilityMaster.getLimitID());
				facilityMaster.setFacilitySeqNo((Long) accInfoMap.get("LMT_FAC_SEQ"));
				facilityMaster.setAccountNo((String) accInfoMap.get("LSX_EXT_SYS_ACCT_NUM"));
				facilityMaster.setAccountType((String) accInfoMap.get("LSX_EXT_SYS_ACCT_TYPE"));

				limits.setFacilityMaster(facilityMaster);
				limits.setFacilityBNM(facilityMaster.getFacilityBnmCodes());

				if (facilityMaster.getOfficerSet() != null && !facilityMaster.getOfficerSet().isEmpty()) {
					limits.setFacilityOfficer(new Vector(facilityMaster.getOfficerSet()));
				}

				if (facilityMaster.getRelationshipSet() != null && !facilityMaster.getRelationshipSet().isEmpty()) {
					limits.setFacilityRelationship(new Vector(facilityMaster.getRelationshipSet()));
				}

				if (facilityMaster.getIslamicFacilityMaster() != null) {
					limits.setIslamicFacilityMaster(facilityMaster.getIslamicFacilityMaster());
				}

				if (facilityMaster.getFacilityMultiTierFinancings() != null
						&& !facilityMaster.getFacilityMultiTierFinancings().isEmpty()) {
					limits.setFacilityMultiTierFinancings(new Vector(facilityMaster.getFacilityMultiTierFinancings()));
				}

				if (facilityMaster.getFacilityBBAVariPackage() != null) {
					limits.setFacilityBBAVariPackage(facilityMaster.getFacilityBBAVariPackage());
				}

				if (facilityMaster.getFacilityIslamicRentalRenewal() != null) {
					limits.setFacilityIslamicRentalRenewal(facilityMaster.getFacilityIslamicRentalRenewal());
				}

				if (facilityMaster.getFacilityIslamicSecurityDeposit() != null) {
					limits.setFacilityIslamicSecurityDeposit(facilityMaster.getFacilityIslamicSecurityDeposit());
				}

				if (facilityMaster.getFacilityIncrementals() != null
						&& !facilityMaster.getFacilityIncrementals().isEmpty()) {
					limits.setFacilityIncrementals(new ArrayList(facilityMaster.getFacilityIncrementals()));
				}

				if (facilityMaster.getFacilityReductions() != null && !facilityMaster.getFacilityReductions().isEmpty()) {
					limits.setFacilityReductions(new ArrayList(facilityMaster.getFacilityReductions()));
				}

				if (facilityMaster.getFacilityMessages() != null && !facilityMaster.getFacilityMessages().isEmpty()) {
					limits.setFacilityMessages(new ArrayList(facilityMaster.getFacilityMessages()));
				}

				removeDeletedFacilityAssociations(limits);
			}

			limitsList.add(limits);
		}

		return limitsList;
	}

	protected void removeDeletedFacilityAssociations(Limits limits) {
		if (limits.getFacilityOfficer() != null) {
			for (Iterator itr = limits.getFacilityOfficer().iterator(); itr.hasNext();) {
				FacilityOfficer officer = (FacilityOfficer) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(officer.getStatus())) {
					itr.remove();
				}
			}
		}

		if (limits.getFacilityRelationship() != null) {
			for (Iterator itr = limits.getFacilityRelationship().iterator(); itr.hasNext();) {
				FacilityRelationship relationship = (FacilityRelationship) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(relationship.getStatus())) {
					itr.remove();
				}
			}
		}

		if (limits.getFacilityMultiTierFinancings() != null) {
			for (Iterator itr = limits.getFacilityMultiTierFinancings().iterator(); itr.hasNext();) {
				FacilityMultiTierFinancing multitier = (FacilityMultiTierFinancing) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(multitier.getStatus())) {
					itr.remove();
				}
			}
		}

		if (limits.getFacilityIncrementals() != null) {
			for (Iterator itr = limits.getFacilityIncrementals().iterator(); itr.hasNext();) {
				FacilityIncremental incremental = (FacilityIncremental) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(incremental.getStatus())) {
					itr.remove();
				}
			}
		}

		if (limits.getFacilityReductions() != null) {
			for (Iterator itr = limits.getFacilityReductions().iterator(); itr.hasNext();) {
				FacilityReduction reduction = (FacilityReduction) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(reduction.getStatus())) {
					itr.remove();
				}
			}
		}

		if (limits.getFacilityMessages() != null) {
			for (Iterator itr = limits.getFacilityMessages().iterator(); itr.hasNext();) {
				FacilityMessage message = (FacilityMessage) itr.next();
				if (ICMSConstant.HOST_STATUS_DELETE.equals(message.getStatus())) {
					itr.remove();
				}
			}
		}
	}

	protected Collection retrieveAccounts(Collection limitList) {
		if (limitList == null || limitList.isEmpty()) {
			return null;
		}

		List cmsLimitIds = new ArrayList();
		for (Iterator itr = limitList.iterator(); itr.hasNext();) {
			Limits limits = (Limits) itr.next();
			cmsLimitIds.add(new Long(limits.getLimitGeneral().getCmsId()));
		}

		Set cmsAccountIds = new HashSet();
		List accounts = new ArrayList();

		List limitAccountMaps = getLimitDao().retrieveLimitAccountMapByStatusAndLimitCmsIdList(
				ICMSConstant.STATE_ACTIVE, cmsLimitIds, LimitsSysXRefMap.class);
		if (limitAccountMaps != null && !limitAccountMaps.isEmpty()) {
			for (Iterator itr = limitAccountMaps.iterator(); itr.hasNext();) {
				LimitsSysXRefMap map = (LimitsSysXRefMap) itr.next();
				cmsAccountIds.add(map.getCmsSystemXRefId());
			}

			if (!cmsAccountIds.isEmpty()) {
				for (Iterator itr = cmsAccountIds.iterator(); itr.hasNext();) {
					Long cmsAccountId = (Long) itr.next();
					SystemXReference xref = (SystemXReference) getLimitDao().retrieve(cmsAccountId,
							SystemXReference.class);
					Long[] accountCmsLimitIds = prepareCmsLimitIds(xref);
					xref.setCmsLimitIds(accountCmsLimitIds);
					accounts.add(xref);
				}
			}
		}
		else {
			return null;
		}

		return accounts;
	}

	private Long[] prepareCmsLimitIds(SystemXReference xref) {
		List accountCmsLimitIds = new ArrayList();
		for (Iterator itr = xref.getLimitsSysXRefMapSet().iterator(); itr.hasNext();) {
			LimitsSysXRefMap map = (LimitsSysXRefMap) itr.next();
			accountCmsLimitIds.add(map.getCmsLimitId());
		}

		return (Long[]) accountCmsLimitIds.toArray(new Long[0]);
	}

	protected Collection retrieveCreditGrades(LimitProfile limitProfile) {
		Map parameters = new HashMap();
		parameters.put("LOSAANumber", limitProfile.getLOSAANumber());

		return getLimitDao().retrieveObjectsListByParameters(parameters, LimitCreditGrade.class);
	}

	protected Collection retrieveJointBorrowers(LimitProfile limitProfile) {
		Map parameters = new HashMap();
		parameters.put("cmsLimitProfileId", new Long(limitProfile.getLimitProfileId()));

		return getLimitDao().retrieveObjectsListByParameters(parameters, JointBorrower.class);
	}

	protected Collection retrieveLimitSecurityMaps(Collection limitList) {
		Collection limitSecurityMapList = new ArrayList();
		for (Iterator itr = limitList.iterator(); itr.hasNext();) {
			Limits limits = (Limits) itr.next();

			String messageSource = EAIMessageSynchronizationManager.getMessageSource();

			String[] applicableSourceIds = ArrayUtils.isEmpty(this.limitsApprovedSecurityMapApplicableSourceIds) ? null
					: (String[]) ArrayUtils.add(this.limitsApprovedSecurityMapApplicableSourceIds, messageSource);

			limitSecurityMapList.addAll(getLimitDao()
					.retrieveListOfActualLimitsApprovedSecurityMapByCmsLimitIdAndSourceIds(
							limits.getLimitGeneral().getCmsId(), applicableSourceIds));
		}

		return limitSecurityMapList;
	}

	protected Collection retrieveSecurityLimitCharges(Collection limitSecurityMapList) {
		Map parameters = new WeakHashMap();

		/* key is the cms collateral id, value is the los collateral id */
		Map cmsCollateralIdLosCollateralIdMap = new HashMap();

		/* key is the charge detail id, value is the list of cms limit id */
		Map chargeDetailIdCmsLimitIdListMap = new HashMap();

		for (Iterator itr = limitSecurityMapList.iterator(); itr.hasNext();) {
			LimitsApprovedSecurityMap limitSecurityMap = (LimitsApprovedSecurityMap) itr.next();
			parameters.put("chargeID", new Long(limitSecurityMap.getCmsId()));
			parameters.put("status", ICMSConstant.STATE_ACTIVE);

			cmsCollateralIdLosCollateralIdMap.put(new Long(limitSecurityMap.getCmsSecurityId()), limitSecurityMap
					.getSecurityId());

			List chargeMapList = (List) getLimitDao().retrieveObjectsListByParameters(parameters, LimitChargeMap.class);

			if (chargeMapList == null || chargeMapList.isEmpty()) {
				continue;
			}

			for (Iterator cmItr = chargeMapList.iterator(); cmItr.hasNext();) {
				LimitChargeMap chargeMap = (LimitChargeMap) cmItr.next();
				Collection cmsLimitIdList = (Collection) chargeDetailIdCmsLimitIdListMap.get(new Long(chargeMap
						.getChargeDetailID()));
				if (cmsLimitIdList == null) {
					cmsLimitIdList = new ArrayList();
				}

				cmsLimitIdList.add(new Long(limitSecurityMap.getCmsLimitId()));
				chargeDetailIdCmsLimitIdListMap.put(new Long(chargeMap.getChargeDetailID()), cmsLimitIdList);
			}
		}

		Collection securityLimitChargesList = new ArrayList();
		for (Iterator itr = chargeDetailIdCmsLimitIdListMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			Long chargeDetailId = (Long) entry.getKey();
			List cmsLimitIdList = (List) entry.getValue();

			ChargeDetail charge = (ChargeDetail) getLimitDao().retrieve(chargeDetailId, ChargeDetail.class);
			charge.setSecurityId((String) cmsCollateralIdLosCollateralIdMap.get(new Long(charge.getCmsSecurityId())));
			charge.setCMSLimitId((Long[]) cmsLimitIdList.toArray(new Long[0]));
			securityLimitChargesList.add(charge);
		}

		return securityLimitChargesList;
	}
}
