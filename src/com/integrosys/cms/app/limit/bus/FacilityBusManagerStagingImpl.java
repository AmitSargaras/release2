package com.integrosys.cms.app.limit.bus;

import java.util.Set;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;

/**
 * Implementation of {@link IFacilityBusManager} to be use to interface with
 * object in staging table.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 */
public class FacilityBusManagerStagingImpl extends AbstractFacilityBusManager {

	public String getFacilityMasterEntityName() {
		return IFacilityDao.STAGE_FACILITY_MASTER;
	}

	public IFacilityMaster createFacilityMaster(IFacilityMaster facilityMaster) {
		Long key = (Long) getFacilityDao().createFacilityMaster(getFacilityMasterEntityName(), facilityMaster);
		facilityMaster = getFacilityDao().retrieveFacilityMasterByPrimaryKey(getFacilityMasterEntityName(), key);
		facilityMaster = updateFacilityReferenceId(facilityMaster);

		return getFacilityDao().updateFacilityMaster(getFacilityMasterEntityName(), facilityMaster);
	}

	public IFacilityMaster updateFacilityMaster(IFacilityMaster facilityMaster) {
		facilityMaster = updateFacilityReferenceId(facilityMaster);

		return getFacilityDao().updateFacilityMaster(getFacilityMasterEntityName(), facilityMaster);
	}

	// copy primary key to reference id
	protected IFacilityMaster updateFacilityReferenceId(IFacilityMaster updatedFacilityMaster) {

		Set updateFacilityInsuranceWithCmsRefIdSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(
				updatedFacilityMaster.getFacilityInsuranceSet(), "cmsRefId", Long.class, "id");

		if (updatedFacilityMaster.getFacilityInsuranceSet() != null) {
			updatedFacilityMaster.getFacilityInsuranceSet().clear();
		}
		if (updateFacilityInsuranceWithCmsRefIdSet != null) {
			updatedFacilityMaster.getFacilityInsuranceSet().addAll(updateFacilityInsuranceWithCmsRefIdSet);
		}

		Set updateFacilityOfficerWithCmsRefIdSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(
				updatedFacilityMaster.getFacilityOfficerSet(), "cmsRefId", Long.class, "id");
		if (updatedFacilityMaster.getFacilityOfficerSet() != null) {
			updatedFacilityMaster.getFacilityOfficerSet().clear();
		}

		if (updateFacilityOfficerWithCmsRefIdSet != null) {
			updatedFacilityMaster.getFacilityOfficerSet().addAll(updateFacilityOfficerWithCmsRefIdSet);
		}

		Set updateFacilityRelationshipWithCmsRefIdSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(
				updatedFacilityMaster.getFacilityRelationshipSet(), "cmsRefId", Long.class, "id");
		if (updatedFacilityMaster.getFacilityRelationshipSet() != null) {
			updatedFacilityMaster.getFacilityRelationshipSet().clear();
		}

		if (updateFacilityRelationshipWithCmsRefIdSet != null) {
			updatedFacilityMaster.getFacilityRelationshipSet().addAll(updateFacilityRelationshipWithCmsRefIdSet);
		}

		Set updateFacilityMultiTierFinWithCmsRefIdSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(
				updatedFacilityMaster.getFacilityMultiTierFinancingSet(), "cmsRefId", Long.class, "id");
		if (updatedFacilityMaster.getFacilityMultiTierFinancingSet() != null) {
			updatedFacilityMaster.getFacilityMultiTierFinancingSet().clear();
		}

		if (updateFacilityMultiTierFinWithCmsRefIdSet != null) {
			updatedFacilityMaster.getFacilityMultiTierFinancingSet().addAll(updateFacilityMultiTierFinWithCmsRefIdSet);
		}

		return updatedFacilityMaster;
		// return updateFacilityMaster(updatedFacilityMaster);
	}

	public IFacilityMaster updateToWorkingCopy(IFacilityMaster workingCopy, IFacilityMaster imageCopy) {
		throw new IllegalStateException("'updateToWorkingCopy' not supposed to be implemented by staging bus manager.");
	}
}