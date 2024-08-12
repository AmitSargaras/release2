/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CheckEditable.java,v 1.3 2006/10/25 08:41:16 hshii Exp $
 */
package com.integrosys.cms.ui.collateral;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/10/25 08:41:16 $ Tag: $Name: $
 */
public class CheckEditable {

	public CheckEditable() {
	}

	public static boolean isSSCEditable(ICollateral iCol) {
		DefaultLogger.debug("CheckEditable", "entering isSSCEditable");
		String colSubType = iCol.getCollateralSubType().getSubTypeCode();

		DefaultLogger.debug("CheckEditable", "colSubType: " + colSubType);

		IDataProtectionProxy dpProxy = DataProtectionProxyFactory.getProxy();
		boolean editable = dpProxy.isDataAccessAllowed(ICMSConstant.INSTANCE_COLLATERAL, colSubType,
				ICMSConstant.TEAM_TYPE_SSC_MAKER, new String[] { iCol.getCollateralLocation() },
				new String[] { IDataProtectionProxy.ANY_ORGANISATION }, false);

		DefaultLogger.debug("check editable", "editable is: " + editable);
		return editable;
	}
}
