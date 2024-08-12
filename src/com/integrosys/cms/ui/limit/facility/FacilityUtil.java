package com.integrosys.cms.ui.limit.facility;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.limit.bus.OBFacilityFeeCharge;
import com.integrosys.cms.app.limit.bus.OBFacilityGeneral;
import com.integrosys.cms.app.limit.bus.OBFacilityInterest;
import com.integrosys.cms.app.limit.bus.OBFacilityPayment;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.ui.limit.facility.main.BNMCodesObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.BbaVariPackageObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.FacilityIslamicMasterObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.FacilityMasterObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.InsuranceObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.MultiTierFinanceObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator;
import com.integrosys.cms.ui.limit.facility.main.RelationshipObjectValidator;
import com.integrosys.cms.ui.limit.facility.multitierfin.FacilityMultiTierFinComparator;

public class FacilityUtil {
	public static IFacilityMaster createObjectsInsideFacilityMaster(IFacilityMaster facilityMaster) {
		if (facilityMaster.getLimit() == null) {
			facilityMaster.setLimit(new OBLimit());
			if (facilityMaster.getLimit().getBookingLocation() == null) {
				facilityMaster.getLimit().setBookingLocation(new OBBookingLocation());
			}
		}
		if (facilityMaster.getFacilityGeneral() == null) {
			facilityMaster.setFacilityGeneral(new OBFacilityGeneral());
		}
		if (facilityMaster.getFacilityInterest() == null) {
			facilityMaster.setFacilityInterest(new OBFacilityInterest());
		}
		if (facilityMaster.getFacilityFeeCharge() == null) {
			facilityMaster.setFacilityFeeCharge(new OBFacilityFeeCharge());
		}
		if (facilityMaster.getFacilityPayment() == null) {
			facilityMaster.setFacilityPayment(new OBFacilityPayment());
		}
		/*
		if (facilityMaster.getFacilityBnmCodes() == null) {
			facilityMaster.setFacilityBnmCodes(new OBFacilityBnmCodes());
		}
		if (facilityMaster.getFacilityIslamicMaster() == null) {
			facilityMaster.setFacilityIslamicMaster(new OBFacilityIslamicMaster());
		}
		if (facilityMaster.getFacilityIslamicBbaVariPackage() == null) {
			facilityMaster.setFacilityIslamicBbaVariPackage(new OBFacilityIslamicBbaVariPackage());
		}*/
		return facilityMaster;
	}

	public static Set filterDeletedOfficer(Set setOfficers) {
		if (setOfficers == null)
			return null;
		Set temp = new HashSet();
		Iterator iter = setOfficers.iterator();
		while (iter.hasNext()) {
			IFacilityOfficer facilityOfficer = (IFacilityOfficer) iter.next();
			if (!"D".equals(facilityOfficer.getStatus())) {
				temp.add(facilityOfficer);
			}
		}
		return temp;
	}

	public static Set filterDeletedRelationship(Set setRelationships) {
		if (setRelationships == null)
			return null;
		Set temp = new HashSet();
		Iterator iter = setRelationships.iterator();
		while (iter.hasNext()) {
			IFacilityRelationship facilityRelationship = (IFacilityRelationship) iter.next();
			if (!"D".equals(facilityRelationship.getStatus())) {
				temp.add(facilityRelationship);
			}
		}
		return temp;
	}

	public static Set filterDeletedMultiTierFin(Set setMultiTierFin) {
		if (setMultiTierFin == null)
			return null;
		Set temp = new TreeSet(new FacilityMultiTierFinComparator());
		Iterator iter = setMultiTierFin.iterator();
		while (iter.hasNext()) {
			IFacilityMultiTierFinancing multiTierFin = (IFacilityMultiTierFinancing) iter.next();
			if (!"D".equals(multiTierFin.getStatus())) {
				temp.add(multiTierFin);
			}
		}
		return temp;
	}

	public static boolean CheckIsStpAllowed(IFacilityMaster facilityMasterObj, String aaType, String limitProductType,
			String limitDealerProductFlag, long cmsSubProfileId, String limitConceptCode, String facilityAccountType,
            String aaLawType) {
		// Andy Wong, 20 Jan 2009: use Stp validator to determine StpReady flag,
		// comment all redundant checking
		ActionErrors facilityMasterErrorMessages = FacilityMasterObjectValidator.validateObject(facilityMasterObj,
				aaType, limitProductType, limitDealerProductFlag, aaLawType, facilityAccountType);
		ActionErrors bnmCodesErrorMessages = BNMCodesObjectValidator.validateObject(facilityMasterObj, cmsSubProfileId);
		ActionErrors officerErrorMessages = OfficerObjectValidator.validateObject(facilityMasterObj
				.getFacilityOfficerSet());
		ActionErrors relationshipErrorMessages = RelationshipObjectValidator.validateObject(facilityMasterObj
				.getFacilityRelationshipSet());
		ActionErrors insuranceErrorMessages = InsuranceObjectValidator.validateObject(facilityMasterObj
                .getFacilityInsuranceSet());

        ActionErrors facilityIslamicMasterErrorMessages = null;
        ActionErrors bbaVariPackageErrorMessages = null;
        ActionErrors multiTierFinanceErrorMessages = null;

        if (aaLawType.equals(ICMSConstant.AA_LAW_TYPE_ISLAM)) {
            if (facilityMasterObj.getFacilityIslamicMaster() != null) {
                facilityIslamicMasterErrorMessages = FacilityIslamicMasterObjectValidator.validateObject(
                        facilityMasterObj, limitConceptCode, facilityAccountType, limitProductType);
            }

            if (facilityMasterObj.getFacilityIslamicBbaVariPackage() != null) {
                bbaVariPackageErrorMessages = BbaVariPackageObjectValidator.validateObject(facilityMasterObj);
            }

            if (facilityMasterObj.getFacilityMultiTierFinancingSet() != null
                    && facilityMasterObj.getFacilityMultiTierFinancingSet().size() > 0) {
                multiTierFinanceErrorMessages = MultiTierFinanceObjectValidator.validateObject(facilityMasterObj);
            }
        }

        if (facilityMasterErrorMessages != null) {
			return false;
		}

		if (bnmCodesErrorMessages != null) {
			return false;
		}

		if (officerErrorMessages != null) {
			return false;
		}

		if (relationshipErrorMessages != null) {
			return false;
		}

		if (insuranceErrorMessages != null) {
			return false;
		}

        if (facilityIslamicMasterErrorMessages != null) {
			return false;
		}

		if (bbaVariPackageErrorMessages != null) {
			return false;
		}

		if (multiTierFinanceErrorMessages != null) {
			return false;
		}

        return true;

		// if (facilityMasterObj != null && facilityMasterObj.getLimit() != null
		// && facilityMasterObj.getFacilityGeneral() != null &&
		// facilityMasterObj.getFacilityInterest() != null
		// && facilityMasterObj.getFacilityFeeCharge() != null &&
		// facilityMasterObj.getFacilityPayment() != null
		// && facilityMasterObj.getFacilityBnmCodes() != null &&
		// facilityMasterObj.getFacilityOfficerSet() != null
		// && facilityMasterObj.getFacilityRelationshipSet() != null) {
		//
		// // limit
		// ILimit limit = facilityMasterObj.getLimit();
		// if (ICMSConstant.LONG_INVALID_VALUE != limit.getLimitID()
		// && StringUtils.isNotBlank(limit.getFacilityCode())
		// // &&
		// //
		// StringUtils.isNotBlank(limit.getBookingLocation().getOrganisationCode())
		// && StringUtils.isNotBlank(limit.getProductDesc()) &&
		// limit.getApprovedLimitAmount() != null
		// && limit.getLimitTenor() != null &&
		// StringUtils.isNotBlank(limit.getLimitTenorUnit())) {
		// // do nothing, continue with the value checking
		// }
		// else {
		// return false;
		// }
		//
		// if (facilityMasterObj.getDrawingLimitAmount() != null
		// &&
		// StringUtils.isNotBlank(facilityMasterObj.getApplicationSourceEntryCode())
		// &&
		// StringUtils.isNotBlank(facilityMasterObj.getFacilityCurrencyCode())
		// && facilityMasterObj.getRevolvingIndicator() != null
		// &&
		// StringUtils.isNotBlank(facilityMasterObj.getDepartmentCodeEntryCode()))
		// {
		// // do nothing, continue with the value checking
		// }
		// else {
		// return false;
		// }
		//
		// if ((ICMSConstant.APPLICATION_TYPE_HP.equals(aaType) &&
		// StringUtils.isBlank(facilityMasterObj
		// .getDealerNumberOrLppCodeEntryCode()))
		// || (!ICMSConstant.APPLICATION_TYPE_HP.equals(aaType) &&
		// StringUtils.isBlank(facilityMasterObj
		// .getAcfNo()))) {
		// return false;
		// }
		//
		// // facility general
		// IFacilityGeneral facilityGeneral =
		// facilityMasterObj.getFacilityGeneral();
		// if (facilityGeneral.getFinancedAmount() != null &&
		// facilityGeneral.getApplicationDate() != null
		// && facilityGeneral.getInstallmentAmount() != null
		// && facilityGeneral.getFinalPaymentAmount() != null
		// && StringUtils.isNotBlank(facilityGeneral.getLoanPurposeEntryCode())
		// && facilityGeneral.getOfferAcceptedDate() != null &&
		// facilityGeneral.getOfferDate() != null
		// &&
		// StringUtils.isNotBlank(facilityGeneral.getPersonApprovedEntryCode())
		// && facilityGeneral.getApprovedDate() != null
		// && StringUtils.isNotBlank(facilityGeneral.getCarEntryCode())
		// && StringUtils.isNotBlank(facilityGeneral.getOfficerEntryCode())
		// && StringUtils.isNotBlank(facilityGeneral.getLimitStatusEntryCode())
		// &&
		// StringUtils.isNotBlank(facilityGeneral.getFacilityStatusEntryCode())
		// && facilityGeneral.getCarCodeFlag() != null) {
		// // do nothing, continue with the value checking
		// }
		// else {
		// return false;
		// }
		//
		// // the EcofVarianceCode was changed from char to String so check not
		// // null instead of '\u0000'
		// if
		// ((StringUtils.isNotBlank(facilityGeneral.getCancelOrRejectEntryCode())
		// && facilityGeneral
		// .getCancelOrRejectDate() == null)
		// || (facilityMasterObj.getEffectiveCostOfFund().equals(new
		// Boolean(true)) && facilityMasterObj
		// .getEcofRate() == null)
		// || (facilityMasterObj.getEffectiveCostOfFund().equals(new
		// Boolean(true)) && facilityMasterObj
		// .getEcofVariance() == null)
		// || (facilityMasterObj.getEffectiveCostOfFund().equals(new
		// Boolean(true)) && facilityMasterObj
		// .getEcofVarianceCode() != null)) {
		// return false;
		// }
		//
		// // facility fee charge
		// IFacilityFeeCharge facilityFeeCharge =
		// facilityMasterObj.getFacilityFeeCharge();
		// if (facilityFeeCharge.getCommissionRate() != null
		// && facilityFeeCharge.getCommissionRate().compareTo(new Double(0)) > 0
		// && (facilityFeeCharge.getCommissionFeesAmount() == null
		// ||
		// Double.compare(facilityMasterObj.getFacilityFeeCharge().getCommissionFeesAmount()
		// .getAmount(), 0) > 0 || StringUtils.isBlank(facilityFeeCharge
		// .getCommissionBasisEntryCode()))) {
		// return false;
		// }
		//
		// // facility interest
		// IFacilityInterest facilityInterest =
		// facilityMasterObj.getFacilityInterest();
		// if
		// (StringUtils.isNotBlank(facilityInterest.getInterestRateTypeEntryCode()))
		// {
		// Double spread = facilityMasterObj.getFacilityInterest().getSpread();
		// String spreadStr = null;
		// if (spread != null) {
		// spreadStr = String.valueOf(spread);
		// }
		// if (StringUtils.isBlank(spreadStr)
		// ||
		// StringUtils.isBlank((facilityMasterObj.getFacilityInterest().getSpreadSign()
		// + "").trim())) {
		// return false;
		// }
		// }
		//
		// // facility officer
		// Set setFacilityOfficer = facilityMasterObj.getFacilityOfficerSet();
		// for (Iterator iter = setFacilityOfficer.iterator(); iter.hasNext();)
		// {
		// IFacilityOfficer facilityOfficer = (IFacilityOfficer) iter.next();
		// if
		// (StringUtils.isNotBlank(facilityOfficer.getRelationshipCodeEntryCode())
		// && StringUtils.isNotBlank(facilityOfficer.getOfficerTypeEntryCode())
		// && StringUtils.isNotBlank(facilityOfficer.getOfficerEntryCode())) {
		// // do nothing, continue with the value checking
		// }
		// else {
		// return false;
		// }
		// }
		//
		// // facility relationship
		// Set setFacilityRelationship =
		// facilityMasterObj.getFacilityRelationshipSet();
		// for (Iterator iter = setFacilityRelationship.iterator();
		// iter.hasNext();) {
		// IFacilityRelationship facilityRelationship = (IFacilityRelationship)
		// iter.next();
		// if
		// (StringUtils.isNotBlank(facilityRelationship.getAccountRelationshipEntryCode()))
		// {
		// // do nothing, continue with the value checking
		// }
		// else {
		// return false;
		// }
		// }
		//
		// // facility bnmcodes
		// IFacilityBnmCodes facilityBnmCodes =
		// facilityMasterObj.getFacilityBnmCodes();
		// if
		// (StringUtils.isNotBlank(facilityBnmCodes.getIndustryCodeEntryCode())
		// && StringUtils.isNotBlank(facilityBnmCodes.getSectorCodeEntryCode())
		// && StringUtils.isNotBlank(facilityBnmCodes.getStateCodeEntryCode())
		// &&
		// StringUtils.isNotBlank(facilityBnmCodes.getBumiOrNrccCodeEntryCode())
		// &&
		// StringUtils.isNotBlank(facilityBnmCodes.getSmallScaleCodeCodeEntryCode())
		// &&
		// StringUtils.isNotBlank(facilityBnmCodes.getRelationshipCodeEntryCode())
		// && facilityBnmCodes.getExemptedCodeIndicator() != null
		// &&
		// StringUtils.isNotBlank(facilityBnmCodes.getPurposeCodeEntryCode())) {
		// // do nothing, if pass this checking then should return true
		// }
		// else {
		// return false;
		// }
		// if
		// (String.valueOf(facilityBnmCodes.getExemptedCodeIndicator()).equals("Y"))
		// {
		// if (StringUtils.isBlank(facilityBnmCodes.getExemptedCodeEntryCode()))
		// {
		// return false;
		// }
		// }
		//
		// return true;
		// }
		// return false;
	}
}
