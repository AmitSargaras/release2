/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBTATEntryLocalHome.java,v 1.2 2003/08/25 07:37:18 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBTATEntry Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/25 07:37:18 $ Tag: $Name: $
 */
public interface EBTATEntryLocalHome extends EJBLocalHome {
	/**
	 * Create a tat entry
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITATEntry object
	 * @return EBTATEntryLocal
	 * @throws CreateException on error
	 */
	public EBTATEntryLocal create(long limitProfileID, ITATEntry value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBTATEntryLocal
	 * @throws FinderException on error
	 */
	public EBTATEntryLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @return Collection of EBLimitLocal
	 * @throws FinderException on error
	 */
	public Collection findByLimitProfile(Long profileID) throws FinderException;
}