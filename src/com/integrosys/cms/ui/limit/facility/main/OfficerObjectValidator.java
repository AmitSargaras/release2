package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.cms.ui.common.constant.ICategoryEntryConstant;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;
import com.integrosys.cms.app.limit.bus.IFacilityOfficer;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.Iterator;

public class OfficerObjectValidator {
    public static ActionErrors validateObject(Set officerSet) {
        ActionErrors officerErrors = new ActionErrors();

        // validate linkage between officer relationship and officer indicator
        if (officerSet != null){
            for (Iterator iterator = officerSet.iterator(); iterator.hasNext();) {
                IFacilityOfficer officer =  (IFacilityOfficer)iterator.next();

//                DefaultLogger.debug("com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator",
//                        "officer.getRelationshipCodeEntryCode() : " + officer.getRelationshipCodeEntryCode());
//                DefaultLogger.debug("com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator",
//                        "officer.getOfficerTypeEntryCode() : " + officer.getOfficerTypeEntryCode());
                if (!StringUtils.equals(officer.getStatus(), "D")) {
                    if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_RE) &&
                            !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_R, officer.getOfficerTypeEntryCode())) {
                        officerErrors.add("officerErrors", new ActionMessage(
                                "error.officer.type.relationship.code.linkage", ICMSConstant.OFFICER_RELATIONSHIP_RE));
                    }
                    else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_MK) &&
                            !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_M, officer.getOfficerTypeEntryCode())) {
                        officerErrors.add("officerErrors", new ActionMessage(
                                "error.officer.type.relationship.code.linkage", ICMSConstant.OFFICER_RELATIONSHIP_MK));
                    }
                    else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_PR) &&
                            !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_P, officer.getOfficerTypeEntryCode())) {
                        officerErrors.add("officerErrors", new ActionMessage(
                                "error.officer.type.relationship.code.linkage", ICMSConstant.OFFICER_RELATIONSHIP_PR));
                    }
                    else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_EV) &&
                            !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_E, officer.getOfficerTypeEntryCode())) {
                        officerErrors.add("officerErrors", new ActionMessage(
                                "error.officer.type.relationship.code.linkage", ICMSConstant.OFFICER_RELATIONSHIP_EV));
                    }
                    else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_AR) &&
                            !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_A, officer.getOfficerTypeEntryCode())) {
                        officerErrors.add("officerErrors", new ActionMessage(
                                "error.officer.type.relationship.code.linkage",  ICMSConstant.OFFICER_RELATIONSHIP_AR));
                    }
                }
            }
        }

        DefaultLogger.debug(" FacilityOfficer Total Errors", "--------->" + officerErrors.size());
        return officerErrors.size() == 0 ? null : officerErrors;
    }

    /**
     * Method to validate single officer OB for detail page display
     * @param officer
     * @return
     */
    public static ActionErrors validateSingleObject(IFacilityOfficer officer) {
        ActionErrors officerErrors = new ActionErrors();

//        DefaultLogger.debug("com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator",
//                "officer.getRelationshipCodeEntryCode() : " + officer.getRelationshipCodeEntryCode());
//        DefaultLogger.debug("com.integrosys.cms.ui.limit.facility.main.OfficerObjectValidator",
//                "officer.getOfficerTypeEntryCode() : " + officer.getOfficerTypeEntryCode());

//        if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_RE) &&
//                !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_R, officer.getOfficerTypeEntryCode())) {
//            officerErrors.add("officerTypeEntryCode", new ActionMessage(
//                    "error.officer.indicator.relationship.code.linkage", ICMSConstant.OFFICER_INDICATOR_R, ICMSConstant.OFFICER_RELATIONSHIP_RE));
//        }
//        else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_MK) &&
//                !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_M, officer.getOfficerTypeEntryCode())) {
//            officerErrors.add("officerTypeEntryCode", new ActionMessage(
//                    "error.officer.indicator.relationship.code.linkage", ICMSConstant.OFFICER_INDICATOR_M, ICMSConstant.OFFICER_RELATIONSHIP_MK));
//        }
//        else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_PR) &&
//                !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_P, officer.getOfficerTypeEntryCode())) {
//            officerErrors.add("officerTypeEntryCode", new ActionMessage(
//                    "error.officer.indicator.relationship.code.linkage", ICMSConstant.OFFICER_INDICATOR_P, ICMSConstant.OFFICER_RELATIONSHIP_PR));
//        }
//        else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_EV) &&
//                !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_E, officer.getOfficerTypeEntryCode())) {
//            officerErrors.add("officerTypeEntryCode", new ActionMessage(
//                    "error.officer.indicator.relationship.code.linkage", ICMSConstant.OFFICER_INDICATOR_E, ICMSConstant.OFFICER_RELATIONSHIP_EV));
//        }
//        else if (StringUtils.equals(officer.getRelationshipCodeEntryCode(), ICMSConstant.OFFICER_RELATIONSHIP_AR) &&
//                !validateOfficerIndicator(ICMSConstant.OFFICER_INDICATOR_A, officer.getOfficerTypeEntryCode())) {
//            officerErrors.add("officerTypeEntryCode", new ActionMessage(
//                    "error.officer.indicator.relationship.code.linkage", ICMSConstant.OFFICER_INDICATOR_A, ICMSConstant.OFFICER_RELATIONSHIP_AR));
//        }

        return officerErrors.size() == 0 ? null : officerErrors;
    }

    /**
     * Validate officer indicator for officer type
     * @param officerIndicator
     * @param officerType
     * @return
     */
    private static boolean validateOfficerIndicator(String officerIndicator, String officerType){
        if (StringUtils.isNotBlank(officerType)
                && StringUtils.isNotBlank((CommonDataSingleton.getCodeCategoryLabelByValue(
                CategoryCodeConstant.OFFICER_TYPE, officerIndicator, officerType)))) {
            return true;
        }
        return false;
    }
}
