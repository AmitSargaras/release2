/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityLocalHome.java,v 1.3 2003/07/03 07:13:01 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCMSLegalEntity Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/03 07:13:01 $ Tag: $Name: $
 */
public interface EBCMSLegalEntityLocalHome extends EJBLocalHome {
	/**
	 * Create a customer Legal Entity information type
	 * 
	 * @param value is the ICMSLegalEntity object
	 * @return EBCMSLegalEntityLocal
	 * @throws CreateException on error
	 */
	public EBCMSLegalEntityLocal create(ICMSLegalEntity value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the Legal Entity ID
	 * @return EBCMSLegalEntityLocal
	 * @throws FinderException on error
	 */
	public EBCMSLegalEntityLocal findByPrimaryKey(Long pk) throws FinderException;
}