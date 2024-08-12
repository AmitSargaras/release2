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
public interface EBPropertyTypeLocalHome extends EJBLocalHome {

	/**
	 * Create a PropertyType object
	 * @param fdrObj is the IPropertyType object
	 * @return EBPropertyType
	 * @throws javax.ejb.CreateException on error
	 */
	public EBPropertyTypeLocal create(IPropertyType fdrObj) throws CreateException;

	/**
	 * Find by Primary Key which is the PropertyType ID.
	 * @param pk of long type
	 * @return EBPropertyType
	 * @throws javax.ejb.FinderException on error
	 */
	public EBPropertyTypeLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBPropertyType
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBPropertyTypeLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
