/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgorLocal.java,v 1.1 2003/07/23 11:39:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

/**
 * Local interface to EBPledgorBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 11:39:05 $ Tag: $Name: $
 */
public interface EBPledgorLocal extends EJBLocalObject {
	/**
	 * Get the pledgor information.
	 * 
	 * @return a pledgor
	 */
	public IPledgor getValue();

	/**
	 * Set the pledgor to this entity.
	 * 
	 * @param pledgor is of type IPledgor
	 */
	public void setValue(IPledgor pledgor);

	/**
	 * Get the pledgor legal ID.
	 * 
	 * @return of type String
	 */
	public String getLegalID();
}