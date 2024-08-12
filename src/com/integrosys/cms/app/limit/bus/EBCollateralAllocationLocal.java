/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCollateralAllocationLocal.java,v 1.2 2003/07/08 10:14:32 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the remote interface to the EBCollateralAllocation entity bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/08 10:14:32 $ Tag: $Name: $
 */
public interface EBCollateralAllocationLocal extends EJBLocalObject {
	/**
	 * Get an object representation from persistance
	 * 
	 * @return ICollateralAllocation
	 */
	public ICollateralAllocation getValue();

	public void setValue(ICollateralAllocation alloc);

	public String getHostStatus();

	public void delete();
}