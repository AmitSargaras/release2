package com.integrosys.cms.ui.collateral.document.docagreement;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.app.collateral.bus.type.document.subtype.leaseagreement.ILeaseAgreement;
import com.integrosys.cms.ui.collateral.document.AbstractDocumentStpValidator;

import java.util.Map;
import java.util.List;

public class DocAgreementStpValidator extends AbstractDocumentStpValidator {
    private List collateralCodeRequireBuyBackVal;

    public List getCollateralCodeRequireBuyBackVal() {
        return collateralCodeRequireBuyBackVal;
    }

    public void setCollateralCodeRequireBuyBackVal(List collateralCodeRequireBuyBackVal) {
        this.collateralCodeRequireBuyBackVal = collateralCodeRequireBuyBackVal;
    }

    public boolean validate(Map context) {
		if (!validateDocumentCollateral(context)) {
			return false;
		}
//		ILeaseAgreement leaseAgreement = (ILeaseAgreement) collateral;
		if (validateAndAccumulate(context).size() <= 0
				/*leaseAgreement.getBuybackValue() != null
				&& leaseAgreement.getGuranteeAmount() != null*/
				) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateDocumentation(context);
		
		ILeaseAgreement leaseAgreement = (ILeaseAgreement) context.get(COL_OB);
		//Andy Wong, 16 Feb 2009: validate Buyback Value must be > 0 for Lease Agreement Lsagr
        if (collateralCodeRequireBuyBackVal.contains(leaseAgreement.getSourceSecuritySubType())) {
            if (leaseAgreement.getBuybackValue() == null
                    || leaseAgreement.getBuybackValue().getAmount() <= 0) {
                errorMessages.add("buybackValue", new ActionMessage("error.collateral.lsagr.buybackvalue"));
            }
        }
		// Guarantee Amount
		if (leaseAgreement.getGuranteeAmount() == null
                || leaseAgreement.getGuranteeAmount().getAmount() <= 0) {
			errorMessages.add("guranteeAmount", new ActionMessage("error.mandatory"));
		}
		// Deposit Expiration date DDMMYYYY
		if (leaseAgreement.getCollateralMaturityDate() == null) {
			errorMessages.add("collateralMaturityDate", new ActionMessage("error.mandatory"));
		}
		return errorMessages;
	}
}
