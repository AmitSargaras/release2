package com.integrosys.cms.host.eai.limit.actualtrxhandler;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.WeakHashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.customer.NoSuchCustomerException;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.FacilityBBAVariPackage;
import com.integrosys.cms.host.eai.limit.bus.FacilityIncremental;
import com.integrosys.cms.host.eai.limit.bus.FacilityIslamicRentalRenewal;
import com.integrosys.cms.host.eai.limit.bus.FacilityIslamicSecurityDeposit;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.FacilityMessage;
import com.integrosys.cms.host.eai.limit.bus.FacilityMultiTierFinancing;
import com.integrosys.cms.host.eai.limit.bus.FacilityOfficer;
import com.integrosys.cms.host.eai.limit.bus.FacilityReduction;
import com.integrosys.cms.host.eai.limit.bus.FacilityRelationship;
import com.integrosys.cms.host.eai.limit.bus.IslamicFacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.support.LimitHelper;
import com.integrosys.cms.host.eai.support.ReflectionUtils;
import com.integrosys.cms.host.eai.support.VariationPropertiesKey;

/**
 * Handler to do update Facility Master task.
 * 
 * @author Chong Jun Yong
 * 
 */
public class UpdateFacilityActualTrxHandler extends FacilityActualTrxHandler {

	private static final LimitHelper limitHelper = new LimitHelper();

	public Message persistActualTrx(Message msg) {
		AAMessageBody msgBody = (AAMessageBody) msg.getMsgBody();

		Vector limits = msgBody.getLimits();
		if (limits == null || limits.isEmpty()) {
			return msg;
		}

		boolean isVariation = ((msgBody.getLimitProfile().getCMSLimitProfileId() != null) && (msgBody.getLimitProfile()
				.getCMSLimitProfileId().longValue() > 0));
		String source = msg.getMsgHeader().getSource();
		String cifSource = msgBody.getLimitProfile().getCIFSource();

		for (Iterator itr = limits.iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();

			if (limit.getFacilityMaster() == null) {
				continue;
			}

			if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& ((limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (limit
							.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {
				updateFacilityMaster(limit, IEaiConstant.ACTUAL_FACILITY_MASTER, isVariation, source, cifSource);
			}
		}

		return msg;
	}

	public Message persistStagingTrx(Message msg, Object trxValue) {
		AAMessageBody msgBody = (AAMessageBody) msg.getMsgBody();

		Vector limits = msgBody.getLimits();
		if (limits == null || limits.isEmpty()) {
			return msg;
		}

		boolean isVariation = ((msgBody.getLimitProfile().getCMSLimitProfileId() != null) && (msgBody.getLimitProfile()
				.getCMSLimitProfileId().longValue() > 0));
		String source = msg.getMsgHeader().getSource();
		String cifSource = msgBody.getLimitProfile().getCIFSource();

		for (Iterator itr = limits.iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();

			if (limit.getFacilityMaster() == null) {
				continue;
			}

			if ((limit.getLimitGeneral().getChangeIndicator().equals(String.valueOf(CHANGEINDICATOR)))
					&& ((limit.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(UPDATEINDICATOR))) || (limit
							.getLimitGeneral().getUpdateStatusIndicator().equals(String.valueOf(DELETEINDICATOR))))) {
				updateFacilityMaster(limit, IEaiConstant.STAGE_FACILITY_MASTER, isVariation, source, cifSource);
			}
		}

		return msg;
	}

	/**
	 * Update the facility master, this method responsible for both actual and
	 * staging, it will update the facility and relating facility officers set
	 * and facility relationships set and facility facilityMultiTierFinancings
	 * set.
	 * @param Limit Limits
	 * @param facilityMasterClass String - type of the updating facility master,
	 *        i.e, actual or staging
	 */
	private void updateFacilityMaster(Limits limit, String facilityMasterClass, boolean isVariation, String source,
			String cifSource) {
		FacilityMaster updatingFacilityMaster = null;
		Map parameters = new WeakHashMap();

		/*
		 * Retrieve the existing facility master to be updated. For the actual
		 * facility master used the limitid of the limit and for staging used
		 * the referenceId of the actual facility master
		 */
		if (facilityMasterClass.equals(IEaiConstant.ACTUAL_FACILITY_MASTER)) {
			parameters.put("limitID", new Long(limit.getLimitGeneral().getCmsId()));
			updatingFacilityMaster = (FacilityMaster) getLimitDao().retrieveObjectByParameters(parameters,
					FacilityMaster.class, IEaiConstant.ACTUAL_FACILITY_MASTER);
		}
		else if (facilityMasterClass.equals(IEaiConstant.STAGE_FACILITY_MASTER)) {
			parameters.put("referenceID", new Long(limit.getFacilityMaster().getId()));
			parameters.put("transactionType", "FACILITY");
			CMSTransaction facilityWorkflow = (CMSTransaction) getLimitDao().retrieveObjectByParameters(parameters,
					CMSTransaction.class);

			updatingFacilityMaster = (FacilityMaster) getLimitDao().retrieve(
					new Long(facilityWorkflow.getStageReferenceID()), IEaiConstant.STAGE_FACILITY_MASTER);
		}

		/* Only the necessary fields are updated in the case of variation */
		if (isVariation) {
			List copyingProperties = (List) getVariationProperties().get(
					new VariationPropertiesKey(source, FacilityMaster.class.getName()));
			limitHelper.copyVariationProperties(limit.getFacilityMaster(), updatingFacilityMaster, copyingProperties);
		}
		else {
			AccessorUtil.copyValue(limit.getFacilityMaster(), updatingFacilityMaster, new String[] { "Id", "LimitID",
					"FacilityBnmCodes", "FacilityBBAVariPackage", "IslamicFacilityMaster",
					"FacilityMultiTierFinancings", "OfficerSet", "RelationshipSet", "FacilityMessages",
					"FacilityIslamicSecurityDeposit", "FacilityIslamicRentalRenewal", "FacilityIncrementals",
					"FacilityReductions" });
			AccessorUtil.copyValue(limit.getFacilityBNM(), updatingFacilityMaster.getFacilityBnmCodes(),
					new String[] { "FacilityMasterId" });

			if (limit.getFacilityIslamicRentalRenewal() != null) {
				if (updatingFacilityMaster.getFacilityIslamicRentalRenewal() == null) {
					FacilityIslamicRentalRenewal renewal = new FacilityIslamicRentalRenewal();
					updatingFacilityMaster.setFacilityIslamicRentalRenewal(renewal);
				}
				AccessorUtil.copyValue(limit.getFacilityIslamicRentalRenewal(), updatingFacilityMaster
						.getFacilityIslamicRentalRenewal(), new String[] { "cmsFacilityMasterId" });
			}

			if (limit.getFacilityIslamicSecurityDeposit() != null) {
				if (updatingFacilityMaster.getFacilityIslamicSecurityDeposit() == null) {
					FacilityIslamicSecurityDeposit deposit = new FacilityIslamicSecurityDeposit();
					updatingFacilityMaster.setFacilityIslamicSecurityDeposit(deposit);
				}
				AccessorUtil.copyValue(limit.getFacilityIslamicSecurityDeposit(), updatingFacilityMaster
						.getFacilityIslamicSecurityDeposit(), new String[] { "cmsFacilityMasterId" });
			}

			if (limit.getFacilityBBAVariPackage() != null) {
				if (updatingFacilityMaster.getFacilityBBAVariPackage() == null) {
					FacilityBBAVariPackage newPackage = new FacilityBBAVariPackage();
					updatingFacilityMaster.setFacilityBBAVariPackage(newPackage);
				}
				AccessorUtil.copyValue(limit.getFacilityBBAVariPackage(), updatingFacilityMaster
						.getFacilityBBAVariPackage(), new String[] { "FacilityMasterId" });
			}

			if (limit.getIslamicFacilityMaster() != null) {
				if (updatingFacilityMaster.getIslamicFacilityMaster() == null) {
					IslamicFacilityMaster iMaster = new IslamicFacilityMaster();
					updatingFacilityMaster.setIslamicFacilityMaster(iMaster);
				}
				AccessorUtil.copyValue(limit.getIslamicFacilityMaster(), updatingFacilityMaster
						.getIslamicFacilityMaster(), new String[] { "FacilityMasterId" });
			}
		}

		/*
		 * Before updating the facility master need to prepare facility
		 * officers, facility relationships and multifinancings which are
		 * associated to that facility master. Preparation involve synchronizing
		 * the record in the database with the the data coming from SI
		 * xml(CA001).
		 */
		if (facilityMasterClass.equals(IEaiConstant.ACTUAL_FACILITY_MASTER)) {
			prepareRelationshipForPersistency(updatingFacilityMaster, limit.getFacilityRelationship(),
					IEaiConstant.ACTUAL_FACILITY_RELATIONSHIP, cifSource, source);
			prepareOfficerForPersistency(updatingFacilityMaster, limit.getFacilityOfficer(),
					IEaiConstant.ACTUAL_FACILITY_OFFICER);
			prepareFacilityMultiFinancings(updatingFacilityMaster, limit.getFacilityMultiTierFinancings(),
					IEaiConstant.ACTUAL_FACILITY_MULTI_FINANCE);
			prepareFacilityMessages(updatingFacilityMaster, limit.getFacilityMessages(),
					IEaiConstant.ENTITY_ACTUAL_FACILITY_MESSAGE, source);
			prepareFacilityIncrementalsForPersistency(updatingFacilityMaster, limit.getFacilityIncrementals());
			prepareFacilityReductionsForPersistency(updatingFacilityMaster, limit.getFacilityReductions());
		}
		else if (facilityMasterClass.equals(IEaiConstant.STAGE_FACILITY_MASTER)) {
			prepareRelationshipForPersistency(updatingFacilityMaster, limit.getFacilityMaster().getRelationshipSet(),
					IEaiConstant.STAGE_FACILITY_RELATIONSHIP, cifSource, source);
			prepareOfficerForPersistency(updatingFacilityMaster, limit.getFacilityMaster().getOfficerSet(),
					IEaiConstant.STAGE_FACILITY_OFFICER);
			prepareFacilityMultiFinancings(updatingFacilityMaster, limit.getFacilityMaster()
					.getFacilityMultiTierFinancings(), IEaiConstant.STAGE_FACILITY_MULTI_FINANCE);
			prepareFacilityMessages(updatingFacilityMaster, limit.getFacilityMaster().getFacilityMessages(),
					IEaiConstant.ENTITY_STAGE_FACILITY_MESSAGE, source);
			prepareFacilityIncrementalsForPersistency(updatingFacilityMaster, limit.getFacilityMaster()
					.getFacilityIncrementals());
			prepareFacilityReductionsForPersistency(updatingFacilityMaster, limit.getFacilityMaster()
					.getFacilityReductions());
		}

		getLimitDao().update(updatingFacilityMaster, FacilityMaster.class, facilityMasterClass);
		limit.setFacilityMaster(updatingFacilityMaster);

		// only for actual
		if (facilityMasterClass.equals(IEaiConstant.ACTUAL_FACILITY_MASTER)) {
			parameters.clear();
			parameters.put("limitID", new Long(limit.getLimitGeneral().getCmsId()));
			FacilityMaster updatedactualFacMaster = (FacilityMaster) getLimitDao().retrieveObjectByParameters(
					parameters, FacilityMaster.class, IEaiConstant.ACTUAL_FACILITY_MASTER);

			ReflectionUtils.copyValuesWithinObjects(updatedactualFacMaster.getOfficerSet(), "id", "cmsReferenceId");
			ReflectionUtils
					.copyValuesWithinObjects(updatedactualFacMaster.getRelationshipSet(), "id", "cmsReferenceId");
			ReflectionUtils.copyValuesWithinObjects(updatedactualFacMaster.getFacilityMultiTierFinancings(), "id",
					"refID");
			ReflectionUtils.copyValuesWithinObjects(updatedactualFacMaster.getFacilityMessages(), "id",
					"cmsReferenceId");
			ReflectionUtils.copyValuesWithinObjects(updatedactualFacMaster.getFacilityIncrementals(), "id",
					"cmsReferenceId");
			ReflectionUtils.copyValuesWithinObjects(updatedactualFacMaster.getFacilityReductions(), "id",
					"cmsReferenceId");

			getLimitDao().update(updatedactualFacMaster, FacilityMaster.class, IEaiConstant.ACTUAL_FACILITY_MASTER);
			limit.setFacilityMaster(updatedactualFacMaster);
		}

	}

	/**
	 * <p>
	 * Sychronize the multitier financing info between the one from source and
	 * the one in persistence storage.
	 * <p>
	 * The main key to be used for comparison would be
	 * {@link FacilityMultiTierFinancing#getTierSeqNo()}, those found in both
	 * source and persistence storage, would do an update/insert, else just
	 * remove.
	 * 
	 * @param FacilityMaster - updating facility master
	 * @param workingMultitierFinancings - FacilityMultiFinancings set of the
	 *        updating facility master
	 * @param className - type of facility officers, i.e. actual or staging
	 */
	private void prepareFacilityMultiFinancings(FacilityMaster master, Collection workingMultitierFinancings,
			String className) {
		if (workingMultitierFinancings == null || workingMultitierFinancings.isEmpty()) {
			return;
		}

		Set existingFinancings = master.getFacilityMultiTierFinancings();

		Collection replicatedMultitierFinancing = ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				workingMultitierFinancings, new String[] { "facilityMasterId", "id" }, new Class[] { String.class,
						StandardCode.class });

		Collection synchronizedMultitierFinancings = EntityAssociationUtils.synchronizeCollectionsByProperties(
				existingFinancings, replicatedMultitierFinancing, new String[] { "tierSeqNo" }, new String[] { "id",
						"facilityMasterId" });
		for (Iterator itr = synchronizedMultitierFinancings.iterator(); itr.hasNext();) {
			FacilityMultiTierFinancing fin = (FacilityMultiTierFinancing) itr.next();
			fin.setFacilityMasterId(master.getId());
		}

		Collection deletedMultitierFinancings = EntityAssociationUtils.retrieveRemovedObjectsCollection(
				existingFinancings, replicatedMultitierFinancing, new String[] { "tierSeqNo" });
		for (Iterator itr = deletedMultitierFinancings.iterator(); itr.hasNext();) {
			FacilityMultiTierFinancing fin = (FacilityMultiTierFinancing) itr.next();
			fin.setFacilityMasterId(master.getId());
			fin.setStatus(String.valueOf(IEaiConstant.DELETEINDICATOR));
		}

		existingFinancings.clear();
		existingFinancings.addAll(synchronizedMultitierFinancings);
		existingFinancings.addAll(deletedMultitierFinancings);
	}

	/**
	 * To prepare the set facility officers before the update process. Logic
	 * include - <li>get the new facility officers from SI xml <li>prepare the
	 * new objects by assigning values to it <li>replace all the old facility
	 * officers of the updating facility master with the new facility officers
	 * 
	 * @param FacilityMaster - updating facility master
	 * @param workingFacilityOfficers - facility officers set of the updating
	 *        facility master
	 * @param className - type of facility officers, i.e. actual or staging
	 */
	private void prepareOfficerForPersistency(FacilityMaster master, Collection workingFacilityOfficers,
			String className) {
		if (workingFacilityOfficers == null || workingFacilityOfficers.isEmpty()) {
			return;
		}

		Set existingOfficers = master.getOfficerSet();

		Collection replicatedFacilityOfficers = ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				workingFacilityOfficers, new String[] { "facilityMasterId", "id" }, new Class[] { String.class,
						StandardCode.class });

		for (Iterator itr = replicatedFacilityOfficers.iterator(); itr.hasNext();) {
			FacilityOfficer newOfficer = (FacilityOfficer) itr.next();
			newOfficer.setFacilityMasterId(master.getId());
			newOfficer.setId(0l);

			if (!existingOfficers.contains(newOfficer)) {
				existingOfficers.add(newOfficer);
			}
		}
	}

	/**
	 * To prepare the set of facility relationships before the update process.
	 * Logic include - <li>get the new facility relationships from SI xml <li>
	 * prepare the new objects by assigning values to it <li>replace all the old
	 * facility relationships of the updating facility master with the new
	 * facility relationships
	 * 
	 * @param master - updating facility master
	 * @param workingRelations - new facility relationships from xml
	 * @param className - type of facility relationships, i.e. actual or staging
	 * @param cifSource Source of the CIF
	 * @param source source of the information coming from
	 */
	private void prepareRelationshipForPersistency(FacilityMaster master, Collection workingRelations,
			String className, String cifSource, String source) {
		if (workingRelations == null || workingRelations.isEmpty()) {
			return;
		}

		Set existingRelations = master.getRelationshipSet();

		Collection replicatedRelationship = ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				workingRelations, new String[] { "facilityMasterId", "id" }, new Class[] { String.class,
						StandardCode.class });

		for (Iterator newRelsIter = replicatedRelationship.iterator(); newRelsIter.hasNext();) {
			FacilityRelationship newRelation = (FacilityRelationship) newRelsIter.next();

			if (newRelation.getCMSFacilityRelationshipID() > 0) {
				for (Iterator itr = existingRelations.iterator(); itr.hasNext();) {
					FacilityRelationship existingRelationship = (FacilityRelationship) itr.next();

					if ((IEaiConstant.ACTUAL_FACILITY_RELATIONSHIP.equals(className) && existingRelationship.getId() == newRelation
							.getCMSFacilityRelationshipID())
							|| (IEaiConstant.STAGE_FACILITY_RELATIONSHIP.equals(className) && existingRelationship
									.getCMSFacilityRelationshipID() == newRelation.getCMSFacilityRelationshipID())) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(source, FacilityRelationship.class.getName()));
						limitHelper.copyVariationProperties(newRelation, existingRelationship, copyingProperties);
					}
				}
			}
			else {
				MainProfile mainprofile = getCustomerDao().searchMainProfileByCIFAndCIFSource(newRelation.getCIFId(),
						cifSource);

				if (mainprofile == null) {
					throw new NoSuchCustomerException(newRelation.getCIFId(), cifSource);
				}

				newRelation.setFacilityMasterId(master.getId());
				newRelation.setId(0l);
				newRelation.setCustomerName(mainprofile.getCustomerNameShort());
				newRelation.setMainProfileID(new Long(mainprofile.getCmsId()));
				newRelation.setCurrencyCode(master.getFacilityCurrencyCode());

				if (!existingRelations.contains(newRelation)) {
					existingRelations.add(newRelation);
				}
			}
		}
	}

	private void prepareFacilityMessages(FacilityMaster updatingFacilityMaster, Collection workingFacilityMessages,
			String entityActualFacilityMessage, String source) {
		if (workingFacilityMessages == null || workingFacilityMessages.isEmpty()) {
			return;
		}

		Set existingFacilityMessages = updatingFacilityMaster.getFacilityMessages();

		Collection replicatedFacilityMessages = ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				workingFacilityMessages, new String[] { "cmsFacilityMasterId", "id" }, new Class[] { String.class,
						StandardCode.class, Date.class });

		for (Iterator itr = replicatedFacilityMessages.iterator(); itr.hasNext();) {
			FacilityMessage replicatedFacilityMessage = (FacilityMessage) itr.next();

			if (replicatedFacilityMessage.getCmsFacilityMessageId() != null
					&& replicatedFacilityMessage.getCmsFacilityMessageId().longValue() > 0) {
				for (Iterator itrExisting = existingFacilityMessages.iterator(); itrExisting.hasNext();) {
					FacilityMessage existingMessage = (FacilityMessage) itrExisting.next();

					if ((IEaiConstant.ENTITY_ACTUAL_FACILITY_MESSAGE.equals(entityActualFacilityMessage) && existingMessage
							.getId().equals(replicatedFacilityMessage.getCmsFacilityMessageId()))
							|| (IEaiConstant.ENTITY_STAGE_FACILITY_MESSAGE.equals(entityActualFacilityMessage) && existingMessage
									.getCmsReferenceId().equals(replicatedFacilityMessage.getCmsFacilityMessageId()))) {
						List copyingProperties = (List) getVariationProperties().get(
								new VariationPropertiesKey(source, FacilityMessage.class.getName()));
						limitHelper.copyVariationProperties(replicatedFacilityMessage, existingMessage,
								copyingProperties);
					}
				}
			}
			else {
				replicatedFacilityMessage.setCmsFacilityMasterId(new Long(updatingFacilityMaster.getId()));
				replicatedFacilityMessage.setId(null);

				if (!existingFacilityMessages.contains(replicatedFacilityMessage)) {
					existingFacilityMessages.add(replicatedFacilityMessage);
				}
			}
		}
	}

	private void prepareFacilityIncrementalsForPersistency(FacilityMaster master, Collection workingFacilityIncrementals) {
		if (workingFacilityIncrementals == null || workingFacilityIncrementals.isEmpty()) {
			return;
		}

		Set existingFacilityIncrementals = master.getFacilityIncrementals();

		Collection replicatedFacilityIncrementals = ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				workingFacilityIncrementals, new String[] { "cmsfacilityMasterId", "id" }, new Class[] { String.class,
						StandardCode.class });

		for (Iterator itr = replicatedFacilityIncrementals.iterator(); itr.hasNext();) {
			FacilityIncremental incremental = (FacilityIncremental) itr.next();
			incremental.setCmsFacilityMasterId(new Long(master.getId()));
			incremental.setId(null);

			if (!existingFacilityIncrementals.contains(incremental)) {
				existingFacilityIncrementals.add(incremental);
			}
		}
	}

	private void prepareFacilityReductionsForPersistency(FacilityMaster master, Collection workingFacilityReductions) {
		if (workingFacilityReductions == null || workingFacilityReductions.isEmpty()) {
			return;
		}

		Set existingFacilityReductions = master.getFacilityReductions();

		Vector replicatedFacilityReductions = new Vector(ReplicateUtils
				.replicateCollectionObjectWithSpecifiedImplClass(workingFacilityReductions, new String[] {
						"cmsfacilityMasterId", "id" }, new Class[] { String.class, StandardCode.class }));

		for (Iterator itr = replicatedFacilityReductions.iterator(); itr.hasNext();) {
			FacilityReduction reduction = (FacilityReduction) itr.next();
			reduction.setCmsFacilityMasterId(new Long(master.getId()));
			reduction.setId(null);

			if (!existingFacilityReductions.contains(reduction)) {
				existingFacilityReductions.add(reduction);
			}
		}
	}
}
