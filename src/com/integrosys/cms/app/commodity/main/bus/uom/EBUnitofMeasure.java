/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/EBUnitofMeasure.java,v 1.3 2004/08/19 04:45:13 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Remote interface for user-defined unit of measure entity bean.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 04:45:13 $ Tag: $Name: $
 */
public interface EBUnitofMeasure extends EJBObject {
	/**
	 * Retrieve an instance of a UnitofMeasure
	 * 
	 * @return IUnitofMeasure - the object encapsulating the UnitofMeasure info
	 * @throws java.rmi.RemoteException on remote errors
	 * @throws com.integrosys.cms.app.commodity.main.CommodityException -
	 *         wrapper of any exceptions within.
	 */
	public IUnitofMeasure getValue() throws CommodityException, RemoteException;

	/**
	 * Set the UnitofMeasure object
	 * 
	 * @param value - an object of IUnitofMeasure
	 * @throws java.rmi.RemoteException
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         thrown when more than one client accessing the method same time.
	 */
	public void setValue(IUnitofMeasure value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException, RemoteException;

}