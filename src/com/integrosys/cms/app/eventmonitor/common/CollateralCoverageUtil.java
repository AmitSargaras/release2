/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/common/CollateralCoverageUtil.java,v 1.5 2006/10/12 02:44:28 hmbao Exp $
 */

package com.integrosys.cms.app.eventmonitor.common;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CollateralCoverageUtil {

	private static final CollateralCoverageUtil INSTANCE = new CollateralCoverageUtil();

	public static CollateralCoverageUtil getInstance() {
		return INSTANCE;
	}

	public String getLimitChargeType(boolean ind) {
		if (ind) {
			return "General";
		}
		return "Specific";
	}

	public String getCollateralChargeType(ICollateral collateral, long limitID) {
		if (collateral == null) {
			return "-";
		}
		DefaultLogger.debug(this, "CollateralID : " + collateral.getCollateralID());
		DefaultLogger.debug(this, "CollateralID : " + limitID);
		ILimitCharge charges[] = collateral.getLimitCharges();
		if ((charges == null) || (charges.length == 0)) {
			return "-";
		}
		for (int ii = 0; ii < charges.length; ii++) {
			ICollateralLimitMap mm[] = charges[ii].getLimitMaps();
			if ((mm == null) || (mm.length == 0)) {
				continue;
			}
			for (int jj = 0; jj < mm.length; jj++) {
				if (mm[jj].getLimitID() == limitID) {
					if (ICMSConstant.CHARGE_TYPE_GENERAL.equals(charges[ii].getChargeType())) {
						return "General";
					}
					else {
						return "Specific";
					}
				}
			}
		}
		return "-";
	}
}
