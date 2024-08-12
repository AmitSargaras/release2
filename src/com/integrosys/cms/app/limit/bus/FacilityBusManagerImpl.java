package com.integrosys.cms.app.limit.bus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.Validate;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

/**
 * Implementation of {@link IFacilityBusManager} to be use to interface with
 * object in actual table.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class FacilityBusManagerImpl extends AbstractFacilityBusManager {

	public String getFacilityMasterEntityName() {
		return IFacilityDao.ACTUAL_FACILITY_MASTER;
	}

	public IFacilityMaster updateToWorkingCopy(IFacilityMaster workingCopy, IFacilityMaster imageCopy) {
		Validate.notNull(imageCopy, "'imageCopy' must not be null");

		boolean isNullWorkingCopy = (workingCopy == null);

		// replicate association entities first.
		Set replicatedFacilityInsuranceSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getFacilityInsuranceSet(), new String[] { "id" });

		Set replicatedOfficerSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(imageCopy
				.getFacilityOfficerSet(), new String[] { "id" });

		/*
		 * Set replicatedFacilityRelationshipSet = (Set)
		 * ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
		 * imageCopy.getFacilityRelationshipSet(), new String[] { "id" });
		 */

		Set replicatedFacilityRelationshipSet = new HashSet();
		if (imageCopy.getFacilityRelationshipSet() != null) {
			Iterator iter = imageCopy.getFacilityRelationshipSet().iterator();
			IFacilityRelationship[] replicatedFacilityRelationship = new OBFacilityRelationship[imageCopy
					.getFacilityRelationshipSet().size()];
			int i = 0;
			while (iter.hasNext()) {
				IFacilityRelationship facilityRelationship = (IFacilityRelationship) iter.next();
				ICMSLegalEntity cmsLegalEntity = null;
				if (facilityRelationship.getCmsLegalEntity() != null) {
					cmsLegalEntity = (ICMSLegalEntity) ReplicateUtils.replicateObject(facilityRelationship
							.getCmsLegalEntity(), new String[] { "LEID" });
				}
				replicatedFacilityRelationship[i] = (IFacilityRelationship) ReplicateUtils.replicateObject(
						facilityRelationship, new String[] { "id" });
				replicatedFacilityRelationship[i].setCmsLegalEntity(cmsLegalEntity);
				replicatedFacilityRelationshipSet.add(replicatedFacilityRelationship[i]);
				i++;
			}
		}

		Set replicatedFacilityMultiTierFinancingSet = (Set) ReplicateUtils
				.replicateCollectionObjectWithSpecifiedImplClass(imageCopy.getFacilityMultiTierFinancingSet(),
						new String[] { "id" });

		Set replicatedFacilityIncrementalSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(imageCopy
				.getFacilityIncrementals(), new String[] { "id" });
		
		Set replicatedFacilityReductionSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(imageCopy
				.getFacilityReductions(), new String[] { "id" });
		
		if (isNullWorkingCopy) {
			workingCopy = new OBFacilityMaster();
			workingCopy.setFacilityInsuranceSet(replicatedFacilityInsuranceSet);
			workingCopy.setFacilityOfficerSet(replicatedOfficerSet);
			workingCopy.setFacilityRelationshipSet(replicatedFacilityRelationshipSet);
			workingCopy.setLimit(imageCopy.getLimit());
			workingCopy.setFacilityMultiTierFinancingSet(replicatedFacilityMultiTierFinancingSet);
			workingCopy.setFacilityIncrementals(replicatedFacilityIncrementalSet);
			workingCopy.setFacilityReductions(replicatedFacilityReductionSet);
			
			workingCopy = createFacilityMaster(workingCopy);
			logger.info("working copy is null, using image copy facility master, id [" + imageCopy.getId()
					+ "] to create, created id [" + workingCopy.getId() + "]");
		}
		else {

			// do the merging from staging to actual associations
			Set mergedFacilityInsuranceSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getFacilityInsuranceSet(), replicatedFacilityInsuranceSet, new String[] { "cmsRefId" },
					new String[] { "id" });

			if (workingCopy.getFacilityInsuranceSet() != null) {
				workingCopy.getFacilityInsuranceSet().clear();
			}

			if (mergedFacilityInsuranceSet != null) {
				workingCopy.getFacilityInsuranceSet().addAll(mergedFacilityInsuranceSet);
			}

			Set mergedOfficerSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(workingCopy
					.getFacilityOfficerSet(), replicatedOfficerSet, new String[] { "cmsRefId" }, new String[] { "id" });

			if (workingCopy.getFacilityOfficerSet() != null) {
				workingCopy.getFacilityOfficerSet().clear();
			}

			if (mergedOfficerSet != null) {
				workingCopy.getFacilityOfficerSet().addAll(mergedOfficerSet);
			}

			Set mergedFacilityRelationshipSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getFacilityRelationshipSet(), replicatedFacilityRelationshipSet,
					new String[] { "cmsRefId" }, new String[] { "id" });

			if (workingCopy.getFacilityRelationshipSet() != null) {
				workingCopy.getFacilityRelationshipSet().clear();
			}

			if (mergedFacilityRelationshipSet != null) {
				workingCopy.getFacilityRelationshipSet().addAll(mergedFacilityRelationshipSet);
			}

			Set mergedFacilityMultiTierFinSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getFacilityMultiTierFinancingSet(), replicatedFacilityMultiTierFinancingSet,
					new String[] { "cmsRefId" }, new String[] { "id" });

			if (workingCopy.getFacilityMultiTierFinancingSet() != null) {
				workingCopy.getFacilityMultiTierFinancingSet().clear();
			}

			if (mergedFacilityMultiTierFinSet != null) {
				workingCopy.getFacilityMultiTierFinancingSet().addAll(mergedFacilityMultiTierFinSet);
			}
			
			Set mergedFacilityIncrementalSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getFacilityIncrementals(), replicatedFacilityIncrementalSet,
					new String[] { "cmsReferenceId" }, new String[] { "id" });
			
			if (workingCopy.getFacilityIncrementals() != null) {
				workingCopy.getFacilityIncrementals().clear();
			}
			
			if (mergedFacilityIncrementalSet != null) {
				workingCopy.getFacilityIncrementals().addAll(mergedFacilityIncrementalSet);
			}
			
			Set mergedFacilityReductionSet = (Set) EntityAssociationUtils.synchronizeCollectionsByProperties(
					workingCopy.getFacilityReductions(), replicatedFacilityReductionSet,
					new String[] { "cmsReferenceId" }, new String[] { "id" });
			
			if (workingCopy.getFacilityReductions() != null) {
				workingCopy.getFacilityReductions().clear();
			}
			
			if (mergedFacilityReductionSet != null) {
				workingCopy.getFacilityReductions().addAll(mergedFacilityReductionSet);
			}
		}

		// do the one-to-one associations
		String[] excludeMethod = new String[] { "FacilityMasterId" };

		if (imageCopy.getFacilityPayment() != null) {
			OBFacilityPayment payment = (workingCopy.getFacilityPayment() == null) ? new OBFacilityPayment()
					: (OBFacilityPayment) workingCopy.getFacilityPayment();

			AccessorUtil.copyValue(imageCopy.getFacilityPayment(), payment, excludeMethod);
			workingCopy.setFacilityPayment(payment);
		}
		else {
			workingCopy.setFacilityPayment(null);
		}

		if (imageCopy.getFacilityBnmCodes() != null) {
			OBFacilityBnmCodes bnmCodes = (workingCopy.getFacilityBnmCodes() == null) ? new OBFacilityBnmCodes()
					: (OBFacilityBnmCodes) workingCopy.getFacilityBnmCodes();

			AccessorUtil.copyValue(imageCopy.getFacilityBnmCodes(), bnmCodes, excludeMethod);
			workingCopy.setFacilityBnmCodes(bnmCodes);
		}
		else {
			workingCopy.setFacilityBnmCodes(null);
		}

		if (imageCopy.getFacilityFeeCharge() != null) {
			OBFacilityFeeCharge feeCharge = (workingCopy.getFacilityFeeCharge() == null) ? new OBFacilityFeeCharge()
					: (OBFacilityFeeCharge) workingCopy.getFacilityFeeCharge();

			AccessorUtil.copyValue(imageCopy.getFacilityFeeCharge(), feeCharge, excludeMethod);
			workingCopy.setFacilityFeeCharge(feeCharge);
		}
		else {
			workingCopy.setFacilityFeeCharge(null);
		}

		if (imageCopy.getFacilityGeneral() != null) {
			OBFacilityGeneral general = (workingCopy.getFacilityGeneral() == null) ? new OBFacilityGeneral()
					: (OBFacilityGeneral) workingCopy.getFacilityGeneral();

			AccessorUtil.copyValue(imageCopy.getFacilityGeneral(), general, excludeMethod);
			workingCopy.setFacilityGeneral(general);
		}
		else {
			workingCopy.setFacilityGeneral(null);
		}

		if (imageCopy.getFacilityInterest() != null) {
			OBFacilityInterest interest = (workingCopy.getFacilityInterest() == null) ? new OBFacilityInterest()
					: (OBFacilityInterest) workingCopy.getFacilityInterest();

			AccessorUtil.copyValue(imageCopy.getFacilityInterest(), interest, excludeMethod);
			workingCopy.setFacilityInterest(interest);
		}
		else {
			workingCopy.setFacilityInterest(null);
		}

		if (imageCopy.getFacilityIslamicMaster() != null) {
			OBFacilityIslamicMaster islamicMaster = (workingCopy.getFacilityIslamicMaster() == null) ? new OBFacilityIslamicMaster()
					: (OBFacilityIslamicMaster) workingCopy.getFacilityIslamicMaster();

			AccessorUtil.copyValue(imageCopy.getFacilityIslamicMaster(), islamicMaster, excludeMethod);
			workingCopy.setFacilityIslamicMaster(islamicMaster);
		}
		else {
			workingCopy.setFacilityIslamicMaster(null);
		}

		if (imageCopy.getFacilityIslamicBbaVariPackage() != null) {
			OBFacilityIslamicBbaVariPackage bba = (workingCopy.getFacilityIslamicBbaVariPackage() == null) ? new OBFacilityIslamicBbaVariPackage()
					: (OBFacilityIslamicBbaVariPackage) workingCopy.getFacilityIslamicBbaVariPackage();

			AccessorUtil.copyValue(imageCopy.getFacilityIslamicBbaVariPackage(), bba, excludeMethod);
			workingCopy.setFacilityIslamicBbaVariPackage(bba);
		}
		else {
			workingCopy.setFacilityIslamicBbaVariPackage(null);
		}

		if (imageCopy.getFacilityIslamicRentalRenewal() != null) {
			OBFacilityIslamicRentalRenewal rentalRenewal = (workingCopy.getFacilityIslamicRentalRenewal() == null) ? new OBFacilityIslamicRentalRenewal()
					: workingCopy.getFacilityIslamicRentalRenewal();
			
			AccessorUtil.copyValue(imageCopy.getFacilityIslamicRentalRenewal(), rentalRenewal, excludeMethod);
			workingCopy.setFacilityIslamicRentalRenewal(rentalRenewal);
		} else {
			workingCopy.setFacilityIslamicRentalRenewal(null);
		}
		
		if (imageCopy.getFacilityIslamicSecurityDeposit() != null) {
			OBFacilityIslamicSecurityDeposit securityDeposit = (workingCopy.getFacilityIslamicSecurityDeposit() == null) ? new OBFacilityIslamicSecurityDeposit()
					: workingCopy.getFacilityIslamicSecurityDeposit();
			
			AccessorUtil.copyValue(imageCopy.getFacilityIslamicSecurityDeposit(), securityDeposit, excludeMethod);
			workingCopy.setFacilityIslamicSecurityDeposit(securityDeposit);
		} else {
			workingCopy.setFacilityIslamicSecurityDeposit(null);
		}
		
		AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "Id", "Limit", "FacilityInsuranceSet",
				"FacilityOfficerSet", "FacilityRelationshipSet", "FacilityPayment", "FacilityBnmCodes",
				"FacilityFeeCharge", "FacilityInterest", "FacilityGeneral", "FacilityMultiTierFinancingSet",
				"FacilityIslamicMaster", "FacilityIslamicBbaVariPackage", "FacilityMessages",
				"FacilityIslamicRentalRenewal", "FacilityIslamicSecurityDeposit", "FacilityIncrementals",
				"FacilityReductions" });
		IFacilityMaster updatedFacilityMaster = updateFacilityMaster(workingCopy);

		return updateFacilityMaster(updatedFacilityMaster);

	}
}
