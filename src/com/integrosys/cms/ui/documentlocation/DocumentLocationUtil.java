/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.documentlocation;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Utility class for UI.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/24 08:13:39 $ Tag: $Name: $
 */

public class DocumentLocationUtil {

	/**
	 * Helper class to set the cc collaboration task trx origin country
	 */
	public static String getDocumentLocationTrxCountry(ITrxContext context, String ccDocOriginLocation,
			ILimitProfile limitProfile, ICMSCustomer customer) {
		if (limitProfile != null) {
			if (UIUtil.isInCountry(context, limitProfile)) {
				return limitProfile.getOriginatingLocation().getCountryCode();
			}
		}
		else {
			if (UIUtil.isInCountry(context, customer)) {
				return customer.getOriginatingLocation().getCountryCode();
			}
		}
		return ccDocOriginLocation;
	}
}