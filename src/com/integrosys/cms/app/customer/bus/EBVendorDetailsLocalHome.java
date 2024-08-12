package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBVendorDetailsLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Contact information type
	 * 
	 * @param legalID the credit application ID of type long
	 * @param value is the IContact object
	 * @return EBContactLocal
	 * @throws CreateException on error
	 */
	public EBVendorDetailsLocal create(IVendor value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is String value of the contact ID
	 * @return EBContactLocal
	 * @throws FinderException on error
	 */
	public EBVendorDetailsLocal findByPrimaryKey(Long pk) throws FinderException;
}
