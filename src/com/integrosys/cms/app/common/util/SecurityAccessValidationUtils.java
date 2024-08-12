package com.integrosys.cms.app.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Common Utils to check for security access for infra of CMS. Normally checking
 * on Team against the customer and limit profile being accessed.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class SecurityAccessValidationUtils {
	public static final int ACCESS_COUNTRY = 0;

	public static final int ACCESS_ORGANISATION = 1;

	public static final int ACCESS_SEGMENT = 2;

	/**
	 * @param aTeam team to be used to check having access to limit profile
	 *        supplied
	 * @param anILimitProfile limit profile that is being accessed
	 * @return whether team has the access to limit profile
	 */
	public static boolean isTeamHasAccessToLimitProfile(ITeam aTeam, ILimitProfile anILimitProfile) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(anILimitProfile, "'anILimitProfile' must not be null");

		IBookingLocation bkgLocation = anILimitProfile.getOriginatingLocation();
		return SecurityAccessValidationUtils.isTeamHasAccessToBookingLocation(aTeam, bkgLocation);
	}

	/**
	 * @param aTeam team to be used to check having access to customer supplied
	 * @param customer customer that is being accessed
	 * @return whether team has the access to customer
	 */
	public static boolean isTeamHasAccessToCustomer(ITeam aTeam, ICMSCustomer customer) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(customer, "'customer' must not be null");

		IBookingLocation bkgLocation = customer.getOriginatingLocation();
		ICMSLegalEntity legalEntity = customer.getCMSLegalEntity();
		return SecurityAccessValidationUtils.isTeamHasAccessOfSegment(aTeam, legalEntity.getCustomerSegment())
				&& SecurityAccessValidationUtils.isTeamHasAccessToBookingLocation(aTeam, bkgLocation);
	}

	/**
	 * @param aTeam team to be used to check having access to limit profile
	 *        supplied
	 * @param aLimitProfile limit profile that is being accessed
	 * @param accessType access type to be checked, either country, organisation
	 *        or segment
	 * @return whether team has the access to limit profile on access type
	 */
	public static boolean isTeamHasAccessToLimitProfile(ITeam aTeam, ILimitProfile aLimitProfile, int accessType) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(aLimitProfile, "'aLimitProfile' must not be null");

		if (accessType == ACCESS_COUNTRY) {
			String country = aLimitProfile.getOriginatingLocation().getCountryCode();
			return SecurityAccessValidationUtils.isTeamHasAccessOfCountry(aTeam, country);
		}
		else if (accessType == ACCESS_ORGANISATION) {
			String orgCode = aLimitProfile.getOriginatingLocation().getOrganisationCode();
			return SecurityAccessValidationUtils.isTeamHasAccessOfOrganisation(aTeam, orgCode);
		}
		else {
			throw new IllegalArgumentException("Invalid accessType: [" + accessType + "]");
		}
	}

	/**
	 * @param aTeam team to be used to check having access to customer supplied
	 * @param customer customer that is being accessed
	 * @param accessType access type to be checked, either country, organisation
	 *        or segment
	 * @return whether team has the access to customer on access type
	 */
	public static boolean isTeamHasAccessToCustomer(ITeam aTeam, ICMSCustomer customer, int accessType) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(customer, "'customer' must not be null");

		if (accessType == ACCESS_COUNTRY) {
			String country = customer.getOriginatingLocation().getCountryCode();
			return SecurityAccessValidationUtils.isTeamHasAccessOfCountry(aTeam, country);
		}
		else if (accessType == ACCESS_ORGANISATION) {
			String orgCode = customer.getOriginatingLocation().getOrganisationCode();
			return SecurityAccessValidationUtils.isTeamHasAccessOfOrganisation(aTeam, orgCode);
		}
		else if (accessType == ACCESS_SEGMENT) {
			ICMSLegalEntity legalEntity = customer.getCMSLegalEntity();
			String segmentCode = legalEntity.getCustomerSegment();
			return SecurityAccessValidationUtils.isTeamHasAccessOfSegment(aTeam, segmentCode);
		}
		else {
			throw new IllegalArgumentException("Invalid accessType: [" + accessType + "]");
		}
	}

	/**
	 * @param aTeam team to be used to check having access to booking location
	 *        supplied
	 * @param bkgLocation booking location that is being accessed
	 * @return whether team has the access to booking location
	 */
	public static boolean isTeamHasAccessToBookingLocation(ITeam aTeam, IBookingLocation bkgLocation) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(bkgLocation, "'bkgLocation' must not be null");

		boolean hasCountryAccess = SecurityAccessValidationUtils.isTeamHasAccessOfCountry(aTeam, bkgLocation.getCountryCode());
		boolean hasOrgCodeAccess = SecurityAccessValidationUtils.isTeamHasAccessOfOrganisation(aTeam, bkgLocation
				.getOrganisationCode());

		return (hasCountryAccess && hasOrgCodeAccess);
	}

	/**
	 * @param aTeam team to be used to check having access to country code
	 *        supplied
	 * @param country country code that is being accessed
	 * @return whether team has the access to the country
	 */
	public static boolean isTeamHasAccessOfCountry(ITeam aTeam, String country) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(country, "'country' must not be null");

		String[] countries = aTeam.getCountryCodes();
		return (countries == null) ? false : ArrayUtils.contains(countries, country);
	}

	/**
	 * @param aTeam team to be used to check having access to organsation code
	 *        supplied
	 * @param orgCode organisation code that is being accessed
	 * @return whether team has the access to the organisation
	 */
	public static boolean isTeamHasAccessOfOrganisation(ITeam aTeam, String orgCode) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(orgCode, "'orgCode' must not be null");

		String[] orgs = aTeam.getOrganisationCodes();
		return (orgs == null) ? false : ArrayUtils.contains(orgs, orgCode);
	}

	/**
	 * @param aTeam team to be used to check having access to segment code
	 *        supplied
	 * @param segment segment code that is being accessed
	 * @return whether team has the access to the segment
	 */
	public static boolean isTeamHasAccessOfSegment(ITeam aTeam, String segment) {
		Validate.notNull(aTeam, "'aTeam' must not be null");
		Validate.notNull(segment, "'segment' must not be null");

		String[] segments = aTeam.getSegmentCodes();
		return (segments == null) ? false : ArrayUtils.contains(segments, segment);
	}

}
