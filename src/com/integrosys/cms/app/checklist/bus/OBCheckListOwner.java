package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/08/07 03:40:52 $ Tag: $Name: $
 */
public class OBCheckListOwner implements ICheckListOwner {

	private static final long serialVersionUID = 8342087731460066274L;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long subOwnerID = ICMSConstant.LONG_INVALID_VALUE;

	private String subOwnerType = null;

	private String subOwnerReference = null;

	private String legalReference = null;

	private String legalName = null;

	private String applicationType = null;

	/**
	 * Default Constructor
	 */
	public OBCheckListOwner() {
	}

	/**
	 * @param aLimitProfileID the long representation of the limit profile ID
	 * @param aSubOwnerID long representation of the sub owner ID
	 * @param aSubOwnerType String representation of the sub owner type
	 */
	public OBCheckListOwner(long aLimitProfileID, long aSubOwnerID, String aSubOwnerType) {
		this.limitProfileID = aLimitProfileID;
		this.subOwnerID = aSubOwnerID;
		this.subOwnerType = aSubOwnerType;
	}

	/**
	 * @param aLimitProfileID the long representation of the limit profile ID
	 * @param aSubOwnerID long representation of the sub owner ID
	 * @param aSubOwnerType String representation of the sub owner type
	 * @param applicationType application type of the Limit Profile, such as HP,
	 *        MO, CC, SF, etc.
	 */
	public OBCheckListOwner(long aLimitProfileID, long aSubOwnerID, String aSubOwnerType, String applicationType) {
		this(aLimitProfileID, aSubOwnerID, aSubOwnerType);
		this.applicationType = applicationType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	/**
	 * Get the legal name
	 * @return String - the legal name
	 */
	public String getLegalName() {
		return this.legalName;
	}

	/**
	 * Get the legal reference
	 * @return String - the legal reference
	 */
	public String getLegalReference() {
		return this.legalReference;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the sub owner ID which can be a borrower, co-borrower or pledger ID
	 * @return long - the sub owner ID
	 */
	public long getSubOwnerID() {
		return this.subOwnerID;
	}

	/**
	 * Get the owner reference
	 * @return String - the owner reference
	 */
	public String getSubOwnerReference() {
		return this.subOwnerReference;
	}

	/**
	 * Get the sub owner type which will indicate if the owner ID is a borrower,
	 * co-borrower or pledger ID
	 * @return String - the sub owner type
	 */
	public String getSubOwnerType() {
		return this.subOwnerType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	/**
	 * Set the legal reference
	 * @param aLegalReference of String type
	 */
	public void setLegalReference(String aLegalReference) {
		this.legalReference = aLegalReference;
	}

	/**
	 * Set the limit profile ID.
	 * @param aLimitProfileID the long representation of the limit profile ID
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the sub owner ID.
	 * @param aSubOwnerID the long representation of the sub owner ID
	 */
	public void setSubOwnerID(long aSubOwnerID) {
		this.subOwnerID = aSubOwnerID;
	}

	/**
	 * Set the sub owner reference
	 * @param aSubOwnerReference of String type
	 */
	public void setSubOwnerReference(String aSubOwnerReference) {
		this.subOwnerReference = aSubOwnerReference;
	}

	/**
	 * Set the sub owner type
	 * @param aSubOwnerType the String representationof the sub owner type
	 */
	public void setSubOwnerType(String aSubOwnerType) {
		this.subOwnerType = aSubOwnerType;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCheckListOwner [");
		buf.append("subOwnerType=");
		buf.append(subOwnerType);
		buf.append(", limitProfileID=");
		buf.append(limitProfileID);
		buf.append(", subOwnerID=");
		buf.append(subOwnerID);
		buf.append(", subOwnerReference=");
		buf.append(subOwnerReference);
		buf.append(", legalName=");
		buf.append(legalName);
		buf.append(", legalReference=");
		buf.append(legalReference);
		buf.append(", applicationType=");
		buf.append(applicationType);
		buf.append("]");
		return buf.toString();
	}

}
