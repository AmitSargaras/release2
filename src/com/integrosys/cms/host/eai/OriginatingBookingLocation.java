package com.integrosys.cms.host.eai;

/**
 * A component to represent a booking location, having country and organisation
 * (branch).
 * 
 * @author marvin
 * @author Chong Jun Yong
 */
public class OriginatingBookingLocation implements java.io.Serializable {

	private static final long serialVersionUID = 8959110446771559431L;

	private Integer originatingLocationId;

	private String originatingLocationCountry;

	private String originatingLocationOrganisation;

	private String originatingLocationOrganisationDesc;

	public OriginatingBookingLocation() {
		super();
	}

	public OriginatingBookingLocation(String country, String organisation) {
		this(country, organisation, null);
	}

	public OriginatingBookingLocation(String country, String organisation, String organisationDesc) {
		this.originatingLocationCountry = country;
		this.originatingLocationOrganisation = organisation;
		this.originatingLocationOrganisationDesc = organisationDesc;
	}

	public OriginatingBookingLocation(Integer id, String country, String organisation, String organisationDesc) {
		this(country, organisation, organisationDesc);
		this.originatingLocationId = id;
	}

	/**
	 * Returns the value of field 'originatingLocationCountry'.
	 * 
	 * @return the value of field 'originatingLocationCountry'.
	 */
	public String getOriginatingLocationCountry() {
		return this.originatingLocationCountry;
	}

	public Integer getOriginatingLocationId() {
		return this.originatingLocationId;
	}

	/**
	 * Returns the value of field 'originatingLocationOrganisation'.
	 * 
	 * @return the value of field 'originatingLocationOrganisation'.
	 */
	public String getOriginatingLocationOrganisation() {
		return this.originatingLocationOrganisation;
	}

	public String getOriginatingLocationOrganisationDesc() {
		return originatingLocationOrganisationDesc;
	}

	/**
	 * Sets the value of field 'originatingLocationCountry'.
	 * 
	 * @param originatingLocationCountry the value of field
	 *        'originatingLocationCountry'.
	 */
	public void setOriginatingLocationCountry(String originatingLocationCountry) {
		this.originatingLocationCountry = originatingLocationCountry;
	}

	public void setOriginatingLocationId(Integer id) {
		this.originatingLocationId = id;
	}

	/**
	 * Sets the value of field 'originatingLocationOrganisation'.
	 * 
	 * @param originatingLocationOrganisation the value of field
	 *        'originatingLocationOrganisation'.
	 */
	public void setOriginatingLocationOrganisation(String originatingLocationOrganisation) {
		this.originatingLocationOrganisation = originatingLocationOrganisation;
	}

	public void setOriginatingLocationOrganisationDesc(String originatingLocationOrganisationDesc) {
		this.originatingLocationOrganisationDesc = originatingLocationOrganisationDesc;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OriginatingBookingLocation [");
		buf.append("Country=");
		buf.append(originatingLocationCountry);
		buf.append(", Organisation=");
		buf.append(originatingLocationOrganisation);
		if (originatingLocationOrganisationDesc != null) {
			buf.append(", Organisation Description=");
			buf.append(originatingLocationOrganisationDesc);
		}
		buf.append("]");
		return buf.toString();
	}

}
