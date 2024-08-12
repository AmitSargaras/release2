package com.integrosys.cms.host.eai.customer;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerMessageBody extends EAIBody implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field mainProfileDetails
	 */
	private MainProfileDetails mainProfileDetails;

	private String CIFId;

	private String oldCIFId;

	private String newCIFId;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public CustomerMessageBody() {
		super();
	} // -- Message()

	// -----------/
	// - Methods -/
	// -----------/
	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public String getOldCIFId() {
		return oldCIFId;
	}

	public void setOldCIFId(String oldCIFId) {
		this.oldCIFId = oldCIFId;
	}

	public String getNewCIFId() {
		return newCIFId;
	}

	public void setNewCIFId(String newCIFId) {
		this.newCIFId = newCIFId;
	}

	/**
	 * Returns the value of field 'mainProfileDetails'.
	 * 
	 * @return the value of field 'mainProfileDetails'.
	 */
	public MainProfileDetails getMainProfileDetails() {
		return this.mainProfileDetails;
	}

	/**
	 * Sets the value of field 'mainProfileDetails'.
	 * 
	 * @param mainProfileDetails the value of field 'mainProfileDetails'.
	 */
	public void setMainProfileDetails(MainProfileDetails mainProfileDetails) {
		this.mainProfileDetails = mainProfileDetails;
	}

}
