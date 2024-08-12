package com.integrosys.cms.ui.collateral.marketablesec.marksecgovtforeignsame;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.ui.collateral.marketablesec.AbstractMarketableSecStpValidator;

import java.util.Map;

public class MarkSecGovtForeignSameStpValidator extends AbstractMarketableSecStpValidator {
	public boolean validate(Map context) {
		if (!validateMarketableCollateral(context)) {
			return false;
		}
//		ILimitCharge[] limitCharges = collateral.getLimitCharges();
//		IMarketableCollateral market = (IMarketableCollateral) collateral;
		if (validateAndAccumulate(context).size() <= 0
				/*StringUtils.isNotBlank(limitCharges[0].getChargeType())
				&& market.getCappedPrice() != null*/
				) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateMarketable(context);

		return errorMessages;
	}
}
