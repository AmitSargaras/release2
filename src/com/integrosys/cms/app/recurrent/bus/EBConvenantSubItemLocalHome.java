/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * @author user
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface EBConvenantSubItemLocalHome extends EJBLocalHome {
	/**
	 * Create a convenant item information
	 * @param aConvenantID of Long type
	 * @param anIConvenantSubItem of IConvenantSubItem type
	 * @return EBConvenantSubItemLocal
	 * @throws CreateException on error
	 */
	public EBConvenantSubItemLocal create(Long aConvenantID, IConvenantSubItem anIConvenantSubItem)
			throws CreateException;

	/**
	 * Find by Primary Key which is the convenant item ID.
	 * @param aConvenantSubItemID of Long type
	 * @return EBConvenantSubItemLocal
	 * @throws FinderException on error
	 */
	public EBConvenantSubItemLocal findByPrimaryKey(Long aConvenantSubItemID) throws FinderException;

	/**
	 * Find by unique Key which is the checklist sub item ID.
	 * @param aConvenantSubItemRef of long type
	 * @return EBConvenantSubItemLocal
	 * @throws FinderException on error
	 */
	public EBConvenantSubItemLocal findByConvenantSubItemRef(long aConvenantSubItemRef) throws FinderException;

}
