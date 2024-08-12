/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/EBSubLimitType.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-16
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitType.java
 */
public interface EBSubLimitType extends EJBObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * 
	 * @return ISubLimitType - the object encapsulating the cc certificate info
	 * @throws RemoteException on remote errors
	 * @throws CommodityException - wrapper of any exceptions within.
	 */
	public ISubLimitType getValue() throws CommodityException, RemoteException;

	/**
	 * Set the cc certificate object
	 * 
	 * @param value - an object of ISubLimitType
	 * @throws RemoteException
	 * @throws ConcurrentUpdateException thrown when more than one client
	 *         accessing the method same time.
	 */
	public void setValue(ISubLimitType value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException, RemoteException;
}
