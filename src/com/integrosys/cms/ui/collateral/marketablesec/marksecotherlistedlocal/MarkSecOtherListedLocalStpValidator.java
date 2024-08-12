package com.integrosys.cms.ui.collateral.marketablesec.marksecotherlistedlocal;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.ui.collateral.marketablesec.AbstractMarketableSecStpValidator;

import java.util.Map;

public class MarkSecOtherListedLocalStpValidator extends AbstractMarketableSecStpValidator {
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
		IMarketableCollateral market = (IMarketableCollateral) context.get(COL_OB);

		IMarketableEquity[] iMarketEquity = market.getEquityList();
		/*if (iMarketEquity != null) {
			for (int i = 0; i < iMarketEquity.length; i++) {
				// Unit Trust Fund Name
				if (StringUtils.isBlank(iMarketEquity[i].getIssuerName())) {
					errorMessages.add("issuerName", new ActionMessage("error.mandatory"));
				}
			}
		}*/
		return errorMessages;
	}
}
