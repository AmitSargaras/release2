/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccoltask;

import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Utility class for UI.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/24 08:15:17 $ Tag: $Name: $
 */

public class SecColTaskUtil {

	/**
	 * Helper class to set the cc collaboration task trx origin country
	 */
	public static String getSecColTaskTrxCountry(ITrxContext context, ICollateralTask task, ILimitProfile limitProfile) {
		if (limitProfile != null) {
			if (UIUtil.isInCountry(context, limitProfile)) {
				return limitProfile.getOriginatingLocation().getCountryCode();
			}
		}
		return task.getCollateralLocation();
	}
}