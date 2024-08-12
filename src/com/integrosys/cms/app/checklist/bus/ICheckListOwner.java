/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListOwner.java,v 1.2 2006/08/07 03:40:52 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;

/**
 * This interface defines the list of attributes that is required for a
 * checklist owner
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/08/07 03:40:52 $ Tag: $Name: $
 */
public interface ICheckListOwner extends Serializable {
	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Set the limit profile ID.
	 * @param aLimitProfileID the long representation of the limit profile ID
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Get the sub owner ID which can be a borrower, co-borrower or pledger ID
	 * @return long - the sub owner ID
	 */
	public long getSubOwnerID();

	/**
	 * Get the sub owner type which will indicate if the owner ID is a borrower,
	 * co-borrower or pledger ID
	 * @return String - the sub owner type
	 */
	public String getSubOwnerType();

	/**
	 * Get the owner reference
	 * @return String - the owner reference
	 */
	public String getSubOwnerReference();

	/**
	 * Get the legal reference
	 * @return String - the legal reference
	 */
	public String getLegalReference();

	/**
	 * Get the legal name
	 * @return String - the legal name
	 */
	public String getLegalName();

	/**
	 * Set the sub owner ID.
	 * @param aSubOwnerID the long representation of the sub owner ID
	 */
	public void setSubOwnerID(long aSubOwnerID);

	/**
	 * Set the sub owner type
	 * @param aSubOwnerType the String representationof the sub owner type
	 */
	public void setSubOwnerType(String aSubOwnerType);

	/**
	 * Set the sub owner reference
	 * @param aSubOwnerReference of String type
	 */
	public void setSubOwnerReference(String aSubOwnerReference);

	/**
	 * Set the legal reference
	 * @param aLegalReference of String type
	 */
	public void setLegalReference(String aLegalReference);

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName);

	public String getApplicationType();

	public void setApplicationType(String applicationType);
}
