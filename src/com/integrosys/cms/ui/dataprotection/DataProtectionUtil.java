/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/dataprotection/DataProtectionUtil.java,v 1.14 2006/03/17 07:27:20 hshii Exp $
 */
package com.integrosys.cms.ui.dataprotection;

import java.util.HashMap;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.dataprotection.bus.ICollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;
import com.integrosys.cms.app.dataprotection.proxy.DataProtectionProxyFactory;
import com.integrosys.cms.app.dataprotection.proxy.IDataProtectionProxy;

/**
 * Purpose: A singleton that caches data access profile.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/03/17 07:27:20 $ Tag: $Name: $
 * 
 */
public class DataProtectionUtil {
	private static DataProtectionUtil myUtil;

	private static HashMap dapMap;

	/**
	 * Contructs DataProtectionUtil instance.
	 * 
	 * @return DataProtectionUtil instance
	 */
	public synchronized static DataProtectionUtil getInstance() {
		if (myUtil == null) {
			myUtil = new DataProtectionUtil();
		}
		return myUtil;
	}

	/**
	 * Default constructs DataProtectionUtil instance.
	 */
	private DataProtectionUtil() {
		dapMap = new HashMap();
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
	public IDataAccessProfile[] getDataAccessProfile(String type, String subType, long roleType) {
		if ((type == null) && (subType == null) && (roleType == ICMSConstant.LONG_INVALID_VALUE)) {
			return null;
		}
		String key = type + subType + roleType;

		if (dapMap.containsKey(key)) {
			return (IDataAccessProfile[]) dapMap.get(key);
		}
		else {
			IDataProtectionProxy proxy = DataProtectionProxyFactory.getProxy();
			IDataAccessProfile[] rs = proxy.getDataAccessProfile(type, subType, roleType);
			dapMap.put(key, rs);
			return rs;
		}
	}

	/**
	 * Get collateral fields based on its subtype.
	 * 
	 * @param subtype collateral subtype
	 * @return ICollateralMetaData[]
	 */
	public ICollateralMetaData[] getFields(String subtype) {
		if (subtype == null) {
			return null;
		}

		if (dapMap.containsKey(subtype)) {
			return (ICollateralMetaData[]) dapMap.get(subtype);
		}
		else {
			IDataProtectionProxy proxy = DataProtectionProxyFactory.getProxy();
			ICollateralMetaData[] rs = proxy.getCollateralMetaDataBySubType(subtype);
			// ICollateralMetaData[] rs = (ICollateralMetaData[])
			// proxy.getDataAccessProfile (ICMSConstant.INSTANCE_COLLATERAL,
			// subtype, ICMSConstant.LONG_INVALID_VALUE);
			dapMap.put(subtype, rs);
			return rs;
		}
	}

	/**
	 * Checks whether a collateral field of a subtype is updatable
	 * @param subtype
	 * @param field
	 * @return true if updatable
	 */
	public boolean getIsSCCUpdatable(String subtype, String field, boolean isSSC) {
		return getIsSCCUpdatable(subtype, field, null, isSSC);
	}

	/**
	 * Checks whether a collateral field of a subtype is updatable by country
	 * @param subtype
	 * @param field
	 * @param countryCode
	 * @return true if updatable
	 */
	public boolean getIsSCCUpdatable(String subtype, String field, String countryCode, boolean isSSC) {

		// DefaultLogger.debug(this, "Entering getIsSCCUpdatable");
		// DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@ isSSC value: "+isSSC);
		if ((subtype == null) || (field == null)) {
			return true;
		}
		ICollateralMetaData[] ret = getFields(subtype);

		String fieldName;
		for (int i = 0; i < ret.length; i++) {
			fieldName = ret[i].getFieldName();
			if (fieldName.equals(field)) {
				// only for property case which will enter countrycode
				// only applicable countries's property case which can access by
				// both SSC and CPC user
				// others are by CPC user
				if (countryCode != null) {
					if ((ret[i].getApplicableCountry() != null) && ret[i].getApplicableCountry().equals(countryCode)) {
						return !(ret[i].getSCCUpdatable() ^ isSSC);
					}
					else {
						return !(false ^ isSSC);
					}
				}

				// for other type of collateral
				return !(ret[i].getSCCUpdatable() ^ isSSC);
			}
		}
		return !(true ^ isSSC);
	}

	/**
	 * Checks whether a team type can edit the security subtype by country
	 * @param subtype
	 * @param countryCode
	 * @param isSSC
	 * @return true if accesible
	 */
	public boolean getIsTeamTypeAccessible(String subtype, String countryCode, boolean isSSC) {
		// DefaultLogger.debug(this, "Entering getIsTeamTypeAccessible");
		if (subtype == null) {
			return true;
		}

		ICollateralMetaData[] ret = getFields(subtype);
		boolean isCPCAccesible = false;
		boolean isSSCAccesible = false;
		if (ret != null) {
			for (int i = 0; i < ret.length; i++) {
				if ((countryCode == null) || (ret[i].getApplicableCountry() == null)) {
					isCPCAccesible = isCPCAccesible || !ret[i].getSCCUpdatable();
					isSSCAccesible = isSSCAccesible || ret[i].getSCCUpdatable();
				}
				else if ((ret[i].getApplicableCountry() != null) && (countryCode != null)) {
					// if has applicable country code, the sccupdatable field is
					// used for accesible check
					// else the access right is only for cpc
					if (ret[i].getApplicableCountry().equals(countryCode)) {
						isCPCAccesible = isCPCAccesible || !ret[i].getSCCUpdatable();
						isSSCAccesible = isSSCAccesible || ret[i].getSCCUpdatable();
					}
					else {
						isCPCAccesible = true;
						isSSCAccesible = isSSCAccesible || false;
					}
				}
			}
			return (!isSSC && isCPCAccesible) || (isSSC && isSSCAccesible);
		}
		return false;
	}

	public boolean isMultipleRoleAccessByCtryOrg(String type, String subtype, String ctryCode, String orgCode) {
		IDataProtectionProxy proxy = DataProtectionProxyFactory.getProxy();
		return proxy.isMultipleRoleAccessByCtryOrg(type, subtype, ctryCode, orgCode);
	}

	public boolean isMultipleRoleAccesByCountry(String subtype, String countryCode) {
		// DefaultLogger.debug(this,
		// "Entering isMultipleRoleAccesByCountry: "+subtype+"\t"+countryCode);

		if (subtype == null) {
			return true;
		}

		ICollateralMetaData[] ret = getFields(subtype);
		boolean isCPCAccesible = false;
		boolean isSSCAccesible = false;
		if (ret != null) {
			for (int i = 0; i < ret.length; i++) {
				if ((countryCode == null) || (ret[i].getApplicableCountry() == null)) {
					isCPCAccesible = isCPCAccesible || !ret[i].getSCCUpdatable();
					isSSCAccesible = isSSCAccesible || ret[i].getSCCUpdatable();
				}
				else if ((ret[i].getApplicableCountry() != null) && (countryCode != null)) {
					// if has applicable country code, the sccupdatable field is
					// used for accesible check
					// else the access right is only for cpc
					if (ret[i].getApplicableCountry().equals(countryCode)) {
						isCPCAccesible = isCPCAccesible || !ret[i].getSCCUpdatable();
						isSSCAccesible = isSSCAccesible || ret[i].getSCCUpdatable();
					}
					else {
						isCPCAccesible = true;
						isSSCAccesible = isSSCAccesible || false;
					}
				}
			}
			return (isCPCAccesible && isSSCAccesible);
		}
		return false;
	}
}
