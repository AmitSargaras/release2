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
public interface EBProjectScheduleLocalHome extends EJBLocalHome {

	/**
	 * Create a ProjectSchedule object
	 * @param fdrObj is the IProjectSchedule object
	 * @return EBProjectSchedule
	 * @throws javax.ejb.CreateException on error
	 */
	public EBProjectScheduleLocal create(IProjectSchedule fdrObj) throws CreateException;

	/**
	 * Find by Primary Key which is the ProjectSchedule ID.
	 * @param pk of long type
	 * @return EBProjectSchedule
	 * @throws javax.ejb.FinderException on error
	 */
	public EBProjectScheduleLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBProjectSchedule
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBProjectScheduleLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
