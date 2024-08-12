package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 10, 2003 Time: 12:03:45 PM
 * To change this template use Options | File Templates.
 */
public class FacilityTypeList {
	private FacilityTypeList() {

	}

	static FacilityTypeList facilityTypeList = null;

	private static HashMap hFacilityTypeList = new HashMap();

	private static Collection hFacilityTypeLabel = new ArrayList();

	private static Collection hFacilityTypeValue = new ArrayList();

	public static FacilityTypeList getInstance() throws CollateralException {

		if (facilityTypeList == null) {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralType colTypes[] = proxy.getAllCollateralTypes();
			facilityTypeList = new FacilityTypeList();
			if ((colTypes != null) && (colTypes.length > 0)) {
				for (int i = 0; i < colTypes.length; i++) {
					hFacilityTypeList.put(colTypes[i].getTypeCode(), colTypes[i].getTypeName());
					hFacilityTypeValue.add(colTypes[i].getTypeCode());
					hFacilityTypeLabel.add(colTypes[i].getTypeName());
				}
			}
			//DefaultLogger.debug("Facility type List ---->",hFacilityTypeList);
		}
		return facilityTypeList;
	}

	public Collection getFacilityTypeLabel(Locale locale) throws CollateralException {
		/*
		 * ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		 * ICollateralType colTypes[] = proxy.getAllCollateralTypes();
		 * Collection ret = new ArrayList(); if(colTypes!=null &&
		 * colTypes.length>0) { for(int i=0;i<colTypes.length;i++){
		 * ret.add(colTypes[i].getTypeName()); } } return ret;
		 */
		return hFacilityTypeLabel;
	}

	public Collection getFacilityTypeProperty() throws CollateralException {
		/*
		 * ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		 * ICollateralType colTypes[] = proxy.getAllCollateralTypes();
		 * Collection ret = new ArrayList(); if(colTypes!=null &&
		 * colTypes.length>0) { for(int i=0;i<colTypes.length;i++){
		 * ret.add(colTypes[i].getTypeCode()); } } return ret;
		 */
		return hFacilityTypeValue;
	}

	public String getFacilityTypeValue(String key, Locale locale) {
		if (!hFacilityTypeList.isEmpty()) {
			return (String) hFacilityTypeList.get(key);
		}
		else {
			return "";
		}
	}

}
