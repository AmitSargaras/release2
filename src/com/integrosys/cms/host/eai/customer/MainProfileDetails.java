package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.customer.bus.MainProfile;


/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class MainProfileDetails implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field mainProfile
	 */
	private MainProfile mainProfile;

	/**
	 * Field creditGrade
	 */
	private java.util.Vector creditGrade;

	public MainProfile getMainProfile() {
		return mainProfile;
	}

	public void setMainProfile(MainProfile mainProfile) {
		this.mainProfile = mainProfile;
	}

	public java.util.Vector getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(java.util.Vector creditGrade) {
		this.creditGrade = creditGrade;
	}

}
