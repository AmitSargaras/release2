package com.integrosys.cms.ui.collateral.property;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Date;
import java.util.Map;

public abstract class AbstractPropertyStpValidator extends AbstractCollateralStpValidator {

    protected boolean validatePropertyCollateral(Map context) {
        if (!validateCommonCollateral(context)) {
            return false;
        }
        if (validateAndAccumulateProperty(context).size() <= 0) {
            // do nothing
        } else return false;

        //Andy Wong: validate by calling accumulate method
//        if (propertyCollateral.getCMV() == null) {
//            return false;
//        }
//
//        if (propertyCollateral.getTitleType() != null && !(propertyCollateral.getTitleType().equals(ICMSUIConstant.PROPERTY_TITLE_TYPE_MARST))) {
//            if(propertyCollateral.getMasterTitleNumber() == null){
//                return false;
//            }
//        }

        return true;
    }

    public boolean validate(ICollateral collateral) {
        // TODO Auto-generated method stub
        return false;
    }

    protected ActionErrors validateAndAccumulateProperty(Map context) {
        ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);
        context.put(ERRORS, errorMessages);
        errorMessages = validateAndAccumulateInsurancePolicies(context);
        IPropertyCollateral propertyCollateral = (IPropertyCollateral) context.get(COL_OB);
        // Commission Type
//        if (propertyCollateral.getPropertyCompletionStatus() == '\u0000') {
//            errorMessages.add("propertyCompletionStatus", new ActionMessage("error.mandatory"));
//        }

        if (propertyCollateral.getTitleType() != null && !(propertyCollateral.getTitleType().equals(ICMSUIConstant.PROPERTY_TITLE_TYPE_MARST))) {
            if (propertyCollateral.getMasterTitleNumber() == null) {
                errorMessages.add("masterTitleNo", new ActionMessage("error.mandatory"));
            }
        }

        //Andy Wong, 16 Jan 2009: validate mandatory pair for auctioneer and auction date
        if (propertyCollateral.getAuctionDate() != null && StringUtils.isEmpty(propertyCollateral.getAuctioneer())) {
            errorMessages.add("auctioneer", new ActionMessage("error.conditional.mandatory.pair", "Auctioneer", "Auction Date"));
        } else if (propertyCollateral.getAuctionDate() == null && StringUtils.isNotEmpty(propertyCollateral.getAuctioneer())) {
            errorMessages.add("auctionDate", new ActionMessage("error.conditional.mandatory.pair", "Auction Date", "Auctioneer"));
        }

        //Andy Wong, 16 Jan 2009: validate mandatory pair for auction price and auction date
        if (propertyCollateral.getAuctionDate() != null &&
                (propertyCollateral.getAuctionPrice() == null
                        || propertyCollateral.getAuctionPrice().getAmount() <= 0)) {
            errorMessages.add("auctionPrice", new ActionMessage("error.conditional.mandatory.pair", "Auction Price", "Auction Date"));
        } else if (propertyCollateral.getAuctionDate() == null && propertyCollateral.getAuctionPrice() != null
                && propertyCollateral.getAuctionPrice().getAmount() > 0) {
            errorMessages.add("auctionDate", new ActionMessage("error.conditional.mandatory.pair", "Auction Date", "Auction Price"));
        }

        if (propertyCollateral.getSalePurchaseDate() != null) {
            if (propertyCollateral.getSalePurchaseDate().after(new Date())) {
                errorMessages.add("salePurchaseDate", new ActionMessage("error.date.compareDate.cannotBelater", "Mortgage Creation /Extension date",
                        "TODAY Date"));
            }
        }

        if (propertyCollateral.getPriCaveatGuaranteeDate() != null) {
            if (propertyCollateral.getPriCaveatGuaranteeDate().before(new Date())) {
                errorMessages.add("priCaveatGuaranteeDate", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Private caveat/G'tee Exp date",
                        "TODAY Date"));
            }
        }

        if (propertyCollateral.getChattelSoldDate() != null) {
            if (propertyCollateral.getChattelSoldDate().after(new Date())) {
                errorMessages.add("chattelSoldDate", new ActionMessage("error.date.compareDate.cannotBelater", "Date Chattel Sold",
                        "TODAY Date"));
            }
        }

        if (propertyCollateral.getLastRemarginDate() != null) {
            if (propertyCollateral.getLastRemarginDate().after(new Date())) {
                errorMessages.add("valDate", new ActionMessage("error.date.compareDate.cannotBelater", "Valuation Date Of OMV",
                        "TODAY Date"));
            }
        }

        if (propertyCollateral.getLastRemarginDate() != null && propertyCollateral.getNextRemarginDate() != null) {
            if (propertyCollateral.getLastRemarginDate().after(propertyCollateral.getNextRemarginDate())) {
                errorMessages.add("valDate", new ActionMessage("error.date.compareDate.cannotBelater", "Valuation Date Of OMV",
                        "Next Valuation Date"));
            }
        }
        
      //Not required for HDFC
        /*if (isPreStpValidationRequired(context)) {
            //Andy Wong: validate FSV cannot empty when chattel and auction date is not blank
            if (propertyCollateral.getChattelSoldDate() != null
                    && propertyCollateral.getAuctionDate() != null
                    && (propertyCollateral.getFSV() == null
                    || propertyCollateral.getFSV().getAmount() <= 0)) {
                errorMessages.add("pendingPerfectError", new ActionMessage("error.val.fsv.cannot.zero"));
            }

            //Andy Wong: validate CMV cannot be empty
            if (!(propertyCollateral.getValuationIntoCMS() != null 
                    && propertyCollateral.getValuationIntoCMS().getCMV() != null
                    && propertyCollateral.getValuationIntoCMS().getCMV().getAmount() > 0)
                    && (propertyCollateral.getCMV() == null || propertyCollateral.getCMV().getAmount() <= 0)) {
                errorMessages.add("pendingPerfectError", new ActionMessage("error.valuation.perfected"));
            }
        }*/

        return errorMessages;
    }
}
