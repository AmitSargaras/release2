/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/IDataProtectionProxy.java,v 1.5 2006/03/17 07:20:31 hshii Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.util.List;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.dataprotection.bus.ICollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Represents the interface for retrieving a list of data access profile.
 * 
 * @author $jtan$<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/03/17 07:20:31 $ Tag: $Name: $
 */
public interface IDataProtectionProxy {
	public static final String DAP_EDIT = "EDIT";

	public static final String ANY_COUNTRY = "*";

	public static final String ANY_ORGANISATION = "*";

	public static final String NO_COUNTRY = "-";

	public static final String No_ORGANISATION = "-";

	/**
	 * @return a list of of ICollateralMetaData given a subType
	 */
	public ICollateralMetaData[] getCollateralMetaDataBySubType(String type);

	/**
	 * Get a list of Data Access Profile.
	 * 
	 * @param type data access module type
	 * @param subtype data access module subtype
	 * @param roleType user team type membership id
	 * @return an array of IDataAccessProfile objects
	 */
	public IDataAccessProfile[] getDataAccessProfile(String type, String subtype, long roleType);

	/**
	 * Check if a team can access the booking location given the segment code.
	 * 
	 * @param team of type ITeam
	 * @param loc of type IBookingLocation
	 * @param segmentCode customer segment code
	 * @return boolean
	 */
	public boolean isLocationAccessibleByUser(ITeam team, String segmentCode, IBookingLocation loc);

	/**
	 * Check if a team can access any of the booking location in the list given
	 * the segment code.
	 * 
	 * @param team of type ITeam
	 * @param locList an ArrayList of IBookingLocation
	 * @param segmentCode customer segment code
	 * @return boolean
	 */
	public boolean isAnyLocationAccessibleByUser(ITeam team, String segmentCode, List locList);

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
			String[] accessOrgCodes, boolean strictCheck);

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
			String orgCode);

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
	public boolean isMultipleRoleAccessByCtryOrg(String type, String subtype, String ctryCode, String orgCode);
}
