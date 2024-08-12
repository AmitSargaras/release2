package com.integrosys.cms.app.collateral.bus.type.asset;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold.ISpecificChargeGold;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeothers.ISpecificChargeOthers;

public class AssetBasedCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		IAssetBasedCollateral col = (IAssetBasedCollateral) collateral;

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

		if ((col instanceof ISpecificChargeGold) || (col instanceof ISpecificChargeOthers)
				|| (col instanceof IAssetPostDatedCheque)) {
			return true;
		}
		else {
			if (col.getCollateralMaturityDate() == null) {
				return false;
			}
		}

		return true;
	}
}