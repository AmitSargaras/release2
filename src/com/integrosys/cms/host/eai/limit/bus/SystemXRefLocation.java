/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.4.3</a>, using an XML
 * Schema.
 * $Id: SystemXRefLocation.java,v 1.2 2003/10/23 02:32:22 slong Exp $
 */

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/
package com.integrosys.cms.host.eai.limit.bus;

/**
 * Class SystemXRefLocation.
 * 
 * @version $Revision: 1.2 $ $Date: 2003/10/23 02:32:22 $
 */
public class SystemXRefLocation implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field _locationCountry
	 */
	private java.lang.String _locationCountry;

	/**
	 * Field _locationOrganization
	 */
	private java.lang.String _locationOrganization;

	private long locationId;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public SystemXRefLocation() {
		super();
	} // -- SystemXRefLocation()

	// -----------/
	// - Methods -/
	// -----------/
	public long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(long id) {
		this.locationId = id;
	}

	/**
	 * Returns the value of field 'locationCountry'.
	 * 
	 * @return the value of field 'locationCountry'.
	 */
	public java.lang.String getLocationCountry() {
		return this._locationCountry;
	} // -- java.lang.String getLocationCountry()

	/**
	 * Returns the value of field 'locationOrganization'.
	 * 
	 * @return the value of field 'locationOrganization'.
	 */
	public java.lang.String getLocationOrganization() {
		return this._locationOrganization;
	} // -- java.lang.String getLocationOrganization()

	/**
	 * Sets the value of field 'locationCountry'.
	 * 
	 * @param locationCountry the value of field 'locationCountry'.
	 */
	public void setLocationCountry(java.lang.String locationCountry) {
		this._locationCountry = locationCountry;
	} // -- void setLocationCountry(java.lang.String)

	/**
	 * Sets the value of field 'locationOrganization'.
	 * 
	 * @param locationOrganization the value of field 'locationOrganization'.
	 */
	public void setLocationOrganization(java.lang.String locationOrganization) {
		this._locationOrganization = locationOrganization;
	} // -- void setLocationOrganization(java.lang.String)

}
