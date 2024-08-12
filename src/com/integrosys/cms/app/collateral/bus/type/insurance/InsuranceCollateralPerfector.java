package com.integrosys.cms.app.collateral.bus.type.insurance;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;

public class InsuranceCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		IInsuranceCollateral col = (IInsuranceCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (!isChargeDetailsPerfected(col.getLimitCharges())) {
			return false;
		}

		if (col instanceof ICreditInsurance) {
			if (!isCreditInsuranceCollateralPerfected((ICreditInsurance) col)) {
				return false;
			}
		}
		else if (col instanceof IKeymanInsurance) {
			if (!isKeymanInsuranceCollateralPerfected((IKeymanInsurance) col)) {
				return false;
			}
		}
		else if (col instanceof ICreditDefaultSwaps) {
			return true;
		}

		if (col.getCollateralMaturityDate() == null) {
			return false;
		}

		return true;
	}

	protected boolean isCreditInsuranceCollateralPerfected(ICreditInsurance col) {
		if (!isValidAmt(col.getInsuredAmount())) {
			return false;
		}

		return true;
	}

	protected boolean isKeymanInsuranceCollateralPerfected(IKeymanInsurance col) {
		if (!isValidAmt(col.getInsuredAmount())) {
			return false;
		}

		return true;
	}
}