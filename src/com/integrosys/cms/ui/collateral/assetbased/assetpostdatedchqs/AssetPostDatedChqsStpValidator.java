package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.ui.collateral.assetbased.AbstractAssetBasedStpValidator;

import java.util.Map;

public class AssetPostDatedChqsStpValidator extends AbstractAssetBasedStpValidator {
	public boolean validate(Map context) {
		if (!validateAssetBasedCollateral(context)) {
			return false;
		}
//		IAssetPostDatedCheque postDatedChqs = (IAssetPostDatedCheque) collateral;
		if (validateAndAccumulate(context).size() <= 0
				/*postDatedChqs.getInterestRate() != 0.0d
				&& StringUtils.isNotBlank(postDatedChqs.getChequeRefNumber())
				&& postDatedChqs.getChequeDate() != null
				&& StringUtils.isNotBlank(postDatedChqs.getRemarks())*/
				) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateAssetBased(context);
		
		IAssetPostDatedCheque postDatedChqs = (IAssetPostDatedCheque) context.get(COL_OB);
		IPostDatedCheque postDatedCheques[] = postDatedChqs.getPostDatedCheques();
		if (postDatedCheques != null) {
			for (int i=0; i<postDatedCheques.length; i++) {
				// Bank/Branch code
				/*if (StringUtils.isBlank(postDatedCheques[i].getIssuerName())) {
					errorMessages.add("issuer", new ActionMessage("error.mandatory"));
				}
				// Deposit Expiration date DDMMYYYY
				if (postDatedCheques[i].getExpiryDate() == null) {
					errorMessages.add("expiryDate", new ActionMessage("error.mandatory"));
				}*/
			}
		}
		return errorMessages;
	}
}
