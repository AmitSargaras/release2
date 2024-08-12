package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 10, 2003 Time: 12:03:45 PM
 * To change this template use Options | File Templates.
 */
public class SecuritySubTypeList {
	private SecuritySubTypeList() {

	}

	static SecuritySubTypeList securitySubTypeList = null;

	private static HashMap hSecuritySubTypeList = new HashMap();

	private static Collection hSecuritySubTypeLabel = new ArrayList();

	private static Collection hSecuritySubTypeValue = new ArrayList();

	public static SecuritySubTypeList getInstance() throws CollateralException {
		if (securitySubTypeList == null) {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralSubType colTypes[] = proxy.getAllCollateralSubTypes();
			securitySubTypeList = new SecuritySubTypeList();
			if ((colTypes != null) && (colTypes.length > 0)) {
				for (int i = 0; i < colTypes.length; i++) {
					hSecuritySubTypeList.put(colTypes[i].getSubTypeCode(), colTypes[i].getSubTypeName());
					hSecuritySubTypeValue.add(colTypes[i].getSubTypeCode());
					hSecuritySubTypeLabel.add(colTypes[i].getSubTypeName());
				}
			}
			// DefaultLogger.debug("Security Sub Type List ---->",
			// hSecuritySubTypeList);
		}

		return securitySubTypeList;
	}

	public Collection getSecuritySubTypeLabel(Locale locale) throws CollateralException {
		return hSecuritySubTypeLabel;
	}

	public Collection getSecuritySubTypeProperty() throws CollateralException {
		return hSecuritySubTypeValue;
	}

	public String getSecuritySubTypeValue(String key, Locale locale) {
		if (!hSecuritySubTypeList.isEmpty()) {
			return (String) hSecuritySubTypeList.get(key);
		}
		else {
			return "";
		}
	}

}
