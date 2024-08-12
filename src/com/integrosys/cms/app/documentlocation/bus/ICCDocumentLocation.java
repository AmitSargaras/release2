/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/ICCDocumentLocation.java,v 1.1 2004/02/17 02:12:02 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for CC
 * documentation location
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:02 $ Tag: $Name: $
 */
public interface ICCDocumentLocation extends IValueObject, Serializable {

	/**
	 * Get the documentation location ID
	 * @return long - the documentation location ID
	 */
	public long getDocLocationID();

	/**
	 * Get the documentation location category
	 * @return String - the documentation location category
	 */
	public String getDocLocationCategory();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID();

	/**
	 * Get the documentation originating location
	 * @return IBookingLocation - the documentation originating location
	 */
	public IBookingLocation getOriginatingLocation();

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
	 * Get the customer type
	 * @return String - the customer type
	 */
	public String getCustomerType();

	/**
	 * Set the documentation location
	 * @param aDocLocationID of long type
	 */
	public void setDocLocationID(long aDocLocationID);

	/**
	 * Set the documentation location category
	 * @param aDocLocationCategory of String type
	 */
	public void setDocLocationCategory(String aDocLocationCategory);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the customer ID param aCustomerID of long type
	 */
	public void setCustomerID(long aCustomerID);

	/**
	 * Set the documentation originating location
	 * @param anOriginatingLocation of IBookingLocation type
	 */
	public void setOriginatingLocation(IBookingLocation anOriginatingLocation);

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
	 * Set the customer type
	 * @param aCustomerType of String type
	 */
	public void setCustomerType(String aCustomerType);
}
