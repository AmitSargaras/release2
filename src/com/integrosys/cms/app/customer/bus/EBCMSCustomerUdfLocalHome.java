package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public interface EBCMSCustomerUdfLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Contact information type
	 * 
	 * @param legalID the credit application ID of type long
	 * @param value is the IContact object
	 * @return EBContactLocal
	 * @throws CreateException on error
	 */
	public EBCMSCustomerUdfLocal create(ICMSCustomerUdf value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is String value of the contact ID
	 * @return EBContactLocal
	 * @throws FinderException on error
	 */
	public EBCMSCustomerUdfLocal findByPrimaryKey(Long pk) throws FinderException;
}
