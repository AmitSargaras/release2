package com.integrosys.cms.ui.limit.facility.main;

import java.util.HashMap;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class FacilityObjectValidatorCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "errorMap", "java.util.Map", SERVICE_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap errorMap = new HashMap();
		ActionErrors errors = new ActionErrors();

		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		String event = (String) map.get("event");

		if (facilityMasterObj != null
				&& (FacilityMainAction.EVENT_PROCESS.equals(event)
						|| FacilityMainAction.EVENT_SUBMIT.equals(event)
						|| FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)
						|| FacilityMainAction.EVENT_NAVIGATE.equals(event)
						|| FacilityMainAction.EVENT_NAVIGATE_WO_FRAME.equals(event)
						// Andy Wong: add these statuses for facility
						// relationship
						|| FacilityMainAction.EVENT_UPDATE.equals(event)
						|| FacilityMainAction.EVENT_UPDATE_WO_FRAME.equals(event)
						|| FacilityMainAction.EVENT_DELETE.equals(event)
						|| FacilityMainAction.EVENT_DELETE_WO_FRAME.equals(event)
						|| FacilityMainAction.EVENT_SAVE.equals(event) || FacilityMainAction.EVENT_SAVE_WO_FRAME
						.equals(event))) {

            String applicationLawType = limitProfile.getApplicationLawType();

            String limitProductType = getFacilityProxy().getProductGroupByProductCode(
					facilityMasterObj.getLimit().getProductDesc());

			String limitDealerProductFlag = getFacilityProxy().getDealerProductFlagByProductCode(
					facilityMasterObj.getLimit().getProductDesc());

			String limitConceptCode = getFacilityProxy().getConceptCodeByProductCode(
					facilityMasterObj.getLimit().getProductDesc());

			String facilityAccountType = getFacilityProxy().getAccountTypeByFacilityCode(
					facilityMasterObj.getLimit().getFacilityCode());

			ActionErrors facilityMasterErrorMessages = FacilityMasterObjectValidator.validateObject(facilityMasterObj,
					limitProfile.getApplicationType(), limitProductType, limitDealerProductFlag,
                    applicationLawType, facilityAccountType);

			ActionErrors bnmCodesErrorMessages = BNMCodesObjectValidator.validateObject(facilityMasterObj, limitProfile
					.getCustomerID());

			ActionErrors officerErrorMessages = OfficerObjectValidator.validateObject(facilityMasterObj
					.getFacilityOfficerSet());

			ActionErrors relationshipErrorMessages = RelationshipObjectValidator.validateObject(facilityMasterObj
					.getFacilityRelationshipSet());

			ActionErrors insuranceErrorMessages = InsuranceObjectValidator.validateObject(facilityMasterObj
					.getFacilityInsuranceSet());

            ActionErrors facilityIslamicMasterErrorMessages = null;
            ActionErrors bbaVariPackageErrorMessages = null;
            ActionErrors multiTierFinanceErrorMessages = null;

            if (applicationLawType.equals(ICMSConstant.AA_LAW_TYPE_ISLAM)) {
                if (facilityMasterObj.getFacilityIslamicMaster() != null) {
                    facilityIslamicMasterErrorMessages = FacilityIslamicMasterObjectValidator.validateObject(
                            facilityMasterObj, limitConceptCode, facilityAccountType, limitProductType);
                }

                if (facilityMasterObj.getFacilityIslamicBbaVariPackage() != null) {
                    bbaVariPackageErrorMessages = BbaVariPackageObjectValidator.validateObject(facilityMasterObj);
                }

                if (facilityMasterObj.getFacilityMultiTierFinancingSet() != null) {
                    multiTierFinanceErrorMessages = MultiTierFinanceObjectValidator.validateObject(facilityMasterObj);
                }
            }

            if (facilityMasterErrorMessages != null) {
				errors.add(facilityMasterErrorMessages);
				errorMap.put("facilityMaster", facilityMasterErrorMessages);
			}

			if (bnmCodesErrorMessages != null) {
				errors.add(bnmCodesErrorMessages);
				errorMap.put("bnmCodes", bnmCodesErrorMessages);
			}

			if (officerErrorMessages != null) {
				errors.add(officerErrorMessages);
				errorMap.put("officer", officerErrorMessages);
			}

			if (relationshipErrorMessages != null) {
				errors.add(relationshipErrorMessages);
				errorMap.put("relationship", relationshipErrorMessages);
			}

			if (insuranceErrorMessages != null) {
				errors.add(insuranceErrorMessages);
				errorMap.put("insurance", insuranceErrorMessages);
			}

			if (facilityIslamicMasterErrorMessages != null) {
				errors.add(facilityIslamicMasterErrorMessages);
				errorMap.put("islamicMaster", facilityIslamicMasterErrorMessages);
			}

			if (bbaVariPackageErrorMessages != null) {
				errors.add(bbaVariPackageErrorMessages);
				errorMap.put("islamicBbaVariPackage", bbaVariPackageErrorMessages);
			}

			if (multiTierFinanceErrorMessages != null) {
				errors.add(multiTierFinanceErrorMessages);
				errorMap.put("multiTierFinancing", multiTierFinanceErrorMessages);
			}

			result.put("errorMap", errorMap);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
