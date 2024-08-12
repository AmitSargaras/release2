/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/DataProtectionProxyImpl.java,v 1.6 2006/03/17 07:20:31 hshii Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.util.HashMap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.dataprotection.bus.ICollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;

/**
 * Purpose: DataProxy implementation to hide the implementation details from the
 * UI layer
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class DataProtectionProxyImpl extends AbstractDataProtectionProxy {
	private static HashMap dapMap = new HashMap();

	// TODO: Exception Handling
	/**
	 * retrieves the meta data for each subtype
	 * @param type - String for type
	 * @return list of ICollateralMetaData
	 */
	public ICollateralMetaData[] getCollateralMetaDataBySubType(String type) {

		try {
			SBDataProtection proxy = getProxy();
			return proxy.getCollateralMetaDataBySubType(type);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error getting CollateralMetaData");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get a list of Data Access Profile.
	 * 
	 * @param type data access module type
	 * @param subtype data access module subtype
	 * @param roleType user team type membership id
	 * @return an array of IDataAccessProfile objects
	 */
	public IDataAccessProfile[] getDataAccessProfile(String type, String subtype, long roleType) {
		try {
			SBDataProtection proxy = getProxy();
			return proxy.getDataAccessProfile(type, subtype, roleType);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			return null;
		}
	}

	/**
	 * Check if given type, subtype, country and organisation code can access by
	 * multiple roles
	 * 
	 * @param type module type
	 * @param subtype module subtype
	 * @param ctryCode access country code
	 * @param orgCode access organisation code
	 * @return true if data access by multiple roles, otherwise false
	 */
	public boolean isMultipleRoleAccessByCtryOrg(String type, String subtype, String ctryCode, String orgCode) {
		try {
			SBDataProtection proxy = getProxy();
			return proxy.isMultipleRoleAccessByCtryOrg(type, subtype, ctryCode, orgCode);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			return false;
		}
	}

	/**
	 * Get data access profile given its module type, subtype, and user role
	 * type.
	 * 
	 * @param type module type
	 * @param subType module subtype
	 * @param roleType user role type
	 * @return a list of IDataAccessProfile
	 */
	protected IDataAccessProfile[] getCachedDAPList(String type, String subType, long roleType) {
		if ((type == null) && (subType == null) && (roleType == ICMSConstant.LONG_INVALID_VALUE)) {
			return null;
		}
		String key = type + subType + roleType;

		if (dapMap.containsKey(key)) {
			return (IDataAccessProfile[]) dapMap.get(key);
		}
		else {
			IDataAccessProfile[] rs = getDataAccessProfile(type, subType, roleType);
			dapMap.put(key, rs);
			return rs;
		}
	}

	/**
	 * Helper method to get ejb object of data protection proxy session bean.
	 * 
	 * @return SBDataProtection proxy ejb object
	 */
	private SBDataProtection getProxy() throws Exception {
		SBDataProtection proxy = (SBDataProtection) BeanController.getEJB(ICMSJNDIConstant.SB_DATA_PROTECTION_HOME,
				ICMSJNDIConstant.SB_DATA_PROTECTION_HOME_PATH);

		if (proxy == null) {
			throw new Exception("SBDataProtectionProxy is null!");
		}
		return proxy;
	}
}
