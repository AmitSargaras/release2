/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/EBUnitofMeasureLocal.java,v 1.3 2004/08/19 05:12:44 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Local interface for user-defined unit of measure entity bean.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 05:12:44 $ Tag: $Name: $
 */
public interface EBUnitofMeasureLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a UnitofMeasure
	 * 
	 * @return IUnitofMeasure - the object encapsulating the UnitofMeasure info
	 * @throws com.integrosys.cms.app.commodity.main.CommodityException -
	 *         wrapper of any exceptions within.
	 */
	public IUnitofMeasure getValue() throws CommodityException;

	/**
	 * Set the UnitofMeasure object
	 * 
	 * @param value - an object of IUnitofMeasure
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         thrown when more than one client accessing the method same time.
	 */
	public void setValue(IUnitofMeasure value) throws CommodityException, ConcurrentUpdateException,
			VersionMismatchException;

}