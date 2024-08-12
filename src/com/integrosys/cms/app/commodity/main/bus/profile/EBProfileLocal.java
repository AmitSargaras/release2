/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBProfileLocal.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * Defines Profile methods for clients.
 *
 * @author  $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since   $Date: 2004/08/12 03:14:49 $
 */

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

public interface EBProfileLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return IProfile - the object encapsulating the cc certificate info
	 * @throws com.integrosys.cms.app.commodity.main.CommodityException -
	 *         wrapper of any exceptions within.
	 */
	public IProfile getValue() throws CommodityException;

	/**
	 * Set the cc certificate object
	 * @param value - an object of IProfile
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         thrown when more than one client accessing the method same time.
	 */
	public void setValue(IProfile value) throws CommodityException, ConcurrentUpdateException, VersionMismatchException;

	/*
	 * public ISupplier[] getSuppliers(); public void setSuppliers(ISupplier[]
	 * suppliers); public void addSuppliers(ISupplier[] suppliers); public void
	 * updateSuppliers(ISupplier[] suppliers); public void
	 * removeSuppliers(long[] supplierIDs); Collection getSuppliersCMR(); void
	 * setSuppliersCMR(Collection suppliersCMR);
	 * 
	 * 
	 * public IBuyer[] getBuyers(); public void setBuyers(IBuyer[] suppliers);
	 * public void addBuyers(IBuyer[] suppliers); public void
	 * updateBuyers(IBuyer[] suppliers); public void removeBuyers(long[]
	 * supplierIDs); Collection getBuyersCMR(); void setBuyersCMR(Collection
	 * suppliersCMR);
	 * 
	 * public void softDelete();
	 */
}