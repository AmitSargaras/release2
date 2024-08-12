/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCCTaskLocalHome.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local Home interface for the CC collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */

public interface EBCCTaskLocalHome extends EJBLocalHome {
	/**
	 * Create a CC collaboration task
	 * @param anICCTask of ICCTask
	 * @return EBCCTaskLocal - the local handler for the created CC task
	 * @throws CreateException if creation fails
	 */
	public EBCCTaskLocal create(ICCTask anICCTask) throws CreateException;

	/**
	 * Find by primary Key, the CC task ID
	 * @param aPK - Long
	 * @return EBCCTaskLocal - the local handler for the CC task that has the PK
	 *         as specified
	 * @throws FinderException
	 */
	public EBCCTaskLocal findByPrimaryKey(Long aPK) throws FinderException;

}