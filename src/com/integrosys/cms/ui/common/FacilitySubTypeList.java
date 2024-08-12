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
public class FacilitySubTypeList {
	private FacilitySubTypeList() {

	}

	static FacilitySubTypeList facilitySubTypeList = null;

	private static HashMap hFacilitySubTypeList = new HashMap();

	private static Collection hFacilitySubTypeLabel = new ArrayList();

	private static Collection hFacilitySubTypeValue = new ArrayList();

	public static FacilitySubTypeList getInstance() throws CollateralException {
		if (facilitySubTypeList == null) {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralSubType colTypes[] = proxy.getAllCollateralSubTypes();
			facilitySubTypeList = new FacilitySubTypeList();
			if ((colTypes != null) && (colTypes.length > 0)) {
				for (int i = 0; i < colTypes.length; i++) {
					hFacilitySubTypeList.put(colTypes[i].getSubTypeCode(), colTypes[i].getSubTypeName());
					hFacilitySubTypeValue.add(colTypes[i].getSubTypeCode());
					hFacilitySubTypeLabel.add(colTypes[i].getSubTypeName());
				}
			}
			// DefaultLogger.debug("Facility Sub Type List ---->",
			// hFacilitySubTypeList);
		}

		return facilitySubTypeList;
	}

	public Collection getFacilitySubTypeLabel(Locale locale) throws CollateralException {
		return hFacilitySubTypeLabel;
	}

	public Collection getFacilitySubTypeProperty() throws CollateralException {
		return hFacilitySubTypeValue;
	}

	public String getFacilitySubTypeValue(String key, Locale locale) {
		if (!hFacilitySubTypeList.isEmpty()) {
			return (String) hFacilitySubTypeList.get(key);
		}
		else {
			return "";
		}
	}

}
