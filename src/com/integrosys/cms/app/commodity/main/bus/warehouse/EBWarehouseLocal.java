/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/EBWarehouseLocal.java,v 1.2 2004/06/04 04:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

/**
 * Defines TitleDOcument home methods for clients.
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since   $Date: 2004/06/04 04:53:33 $
 * Tag:     $Name:  $
 */

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

public interface EBWarehouseLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return IWarehouse - the object encapsulating the cc certificate info
	 * @throws com.integrosys.cms.app.commodity.main.CommodityException -
	 *         wrapper of any exceptions within.
	 */
	public IWarehouse getValue() throws CommodityException;

	/**
	 * Set the cc certificate object
	 * @param value - an object of IWarehouse
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         thrown when more than one client accessing the method same time.
	 */
	public void setValue(IWarehouse value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException;

}