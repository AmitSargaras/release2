/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBStockLocal.java,v 1.3 2005/03/16 03:18:02 lini Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Entity bean local interface to the details of a stock.
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/03/16 03:18:02 $ Tag: $Name: $
 */
public interface EBStockLocal extends EJBLocalObject {
	/**
	 * Get the stock business object.
	 * 
	 * @return stock
	 */
	public IStock getValue();

	/**
	 * Set the stock business object.
	 * 
	 * @param stock is of type IStock
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 */
	public void setValue(IStock stock) throws ConcurrentUpdateException;

	/**
	 * Set Status for soft delete of the stock business object.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);
}