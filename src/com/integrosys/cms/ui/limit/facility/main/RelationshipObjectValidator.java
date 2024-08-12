package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.limit.bus.IFacilityRelationship;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.apache.struts.action.ActionErrors;
import com.integrosys.base.businfra.currency.Amount;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.Iterator;

public class RelationshipObjectValidator {
    /**
     * Method to validate set of relationships for listing page display
     * @param RelationshipSet
     * @return
     */
    public static ActionErrors validateObject(Set RelationshipSet) {
        ActionErrors RelationshipErrors = new ActionErrors();
        long totalPriRelationship = 0;

        //Name associated with facility order must be 0 when it is primary relationship
        if (RelationshipSet != null){
            for (Iterator iterator = RelationshipSet.iterator(); iterator.hasNext();) {
                IFacilityRelationship rel =  (IFacilityRelationship)iterator.next();

                if(!validateNameAssocForPriRelationship(rel))
                    RelationshipErrors.add("relationshipErrors", new ActionMessage("error.name.assoc.fac.order.0.primary.relation"));

                if(!validateGuaranteeAmtPercentForGORelationship(rel))
                    RelationshipErrors.add("relationshipErrors", new ActionMessage("error.guarantee.amount.or.percent.required"));

                if (!StringUtils.equals(rel.getStatus(), "D")
                        && ICMSConstant.ACCOUNT_RELATIONSHIP_PRIMARY.equals(rel.getAccountRelationshipEntryCode())) {
                    totalPriRelationship = totalPriRelationship + 1;
                }
            }

            // cannot have duplication for Account RelationShip = 'P'
            if (totalPriRelationship > 1) {
                RelationshipErrors.add("relationshipErrors",
                        new ActionMessage("error.facility.account.relationship.cannot.duplicate", "Primary account owner"));
            }
        }

        DefaultLogger.debug(" FacilityRelationship Total Errors", "--------->" + RelationshipErrors.size());
        return RelationshipErrors.size() == 0 ? null : RelationshipErrors;
    }

    /**
     * Method to validate single relationship OB for detail page display
     * @param rel
     * @return
     */
    public static ActionErrors validateSingleObject(IFacilityRelationship rel) {
        ActionErrors RelationshipErrors = new ActionErrors();

        if(!validateNameAssocForPriRelationship(rel))
            RelationshipErrors.add("nameAssociateWithFacilityOrder", new ActionMessage("error.name.assoc.fac.order.0.primary.relation"));

        if(!validateGuaranteeAmtPercentForGORelationship(rel))
            RelationshipErrors.add("guaranteeAmount", new ActionMessage("error.guarantee.amount.or.percent.required"));
        
        return RelationshipErrors.size() == 0 ? null : RelationshipErrors;
    }

    /**
     * Validate name associate with fac order for primary relationship
     * @param rel
     * @return true when pass validation, false otherwise
     */
    private static boolean validateNameAssocForPriRelationship(IFacilityRelationship rel){
        if (!StringUtils.equals(rel.getStatus(), "D")
                && ICMSConstant.ACCOUNT_RELATIONSHIP_PRIMARY.equals(rel.getAccountRelationshipEntryCode())) {
            if (rel.getNameAssociateWithFacilityOrder() != null
                    && (rel.getNameAssociateWithFacilityOrder().intValue() != 0)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate guarantee amount or percent contains value for guarantee relationship
     * @param rel
     * @return true when pass validation, false otherwise
     */
    private static boolean validateGuaranteeAmtPercentForGORelationship(IFacilityRelationship rel){
        if (!StringUtils.equals(rel.getStatus(), "D")
                && ICMSConstant.ACCOUNT_RELATIONSHIP_GUARANTOR.equals(rel.getAccountRelationshipEntryCode())) {
            //Andy Wong, 3 Feb 2009: validate for empty guarantee amount and percentage
            if ((rel.getGuaranteeAmount() == null
                    || rel.getGuaranteeAmount().getAmount() <= 0)
                    && (rel.getGuaranteePercentage() == null
                    || rel.getGuaranteePercentage().doubleValue() <= 0)) {
                return false;
            }
        }
        return true;
    }
}
