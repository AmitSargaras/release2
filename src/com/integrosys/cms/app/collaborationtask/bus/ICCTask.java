/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/ICCTask.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface defines the list of attributes that is required for CC Task
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */
public interface ICCTask extends IValueObject, Serializable {
	/**
	 * Get the task ID
	 * @return long - the task ID
	 */
	public long getTaskID();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the customer category
	 * @return String - the customer category
	 */
	public String getCustomerCategory();

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID();

	/*
	 * Get the legal Ref
	 * 
	 * @return String - the legal Ref
	 */
	public String getLegalRef();

	/**
	 * Get the legal Name
	 * @return String - the legal name
	 */
	public String getLegalName();

	/**
	 * Get the domicile country
	 * @return String - the domicile country
	 */
	public String getDomicileCountry();

	/**
	 * Get the customer type
	 * @return String - the customer type
	 */
	public String getCustomerType();

	/**
	 * Get the organisation code
	 */
	public String getOrgCode();

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks();

	/**
	 * Get the deleted indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	/**
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the customer category
	 * @param aCustomerCategory of String type
	 */
	public void setCustomerCategory(String aCustomerCategory);

	/**
	 * Set the customer ID
	 * @param aCustomerID of String type
	 */
	public void setCustomerID(long aCustomerID);

	/**
	 * Set the legal Ref
	 * @param aLegalRef of String type
	 */
	public void setLegalRef(String aLegalRef);

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName);

	/**
	 * Set the domicile country
	 * @param aDomicileCountry of String type
	 */
	public void setDomicileCountry(String aDomicileCountry);

	/**
	 * Set the customer type
	 * @param aCustomerType of String type
	 */
	public void setCustomerType(String aCustomerType);

	/**
	 * Set the organisation code
	 * @param anOrgCode of String type
	 */
	public void setOrgCode(String anOrgCode);

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the deleted indicator
	 * @param anIsDeletedInd - true if it is deleted and false otherwisef
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);
}
