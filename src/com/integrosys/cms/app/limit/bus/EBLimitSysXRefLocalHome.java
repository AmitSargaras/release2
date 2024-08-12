/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitSysXRefLocalHome.java,v 1.1 2003/07/14 08:22:31 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCoBorrowerLimit Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 08:22:31 $ Tag: $Name: $
 */
public interface EBLimitSysXRefLocalHome extends EJBLocalHome {
	/**
	 * Create a limit x-ref information type
	 * 
	 * @param value is the ILimitSysXRef object
	 * @return EBLimitSysXRefLocal
	 * @throws CreateException on error
	 */
	public EBLimitSysXRefLocal create(ILimitSysXRef value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBLimitSysXRefLocal
	 * @throws FinderException on error
	 */
	public EBLimitSysXRefLocal findByPrimaryKey(Long pk) throws FinderException;
}