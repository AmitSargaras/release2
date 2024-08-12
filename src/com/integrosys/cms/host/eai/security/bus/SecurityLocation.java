package com.integrosys.cms.host.eai.security.bus;

/**
 * A component to represent a registered location of a security/collateral,
 * having country and organisation (branch).
 * 
 * @author Chong Jun Yong
 * @since 2003/11/05
 */
public class SecurityLocation implements java.io.Serializable {

	private static final long serialVersionUID = 3371936858099117796L;

	private String locationCountry;

	private String locationOrganization;

	private int locationId;

	private String locationOrganisationDesc;

	public SecurityLocation() {
		super();
	}

	public SecurityLocation(String country, String organisation) {
		locationCountry = country;
		locationOrganization = organisation;
	}

	/**
	 * Returns the value of field 'locationCountry'.
	 * 
	 * @return the value of field 'locationCountry'.
	 */
	public String getLocationCountry() {
		return this.locationCountry;
	}

	public int getLocationId() {
		return locationId;
	}

	/**
	 * Returns the value of field 'locationOrganization'.
	 * 
	 * @return the value of field 'locationOrganization'.
	 */
	public String getLocationOrganisation() {
		return this.locationOrganization;
	}

	public String getLocationOrganisationDesc() {
		return locationOrganisationDesc;
	}

	/**
	 * Sets the value of field 'locationCountry'.
	 * 
	 * @param locationCountry the value of field 'locationCountry'.
	 */
	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}

	public void setLocationId(int id) {
		locationId = id;
	}

	/**
	 * Sets the value of field 'locationOrganization'.
	 * 
	 * @param locationOrganisation the value of field 'locationOrganization'.
	 */
	public void setLocationOrganisation(String locationOrganisation) {
		this.locationOrganization = locationOrganisation;
	}

	public void setLocationOrganisationDesc(String locationOrganisationDesc) {
		this.locationOrganisationDesc = locationOrganisationDesc;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("SecurityLocation [");
		buf.append("Country=");
		buf.append(locationCountry);
		buf.append(", Organisation=");
		buf.append(locationOrganization);
		if (locationOrganisationDesc != null) {
			buf.append(", Organisation Description=");
			buf.append(locationOrganisationDesc);
		}
		buf.append("]");
		return buf.toString();
	}

}
