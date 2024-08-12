package com.integrosys.cms.ui.collateral.assetbased;

import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Jul 9, 2007 Time: 11:43:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class AssetResidualLifeValidator {

	/**
	 * // MBB-1281 This validation is applicable for AB - Aircraft, Vessel,
	 * Plant & Equipment Vehicles
	 * @param secSubTypeId
	 * @return int
	 */

	public static int getAssetResidualLife(String secSubTypeId) {
		int assetResidualLife = ICMSConstant.INT_INVALID_VALUE;
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			assetResidualLife = proxy.getAssetResidualLife(secSubTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return assetResidualLife;

	}
}
