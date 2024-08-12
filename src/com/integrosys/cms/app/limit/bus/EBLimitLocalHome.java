/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitLocalHome.java,v 1.3 2003/07/10 06:28:03 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface to the EBLimit Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/10 06:28:03 $ Tag: $Name: $
 */
public interface EBLimitLocalHome extends EJBLocalHome {
	/**
	 * Create a Customer
	 * 
	 * @param value is the ILimit object
	 * @return EBLimitLocal
	 * @throws CreateException
	 */
	public EBLimitLocal create(ILimit value) throws CreateException;

	/**
	 * Find by primary Key, the customer ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBSMECreditApplication
	 * @throws FinderException on error
	 */
	public EBLimitLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBLimitLocal
	 * @throws FinderException on error
	 */
	public Collection findByLimitProfile(Long profileID, String status) throws FinderException;
}