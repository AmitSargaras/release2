package com.integrosys.cms.app.limit.bus;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

public class FacilityMasterReplicationUtils {
	public static IFacilityMaster replicateFacilityMasterForCreateStagingCopy(IFacilityMaster facilityMaster) {
		Set replicatedFacilityInsuranceSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				facilityMaster.getFacilityInsuranceSet(), new String[] { "id" });
		Set replicatedFacilityOfficerSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				facilityMaster.getFacilityOfficerSet(), new String[] { "id" });
		Set replicatedFacilityMultiTierFinSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				facilityMaster.getFacilityMultiTierFinancingSet(), new String[] { "id" });
		Set replicatedFacilityIncrementalSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				facilityMaster.getFacilityIncrementals(), new String[] { "id"});
		Set replicatedFacilityReductionSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				facilityMaster.getFacilityReductions(), new String[] { "id"});
		
		Set replicatedRelationshipSet = new HashSet();
		if (facilityMaster.getFacilityRelationshipSet() != null) {
			Iterator iter = facilityMaster.getFacilityRelationshipSet().iterator();
			IFacilityRelationship[] replicatedFacilityRelationship = new OBFacilityRelationship[facilityMaster
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
				replicatedRelationshipSet.add(replicatedFacilityRelationship[i]);
				i++;
			}
		}

		IFacilityBnmCodes facilityBnmCodes = (IFacilityBnmCodes) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityBnmCodes(), new String[] { "" });
		IFacilityGeneral facilityGeneral = (IFacilityGeneral) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityGeneral(), new String[] { "" });
		IFacilityFeeCharge facilityFeeCharge = (IFacilityFeeCharge) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityFeeCharge(), new String[] { "" });
		IFacilityInterest facilityInterest = (IFacilityInterest) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityInterest(), new String[] { "" });
		IFacilityPayment facilityPayment = (IFacilityPayment) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityPayment(), new String[] { "" });
		
		IFacilityIslamicMaster facilityIslamicMaster = null;
		if (facilityMaster.getFacilityIslamicMaster() != null) {
			facilityIslamicMaster = (IFacilityIslamicMaster) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityIslamicMaster(), new String[] { "" });
		}
		
		IFacilityIslamicBbaVariPackage facilityIslamicBbaVariPackage = null;
		if (facilityMaster.getFacilityIslamicBbaVariPackage() != null) {
			facilityIslamicBbaVariPackage = (IFacilityIslamicBbaVariPackage) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityIslamicBbaVariPackage(), new String[] { "" });
		}
		
		OBFacilityIslamicRentalRenewal facilityIslamicRentalRenewal = null;
		if (facilityMaster.getFacilityIslamicRentalRenewal() != null) {
			facilityIslamicRentalRenewal = (OBFacilityIslamicRentalRenewal) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityIslamicRentalRenewal(), new String[] { "" });
		}
		
		OBFacilityIslamicSecurityDeposit facilityIslamicSecurityDeposit = null;
		if (facilityMaster.getFacilityIslamicSecurityDeposit() != null) {
			facilityIslamicSecurityDeposit = (OBFacilityIslamicSecurityDeposit) ReplicateUtils.replicateObject(facilityMaster
				.getFacilityIslamicSecurityDeposit(), new String[] { "" });
		}
		
		ILimit limit = (ILimit) ReplicateUtils.replicateObject(facilityMaster.getLimit(), new String[] { "" });
		IFacilityMaster replicatedFacilityMaster = (IFacilityMaster) ReplicateUtils.replicateObject(facilityMaster,
				new String[] { "id" });

        //Default facilityIslamicMaster.FullRelPft12Method to false if null
        if (facilityIslamicMaster != null) {
            if (facilityIslamicMaster.getFullRelPft12Method() == null) {
                facilityIslamicMaster.setFullRelPft12Method(new Boolean(false));
            }
        }

        replicatedFacilityMaster.setFacilityInsuranceSet(replicatedFacilityInsuranceSet);
		replicatedFacilityMaster.setFacilityOfficerSet(replicatedFacilityOfficerSet);
		replicatedFacilityMaster.setFacilityRelationshipSet(replicatedRelationshipSet);
		replicatedFacilityMaster.setFacilityBnmCodes(facilityBnmCodes);
		replicatedFacilityMaster.setFacilityGeneral(facilityGeneral);
		replicatedFacilityMaster.setFacilityFeeCharge(facilityFeeCharge);
		replicatedFacilityMaster.setFacilityInterest(facilityInterest);
		replicatedFacilityMaster.setFacilityInterest(facilityInterest);
		replicatedFacilityMaster.setFacilityPayment(facilityPayment);
		replicatedFacilityMaster.setFacilityIslamicMaster(facilityIslamicMaster);
		replicatedFacilityMaster.setFacilityIslamicBbaVariPackage(facilityIslamicBbaVariPackage);
		replicatedFacilityMaster.setFacilityMultiTierFinancingSet(replicatedFacilityMultiTierFinSet);
		replicatedFacilityMaster.setFacilityIslamicRentalRenewal(facilityIslamicRentalRenewal);
		replicatedFacilityMaster.setFacilityIslamicSecurityDeposit(facilityIslamicSecurityDeposit);
		replicatedFacilityMaster.setFacilityIncrementals(replicatedFacilityIncrementalSet);
		replicatedFacilityMaster.setFacilityReductions(replicatedFacilityReductionSet);
		
		replicatedFacilityMaster.setLimit(limit);

		return replicatedFacilityMaster;
	}
}
