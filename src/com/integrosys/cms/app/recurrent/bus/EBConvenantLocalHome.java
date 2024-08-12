/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBConvenantLocalHome.java,v 1.2 2003/07/28 12:48:33 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBConvenant Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/28 12:48:33 $ Tag: $Name: $
 */
public interface EBConvenantLocalHome extends EJBLocalHome {
	/**
	 * Create a convenant information
	 * @param aCheckListID of Long type
	 * @param anIConvenant of IConvenant type
	 * @return EBConvenantLocal
	 * @throws CreateException on errors
	 */
	public EBConvenantLocal create(Long aCheckListID, IConvenant anIConvenant) throws CreateException;

	/**
	 * Find by Primary Key which is the convenant ID.
	 * @param aConvenantID of Long type
	 * @return EBConvenantLocal
	 * @throws FinderException on errors
	 */
	public EBConvenantLocal findByPrimaryKey(Long aConvenantID) throws FinderException;

	/**
	 * Find by Convenant reference
	 * @param aConvenantRef of long type
	 * @return EBConvenantLocal
	 * @throws FinderException on errors
	 */
	public EBConvenantLocal findByConvenantRef(long aConvenantRef) throws FinderException;
}