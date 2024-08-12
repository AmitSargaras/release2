package com.integrosys.cms.ui.collateral.assetbased.assetspecgold;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetSpecGoldStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
//		ISpecificChargeGold specChargeGold = (ISpecificChargeGold) collateral;
		if (validateAndAccumulate(context).size() <= 0
				/*StringUtils.isNotBlank(specChargeGold.getAssetType())
				&& StringUtils.isNotBlank(specChargeGold.getGoldGrade())
				&& StringUtils.isNotBlank(specChargeGold.getPurchaseReceiptNo())
				&& specChargeGold.getGoldUnitPrice() != null
				&& specChargeGold.getGoldWeight() != 0.0d
				&& specChargeGold.getPurchasePrice() != null
				&& specChargeGold.getAuctionPrice() != null
				&& StringUtils.isNotBlank(specChargeGold.getAuctioneer())
				&& specChargeGold.getSalesProceed() != null
				&& specChargeGold.getAuctionDate() != null
				&& StringUtils.isNotBlank(specChargeGold.getRemarks())*/
				) {
			// do nothing
		}
		else return false;

        return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateAssetBased(context);
		
		ISpecificChargeGold specChargeGold = (ISpecificChargeGold) context.get(COL_OB);
		String errorCode = null;
		/*commented By Govind Sahu:270811
		// Purchase Price
		if (specChargeGold.getPurchasePrice() == null) {
			errorMessages.add("purchasePrice", new ActionMessage("error.mandatory"));
		}
		*/
		// Item Type
		//Govind S:Commented for gold type
		/*if (StringUtils.isBlank(specChargeGold.getAssetType())) {
			errorMessages.add("assetType", new ActionMessage("error.mandatory"));
		}*/

        //Andy Wong, 16 Jan 2009: validate mandatory pair for auctioneer and auction date
        if(specChargeGold.getAuctionDate()!=null && StringUtils.isEmpty(specChargeGold.getAuctioneer())){
            errorMessages.add("auctioneer", new ActionMessage("error.conditional.mandatory.pair", "Auctioneer", "Auction Date"));
        } else if(specChargeGold.getAuctionDate()==null && StringUtils.isNotEmpty(specChargeGold.getAuctioneer())){
            errorMessages.add("auctionDate", new ActionMessage("error.conditional.mandatory.pair", "Auction Date", "Auctioneer"));
        }

        //Andy Wong, 16 Jan 2009: validate mandatory pair for auction price and auction date
        if(specChargeGold.getAuctionDate()!=null &&
                (specChargeGold.getAuctionPrice()==null
                || specChargeGold.getAuctionPrice().getAmount() <= 0)){
            errorMessages.add("auctionPrice", new ActionMessage("error.conditional.mandatory.pair", "Auction Price", "Auction Date"));
        } else if(specChargeGold.getAuctionDate()==null && specChargeGold.getAuctionPrice()!=null
                && specChargeGold.getAuctionPrice().getAmount()>0){
            errorMessages.add("auctionDate", new ActionMessage("error.conditional.mandatory.pair", "Auction Date", "Auction Price"));
        }
//Not required for HDFC
        
       /* if (isPreStpValidationRequired(context)) {
            //Andy Wong: validate FSV cannot empty when chattel and auction date provided
            if (specChargeGold.getChattelSoldDate() != null
                    && specChargeGold.getAuctionDate() != null
                    && (specChargeGold.getFSV() == null
                    || specChargeGold.getFSV().getAmount() <= 0)) {
                errorMessages.add("pendingPerfectError", new ActionMessage("error.val.fsv.cannot.zero"));
            }

            //Andy Wong: validate CMV cannot be empty
            if (!(specChargeGold.getValuationIntoCMS() != null 
                    && specChargeGold.getValuationIntoCMS().getCMV() != null
                    && specChargeGold.getValuationIntoCMS().getCMV().getAmount() > 0)
                    && (specChargeGold.getCMV() == null || specChargeGold.getCMV().getAmount() <= 0)) {
                errorMessages.add("pendingPerfectError", new ActionMessage("error.valuation.perfected"));
            }
        }*/

		return errorMessages;
	}
}
