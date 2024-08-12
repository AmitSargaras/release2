package com.integrosys.cms.app.collateral.bus.type.others;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;

public class OthersCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		IOthersCollateral col = (IOthersCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (!isInsurancePolicyDetailsPerfected(col.getInsurancePolicies())) {
			return false;
		}

		if (!isChargeDetailsPerfected(col.getLimitCharges())) {
			return false;
		}

		if (!isValuationDetailPerfected(col.getValuationIntoCMS())) {
			return false;
		}

		if (col.getCollateralMaturityDate() == null) {
			return false;
		}

		return true;
	}
}