package com.integrosys.cms.app.collateral.bus.type.document;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;

public class DocumentCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		IDocumentCollateral col = (IDocumentCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (col.getCollateralMaturityDate() == null) {
			return false;
		}

		return true;
	}
}