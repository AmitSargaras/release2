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
public interface EBBuildUpLocalHome extends EJBLocalHome {

	/**
	 * Create a BuildUp object
	 * @param fdrObj is the IBuildUp object
	 * @return EBBuildUp
	 * @throws javax.ejb.CreateException on error
	 */
	public EBBuildUpLocal create(IBuildUp fdrObj) throws CreateException;

	/**
	 * Find by Primary Key which is the BuildUp ID.
	 * @param pk of long type
	 * @return EBBuildUp
	 * @throws javax.ejb.FinderException on error
	 */
	public EBBuildUpLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBBuildUp
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBBuildUpLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
