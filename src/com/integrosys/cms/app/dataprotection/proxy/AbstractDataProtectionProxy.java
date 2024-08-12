/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/AbstractDataProtectionProxy.java,v 1.9 2006/10/06 03:38:33 lini Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.dataprotection.bus.DataAccessProfileDAO;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Purpose: An abstract class that implements IDataProtectionProxy Description:
 * 
 * @author $jtan$<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/10/06 03:38:33 $ Tag: $Name: $
 */
public abstract class AbstractDataProtectionProxy implements IDataProtectionProxy {
	/**
	 * Get a list of Data Access Profile.
	 * 
	 * @param type data access module type
	 * @param subtype data access module subtype
	 * @param roleType user team type membership id
	 * @return an array of IDataAccessProfile objects
	 */
	public IDataAccessProfile[] getDataAccessProfile(String type, String subtype, long roleType) {
		DataAccessProfileDAO dao = new DataAccessProfileDAO();
		return dao.getDataAccessProfile(type, subtype, roleType);
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
		DataAccessProfileDAO dao = new DataAccessProfileDAO();
		return dao.isMultipleRoleAccessible(type, subtype, ctryCode, orgCode);
	}

	/**
	 * Check if a team can access the booking location given the segment code.
	 * 
	 * @param team of type ITeam
	 * @param loc of type IBookingLocation
	 * @param segmentCode customer segment code
	 * @return boolean
	 */
	public boolean isLocationAccessibleByUser(ITeam team, String segmentCode, IBookingLocation loc) {
		String country = null;
		String org = null;
		String segment = null;
		if (loc != null) {
			country = loc.getCountryCode();
			org = loc.getOrganisationCode();
		}
		segment = segmentCode == null ? "" : segmentCode;

		String[] countryList = team.getCountryCodes();
		String[] orgList = team.getOrganisationCodes();
		String[] segmentList = team.getSegmentCodes();

		// check for country
		boolean foundCountry = isInTheList(country, countryList);
		if (!foundCountry) {
			return false;
		}
		boolean foundOrg = isInTheList(org, orgList);
		if (!foundOrg) {
			return false;
		}
		boolean foundSegment = isInTheList(segment, segmentList);
		if (!foundSegment) {
			return false;
		}
		// all is found, so return true
		return true;
	}

	/**
	 * Check if a team can access any of the booking location in the list given
	 * the segment code.
	 * 
	 * @param team of type ITeam
	 * @param locList an ArrayList of IBookingLocation
	 * @param segmentCode customer segment code
	 * @return boolean
	 */
	public boolean isAnyLocationAccessibleByUser(ITeam team, String segmentCode, List locList) {
		Iterator i = locList.iterator();
		while (i.hasNext()) {
			IBookingLocation loc = (IBookingLocation) i.next();
			if (isLocationAccessibleByUser(team, segmentCode, loc)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to check if the user access countries are in dap location
	 * list.
	 * 
	 * @param accessCtry user access countries
	 * @param accessOrg user access organisation codes
	 * @param dapLoc data access location
	 * @param strictCheck true if all the user access countries must be in the
	 *        dap location
	 * @return true if user access is in the countries based on the strictCheck
	 *         indicator
	 */
	private boolean isDAPPassed(String[] accessCtry, String[] accessOrg, List dapLoc, boolean strictCheck,
			boolean grantedCtry) {
		boolean found = false;
		TOP_LOOP: for (int i = accessCtry.length - 1; i >= 0; i--) {
			List locList = getDAPLocation(accessCtry[i], dapLoc);
			if (!locList.isEmpty()) {
				for (int j = accessOrg.length - 1; j >= 0; j--) {
					if (isInDAPLocation(accessOrg[j], locList, false)) {
						found = true;
						if ((grantedCtry && !strictCheck) || (!grantedCtry && strictCheck)) {
							break TOP_LOOP;
						}
					}
					else {
						found = false;
						if ((grantedCtry && strictCheck) || (!grantedCtry && !strictCheck)) {
							break TOP_LOOP;
						}
					}
				}
			}
			else {
				found = false;
				if ((grantedCtry && strictCheck) || (!grantedCtry && !strictCheck)) {
					break;
				}
			}
		}
		return grantedCtry ? found : !found;
	}

	/**
	 * Check if the given action, event, and roleType can access data.
	 * 
	 * @param type module type
	 * @param subType module subtype
	 * @param roleType user role type
	 * @param accessCtryCodes user team countries that are trying to update the
	 *        data
	 * @param accessOrgCodes user team organisation codes that are trying to
	 *        update the data
	 * @param strictCheck false as long as any country can access
	 * @return true if data access is allowed, otherwise false
	 */
	public boolean isDataAccessAllowed(String type, String subType, long roleType, String[] accessCtryCodes,
			String[] accessOrgCodes, boolean strictCheck) {
		IDataAccessProfile[] profile = getCachedDAPList(type, subType, roleType);
		boolean isAllowed = false;
		if ((profile == null) || (profile.length == 0)) {
			return true;
		}
		for (int i = profile.length - 1; i >= 0; i--) {
			IDataAccessProfile dap = profile[i];
			boolean isGrantedPassed = false, isDeniedPassed = false;

			if (dap.getGrantedBkgLoc().isEmpty()) {
				isGrantedPassed = true;
				if (dap.getDeniedBkgLoc().isEmpty()) {
					continue;
				}
			}
			else {
				// check for granted countries
				isGrantedPassed = isDAPPassed(accessCtryCodes, accessOrgCodes, dap.getGrantedBkgLoc(), strictCheck,
						true);
			}

			// check for exception countries
			if (dap.getDeniedBkgLoc().isEmpty()) {
				isDeniedPassed = true;
			}
			else {
				isDeniedPassed = isDAPPassed(accessCtryCodes, accessOrgCodes, dap.getDeniedBkgLoc(), strictCheck, false);
			}
			isAllowed = isGrantedPassed && isDeniedPassed;

			if (!strictCheck && isAllowed) {
				return true;
			}
			if (strictCheck && !isAllowed) {
				return false;
			}
		}
		return isAllowed;
	}

	/**
	 * Check if given action, event, role type can access the field
	 * 
	 * @param type module type
	 * @param subType module subType
	 * @param fieldName field name
	 * @param roleType user role type
	 * @param ctryCode access country code
	 * @param orgCode access organisation code
	 * 
	 * @return true if field access allowed, otherwise false
	 */
	public boolean isFieldAccessAllowed(String type, String subType, String fieldName, long roleType, String ctryCode,
			String orgCode) {
		IDataAccessProfile[] profile = getCachedDAPList(type, subType, roleType);

		boolean isAllowed = false;
		for (int i = profile.length - 1; i >= 0; i--) {
			IDataAccessProfile dap = profile[i];
			if (dap.getFieldName() == null) {
				continue;
			}
			if (dap.getFieldName().equals(fieldName)) {
				return checkDAPAccess(dap, new String[] { ctryCode }, new String[] { orgCode }, false);
			}
		}
		return isAllowed;
	}

	private boolean checkDAPAccess(IDataAccessProfile dap, String[] accessCtryCodes, String[] accessOrgCodes,
			boolean strictCheck) {
		boolean isGrantedPassed = false, isDeniedPassed = false;

		if (dap.getGrantedBkgLoc().isEmpty()) {
			isGrantedPassed = true;
			if (dap.getDeniedBkgLoc().isEmpty()) {
				return true;
			}
		}
		else {
			// check for granted countries
			isGrantedPassed = isDAPPassed(accessCtryCodes, accessOrgCodes, dap.getGrantedBkgLoc(), strictCheck, true);
		}

		// check for exception countries
		if (dap.getDeniedBkgLoc().isEmpty()) {
			isDeniedPassed = true;
		}
		else {
			isDeniedPassed = isDAPPassed(accessCtryCodes, accessOrgCodes, dap.getDeniedBkgLoc(), strictCheck, false);
		}
		return isGrantedPassed && isDeniedPassed;
	}

	/**
	 * Helper method to check if a value can be found in the given value list.
	 * 
	 * @param value of type String
	 * @param valueList of type String[]
	 * @return true if the value can be found in the list, otherwise false
	 */
	private boolean isInTheList(String value, String[] valueList) {
		for (int i = valueList.length - 1; i >= 0; i--) {
			if (valueList[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to the DAP location given the access location
	 * 
	 * @param accessLoc access country or organisation code
	 * @param dapLoc a List of IBookingLocation objects
	 * @return a List of IBookingLocation objects
	 */
	private List getDAPLocation(String accessLoc, List dapLoc) {
		Iterator iterator = dapLoc.iterator();
		ArrayList list = new ArrayList();
		while (iterator.hasNext()) {
			IBookingLocation bkgLoc = (IBookingLocation) iterator.next();
			if (bkgLoc.getCountryCode().equals(accessLoc)
					|| bkgLoc.getCountryCode().equals(IDataProtectionProxy.ANY_COUNTRY)
					|| bkgLoc.getCountryCode().equals(IDataProtectionProxy.NO_COUNTRY)) {
				list.add(bkgLoc);
			}
		}
		return list;
	}

	/**
	 * Helper method to check if any of the country access is in the DAP
	 * location list.
	 * 
	 * @param accessLoc access country or organisation code
	 * @param dapLoc a List of IBookingLocation objects
	 * @param isCtry true for checking country, false for checking organisation
	 *        code
	 * @return true if any of the access country/organisation in in the DAP
	 *         location, otherwise false
	 */
	private boolean isInDAPLocation(String accessLoc, List dapLoc, boolean isCtry) {
		Iterator iterator = dapLoc.iterator();
		while (iterator.hasNext()) {
			IBookingLocation bkgLoc = (IBookingLocation) iterator.next();
			if (bkgLoc.getCountryCode().equals(IDataProtectionProxy.NO_COUNTRY)) {
				return false;
			}

			if (isCtry
					&& (bkgLoc.getCountryCode().equals(accessLoc) || bkgLoc.getCountryCode().equals(
							IDataProtectionProxy.ANY_COUNTRY))) {
				return true;
			}
			else if (!isCtry
					&& (bkgLoc.getOrganisationCode().equals(accessLoc) || bkgLoc.getOrganisationCode().equals(
							IDataProtectionProxy.ANY_ORGANISATION))) {
				return true;
			}
		}
		return false;
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
	protected abstract IDataAccessProfile[] getCachedDAPList(String type, String subType, long roleType);
}
