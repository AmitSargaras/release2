package com.integrosys.cms.app.collateral.bus.type.property;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;

public class PropertyCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {
		IPropertyCollateral col = (IPropertyCollateral) collateral;

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

		if ((col.getTenure() > 0) && (col.getCollateralMaturityDate() == null)) {
			return false;
		}

		return true;
	}
}