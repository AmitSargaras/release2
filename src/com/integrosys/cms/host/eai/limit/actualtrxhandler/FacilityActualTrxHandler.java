package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonActualTrxHandler;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.NoSuchLimitException;
import com.integrosys.cms.host.eai.limit.bus.FacilityBBAVariPackage;
import com.integrosys.cms.host.eai.limit.bus.FacilityBNM;
import com.integrosys.cms.host.eai.limit.bus.FacilityIncremental;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.FacilityMessage;
import com.integrosys.cms.host.eai.limit.bus.FacilityMultiTierFinancing;
import com.integrosys.cms.host.eai.limit.bus.FacilityOfficer;
import com.integrosys.cms.host.eai.limit.bus.FacilityReduction;
import com.integrosys.cms.host.eai.limit.bus.FacilityRelationship;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.limit.bus.IslamicFacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.LimitGeneral;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;

/**
 * Handler to do pre process of facility info (will be used by update handler),
 * create facility info.
 * @author Thurein
 * @author Chong Jun Yong
 * 
 */
public class FacilityActualTrxHandler extends AbstractCommonActualTrxHandler {

	private ILimitDao limitDao;

	private ICustomerDao customerDao;

	private Map variationProperties;

	private LimitHelper helper;

	private String[] facilityStatusForNonRevolvingIndicator;

	/**
	 * Hold the list of application type, which are not including the facility
	 * master information, for those application processing of the facility
	 * master need to be skipped.
	 */
	protected List facilityNotRequiredAppTypeList;

	public ILimitDao getLimitDao() {
		return limitDao;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
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

	public List getFacilityNotRequiredAppTypeList() {
		return facilityNotRequiredAppTypeList;
	}

	public void setFacilityNotRequiredAppTypeList(List facilityNotRequiredAppTypeList) {
		this.facilityNotRequiredAppTypeList = facilityNotRequiredAppTypeList;
	}

	public void setFacilityStatusForNonRevolvingIndicator(String[] facilityStatusForNonRevolvingIndicator) {
		this.facilityStatusForNonRevolvingIndicator = facilityStatusForNonRevolvingIndicator;
	}

	public Message persistActualTrx(Message msg) {
		AAMessageBody aaMessage = ((AAMessageBody) msg.getMsgBody());

		String aaType = aaMessage.getLimitProfile().getAAType();

		if (getFacilityNotRequiredAppTypeList().contains(aaType)) {
			return msg;
		}

		String cifSource = aaMessage.getLimitProfile().getCIFSource();

		if ((aaMessage.getLimits() == null) || (aaMessage.getLimits().isEmpty())) {
			return msg;
		}

		for (Iterator itr = aaMessage.getLimits().iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();

			if (limit.getFacilityMaster() == null) {
				continue;
			}

			prepareFacilityMasterForPersistency(limit.getLimitGeneral(), limit);

			if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {
				storeFacilityMaster(limit.getFacilityMaster(), IEaiConstant.ACTUAL_FACILITY_MASTER);

				storeFacilityOfficers(limit, IEaiConstant.ACTUAL_FACILITY_OFFICER);

				storeFacilityRelationships(limit, IEaiConstant.ACTUAL_FACILITY_RELATIONSHIP, cifSource);

				storeFacilityMultiTierFinancing(limit, IEaiConstant.ACTUAL_FACILITY_MULTI_FINANCE);

				storeFacilityMessages(limit, IEaiConstant.ENTITY_ACTUAL_FACILITY_MESSAGE);

				storeFacilityIncrementals(limit, IEaiConstant.ENTITY_ACTUAL_FACILITY_INCREMENTAL);

				storeFacilityReductions(limit, IEaiConstant.ENTITY_ACTUAL_FACILITY_REDUCTION);
			}
		}

		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		AAMessageBody aaMessage = ((AAMessageBody) msg.getMsgBody());

		String aaType = aaMessage.getLimitProfile().getAAType();

		if (facilityNotRequiredAppTypeList.contains(aaType)) {
			return msg;
		}

		Vector limits = aaMessage.getLimits();

		if (limits == null || limits.isEmpty()) {
			return msg;
		}

		String cifSource = aaMessage.getLimitProfile().getCIFSource();

		int limitSize = limits.size();

		for (int i = 0; i < limitSize; i++) {
			Limits limit = (Limits) limits.elementAt(i);

			if (limit.getFacilityMaster() == null) {
				continue;
			}
			// need to retrieve the actual limit general, to get the limitid.
			Map parameters = new WeakHashMap();
			parameters.put("LOSLimitId", limit.getLimitGeneral().getLOSLimitId());
			parameters.put("LOSAANumber", limit.getLimitGeneral().getLOSAANumber());

			LimitGeneral actualLimitGeneral = (LimitGeneral) getLimitDao().retrieveObjectByParameters(parameters,
					LimitGeneral.class, IEaiConstant.ACTUAL_LIMIT_GENERAL);
			if (actualLimitGeneral == null && limit.getLimitGeneral().getCMSLimitId() > 0) {
				actualLimitGeneral = (LimitGeneral) getLimitDao().retrieve(
						new Long(limit.getLimitGeneral().getCMSLimitId()), IEaiConstant.ACTUAL_LIMIT_GENERAL);
			}
			if (actualLimitGeneral == null) {
				throw new NoSuchLimitException(limit.getLimitGeneral().getCIFId(), limit.getLimitGeneral()
						.getLOSLimitId());
			}

			prepareFacilityMasterForPersistency(actualLimitGeneral, limit);

			if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& (limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(CREATEINDICATOR)))) {
				storeFacilityMaster(limit.getFacilityMaster(), IEaiConstant.STAGE_FACILITY_MASTER);

				storeFacilityOfficers(limit, IEaiConstant.STAGE_FACILITY_OFFICER);

				storeFacilityRelationships(limit, IEaiConstant.STAGE_FACILITY_RELATIONSHIP, cifSource);

				storeFacilityMultiTierFinancing(limit, IEaiConstant.STAGE_FACILITY_MULTI_FINANCE);

				storeFacilityMessages(limit, IEaiConstant.ENTITY_STAGE_FACILITY_MESSAGE);

				storeFacilityIncrementals(limit, IEaiConstant.ENTITY_STAGE_FACILITY_INCREMENTAL);

				storeFacilityReductions(limit, IEaiConstant.ENTITY_STAGE_FACILITY_REDUCTION);
			}
		}

		return msg;
	}

	/**
	 * Store the facility master.
	 * @param FacilityMaster to be stored
	 * @param facilityClass type of the facility master, i.e. actual or stage
	 */
	private void storeFacilityMaster(FacilityMaster fm, String entityName) {
		Long id = (Long) getLimitDao().store(fm, FacilityMaster.class, entityName);

		fm.setId(id.longValue());

	}

	private void storeFacilityOfficers(Limits limit, String facilityOffClass) {
		Vector facilityOfficers = limit.getFacilityOfficer();
		long facilityMasterID = limit.getFacilityMaster().getId();

		if (facilityOfficers == null) {
			facilityOfficers = new Vector();
		}
		Iterator iter = facilityOfficers.iterator();

		while (iter.hasNext()) {
			FacilityOfficer officer = (FacilityOfficer) iter.next();
			officer.setFacilityMasterId(facilityMasterID);
			Long officerId = (Long) getLimitDao().store(officer, FacilityOfficer.class, facilityOffClass);

			officer.setId(officerId.longValue());

			/*
			 * Only the actual facility offer need to update the cmsOfficerId.
			 * For the staging officer, need to reuse the cmsOfficerId from
			 * actual officer
			 */
			if (facilityOffClass.equals(IEaiConstant.ACTUAL_FACILITY_OFFICER)) {
				officer.setCmsReferenceId(officerId);
				getLimitDao().update(officer, FacilityOfficer.class, facilityOffClass);
			}

		}
	}

	/**
	 * Insert the list of facility multiTierFinancing into database.
	 * @param limit
	 * @param facilityMultiFinanceClass String - indicate the storing facility
	 *        multiTierFinancing are for actual or staging.
	 */
	private void storeFacilityMultiTierFinancing(Limits limit, String facilityMultiFinanceClass) {
		long facilityMasterID = limit.getFacilityMaster().getId();
		Vector facMultiFinanceList = limit.getFacilityMultiTierFinancings();
		if (facMultiFinanceList == null) {
			facMultiFinanceList = new Vector();
		}
		for (int i = 0; i < facMultiFinanceList.size(); i++) {
			FacilityMultiTierFinancing facilityMultiFinancing = (FacilityMultiTierFinancing) limit
					.getFacilityMultiTierFinancings().get(i);
			facilityMultiFinancing.setFacilityMasterId(facilityMasterID);

			Long id = (Long) getLimitDao().store(facilityMultiFinancing, FacilityMultiTierFinancing.class,
					facilityMultiFinanceClass);

			facilityMultiFinancing.setId(id.longValue());

			if (facilityMultiFinanceClass.equals(IEaiConstant.ACTUAL_FACILITY_MULTI_FINANCE)) {
				facilityMultiFinancing.setRefID(id.longValue());
				getLimitDao().update(facilityMultiFinancing, FacilityMultiTierFinancing.class,
						facilityMultiFinanceClass);
			}
		}
	}

	/**
	 * Insert the facility relationships into database.
	 * @param limit
	 * @param facilityRelClass String - indicate the storing facility
	 *        relationships are for actual or staging.
	 */
	private void storeFacilityRelationships(Limits limit, String facilityRelClass, String cifSource) {
		long facilityMasterID = limit.getFacilityMaster().getId();
		Vector facRelationList = limit.getFacilityRelationship();
		if (facRelationList == null) {
			facRelationList = new Vector();
		}

		for (int i = 0; i < facRelationList.size(); i++) {
			FacilityRelationship facRelationship = (FacilityRelationship) limit.getFacilityRelationship().get(i);
			facRelationship.setFacilityMasterId(facilityMasterID);
			MainProfile mainprofile = getCustomerDao().searchMainProfileByCIFAndCIFSource(facRelationship.getCIFId(),
					cifSource);
			if (mainprofile == null) {
				throw new NoSuchCustomerException(facRelationship.getCIFId(), cifSource);
			}

			facRelationship.setCustomerName(mainprofile.getCustomerNameShort());
			facRelationship.setMainProfileID(new Long(mainprofile.getCmsId()));
			facRelationship.setCurrencyCode(limit.getLimitGeneral().getLimitCurrency());

			Long relationshipId = (Long) getLimitDao().store(facRelationship, FacilityRelationship.class,
					facilityRelClass);

			facRelationship.setId(relationshipId.longValue());

			if (facilityRelClass.equals(IEaiConstant.ACTUAL_FACILITY_RELATIONSHIP)) {
				facRelationship.setCmsReferenceId(relationshipId);
				getLimitDao().update(facRelationship, FacilityRelationship.class, facilityRelClass);
			}
		}
	}

	private void storeFacilityMessages(Limits limit, String facilityMessageEntityName) {
		long facilityMasterID = limit.getFacilityMaster().getId();

		List facilityMessages = limit.getFacilityMessages();
		if (facilityMessages == null || facilityMessages.isEmpty()) {
			return;
		}

		for (Iterator itr = facilityMessages.iterator(); itr.hasNext();) {
			FacilityMessage message = (FacilityMessage) itr.next();
			message.setCmsFacilityMasterId(new Long(facilityMasterID));

			Long id = (Long) getLimitDao().store(message, FacilityMessage.class, facilityMessageEntityName);

			message.setId(id);

			if (facilityMessageEntityName.equals(IEaiConstant.ENTITY_ACTUAL_FACILITY_MESSAGE)) {
				message.setCmsReferenceId(message.getId());
				getLimitDao().update(message, FacilityMessage.class, facilityMessageEntityName);
			}
		}
	}

	private void storeFacilityIncrementals(Limits limit, String facilityIncrementalEntityName) {
		long facilityMasterID = limit.getFacilityMaster().getId();

		List facilityIncrementals = limit.getFacilityIncrementals();
		if (facilityIncrementals == null || facilityIncrementals.isEmpty()) {
			return;
		}

		for (Iterator itr = facilityIncrementals.iterator(); itr.hasNext();) {
			FacilityIncremental incremental = (FacilityIncremental) itr.next();
			incremental.setCmsFacilityMasterId(new Long(facilityMasterID));

			Long id = (Long) getLimitDao().store(incremental, FacilityIncremental.class, facilityIncrementalEntityName);

			incremental.setId(id);

			if (facilityIncrementalEntityName.equals(IEaiConstant.ENTITY_ACTUAL_FACILITY_INCREMENTAL)) {
				incremental.setCmsReferenceId(incremental.getId());
				getLimitDao().update(incremental, FacilityIncremental.class, facilityIncrementalEntityName);
			}
		}
	}

	private void storeFacilityReductions(Limits limit, String facilityReductionEntityName) {
		long facilityMasterID = limit.getFacilityMaster().getId();

		List facilityReductions = limit.getFacilityReductions();
		if (facilityReductions == null || facilityReductions.isEmpty()) {
			return;
		}

		for (Iterator itr = facilityReductions.iterator(); itr.hasNext();) {
			FacilityReduction reduction = (FacilityReduction) itr.next();
			reduction.setCmsFacilityMasterId(new Long(facilityMasterID));

			Long id = (Long) getLimitDao().store(reduction, FacilityReduction.class, facilityReductionEntityName);

			reduction.setId(id);

			if (facilityReductionEntityName.equals(IEaiConstant.ENTITY_ACTUAL_FACILITY_REDUCTION)) {
				reduction.setCmsReferenceId(reduction.getId());
				getLimitDao().update(reduction, FacilityReduction.class, facilityReductionEntityName);
			}
		}
	}

	/**
	 * <p>
	 * Before persisting to db, need to prepare the facility master by assigning
	 * the values form limit general. Also need to assign facility bnm,
	 * bbaPackage, islamic facility from limit which are required for the update
	 * process.
	 * <p>
	 * For staging facility, the limit general will be retrieved from db and not
	 * taking from SI message.
	 * @param limitGeneral limit general object
	 * @param limit limit object
	 */
	protected void prepareFacilityMasterForPersistency(LimitGeneral limitGeneral, Limits limit) {
		FacilityMaster fm = limit.getFacilityMaster();

		FacilityBNM bnm = limit.getFacilityBNM();

		FacilityBBAVariPackage bbaPackage = limit.getFacilityBBAVariPackage();

		IslamicFacilityMaster iFacilityMaster = limit.getIslamicFacilityMaster();

		if (iFacilityMaster != null) {
			iFacilityMaster.setCurrencyCode(limitGeneral.getLimitCurrency());
		}

		fm.setLimitID(limitGeneral.getCmsId());

		fm.setFacilityBnmCodes(bnm);

		fm.setFacilityIslamicRentalRenewal(limit.getFacilityIslamicRentalRenewal());

		fm.setFacilityIslamicSecurityDeposit(limit.getFacilityIslamicSecurityDeposit());

		fm.setIslamicFacilityMaster(iFacilityMaster);

		fm.setFacilityBBAVariPackage(bbaPackage);

		fm.setFacilityCurrencyCode(limitGeneral.getLimitCurrency());

		fm.setFacilityStatusCategoryCode("FACILITY_STATUS");

		fm.setDrawingAmount(new Amount(limitGeneral.getDrawingLimit().doubleValue(), limitGeneral.getLimitCurrency()));

		fm.setApprovedAmount(limitGeneral.getApprovedLimit());

		fm.setTerm(limitGeneral.getLimitTenor());

		fm.setTermCode(limitGeneral.getLimitTenorBasis());

		if (ArrayUtils.contains(this.facilityStatusForNonRevolvingIndicator, fm.getFacilityStatusEntryCode())) {
			fm.setRevolvingInd(ICMSConstant.FALSE_VALUE);
		}
	}
}