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
public class SecurityTypeList {
	private SecurityTypeList() {

	}

	static SecurityTypeList securityTypeList = null;

	private static HashMap hSecurityTypeList = new HashMap();

	private static Collection hSecurityTypeLabel = new ArrayList();

	private static Collection hSecurityTypeValue = new ArrayList();

	public static SecurityTypeList getInstance() throws CollateralException {

		if (securityTypeList == null) {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateralType colTypes[] = proxy.getAllCollateralTypes();
			securityTypeList = new SecurityTypeList();
			if ((colTypes != null) && (colTypes.length > 0)) {
				for (int i = 0; i < colTypes.length; i++) {
					hSecurityTypeList.put(colTypes[i].getTypeCode(), colTypes[i].getTypeName());
					hSecurityTypeValue.add(colTypes[i].getTypeCode());
					hSecurityTypeLabel.add(colTypes[i].getTypeName());
				}
			}
			//DefaultLogger.debug("Security type List ---->",hSecurityTypeList);
		}
		return securityTypeList;
	}

	public Collection getSecurityTypeLabel(Locale locale) throws CollateralException {
		/*
		 * ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		 * ICollateralType colTypes[] = proxy.getAllCollateralTypes();
		 * Collection ret = new ArrayList(); if(colTypes!=null &&
		 * colTypes.length>0) { for(int i=0;i<colTypes.length;i++){
		 * ret.add(colTypes[i].getTypeName()); } } return ret;
		 */
		return hSecurityTypeLabel;
	}

	public Collection getSecurityTypeProperty() throws CollateralException {
		/*
		 * ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		 * ICollateralType colTypes[] = proxy.getAllCollateralTypes();
		 * Collection ret = new ArrayList(); if(colTypes!=null &&
		 * colTypes.length>0) { for(int i=0;i<colTypes.length;i++){
		 * ret.add(colTypes[i].getTypeCode()); } } return ret;
		 */
		return hSecurityTypeValue;
	}

	public String getSecurityTypeValue(String key, Locale locale) {
		if (!hSecurityTypeList.isEmpty()) {
			return (String) hSecurityTypeList.get(key);
		}
		else {
			return "";
		}
	}

}
