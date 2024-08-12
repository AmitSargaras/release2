package com.integrosys.cms.app.contractfinancing.bus;

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
public interface EBAdvanceLocalHome extends EJBLocalHome {

	/**
	 * Create a Advance object
	 * @param obj is the IAdvance object
	 * @return EBAdvance
	 * @throws javax.ejb.CreateException on error
	 */
	public EBAdvanceLocal create(IAdvance obj) throws CreateException;

	/**
	 * Find by Primary Key which is the Advance ID.
	 * @param pk of long type
	 * @return EBAdvance
	 * @throws javax.ejb.FinderException on error
	 */
	public EBAdvanceLocal findByPrimaryKey(Long pk) throws FinderException;

}
