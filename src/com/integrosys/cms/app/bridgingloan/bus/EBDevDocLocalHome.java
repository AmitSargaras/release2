package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBDevDocLocalHome extends EJBLocalHome {

	/**
	 * Create a DevDoc object
	 * @param obj is the IDevDoc object
	 * @return EBDevDoc
	 * @throws javax.ejb.CreateException on error
	 */
	public EBDevDocLocal create(IDevelopmentDoc obj) throws CreateException;

	/**
	 * Find by Primary Key which is the DevDoc ID.
	 * @param pk of long type
	 * @return EBDevDoc
	 * @throws javax.ejb.FinderException on error
	 */
	public EBDevDocLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBDevDoc
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBDevDocLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
