/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBHedgingContractInfoLocal.java,v 1.4 2004/06/09 07:51:13 dayanand Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Defines HedgingContractInfo home methods for clients.
 *
 * @author  $Author: dayanand $<br>
 * @version $Revision: 1.4 $
 * @since   $Date: 2004/06/09 07:51:13 $
 * Tag:     $Name:  $
 */

import javax.ejb.EJBLocalObject;

public interface EBHedgingContractInfoLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a cc certificate
	 * @return IHedgingContractInfo - the object encapsulating the cc
	 *         certificate info
	 */
	public IHedgingContractInfo getValue();

	/**
	 * Set the cc certificate object
	 * @param value - an object of IHedgingContractInfo than one client
	 *        accessing the method same time.
	 */
	public void setValue(IHedgingContractInfo value);

	public void softDelete();

}