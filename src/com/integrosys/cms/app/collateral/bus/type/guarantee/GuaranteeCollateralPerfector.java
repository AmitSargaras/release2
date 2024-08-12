package com.integrosys.cms.app.collateral.bus.type.guarantee;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;

public class GuaranteeCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		IGuaranteeCollateral col = (IGuaranteeCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (!isChargeDetailsPerfected(col.getLimitCharges())) {
			return false;
		}

		if (col.getCollateralMaturityDate() == null) {
			return false;
		}

		return true;
	}
}